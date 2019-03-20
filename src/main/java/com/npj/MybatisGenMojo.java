package com.npj;


import com.npj.gen.clazz.Clazz;
import com.npj.gen.clazz.Column;
import com.npj.jgit.Jgit;
import com.npj.name.strategy.NameStrategy;
import com.npj.name.strategy.NameStrategyFactory;
import com.npj.util.JsonUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.*;
import java.sql.*;
import java.util.*;

import static com.npj.sql.SqlTemplate.ALL_COLUMNS_INFO;
import static com.npj.sql.SqlTemplate.ALL_TABLE_NAME;

@Mojo(
        name = "gen",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.RUNTIME
)
public class MybatisGenMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        checkInputTableList();
        // 如果输入的table是有效的
        List<Clazz> clazzList = getMappingClazzInfo();
        //输出实体
        getLog().info("class is {}" + JsonUtil.toJson(clazzList));
        outEntity(clazzList);
    }

    private void outEntity(List<Clazz> clazzList) throws MojoExecutionException, MojoFailureException {
        Template template = getTemplate("clazz.ftl");
        // 生成时创建分支
        Jgit jgit = Jgit.init(getLog());
        // 新建分支，切换分支，生成代码，提交代码，切换回分支
        jgit.createBranch(branchName)
                .checkout(branchName)
                .execute(() -> {
                    for (Clazz c : clazzList) {
                        Writer out = null;
                        try {
                            out = getEntityOut(c.getClassName() + ".java");
                            template.process(c, out);
                        } catch (MojoExecutionException | TemplateException | IOException e) {
                            getLog().error("输出异常", e);
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                getLog().error("输出异常", e);
                            }
                        }
                    }
                })
                .addAll()
                .commit()
                .checkout(jgit.getOldBranch())
                .close();
    }


    private Template getTemplate(String templateFile) throws MojoExecutionException {

        Configuration config = new Configuration();
        config.setClassForTemplateLoading(MybatisGenMojo.class, "/template");
        try {
            return config.getTemplate("clazz.ftl");
        } catch (IOException e) {
            throw new MojoExecutionException("clazz.ftl异常", e);
        }
    }

    private Writer getEntityOut(String fileName) throws MojoExecutionException {
        String fullEntityPath = basedir.getAbsolutePath() + entityPath;
        File file = new File(fullEntityPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            String finalName = fullEntityPath + File.separator + fileName;
            getLog().info("create file " + finalName);
            return new OutputStreamWriter(new FileOutputStream(finalName));
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException("", e);
        }
    }


    private List<Clazz> getMappingClazzInfo() throws MojoExecutionException {
        NameStrategy nameStrategy = NameStrategyFactory.getNameStrategy(this.nameStrategy);
        Connection conn = getDbConn();
        try {
            List<Clazz> clazzList = new ArrayList<>();
            for (String table : tableMap.keySet()) {
                Clazz clazz = getClazz(nameStrategy, conn, table);
                clazzList.add(clazz);
            }
            return clazzList;
        } catch (SQLException e) {
            throw new MojoExecutionException("获取列信息异常", e);
        } finally {
            closeConn(conn);
        }

    }

    private Clazz getClazz(NameStrategy nameStrategy, Connection conn, String table) throws MojoExecutionException, SQLException {
        Clazz clazz = new Clazz();
        List<Column> columnList = new ArrayList<>();
        Set<String> needImport = new HashSet<>();

        String columnInfoSql = ALL_COLUMNS_INFO.replace("${db}", db).replace("${table}", table);
        ResultSet resultSet = executeQuery(prepareStatement(conn, columnInfoSql));
        while (resultSet.next()) {
            Column column = new Column();
            String jdbcType = resultSet.getString("data_type");
            column.setColumnName(resultSet.getString("column_name"));
            column.setName(nameStrategy.transform(resultSet.getString("column_name")));
            column.setComment(resultSet.getString("column_comment"));
            column.setJdbcType(jdbcType);
            column.setDefaultValue(resultSet.getString("column_default"));
            column.setIsNullable(resultSet.getString("is_nullable"));
            String javaType = TypeMap.jdbcTypeToJavaMap.get(jdbcType);
            getLog().info(jdbcType + "-->" + javaType);
            column.setJavaType(javaType);
            column.setKey("PRI".equalsIgnoreCase(resultSet.getString("column_key")));
            columnList.add(column);
            needImport.add(javaType);
        }
        clazz.setColumns(columnList);
        clazz.setPackageValue(entityPackage);
        clazz.setClassName(tableMap.get(table));
        clazz.setAuthor(author);
        clazz.setNeedImport(needImport);
        clazz.setLombok(lombok);
        return clazz;
    }

    private void checkInputTableList() throws MojoExecutionException {
        Connection conn = getInformationSchemaConn();
        try {
            List<String> dbTables = queryDbTables(
                    executeQuery(
                            prepareStatement(conn, ALL_TABLE_NAME.replace("${db}", db))
                    )
            );
            List<String> list = new ArrayList<>(tableMap.keySet());
            list.removeAll(dbTables);

            if (!list.isEmpty()) {
                throw new MojoExecutionException("未找到table" + Arrays.toString(dbTables.toArray(new String[]{})));
            }
        } finally {
            closeConn(conn);
        }
    }

    private void closeConn(Connection conn) throws MojoExecutionException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new MojoExecutionException("关闭连接失败!", e);
        }
    }

    private List<String> queryDbTables(ResultSet resultSet) throws MojoExecutionException {
        List<String> queryTableList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                queryTableList.add(resultSet.getString("tableName"));
            }
        } catch (SQLException e) {
            throw new MojoExecutionException("获取结果集失败", e);
        }
        return queryTableList;
    }

    private ResultSet executeQuery(PreparedStatement statement) throws MojoExecutionException {
        try {
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new MojoExecutionException("执行查询失败", e);
        }
    }

    private PreparedStatement prepareStatement(Connection connection, String sql) throws MojoExecutionException {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new MojoExecutionException("prepareStatement fail", e);
        }
    }

    private String getInformationSchemaUrl() throws MojoExecutionException {
        if (StringUtils.isEmpty(db)) {
            throw new MojoExecutionException("未配置db参数");
        }
        return URL.replace("${host}", host).replace("${port}", port).replace("${db}", INFORMATION_SCHEMA);
    }

    private String getDbUrl() throws MojoExecutionException {
        if (StringUtils.isEmpty(db)) {
            throw new MojoExecutionException("未配置db参数");
        }
        return URL.replace("${host}", host).replace("${port}", port).replace("${db}", db);
    }

    private Connection getDbConn() throws MojoExecutionException {
        return getConnection(getDbUrl());
    }

    private Connection getInformationSchemaConn() throws MojoExecutionException {
        return getConnection(getInformationSchemaUrl());
    }

    private Connection getConnection(String url) throws MojoExecutionException {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new MojoExecutionException("获取连接失败", e);
        }
    }


    private static final String INFORMATION_SCHEMA = "information_schema";
    private static final String URL = "jdbc:mysql://${host}:${port}/${db}";

    @Parameter(property = "project.build.sourceDirectory")
    private File srcdir;

    @Parameter(property = "basedir")
    private File basedir;

    @Parameter
    private String mapperInterfacePath;

    @Parameter
    private String xmlPath;

    @Parameter
    private String entityPath;

    @Parameter
    private Map<String, String> tableMap;

    @Parameter(defaultValue = "localhost")
    private String host;

    @Parameter(defaultValue = "3306")
    private String port;

    @Parameter
    private String db;

    @Parameter
    private String username;

    @Parameter
    private String password;

    @Parameter
    private String entityPackage;

    @Parameter(defaultValue = "")
    private String author;

    /**
     * 可选值
     * copy
     * underscoreToCamelCase
     */
    @Parameter
    private String nameStrategy;

    /**
     * 强制生成覆盖
     */
    @Parameter(defaultValue = "false")
    private Boolean forceGen;

    /**
     * 是否使用lombok
     */
    @Parameter(defaultValue = "false")
    private Boolean lombok;

    @Parameter(defaultValue = "gen_branch_001_001_001_001")
    private String branchName;

}

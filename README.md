#### 安装
git clone https://github.com/563809169/mgen.git

cd mgen

mvn clean install

#### 配置
~~~xml
<plugin>
    <groupId>com.npj</groupId>
    <artifactId>mgen-maven-plugin</artifactId>
    <version>2.1</version>
    <configuration>
        <host>localhost</host>
        <port>3306</port>
        <username>root</username>
        <password>123456</password>
        <db>test</db>
        <xmlPath>/src/main/resource/sqlmap</xmlPath>
        <mapperPath>/src/main/java/com/npj/mapper</mapperPath>
        <entityPath>/src/main/java/com/npj/entity</entityPath>
        <!--如果强制生成，不创建分支-->
        <forceGen>false</forceGen>
        <entityPackage>com.npj.entity</entityPackage>
        <mapperPackage>com.npj.mapper</mapperPackage>
        <!--可选值 copy 和数据库字段一样  underscoreToCamelCase下划线转驼峰-->
        <nameStrategy>underscoreToCamelCase</nameStrategy>
        <!--生成代码分支名-->
        <branchName>testGen</branchName>
        <tableMap>
            <t_user>User</t_user>
        </tableMap>
    </configuration>
</plugin>
~~~

#### 执行
默认会生成一个分支,会将代码生成到这个分支上,可以用idea的分支比较合并内容。
 
如果想直接覆盖当前分支forceGen指定为true
~~~shell
mvn mgen:gen
~~~
合并完成执行一下命令删除分支
~~~shell
mvn mgen:delBranch
~~~


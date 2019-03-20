package com.npj.gen.clazz;

/**
 * @author pengjie.nan
 * @date 2019-03-11
 */
public class Column {

    /**
     * 是否是主键
     */
    private Boolean key;
    /**
     * 列名
     */
    private String name;
    /**
     * 注释
     */
    private String comment;

    /**
     * 类型
     */
    private String jdbcType;

    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 是否可以为空
     */
    private String isNullable;
    /**
     * java类型
     */
    private String javaType;

    public Boolean getKey() {
        return key;
    }

    public void setKey(Boolean key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}

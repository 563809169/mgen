package com.npj.name.strategy;

/**
 * @author pengjie.nan
 * @date 2019-03-14
 */
public interface NameStrategy {

    /**
     * 不做转换
     */
    String COPY = "copy";

    /**
     * 下划线转驼峰
     */
    String UNDERSCORE_TO_CAMEL_CASE = "underscoreToCamelCase";


    /**
     * 转换操作
     */
    String transform(String dbName);

}

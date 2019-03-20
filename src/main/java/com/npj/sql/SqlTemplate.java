package com.npj.sql;


/**
 * @author pengjie.nan
 * @date 2019-03-09
 */
public interface SqlTemplate {


    String ALL_TABLE_NAME = "select table_name as tableName from tables where table_type='BASE TABLE' and table_schema='${db}'";

    String ALL_COLUMNS_INFO = "select column_key, column_name, column_comment, data_type, column_default, is_nullable from information_schema.columns where table_schema='${db}' and table_name='${table}'";




}

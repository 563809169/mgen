<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org/DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="${mapper.packageValue}.${clazz.className}Mapper">

    <resultMap type="${clazz.packageValue}.${clazz.className}" id="${clazz.className?uncap_first}Map">
        <#list clazz.columns as column>
            <#if column.key == true>
        <id property="${column.name}" jdbcType="${column.jdbcActualType}" column="${column.columnName}"/>
                <#else>
        <result property="${column.name}" jdbcType="${column.jdbcActualType}" column="${column.columnName}"/>
            </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column">
        <#list clazz.columns as column>${clazz.tableName}.${column.columnName}<#if column_has_next>, </#if></#list>
    </sql>

    <sql id="insert_field">
        <#list clazz.columns as column>#@{@itemWaitReplace@${column.name},jdbcType=${column.jdbcActualType}}<#if column_has_next>, </#if></#list>
    </sql>

    <sql id="update_field">
        <#list clazz.columns as column>${column.columnName} = VALUES(${column.columnName})<#if column_has_next>, </#if></#list>
    </sql>

</mapper>
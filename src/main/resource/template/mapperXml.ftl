<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org/DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapper.packageValue}.${clazz.className}Mapper">
    <resultMap type="${clazz.packageValue}.${clazz.className}" id="${clazz.className?uncap_first}Map">
        <#list clazz.columns as column>
            <#if column.key == true>
        <id property="${column.name}" column="${column.columnName}"/>
                <#else>
        <result property="${column.name}" column="${column.columnName}"/>
            </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column">
        ${baseColumn}
    </sql>

</mapper>
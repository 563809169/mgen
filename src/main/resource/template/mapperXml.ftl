<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org/DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapper.packageValue}.${clazz.className}Mapper">
    <resultMap type="${clazz.packageValue}.${clazz.className}" id="${clazz.className?uncap_first}Map">
        <#list columns as column>
            <#if column.key == true>
                <id property="${column.}" jdbcType="INTEGER" column="id"/>
            </#if>
        </#list>

        <result property="hospitalId" jdbcType="INTEGER" column="hospitalId"/>
        <result property="name" jdbcType="VARCHAR" column="name"/>
        <result property="region" jdbcType="VARCHAR" column="region"/>
        <result property="grade" jdbcType="TINYINT" column="grade"/>
        <result property="level" jdbcType="TINYINT" column="level"/>
        <result property="medicine" jdbcType="TINYINT" column="medicine"/>
        <result property="createdTime" jdbcType="TIMESTAMP" column="createdTime"/>
        <result property="status" jdbcType="TINYINT" column="status"/>
        <result property="ownerName" jdbcType="VARCHAR" column="ownerName"/>
        <result property="ownerTel" jdbcType="VARCHAR" column="ownerTel"/>
        <result property="ownerIdCard" jdbcType="VARCHAR" column="ownerIdCard"/>
        <result property="ownerIdCardUrl" jdbcType="VARCHAR" column="ownerIdCardURL"/>
    </resultMap>

    <sql id="Base_Column">
        id, hospitalId, name, region, grade, level, medicine, createdTime,
        status, ownerName, ownerTel, ownerIdCard, ownerIdCardUrl
    </sql>
    <insert id="create" useGeneratedKeys="true" keyProperty="id" keyColumn="id">
        insert into t_hospital
        (hospitalId, name, region, grade, level, medicine, status,
        ownerName, ownerTel, ownerIdCard, ownerIdCardUrl)
        VALUES
        (#{hospitalId}, #{name}, #{region}, #{grade}, #{level}, #{medicine},
        #{status}, #{ownerName}, #{ownerTel}, #{ownerIdCard}, #{ownerIdCardUrl})
    </insert>

    <update id="update">
        update t_hospital
        <set>
            <if test="status != null">
                status = #{status},
            </if>
            <if test="name != null and name != ''">
                name = #{name},
            </if>
            <if test="region !=null and region != ''">
                region = #{region},
            </if>
            <if test="grade !=null">
                grade = #{grade},
            </if>
            <if test="level != null">
                level = #{level},
            </if>
            <if test="ownerName != null and ownerName != ''">
                ownerName = #{ownerName},
            </if>
            <if test="ownerTel != null and ownerTel !=''">
                ownerTel = #{ownerTel},
            </if>
            <if test="ownerIdCard != null and ownerIdCard != ''">
                ownerIdCard = #{ownerIdCard},
            </if>
            <if test="ownerIdCardUrl != null and ownerIdCardUrl != ''">
                ownerIdCardUrl = #{ownerIdCardUrl}
            </if>
        </set>
        where hospitalId = #{hospitalId}
    </update>

    <select id="findHospitalById" resultMap="hospitalMap">
        select
        <include refid="Base_Column"/>
        from t_hospital
        where id = #{id}
    </select>

    <select id="list" resultMap="hospitalMap">
        select
        <include refid="Base_Column"/>
        from t_hospital
        <where>
            <if test="region != null and region != ''">
                and region = #{region}
            </if>
            <if test="hospitalName != null and hospitalName != ''">
                and name like concat(#{hospitalName}, '%')
            </if>
            <if test="status != null">
                and status = #{status}
            </if>
            <if test="hospitalId != null and hospitalId != ''">
                and hospitalId = #{hospitalId}
            </if>
            <if test="level != null">
                and level = #{level}
            </if>
        </where>
        <choose>
            <when test="hospitalId != null and hospitalId != ''">
                limit 1
            </when>
            <otherwise>
                order by createdTime desc
            </otherwise>
        </choose>
    </select>

    <select id="findByIdList" resultMap="hospitalMap">
        select
        id, hospitalId, name
        from t_hospital
        where id in
        <foreach collection="list" item="id" separator="," open="(" close=")">
            #{id}
        </foreach>
    </select>

    <select id="listHospitalEnum" resultMap="hospitalMap">
        select
        id, name
        from t_hospital
    </select>

</mapper>
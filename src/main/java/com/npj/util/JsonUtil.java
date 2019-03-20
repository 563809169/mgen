package com.npj.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author pengjie.nan
 * @date 2019-03-08 14:50
 */
public class JsonUtil {
    /**
     * mapper
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * 将对象转换成json字符串。
     */
    public static String toJson(Object data) {
        if (data == null) {
            return null;
        }
        if (data instanceof String) {
            return (String) data;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return MAPPER.writer(sdf).writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json结果集转化为对象
     */
    public static <T> T parse(String jsonData, Class<T> beanType) {
        try {
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json数据转换成pojo对象list
     */
    public  static <T> List<T> ofList(String jsonData, Class<T> type) {
        if (StringUtils.isEmpty(jsonData)) {
            return null;
        }
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, type);
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json数据转换成pojo对象list
     */
    public  static <K, V> Map<K, V> ofMap(String jsonData, Class<K> kType, Class<V> vType) {
        if (StringUtils.isEmpty(jsonData)) {
            return null;
        }
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(Map.class, kType, vType);
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

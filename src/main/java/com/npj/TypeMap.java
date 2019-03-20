package com.npj;


import com.npj.util.JsonUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * @author pengjie.nan
 * @date 2019-03-11
 * 类型映射
 */
public class TypeMap {

    public static Map<String, String> jdbcTypeToJavaMap;

    static {
        InputStream in = getTypeMapJson();
        byte[] buffer = new byte[512];
        BufferedInputStream bufferedIn = new BufferedInputStream(in);
        int len;
        StringBuilder sb = new StringBuilder();
        try {
            while (((len = bufferedIn.read(buffer)) != -1)) {
                sb.append(new String(buffer, 0, len));
            }
            jdbcTypeToJavaMap = JsonUtil.ofMap(sb.toString(), String.class, String.class);
        } catch (IOException e) {
            throw new RuntimeException("读取数据异常!");
        } finally {
            try {
                bufferedIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static InputStream getTypeMapJson() {
        InputStream in = TypeMap.class.getClassLoader().getResourceAsStream("type.json");
        if (in == null) {
            throw new RuntimeException("读取type.json失败！");
        }
        return in;
    }

}

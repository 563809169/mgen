package com.npj.name.strategy;

import org.apache.commons.lang3.StringUtils;

/**
 * @author pengjie.nan
 * @date 2019-03-14
 */

public class UnderscoreToCamelCase implements NameStrategy {

    @Override
    public String transform(String dbName) {
        if (StringUtils.isEmpty(dbName)) {
            return "";
        }
        // 仅将首字母小写
        if (!dbName.contains("_")) {
            return dbName.substring(0, 1).toLowerCase() + dbName.substring(1);
        }
        StringBuilder result = new StringBuilder();
        // 用下划线将原始字符串分割
        String[] camels = dbName.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();

    }
}

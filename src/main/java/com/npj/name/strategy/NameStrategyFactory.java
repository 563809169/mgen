package com.npj.name.strategy;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @author pengjie.nan
 * @date 2019-03-14
 */
public class NameStrategyFactory {


    public static NameStrategy getNameStrategy(String name) throws MojoExecutionException {
        if (StringUtils.isEmpty(name) || name.equalsIgnoreCase(NameStrategy.UNDERSCORE_TO_CAMEL_CASE)) {
            return new UnderscoreToCamelCase();
        }
        if (name.equalsIgnoreCase(NameStrategy.COPY)) {
            return new CopyStrategy();
        }
        if (name.equalsIgnoreCase(NameStrategy.LOWER_CASE)) {
            return new LowerCaseStrategy();
        }
        throw new MojoExecutionException("不可识别的转换策略");
    }

}

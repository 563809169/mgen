package com.npj.name.strategy;

/**
 * @author pengjie.nan
 * @date 2019-04-11
 */
public class LowerCaseStrategy implements NameStrategy {

    @Override
    public String transform(String dbName) {
        return dbName.replaceAll("_", "").toLowerCase();
    }
}

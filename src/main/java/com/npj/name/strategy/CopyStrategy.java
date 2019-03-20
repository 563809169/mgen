package com.npj.name.strategy;

/**
 * @author pengjie.nan
 * @date 2019-03-14
 */
public class CopyStrategy implements NameStrategy {

    @Override
    public String transform(String dbName) {
        return dbName;
    }
}

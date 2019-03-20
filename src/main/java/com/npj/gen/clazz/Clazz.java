package com.npj.gen.clazz;

import java.util.List;
import java.util.Set;

/**
 * @author pengjie.nan
 * @date 2019-03-11
 */
public class Clazz extends BaseClazz {

    private List<Column> columns;

    private Set<String> needImport;

    private Boolean lombok;

    private String className;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public Set<String> getNeedImport() {
        return needImport;
    }

    public void setNeedImport(Set<String> needImport) {
        this.needImport = needImport;
    }

    public Boolean getLombok() {
        return lombok;
    }

    public void setLombok(Boolean lombok) {
        this.lombok = lombok;
    }
}

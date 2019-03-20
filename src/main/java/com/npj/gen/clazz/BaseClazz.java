package com.npj.gen.clazz;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author pengjie.nan
 * @date 2019-03-20
 */
public abstract class BaseClazz {

    protected String author;

    protected String packageValue;

    protected String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


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
}

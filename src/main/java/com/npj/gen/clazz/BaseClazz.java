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

    protected String className;

}

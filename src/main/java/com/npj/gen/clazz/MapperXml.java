package com.npj.gen.clazz;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pengjie.nan
 * @date 2019-03-20
 */
public class MapperXml extends BaseClazz {

    private Mapper mapper;

    private List<Column> columns;

    /**
     * 基础列
     */
    private String baseColumn;


    private Boolean isGeneratedKey;

    private String keyProperty;

    private String keyColumn;


    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
        this.baseColumn = columns.stream().map(Column::getName).collect(Collectors.joining(", "));
    }

    public String getBaseColumn() {
        return baseColumn;
    }

    public void setBaseColumn(String baseColumn) {
        this.baseColumn = baseColumn;
    }

    public Boolean getGeneratedKey() {
        return isGeneratedKey;
    }

    public void setGeneratedKey(Boolean generatedKey) {
        isGeneratedKey = generatedKey;
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }
}

package com.npj.gen.clazz;

/**
 * @author pengjie.nan
 * @date 2019-03-20
 */
public class MapperXml extends BaseClazz {

    private Mapper mapper;

    private Clazz clazz;

    private Boolean isGeneratedKey;

    private String keyProperty;

    private String keyColumn;


    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
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

package com.xingkaichun.helloworldblockchain.language.parser.struct;

public class StructFeildDescription {

    private String fieldName;
    private String fieldType;
    private boolean array;
    private String position;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isArray() {
        return array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }
}

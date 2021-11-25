package com.xingkaichun.helloworldblockchain.language.parser.struct;

import java.util.ArrayList;
import java.util.List;

public class StructDescription {

    private String structName;

    private List<StructFeildDescription> fileds;

    public StructDescription(String structName) {
        this.structName = structName;
        fileds = new ArrayList<>();
    }


    public StructFeildDescription getFiledByName(String fieldName) {
        for(StructFeildDescription structFeildDescription:fileds){
            if(structFeildDescription.getFieldName().equals(fieldName)){
                return structFeildDescription;
            }
        }
        return null;
    }


    public List<StructFeildDescription> getFileds() {
        return fileds;
    }

    public void setFileds(List<StructFeildDescription> fileds) {
        this.fileds = fileds;
    }

    public String getStructName() {
        return structName;
    }

    public void setStructName(String structName) {
        this.structName = structName;
    }
}

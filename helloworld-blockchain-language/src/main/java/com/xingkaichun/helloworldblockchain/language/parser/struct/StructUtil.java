package com.xingkaichun.helloworldblockchain.language.parser.struct;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructUtil {


    private static Map<String, StructDescription> map = new HashMap<>();



    public static void put(String structName, StructDescription structDescription){
        map.put(structName,structDescription);
    }

    public static boolean isStruct(String structName){
        return map.containsKey(structName);
    }

    public static int getFiledIndex(String structName,String fieldName){
        StructDescription description = map.get(structName);
        List<StructFeildDescription> fields = description.getFileds();
        for(int i=0; i<fields.size(); i++){
            if(fields.get(i).getFieldName().equals(fieldName)){
                return i;
            }
        }
        throw new ParseException();
    }
    public static String getFiledType(String structName,String fieldName){
        StructDescription description = map.get(structName);
        List<StructFeildDescription> fields = description.getFileds();
        for(int i=0; i<fields.size(); i++){
            if(fields.get(i).getFieldName().equals(fieldName)){
                return fields.get(i).getFieldType();
            }
        }
        throw new ParseException();
    }
    public static boolean getFiledIsArray(String structName, String fieldName) {
        StructDescription description = map.get(structName);
        List<StructFeildDescription> fields = description.getFileds();
        for(int i=0; i<fields.size(); i++){
            if(fields.get(i).getFieldName().equals(fieldName)){
                return fields.get(i).isArray();
            }
        }
        throw new ParseException();
    }

    public static int getFiledCount(String structName) {
        StructDescription description = map.get(structName);
        if(description == null){
            throw new ParseException();
        }
        return description.getFileds().size();
    }
}

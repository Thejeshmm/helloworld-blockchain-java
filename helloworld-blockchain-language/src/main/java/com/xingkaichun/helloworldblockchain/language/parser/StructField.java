package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class StructField extends ASTNode{

    private String structFieldType;
    private String structFieldName;
    private boolean array;

    public StructField() {
        super(ASTNodeTypes.STRUCT_FIELD);
    }


    public static StructField parse(TokenReader reader) throws ParseException {
        StructField structField = new StructField();
        structField.structFieldType = reader.next().getValue();
        if(reader.nextIs("[")){
            structField.array = true;
            reader.next("[");
            reader.next("]");
        }
        structField.structFieldName = reader.next().getValue();
        return structField;
    }


    public String getStructFieldType() {
        return structFieldType;
    }

    public String getStructFieldName() {
        return structFieldName;
    }

    public boolean isArray() {
        return array;
    }
}

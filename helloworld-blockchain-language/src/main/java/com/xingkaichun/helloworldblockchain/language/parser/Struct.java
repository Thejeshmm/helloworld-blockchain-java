package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.parser.struct.StructDescription;
import com.xingkaichun.helloworldblockchain.language.parser.struct.StructFeildDescription;
import com.xingkaichun.helloworldblockchain.language.parser.struct.StructUtil;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

import java.util.ArrayList;
import java.util.List;

public class Struct extends ASTNode{

    private String structName;
    private List<StructField> structFields = new ArrayList<>();

    public Struct() {
        super(ASTNodeTypes.STRUCT);
    }


    public static Struct parse(TokenReader reader) throws ParseException {
        Struct struct = new Struct();

        reader.next(TokenType.STRUCT);
        struct.structName = reader.next().getValue();

        reader.next(TokenType.LBRACE);
        while (reader.hasNext() && reader.nextIsNot("}")){
            StructField structField = StructField.parse(reader);
            struct.structFields.add(structField);
        }
        reader.next("}");


        StructDescription description = new StructDescription(struct.structName);
        for(int i=0;i<struct.structFields.size();i++){
            StructFeildDescription structFeildDescription = new StructFeildDescription();
            structFeildDescription.setFieldName(struct.structFields.get(i).getStructFieldName());
            structFeildDescription.setFieldType(struct.structFields.get(i).getStructFieldType());
            structFeildDescription.setArray(struct.structFields.get(i).isArray());
            description.getFileds().add(structFeildDescription);
        }
        StructUtil.put(struct.structName,description);
        return struct;
    }


    public String getStructName(){
        return this.structName;
    }

    public List<StructField> getStructFields(){
        return this.structFields;
    }
}

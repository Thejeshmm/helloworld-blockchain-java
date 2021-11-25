package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class DeclareVariableType extends ASTNode{

    private boolean array;

    public DeclareVariableType() {
        super(ASTNodeTypes.DECLARE_TYPE);
    }


    public static DeclareVariableType parse(TokenReader reader) throws ParseException {
        DeclareVariableType declareVariableType = new DeclareVariableType();
        Token token = reader.next();
        declareVariableType.setLexeme(token);
        while (reader.nextIs("[")){
            declareVariableType.array = true;
            reader.next("[");
            reader.next("]");
        }
        return declareVariableType;
    }


    public boolean isArray() {
        return this.array;
    }

    public String getDeclareTypeName() {
        return this.getLexeme().getValue();
    }

    public Token getToken() {
        return this.getLexeme();
    }
}

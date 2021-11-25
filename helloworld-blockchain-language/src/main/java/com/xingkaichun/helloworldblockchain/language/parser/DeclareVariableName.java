package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class DeclareVariableName extends ASTNode{


    public DeclareVariableName() {
        super(ASTNodeTypes.DECLARE_VARIABLE_NAME);
    }


    public static DeclareVariableName parse(TokenReader reader) throws ParseException {
        DeclareVariableName declareVariableName = new DeclareVariableName();
        Token name = reader.next();
        declareVariableName.setLexeme(name);
        return declareVariableName;
    }

    public String getDeclareVariableName() {
        return this.getLexeme().getValue();
    }

    public Token getToken() {
        return this.getLexeme();
    }
}

package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class FunctionReturn extends ASTNode {

    public FunctionReturn() {
        super(ASTNodeTypes.FUNCTION_RETURN);
    }


    public static FunctionReturn parse(TokenReader reader) throws ParseException {
        FunctionReturn functionReturn = new FunctionReturn();
        Token token = reader.next();
        functionReturn.setLexeme(token);
        return functionReturn;
    }

    public String getReturnType(){
        return this.lexeme.getValue();
    }

}

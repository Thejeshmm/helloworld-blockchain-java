package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class ReturnStatement extends Statement {

    public ReturnStatement() {
        super(ASTNodeTypes.RETURN_STATEMENT);
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        Token lexeme = reader.next("return");
        ReturnVariables returnVariables = ReturnVariables.parse(reader);

        ReturnStatement returnStatement = new ReturnStatement();
        returnStatement.setLexeme(lexeme);
        returnStatement.addChild(returnVariables);
        return returnStatement;
    }

    public ReturnVariables getReturnVariables() {
        return (ReturnVariables)this.getChild(0);
    }

}

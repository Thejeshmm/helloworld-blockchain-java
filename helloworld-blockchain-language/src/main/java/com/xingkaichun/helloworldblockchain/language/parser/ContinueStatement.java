package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class ContinueStatement extends Statement {

    public ContinueStatement() {
        super(ASTNodeTypes.CONTINUE_STATEMENT);
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        Token lexeme = reader.next(TokenType.CONTINUE);
        ContinueStatement continueStatement = new ContinueStatement();
        continueStatement.setLexeme(lexeme);
        return continueStatement;
    }

}

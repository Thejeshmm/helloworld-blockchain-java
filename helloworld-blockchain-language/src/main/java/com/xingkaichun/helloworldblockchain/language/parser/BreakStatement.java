package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class BreakStatement extends Statement {

    public BreakStatement() {
        super(ASTNodeTypes.BREAK_STATEMENT);
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        Token lexeme = reader.next(TokenType.BREAK);
        BreakStatement breakStatement = new BreakStatement();
        breakStatement.setLexeme(lexeme);
        return breakStatement;
    }

}

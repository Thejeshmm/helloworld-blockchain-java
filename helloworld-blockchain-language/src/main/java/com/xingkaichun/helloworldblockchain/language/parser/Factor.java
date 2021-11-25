package com.xingkaichun.helloworldblockchain.language.parser;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class Factor extends Expression {

    public Factor(Token token) {
        super();
        this.lexeme = token;
    }


    public static Factor parse(TokenReader reader) {
        Token token = reader.peek();
        TokenType type = token.getTokenType();

        if(type == TokenType.VARIABLE) {
            reader.next();
            return new Variable(token);
        } else if(token.isBaseType()){
            reader.next();
            return new Scalar(token);
        }
        return null;
    }
}

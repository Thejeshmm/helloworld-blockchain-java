package com.xingkaichun.helloworldblockchain.language.lexer;

import com.xingkaichun.helloworldblockchain.language.parser.struct.StructUtil;

/**
 * @author xingkaichun@ceair.com
 */
public class Token {

    private TokenType tokenType;
    private String value;

    public Token(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public boolean isBaseType() {
        return tokenType == TokenType.INT ||
               tokenType == TokenType.CHAR ||
               tokenType == TokenType.BOOLEAN;
    }

    public boolean valueIsBaseType() {
        return TokenType.INT.getName().equals(value) ||
               TokenType.CHAR.getName().equals(value) ||
               TokenType.BOOLEAN.getName().equals(value) ;
    }

    public boolean valueIsType() {
        return TokenType.INT.getName().equals(value) ||
               TokenType.CHAR.getName().equals(value) ||
               TokenType.BOOLEAN.getName().equals(value) ||
               StructUtil.isStruct(value);
    }




    public TokenType getTokenType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", value='" + value + '\'' +
                '}';
    }
}

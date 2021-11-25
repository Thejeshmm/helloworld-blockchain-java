package com.xingkaichun.helloworldblockchain.language.util;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;

import java.util.List;

/**
 * @author xingkaichun@ceair.com
 */
public class TokenReader extends Reader<Token> {

    public TokenReader(Token[] tokens) {
        super(tokens);
    }

    public TokenReader(List<Token> tokens) {
        super(convert(tokens));
    }

    private static Token[] convert(List<Token> tokens) {
        Token[] temp = new Token[tokens.size()];
        for(int i=0;i<tokens.size();i++){
            temp[i]=tokens.get(i);
        }
        return temp;
    }

    public boolean nextIs(String value) {
        if(!hasNext()){
            return false;
        }
        Token token = this.peek();
        return token.getValue().equals(value);
    }
    public boolean nextIsNot(String value) {
        if(!hasNext()){
            return false;
        }
        Token token = this.peek();
        return !token.getValue().equals(value);
    }
    public Token next(String value) throws ParseException {
        Token token = this.next();
        if(!token.getValue().equals(value)) {
            throw new ParseException();
        }
        return token;
    }

    public Token next(TokenType type) throws ParseException {
        Token token = this.next();
        if(!token.getTokenType().equals(type)) {
            throw new ParseException();
        }
        return token;
    }
    public boolean nextIs(TokenType type) throws ParseException {
        Token token = this.peek();
        return token.getTokenType().equals(type);
    }

    public boolean nextNextIs(String value) {
        if(!hasNextNext()){
            return false;
        }
        Token token = this.peekPeek();
        return token.getValue().equals(value);
    }
    public boolean nextNextNextIs(String value) {
        if(!hasNextNextNext()){
            return false;
        }
        Token token = this.peekPeekPeek();
        return token.getValue().equals(value);
    }
    public boolean nextIsType() {
        if(!hasNext()){
            return false;
        }
        Token token = this.peek();
        return token.valueIsType();
    }
    public boolean backIs(String value) throws ParseException {
        if(!hasBack()){
            return false;
        }
        return review().getValue().equals(value);
    }
}

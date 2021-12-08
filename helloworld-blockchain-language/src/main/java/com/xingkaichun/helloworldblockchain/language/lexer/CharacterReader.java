package com.xingkaichun.helloworldblockchain.language.lexer;

import com.xingkaichun.helloworldblockchain.language.util.FileUtil;
import com.xingkaichun.helloworldblockchain.language.util.LexerException;
import com.xingkaichun.helloworldblockchain.language.util.Reader;

/**
 * @author xingkaichun@ceair.com
 */
public class CharacterReader extends Reader<String> {


    public CharacterReader(String[] values) {
        super(values);
    }


    private void next(String c) {
        next().equals(c);
    }
    public boolean nextIs(String value) {
        if(!hasNext()){
            return false;
        }
        return peek().equals(value);
    }
    public boolean nextNextIs(String value) {
        if(!hasNextNext()){
            return false;
        }
        return peekPeek().equals(value);
    }
    public boolean nextIsNumber() {
        if(!hasNext()){
            return false;
        }
        return "0123456789".contains(peek());
    }
    public boolean nextIsBracket() {
        if(!hasNext()){
            return false;
        }
        return "[]{}()".contains(peek());
    }
    public boolean nextIsAlphabet() {
        if(!hasNext()){
            return false;
        }
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".contains(peek());
    }
    public boolean nextIsOperator() {
        if(!hasNext()){
            return false;
        }
        return "+-*/<>=!&|^()%".contains(peek());
    }
    public boolean nextIsLetter() {
        if(!hasNext()){
            return false;
        }
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".contains(peek());
    }

    public static CharacterReader fromFile(String sourceCodePath) {
        String text = FileUtil.readFileContent(sourceCodePath);
        int length = text.length();
        String[] values = new String[length];
        for(int i=0; i<length; i++){
            values[i] = text.substring(i,i+1);
        }
        return new CharacterReader(values);
    }


    public Token readStringToken() {
        String s = "";
        int state = 0;

        while(hasNext()) {
            String c = next();
            switch (state) {
                case 0:
                    if("\"".equals(c)) {
                        state = 1;
                    }
                    break;
                case 1:
                    if("\"".equals(c)) {
                        return new Token(TokenType.STRING, s);
                    }
                    else {
                        s += c;
                    }
                    break;
            }
        }
        throw new LexerException();
    }

    public Token readCharToken() {
        next("'");
        Token token;
        if(nextIs("\\") && nextNextIs("n")){
            next();
            next();
            token = new Token(TokenType.CHAR, "\n");
        }else {
            token = new Token(TokenType.CHAR, next());
        }
        next("'");
        return token;
    }

    public String readBlockToken() {
        String s = "";
        while(hasNext()) {
            if(nextIsLetter()) {
                s += next();
            } else {
                break;
            }
        }
        return  s;
    }

    public Token readNumberToken() {
        String s = "";
        while(hasNext()) {
            if(nextIsNumber()) {
                s += next();
            } else {
                break;
            }
        }
        return new Token(TokenType.INT, s);
    }

    public Token readOperatorToken() {
        if(nextIs("+")){
            return new Token(TokenType.ADD, next());
        }
        if(nextIs("-")){
            return new Token(TokenType.SUB, next());
        }
        if(nextIs("*")){
            return new Token(TokenType.MUL, next());
        }
        if(nextIs("/")){
            return new Token(TokenType.DIV, next());
        }
        if(nextIs("%")){
            return new Token(TokenType.MOD, next());
        }
        if(nextIs("=")){
            if(nextNextIs("=")){
                return new Token(TokenType.EQ, next()+next());
            }else {
                return new Token(TokenType.ASSIGN, next());
            }
        }
        if(nextIs("<")){
            if(nextNextIs("=")){
                return new Token(TokenType.LE, next()+next());
            }else {
                return new Token(TokenType.LT, next());
            }
        }
        if(nextIs(">")){
            if(nextNextIs("=")){
                return new Token(TokenType.GE, next()+next());
            }else {
                return new Token(TokenType.GT, next());
            }
        }
        if(nextIs("!")){
            if(nextNextIs("=")){
                return new Token(TokenType.NE, next()+next());
            }else {
                return new Token(TokenType.NOT, next());
            }
        }
        if(nextIs("|") && nextNextIs("|")){
            return new Token(TokenType.OR, next()+next());
        }
        if(nextIs("&") && nextNextIs("&")){
            return new Token(TokenType.AND, next()+next());
        }
        throw new LexerException();
    }

    public Token readBracketToken() {
        String next = next();
        if(TokenType.LPAREN.getName().equals(next)){
            return new Token(TokenType.LPAREN, next);
        }else if(TokenType.RPAREN.getName().equals(next)){
            return new Token(TokenType.RPAREN, next);
        }else if(TokenType.LBRACKET.getName().equals(next)){
            return new Token(TokenType.LBRACKET, next);
        }else if(TokenType.RBRACKET.getName().equals(next)){
            return new Token(TokenType.RBRACKET, next);
        }else if(TokenType.LBRACE.getName().equals(next)){
            return new Token(TokenType.LBRACE, next);
        }else if(TokenType.RBRACE.getName().equals(next)){
            return new Token(TokenType.RBRACE, next);
        }
        throw new LexerException();
    }
}

package com.xingkaichun.helloworldblockchain.language.lexer;

import com.xingkaichun.helloworldblockchain.language.util.LexerException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingkaichun@ceair.com
 */
public class Lexer {


    public static TokenReader fromFile(String sourceCodePath) throws LexerException {
        CharacterReader reader = CharacterReader.fromFile(sourceCodePath);

        List<Token> tokens = new ArrayList<>();

        while (reader.hasNext()){

            //跳过空格
            if(reader.nextIs(" ")){
                reader.next();
                continue;
            }

            //跳过换行符
            if(reader.nextIs("\r") || reader.nextIs("\n") || reader.nextIs("\t")){
                reader.next();
                continue;
            }

            //跳过注释
            if(reader.nextIs("/") && reader.nextNextIs("/")){
                while(reader.hasNext()) {
                    //遇到换行符，表示注释处理完毕
                    if(reader.nextIs("\r") || reader.nextIs("\n")){
                        break;
                    }
                    reader.next();
                }
                continue;
            }

            //根据首字母判断token类型
            //作用域
            if(reader.nextIsBracket()){
                tokens.add(reader.readBracketToken());
                continue;
            }

            //逗号分隔符
            if(reader.nextIs(",")){
                tokens.add(new Token(TokenType.COMMA, reader.next()));
                continue;
            }
            //点分隔符
            if(reader.nextIs(".")){
                tokens.add(new Token(TokenType.PERIOD, reader.next()));
                continue;
            }
            if(reader.nextIs(";")){
                tokens.add(new Token(TokenType.SEMICOLON, reader.next()));
                continue;
            }
            //字符串
            if(reader.nextIs("\"")){
                tokens.add(reader.readStringToken());
                continue;
            }
            //字符
            if(reader.nextIs("'")){
                tokens.add(reader.readCharToken());
                continue;
            }
            //数字
            if(reader.nextIsNumber()) {
                tokens.add(reader.readNumberToken());
                continue;
            }

            //操作符
            if(reader.nextIsOperator()) {
                tokens.add(reader.readOperatorToken());
                continue;
            }

            //字母
            if(reader.nextIsAlphabet()) {
                String block = reader.readBlockToken();
                if(TokenType.isKeyword(block)) {
                    TokenType tokenType = TokenType.getKeyword(block);
                    tokens.add(new Token(tokenType, block));
                    continue;
                }else if(TokenType.isBoolean(block)) {
                    tokens.add(new Token(TokenType.BOOLEAN, block));
                    continue;
                }else if(TokenType.isInt(block)) {
                    tokens.add(new Token(TokenType.INT, block));
                    continue;
                }else if(TokenType.isNew(block)) {
                    tokens.add(new Token(TokenType.NEW, block));
                    continue;
                }else if(TokenType.isVariable(block)){
                    tokens.add(new Token(TokenType.VARIABLE, block));
                    continue;
                }
                throw new LexerException();
            }

            throw new RuntimeException();
        }

        return new TokenReader(tokens);
    }
}

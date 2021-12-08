package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class IfStatement extends Statement {

    public IfStatement() {
        super(ASTNodeTypes.IF_STATEMENT);
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        return parseIf(reader);
    }

    public static ASTNode parseIf(TokenReader reader) throws ParseException {
        Token lexeme = reader.next("if");
        IfStatement ifStatement = new IfStatement();
        ifStatement.setLexeme(lexeme);
        reader.next("(");
        ASTNode condition = Expression.parse(reader);
        ifStatement.addChild(condition);
        reader.next(")");

        IfBlock body = IfBlock.parse(reader);
        ifStatement.addChild(body);

        ASTNode tail = parseTail(reader);
        if(tail != null) {
            ifStatement.addChild(tail);
        }
        return ifStatement;
    }

    public static ASTNode parseTail(TokenReader reader) throws ParseException {
        if(!reader.nextIs("else")) {
            return null;
        }
        reader.next("else");
        if(reader.nextIs("{")) {
            return IfTailBlock.parse(reader);
        } else if(reader.nextIs("if")){
            return parseIf(reader);
        } else {
            throw new ParseException();
        }
    }

    public ASTNode getCondition() {
        return this.getChild(0);
    }

    public ASTNode getBody(){
        return this.getChild(1);
    }

    public ASTNode getElseBody(){
        ASTNode block = this.getChild(2);
        if(block instanceof IfTailBlock) {
            return block;
        }
        return null;
    }

    public ASTNode getElseIfStatement(){
        ASTNode ifStatement = this.getChild(2);
        if(ifStatement instanceof IfStatement) {
            return ifStatement;
        }
        return null;
    }

    public boolean hasTail(){
        return this.getChildren().size() >= 2;
    }
}

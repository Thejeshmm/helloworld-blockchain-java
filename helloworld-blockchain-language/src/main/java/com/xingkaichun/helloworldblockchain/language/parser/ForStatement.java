package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.parser.expression.CallExpression;
import com.xingkaichun.helloworldblockchain.language.parser.expression.EmptyExpression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class ForStatement extends Statement {

    private ASTNode initialization;
    private ASTNode termination;
    private ASTNode update;
    private ASTNode body;

    public ForStatement() {
        super(ASTNodeTypes.FOR_STATEMENT);
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        return parseIF(reader);
    }

    public static ASTNode parseIF(TokenReader reader) throws ParseException {
        Token lexeme = reader.next("for");
        ForStatement forStatement = new ForStatement();
        forStatement.setLexeme(lexeme);
        reader.next("(");
        if(CallExpression.nextIsCallExpression(reader)) {
            forStatement.initialization = Expression.parse(reader);
        }else if(Statement.nextIsAssignStatement(reader)){
            forStatement.initialization = AssignStatement.parse(reader);
        }else if(Statement.nextIsDeclareStatement(reader)){
            forStatement.initialization = DeclareStatement.parse(reader);
        }else if(reader.nextIs(";")){
            forStatement.initialization = EmptyExpression.parse(reader);
        }else {
            throw new ParseException();
        }
        reader.next(";");
        if(CallExpression.nextIsCallExpression(reader)) {
            forStatement.termination = Expression.parse(reader);
        }else if(Statement.nextIsAssignStatement(reader)){
            throw new ParseException();
        }else if(Statement.nextIsDeclareStatement(reader)){
            throw new ParseException();
        }else if(reader.nextIs(";")){
            forStatement.termination = EmptyExpression.parse(reader);
        }else {
            forStatement.termination = Expression.parse(reader);
        }
        reader.next(";");
        if(CallExpression.nextIsCallExpression(reader)) {
            forStatement.update = Expression.parse(reader);
        }else if(Statement.nextIsAssignStatement(reader)){
            forStatement.update = AssignStatement.parse(reader);
        }else if(Statement.nextIsDeclareStatement(reader)){
            throw new ParseException();
        }else if(reader.nextIs(")")){
            forStatement.update = EmptyExpression.parse(reader);
        }else {
            throw new ParseException();
        }
        reader.next(")");
        forStatement.body = ForBlock.parse(reader);
        return forStatement;
    }

    public ASTNode getInitialization() {
        return initialization;
    }

    public ASTNode getTermination() {
        return termination;
    }

    public ASTNode getUpdate() {
        return update;
    }

    public ASTNode getBody() {
        return body;
    }
}

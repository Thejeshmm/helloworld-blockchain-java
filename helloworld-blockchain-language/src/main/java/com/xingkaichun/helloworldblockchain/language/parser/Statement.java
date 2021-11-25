package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.parser.expression.CallExpression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;


public abstract class Statement extends ASTNode{

    public Statement(ASTNodeTypes type) {
        super(type);
    }


    public static ASTNode parseStmt(TokenReader reader) throws ParseException {
        if(reader.nextIs(TokenType.RETURN)) {
            //return..
            return ReturnStatement.parse(reader);
        }else if(reader.nextIs(TokenType.IF)) {
            return IfStatement.parse(reader);
        }else if(reader.nextIs(TokenType.FOR)) {
            return ForStatement.parse(reader);
        }else if(reader.nextIs(TokenType.BREAK)) {
            return BreakStatement.parse(reader);
        }else if(reader.nextIs(TokenType.CONTINUE)) {
            //for(..)
            return ContinueStatement.parse(reader);
        }else if(CallExpression.nextIsCallExpression(reader)) {
            //function_name(..)..
            return CallExpression.parse(reader);
        }else if(nextIsAssignStatement(reader)) {
            //variable_name[,variable_name]=..
            //variable_name=(variable_name|array_variable_name([index]+))(.)()
            return AssignStatement.parse(reader);
        }else if(nextIsDeclareStatement(reader)) {
            //variable_type variable_name[,variable_type variable_name]..
            return DeclareStatement.parse(reader);
        }
        return null;
    }

    public static ASTNode parseIfBlockStmt(TokenReader reader) throws ParseException {
        if(reader.nextIs(TokenType.RETURN)) {
            //return..
            return ReturnStatement.parse(reader);
        }else if(reader.nextIs(TokenType.IF)) {
            return IfStatement.parse(reader);
        }else if(reader.nextIs(TokenType.FOR)) {
            return ForStatement.parse(reader);
        }else if(reader.nextIs(TokenType.BREAK)) {
            throw new ParseException();
        }else if(reader.nextIs(TokenType.CONTINUE)) {
            throw new ParseException();
        }else if(CallExpression.nextIsCallExpression(reader)) {
            //function_name(..)..
            return CallExpression.parse(reader);
        }else if(nextIsAssignStatement(reader)) {
            //variable_name[,variable_name]=..
            //variable_name=(variable_name|array_variable_name([index]+))(.)()
            return AssignStatement.parse(reader);
        }else if(nextIsDeclareStatement(reader)) {
            //variable_type variable_name[,variable_type variable_name]..
            return DeclareStatement.parse(reader);
        }
        return null;
    }

    public static ASTNode parseIfTailBlockStmt(TokenReader reader) throws ParseException {
        if(reader.nextIs(TokenType.RETURN)) {
            //return..
            return ReturnStatement.parse(reader);
        }else if(reader.nextIs(TokenType.IF)) {
            return IfStatement.parse(reader);
        }else if(reader.nextIs(TokenType.FOR)) {
            return ForStatement.parse(reader);
        }else if(reader.nextIs(TokenType.BREAK)) {
            throw new ParseException();
        }else if(reader.nextIs(TokenType.CONTINUE)) {
            throw new ParseException();
        }else if(CallExpression.nextIsCallExpression(reader)) {
            //function_name(..)..
            return CallExpression.parse(reader);
        }else if(nextIsAssignStatement(reader)) {
            //variable_name[,variable_name]=..
            //variable_name=(variable_name|array_variable_name([index]+))(.)()
            return AssignStatement.parse(reader);
        }else if(nextIsDeclareStatement(reader)) {
            //variable_type variable_name[,variable_type variable_name]..
            return DeclareStatement.parse(reader);
        }
        return null;
    }

    public static ASTNode parseForBlockStmt(TokenReader reader) throws ParseException {
        if(reader.nextIs(TokenType.RETURN)) {
            //return..
            return ReturnStatement.parse(reader);
        }else if(reader.nextIs(TokenType.IF)) {
            //if(..)
            return IfStatement.parse(reader);
        }else if(reader.nextIs(TokenType.FOR)) {
            //for(..)
            return ForStatement.parse(reader);
        }else if(reader.nextIs(TokenType.BREAK)) {
            return BreakStatement.parse(reader);
        }else if(reader.nextIs(TokenType.CONTINUE)) {
            return ContinueStatement.parse(reader);
        }else if(CallExpression.nextIsCallExpression(reader)) {
            //function_name(..)..
            return CallExpression.parse(reader);
        }else if(nextIsAssignStatement(reader)) {
            //variable_name[,variable_name]=..
            //variable_name=(variable_name|array_variable_name([index]+))(.)()
            return AssignStatement.parse(reader);
        }else if(nextIsDeclareStatement(reader)) {
            //variable_type variable_name[,variable_type variable_name]..
            return DeclareStatement.parse(reader);
        }
        return null;
    }

    public static ASTNode parseFunctionBlockStmt(TokenReader reader) throws ParseException {
        if(reader.nextIs(TokenType.RETURN)) {
            //return..
            return ReturnStatement.parse(reader);
        }else if(reader.nextIs(TokenType.IF)) {
            //if(..)
            return IfStatement.parse(reader);
        }else if(reader.nextIs(TokenType.FOR)) {
            //for(..)
            return ForStatement.parse(reader);
        }else if(reader.nextIs(TokenType.BREAK)) {
            throw new ParseException();
        }else if(reader.nextIs(TokenType.CONTINUE)) {
            throw new ParseException();
        }else if(CallExpression.nextIsCallExpression(reader)) {
            //function_name(..)..
            return CallExpression.parse(reader);
        }else if(nextIsAssignStatement(reader)) {
            //variable_name[,variable_name]=..
            //variable_name=(variable_name|array_variable_name([index]+))(.)()
            return AssignStatement.parse(reader);
        }else if(nextIsDeclareStatement(reader)) {
            //variable_type variable_name[,variable_type variable_name]..
            return DeclareStatement.parse(reader);
        }
        return null;
    }

    public static boolean nextIsDeclareStatement(TokenReader reader) {
        return reader.nextIsType();
    }

    public static boolean nextIsAssignStatement(TokenReader reader) {
        Boolean isAssignStatement = null;
        int nextCount = 0;

        boolean firstLoop = true;
        while (firstLoop || reader.nextIs(".")){
            if(!firstLoop){
                if(reader.nextIs(".")){
                    reader.next(".");
                    nextCount++;
                }else {
                    isAssignStatement = false;
                }
            }
            reader.next();
            nextCount++;
            while (reader.nextIs("[")){
                reader.next("[");
                nextCount++;
                reader.next();
                nextCount++;
                if(!reader.nextIs("]")){
                    isAssignStatement = false;
                    break;
                }else {
                    reader.next();
                    nextCount++;
                }
            }
            if(isAssignStatement != null){
                break;
            }
            firstLoop = false;
        }

        if(isAssignStatement == null){
            if(reader.nextIs(",") || reader.nextIs("=")){
                isAssignStatement = true;
            }else {
                isAssignStatement = false;
            }
        }

        for(int i=0;i<nextCount;i++){
            reader.back();
        }
        return isAssignStatement;
    }

}

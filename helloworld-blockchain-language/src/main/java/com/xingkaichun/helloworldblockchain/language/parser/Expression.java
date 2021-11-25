package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.parser.expression.*;
import com.xingkaichun.helloworldblockchain.language.util.ExpressionHOF;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.PriorityTable;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class Expression extends ASTNode {

    private static PriorityTable table = new PriorityTable();

    public Expression() {
        super();
    }

    public Expression(ASTNodeTypes type, Token lexeme) {
        super();
        this.type = type;
        this.lexeme = lexeme;
    }

    // left:E(k) -> E(k) op(k) E(k+1) | E(k+1)
    // right:
    //    E(k) -> E(k+1) E_(k)
    //       var e = new Expression(); e.left = E(k+1); e.op = op(k); e.right = E(k+1) E_(k)
    //    E_(k) -> op(k) E(k+1) E_(k) | ε
    // 最高优先级处理:
    // left:E(t) -> E(t) op(t) E(t) | F | U
    //    E(t) -> F E_(k) | U E_(k)
    //    E_(t) -> op(t) E(t) E_(t) | ε

    private static ASTNode E(int k, TokenReader reader) throws ParseException {
        if(k < table.level() - 1) {
            return combine( reader, () -> E( k+1, reader), () -> E_(k, reader));
        } else {
            return race(
                    reader,
                    () -> combine( reader, () -> F(reader), () -> E_( k, reader)),
                    () -> combine( reader, () -> U(reader), () -> E_( k, reader))
            );
        }
    }

    private static ASTNode E_(int k, TokenReader reader) throws ParseException {
        Token token = reader.peek();
        String value = token.getValue();

        if(table.get(k).indexOf(value) != -1) {
            Expression expr = new Expression(ASTNodeTypes.BINARY_EXPR, reader.next(value));
            expr.addChild(combine(reader, () -> E(k+1, reader), () -> E_(k, reader) ));
            return expr;
        }
        return null;
    }

    private static ASTNode U(TokenReader reader) throws ParseException {
        Token token = reader.peek();
        String value = token.getValue();

        if(value.equals("(")) {
            reader.next("(");
            ASTNode expr = E(0, reader);
            reader.next(")");
            return expr;
        }
        else if (value.equals("++") || value.equals("--") || value.equals("!")) {
            Token t = reader.peek();
            reader.next(value);
            Expression unaryExpr = new Expression(ASTNodeTypes.UNARY_EXPR, t);
            unaryExpr.addChild(E(0, reader));
            return unaryExpr;
        }
        return null;
    }


    private static ASTNode F(TokenReader reader) throws ParseException {
        if(!reader.hasNext()){
            return null;
        }
        //can not arithmetic symbol TODO
        if(reader.nextIs("!")){
            return null;
        }
        if(reader.nextIs(TokenType.NULL)){
            return NullExpression.parse(reader);
        }else if(reader.nextIs(TokenType.NEW)){
            if(reader.nextNextNextIs("[")){
                return NewArrayExpression.parse(reader);
            }else if(reader.nextNextNextIs("(")){
                return NewObjectExpression.parse(reader);
            }else {
                throw new ParseException();
            }
        }else if(reader.nextIs(TokenType.STRING)){
            return StaticStringExpression.parse(reader);
        }else if(CallExpression.nextIsCallExpression(reader)){
            return CallExpression.parse(reader);
        }else if(reader.nextNextIs(".") || reader.nextNextIs("[")){
            return ComplexVariableExpression.parse(reader);
        }else if(reader.backIs("(") && reader.peek().valueIsBaseType() && reader.nextNextIs(")")){
            return VariableExpression.parse(reader);
        }else if(reader.peek().isBaseType()){
            return ScalarExpression.parse(reader);
        }else if(reader.hasNext()){
            return VariableExpression.parse(reader);
        }
        return null;
    }

    private static ASTNode combine(TokenReader reader, ExpressionHOF hof1, ExpressionHOF hof2) throws ParseException {
        ASTNode a = hof1.hoc();
        if(a == null) {
            return reader.hasNext() ? hof2.hoc() : null;
        }
        ASTNode b = reader.hasNext() ? hof2.hoc() : null;
        if(b == null) {
            return a;
        }

        Expression expr = new Expression(ASTNodeTypes.BINARY_EXPR, b.lexeme);
        expr.addChild(a);
        expr.addChild(b.getChild(0));
        return expr;

    }

    private static ASTNode race(TokenReader reader, ExpressionHOF hof1, ExpressionHOF hof2) throws ParseException {
        if(!reader.hasNext()) {
            return null;
        }
        ASTNode a = hof1.hoc();
        if(a != null) {
            return a;
        }
        return hof2.hoc();
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        return E(0, reader);
    }
}

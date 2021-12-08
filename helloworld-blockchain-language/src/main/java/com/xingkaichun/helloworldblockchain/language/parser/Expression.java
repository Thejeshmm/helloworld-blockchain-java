package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.parser.expression.*;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.PriorityTable;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

import java.util.ArrayList;
import java.util.List;

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

    private static ASTNode U(TokenReader reader) throws ParseException {
        Token token = reader.peek();
        String value = token.getValue();

        if(value.equals("(")) {
            reader.next("(");
            ASTNode expr = level(0, reader);
            reader.next(")");
            return expr;
        }
        else if (value.equals("++") || value.equals("--") || value.equals("!")) {
            Token t = reader.peek();
            reader.next(value);
            Expression unaryExpr = new Expression(ASTNodeTypes.UNARY_EXPR, t);
            unaryExpr.addChild(level(0, reader));
            return unaryExpr;
        }
        return null;
    }


    private static ASTNode F(TokenReader reader) throws ParseException {
        if(!reader.hasNext()){
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
        }else if(reader.nextIs(TokenType.VARIABLE)){
            return ComplexVariableExpression.parse(reader);
        }
        return null;
    }

    public static ASTNode parse(TokenReader reader) throws ParseException {
        return level(0, reader);
    }

    private static ASTNode level(int k, TokenReader reader) {
        if(k > table.level() - 1){
            return null;
        }
        List<ASTNode> level0AstNodes = new ArrayList<>();
        List<Token> opLexemes = new ArrayList<>();
        if(k < table.level() - 1) {
            while (true){
                ASTNode ka1ASTNode = level(k+1,reader);
                if(ka1ASTNode == null){
                    break;
                }else {
                    level0AstNodes.add(ka1ASTNode);
                    if(reader.hasNext()){
                        if(table.get(k).indexOf(reader.peek().getValue()) != -1) {
                            opLexemes.add(reader.next());
                        }else {
                            break;
                        }
                    }
                }
            }
        } else {
            boolean init = true;
            while (true){
                if(!init){
                    if(table.get(k).indexOf(reader.peek().getValue()) == -1) {
                        break;
                    }else {
                        opLexemes.add(reader.next());
                    }
                }
                init = false;
                ASTNode u = U(reader);
                if(u != null){
                    level0AstNodes.add(u);
                    continue;
                }
                ASTNode f = F(reader);
                if(f != null){
                    level0AstNodes.add(f);
                    continue;
                }
                break;
            }
        }
        if(level0AstNodes.isEmpty()){
            return null;
        }
        ASTNode returnAstNode = null;
        for(int i=0;i<level0AstNodes.size();i++){
            if(i == 0){
                returnAstNode = level0AstNodes.get(i);
            }else {
                Expression expr = new Expression(ASTNodeTypes.BINARY_EXPR, opLexemes.get(i-1));
                expr.addChild(returnAstNode);
                expr.addChild(level0AstNodes.get(i));
                returnAstNode = expr;
            }
        }
        return returnAstNode;
    }

}

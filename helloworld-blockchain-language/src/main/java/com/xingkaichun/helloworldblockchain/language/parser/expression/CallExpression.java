package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.parser.Expression;
import com.xingkaichun.helloworldblockchain.language.parser.Factor;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

import java.util.ArrayList;
import java.util.List;

public class CallExpression extends Expression {

    public CallExpression() {
        super();
        this.type = ASTNodeTypes.CALL_EXPRESSION;
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        CallExpression callExpression = new CallExpression();
        Factor factor = Factor.parse(reader);
        callExpression.addChild(factor);
        reader.next("(");
        if(!reader.nextIs(")")) {
            ASTNode parameter;
            while((parameter = Expression.parse(reader)) != null) {
                callExpression.addChild(parameter);
                if(reader.nextIs(")")) {
                    break;
                }
                reader.next(",");
            }
        }
        reader.next(")");
        return callExpression;
    }

    public static boolean nextIsCallExpression(TokenReader reader) {
        if(!reader.hasNextNext()){
            return false;
        }
        return reader.peekPeek().getValue().equals("(");
    }

    public String getFunctionName() {
        List<ASTNode> childrens = getChildren();
        return childrens.get(0).getLexeme().getValue();
    }

    public List<ASTNode> getParameters() {
        List<ASTNode> parameters = new ArrayList<>();
        List<ASTNode> childrens = getChildren();
        for(int i=1;i<childrens.size();i++){
            parameters.add(childrens.get(i));
        }
        return parameters;
    }

}

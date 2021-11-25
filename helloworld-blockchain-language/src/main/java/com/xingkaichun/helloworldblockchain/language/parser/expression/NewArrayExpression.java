package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.parser.Expression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

import java.util.ArrayList;
import java.util.List;

public class NewArrayExpression extends Expression {

    private List<ASTNode> values;
    public NewArrayExpression() {
        super();
        this.type = ASTNodeTypes.NEW_ARRAY_EXPRESSION;
        values = new ArrayList<>();
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        reader.next(TokenType.NEW);
        NewArrayExpression expression = new NewArrayExpression();
        Token lexeme = reader.next();
        expression.setLexeme(lexeme);
        while (reader.nextIs("[")){
            reader.next("[");
            expression.addChild(Expression.parse(reader));
            reader.next("]");
        }
        if (reader.nextIs("{")){
            reader.next("{");
            while (!reader.nextIs("}")){
                expression.values.add(Expression.parse(reader));
                if(reader.nextIs(",")){
                    reader.next(",");
                }
            }
            reader.next("}");
        }
        return expression;
    }

    public String getArrayVariableType() {
        return this.lexeme.getValue();
    }

    public ASTNode getArrayLengthExpression() {
        return this.getChild(0);
    }

    public List<ASTNode> getValues() {
        return values;
    }
}

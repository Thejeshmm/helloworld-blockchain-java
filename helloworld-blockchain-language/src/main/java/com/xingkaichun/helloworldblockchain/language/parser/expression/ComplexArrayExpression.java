package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.parser.Expression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class ComplexArrayExpression extends ComplexSonExpression {

    private ASTNode arrayIndexExpression;

    public ComplexArrayExpression() {
        super();
        this.type = ASTNodeTypes.COMPLEX_ARRAY_EXPRESSION;
    }


    public static ComplexArrayExpression parse(TokenReader reader) throws ParseException {
        ComplexArrayExpression complexFieldExpression = new ComplexArrayExpression();
        reader.next("[");
        complexFieldExpression.arrayIndexExpression = Expression.parse(reader);
        reader.next("]");
        addSon(reader,complexFieldExpression);
        return complexFieldExpression;
    }

    public ASTNode getArrayIndexExpression() {
        return arrayIndexExpression;
    }
}

package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class ComplexFieldExpression extends ComplexSonExpression {

    public ComplexFieldExpression() {
        super();
        this.type = ASTNodeTypes.COMPLEX_FIELD_EXPRESSION;
    }


    public static ComplexFieldExpression parse(TokenReader reader) throws ParseException {
        reader.next(".");
        ComplexFieldExpression complexFieldExpression = new ComplexFieldExpression();
        complexFieldExpression.lexeme = reader.next();
        addSon(reader,complexFieldExpression);
        return complexFieldExpression;
    }

    public String getFieldName(){
        return this.lexeme.getValue();
    }
}

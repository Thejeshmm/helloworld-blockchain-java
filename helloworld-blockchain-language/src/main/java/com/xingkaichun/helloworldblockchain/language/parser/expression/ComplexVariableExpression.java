package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.parser.Expression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class ComplexVariableExpression extends Expression {

    public ComplexVariableExpression() {
        super();
        this.type = ASTNodeTypes.COMPLEX_VARIABLE_EXPRESSION;
    }


    public static ComplexVariableExpression parse(TokenReader reader) throws ParseException {
        ComplexVariableExpression complexVariableExpression = new ComplexVariableExpression();
        complexVariableExpression.lexeme = reader.next();
        if(reader.nextIs("[") || reader.nextIs(".")){
            ComplexSonExpression complexSonExpression = ComplexSonExpression.parse(reader);
            complexVariableExpression.addChild(complexSonExpression);
        }
        return complexVariableExpression;
    }

    public String getVariableName(){
        return this.lexeme.getValue();
    }

    public ComplexSonExpression getComplexSonExpression(){
        if(this.children.isEmpty()){
            return null;
        }
        return (ComplexSonExpression) this.getChildren().get(0);
    }
}

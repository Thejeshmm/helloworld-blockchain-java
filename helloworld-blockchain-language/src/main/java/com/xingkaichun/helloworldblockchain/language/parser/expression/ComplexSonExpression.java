package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class ComplexSonExpression extends ASTNode {

    public ComplexSonExpression() {
        super();
        this.type = ASTNodeTypes.COMPLEX_SON_EXPRESSION;
    }


    public static ComplexSonExpression parse(TokenReader reader) throws ParseException {
        if(reader.nextIs("[")) {
            ComplexSonExpression complexSonExpression = ComplexArrayExpression.parse(reader);
            return complexSonExpression;
        }else if(reader.nextIs(".")) {
            ComplexSonExpression complexSonExpression = ComplexFieldExpression.parse(reader);
            return complexSonExpression;
        }else {
            return null;
        }
    }



    protected static void addSon(TokenReader reader, ComplexSonExpression complexSonExpression) {
        ComplexSonExpression expression = ComplexSonExpression.parse(reader);
        if(expression != null){
            complexSonExpression.addChild(expression);
        }
    }
}

package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.parser.Expression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class ScalarExpression extends Expression {

    public ScalarExpression() {
        super();
        this.type = ASTNodeTypes.SCALAR_EXPRESSION;
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        ScalarExpression scalarExpression = new ScalarExpression();
        Token token = reader.next();
        scalarExpression.setLexeme(token);
        return scalarExpression;
    }

    public String getScalarValue() {
        return this.lexeme.getValue();
    }
    public String getScalarType() {
        return this.lexeme.getTokenType().getName();
    }
}

package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.parser.Expression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class StaticStringExpression extends Expression {

    public StaticStringExpression() {
        super();
        this.type = ASTNodeTypes.STATIC_STRING_EXPRESSION;
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        StaticStringExpression staticStringExpression = new StaticStringExpression();
        Token token = reader.next();
        staticStringExpression.setLexeme(token);
        return staticStringExpression;
    }

    public String getScalarValue() {
        return this.lexeme.getValue();
    }
    public String getScalarType() {
        return this.lexeme.getTokenType().getName();
    }
}

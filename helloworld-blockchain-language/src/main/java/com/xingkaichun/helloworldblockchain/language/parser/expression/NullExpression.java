package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.parser.Expression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class NullExpression extends Expression {

    public NullExpression() {
        super();
        this.type = ASTNodeTypes.NULL_EXPRESSION;
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        NullExpression nullExpression = new NullExpression();
        Token token = reader.next(TokenType.NULL);
        nullExpression.setLexeme(token);
        return nullExpression;
    }
}

package com.xingkaichun.helloworldblockchain.language.parser.expression;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.lexer.TokenType;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNode;
import com.xingkaichun.helloworldblockchain.language.parser.ASTNodeTypes;
import com.xingkaichun.helloworldblockchain.language.parser.Expression;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class NewObjectExpression extends Expression {

    public NewObjectExpression() {
        super();
        this.type = ASTNodeTypes.NEW_OBJECT_EXPRESSION;
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        reader.next(TokenType.NEW);
        NewObjectExpression newExpression = new NewObjectExpression();
        Token lexeme = reader.next();
        newExpression.setLexeme(lexeme);
        reader.next("(");
        reader.next(")");
        return newExpression;
    }

    public Token getObjectVariableType() {
        return this.lexeme;
    }
}

package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.lexer.Token;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class AssignStatement extends Statement {

    public AssignStatement() {
        super(ASTNodeTypes.ASSIGN_STATEMENT);
    }


    public static ASTNode parse(TokenReader reader) throws ParseException {
        AssignStatement assignStatement = new AssignStatement();

        AssignVariables assignVariables = AssignVariables.parse(reader);
        assignStatement.addChild(assignVariables);

        Token lexeme = reader.next("=");
        assignStatement.setLexeme(lexeme);

        ASTNode expression = Expression.parse(reader);
        assignStatement.addChild(expression);
        return assignStatement;
    }

    public AssignVariables getAssignVariables() {
        return (AssignVariables)this.getChild(0);
    }

    public Expression getExpression() {
        return (Expression)this.getChild(1);
    }
}

package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class DeclareStatement extends Statement {

    public DeclareStatement() {
        super(ASTNodeTypes.DECLARE_STATEMENT);
    }


    public static DeclareStatement parse(TokenReader reader) throws ParseException {
        DeclareStatement declareStatement = new DeclareStatement();

        ASTNode declareVariables = DeclareVariables.parse(reader);
        declareStatement.addChild(declareVariables);

        if(reader.nextIs("=")){
            reader.next("=");
            ASTNode expression = Expression.parse(reader);
            declareStatement.addChild(expression);
        }

        return declareStatement;
    }

    public DeclareVariables getDeclareVariables() {
        return (DeclareVariables)this.getChild(0);
    }

    public Expression getExpression() {
        return (Expression)this.getChild(1);
    }
}

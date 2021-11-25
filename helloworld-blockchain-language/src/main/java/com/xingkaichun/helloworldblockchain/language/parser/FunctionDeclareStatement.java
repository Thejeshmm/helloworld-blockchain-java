package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.FunctionDeclareStatementCache;
import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class FunctionDeclareStatement extends Statement {

    public FunctionDeclareStatement() {
        super(ASTNodeTypes.FUNCTION_DECLARE_STATEMENT);
    }


    public static FunctionDeclareStatement parse(TokenReader reader) throws ParseException {
        FunctionDeclareStatement func = new FunctionDeclareStatement();
        func.setLexeme(reader.next("function"));

        FunctionName functionName = FunctionName.parse(reader);
        func.addChild(functionName);

        reader.next("(");
        FunctionParameters functionParameters = FunctionParameters.parse(reader);
        func.addChild(functionParameters);
        reader.next(")");

        FunctionReturns functionReturns = FunctionReturns.parse(reader);
        func.addChild(functionReturns);

        FunctionBlock block = FunctionBlock.parse(reader);
        func.addChild(block);

        FunctionDeclareStatementCache.cache(functionName.getLexeme().getValue(),func);
        return func;
    }



    public FunctionName getFunctionName() {
        return (FunctionName)this.getChild(0);
    }
    public FunctionParameters getFunctionParameters(){
        return (FunctionParameters)this.getChild(1);
    }
    public FunctionReturns getFunctionReturns(){
        return (FunctionReturns)this.getChild(2);
    }
    public FunctionBlock getBlock(){
        return (FunctionBlock)this.getChild(3);
    }

}

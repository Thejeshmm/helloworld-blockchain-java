package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class FunctionBlock extends Statement {

    public FunctionBlock() {
        super(ASTNodeTypes.FUNCTION_BLOCK);
    }

    public static FunctionBlock parse(TokenReader reader) throws ParseException {
        reader.next("{");
        FunctionBlock block = new FunctionBlock();
        ASTNode stmt;
        while(reader.hasNext() && reader.nextIsNot("}") && (stmt = Statement.parseFunctionBlockStmt(reader)) != null) {
            block.addChild(stmt);
        }
        reader.next("}");
        return block;
    }
}

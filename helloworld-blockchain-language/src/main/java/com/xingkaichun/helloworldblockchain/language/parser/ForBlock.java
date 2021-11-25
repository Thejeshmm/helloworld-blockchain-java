package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class ForBlock extends Statement {

    public ForBlock() {
        super(ASTNodeTypes.FOR_BLOCK);
    }

    public static ForBlock parse(TokenReader reader) throws ParseException {
        reader.next("{");
        ForBlock block = new ForBlock();
        ASTNode stmt;
        while(reader.hasNext() && reader.nextIsNot("}") && (stmt = Statement.parseForBlockStmt(reader)) != null) {
            block.addChild(stmt);
        }
        reader.next("}");
        return block;
    }
}

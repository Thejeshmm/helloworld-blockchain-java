package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class Block extends Statement {

    public Block() {
        super(ASTNodeTypes.BLOCK);
    }

    public static Block parse(TokenReader reader) throws ParseException {
        reader.next("{");
        Block block = new Block();
        ASTNode stmt;
        while(reader.hasNext() && reader.nextIsNot("}") && (stmt = Statement.parseStmt(reader)) != null) {
            block.addChild(stmt);
        }
        reader.next("}");
        return block;
    }
}

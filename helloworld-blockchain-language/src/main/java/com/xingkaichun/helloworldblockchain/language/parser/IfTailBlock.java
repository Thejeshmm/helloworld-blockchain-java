package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class IfTailBlock extends Statement {

    public IfTailBlock() {
        super(ASTNodeTypes.IF_TAIL_BLOCK);
    }

    public static IfTailBlock parse(TokenReader reader) throws ParseException {
        reader.next("{");
        IfTailBlock block = new IfTailBlock();
        ASTNode stmt;
        while(reader.hasNext() && reader.nextIsNot("}") && (stmt = Statement.parseIfTailBlockStmt(reader)) != null) {
            block.addChild(stmt);
        }
        reader.next("}");
        return block;
    }
}

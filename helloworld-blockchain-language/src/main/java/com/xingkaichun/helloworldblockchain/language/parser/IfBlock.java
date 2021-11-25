package com.xingkaichun.helloworldblockchain.language.parser;

import com.xingkaichun.helloworldblockchain.language.util.ParseException;
import com.xingkaichun.helloworldblockchain.language.util.TokenReader;

public class IfBlock extends Statement {

    public IfBlock() {
        super(ASTNodeTypes.IF_BLOCK);
    }

    public static IfBlock parse(TokenReader reader) throws ParseException {
        reader.next("{");
        IfBlock block = new IfBlock();
        ASTNode stmt;
        while(reader.hasNext() && reader.nextIsNot("}") && (stmt = Statement.parseIfBlockStmt(reader)) != null) {
            block.addChild(stmt);
        }
        reader.next("}");
        return block;
    }
}

package com.xingkaichun.helloworldblockchain.language.translator.symbol;

public abstract class AbstractTypeSymbol extends Symbol{

    protected String structTypeName;




    public String getStructTypeName() {
        return structTypeName;
    }

    public void setStructTypeName(String structTypeName) {
        this.structTypeName = structTypeName;
    }
}

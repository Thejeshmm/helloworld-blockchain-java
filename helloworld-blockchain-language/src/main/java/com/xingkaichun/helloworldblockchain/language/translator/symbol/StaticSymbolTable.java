package com.xingkaichun.helloworldblockchain.language.translator.symbol;

import com.xingkaichun.helloworldblockchain.language.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class StaticSymbolTable {

    private int offsetCounter = 0;
    private List<Symbol> symbols;

    public StaticSymbolTable(){
        symbols = new ArrayList<>();
    }


    public void add(StaticIntSymbol symbol){
        symbol.setOffset(offsetCounter++);
        symbols.add(symbol);
    }
    public void add(StaticCharSymbol symbol){
        symbol.setOffset(offsetCounter++);
        symbols.add(symbol);
    }
    public void add(StaticBooleanSymbol symbol){
        symbol.setOffset(offsetCounter++);
        symbols.add(symbol);
    }
    public void add(StaticStringSymbol symbol) {
        symbol.setOffset(offsetCounter++);
        symbols.add(symbol);
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < this.symbols.size(); i++) {
            list.add(i+":" + this.symbols.get(i).toString());
        }
        return StringUtil.join(list, "\n");
    }

    public List<Symbol> getSymbols() {
        return this.symbols;
    }
}

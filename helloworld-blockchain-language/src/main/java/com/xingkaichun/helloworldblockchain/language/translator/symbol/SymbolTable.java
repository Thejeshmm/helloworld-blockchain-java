package com.xingkaichun.helloworldblockchain.language.translator.symbol;


import com.xingkaichun.helloworldblockchain.language.lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable {

    private SymbolTable parent;
    private List<SymbolTable> children;
    private List<Symbol> symbols;
    private int tempIndex = 0;
    private int offsetIndex = 0;
    private int level = 0;

    public SymbolTable(){
        this.children = new ArrayList<>();
        this.symbols = new ArrayList<>();
    }


    public void addSymbol(Symbol symbol) {
        this.symbols.add(symbol);
    }

    public void addChildSymbolTable(SymbolTable childSymbolTable) {
        childSymbolTable.parent = this;
        childSymbolTable.level = this.level + 1;
        this.children.add(childSymbolTable);
    }

    public int localSize() {
        return this.offsetIndex;
    }

    public List<Symbol> getSymbols(){
        return this.symbols;
    }

    public List<SymbolTable> getChildren(){
        return this.children;
    }


    public boolean existsVariable(String variableName) {
        Symbol symbol = cloneVariableSymbol(null,variableName,0);
        return symbol != null;
    }
    public Symbol cloneVariableSymbol(Token lexeme, String variableName) {
        return cloneVariableSymbol(lexeme,variableName,0);
    }
    private Symbol cloneVariableSymbol(Token lexeme, String variableName, int layerOffset) {
        Symbol symbol = getSymbolByVariableName(variableName);
        if(symbol != null) {
            Symbol cloneSymbol = symbol.copy();
            if(cloneSymbol instanceof AddressSymbol){
                ((AddressSymbol)cloneSymbol).setLayerOffset(layerOffset);
            }
            cloneSymbol.setLexeme(lexeme);
            return cloneSymbol;
        }
        if(this.parent != null) {
            return this.parent.cloneVariableSymbol(lexeme, variableName,layerOffset + 1);
        }
        return null;
    }
    private Symbol getSymbolByVariableName(String variableName) {
        for(Symbol symbol:symbols){
            if(symbol instanceof AddressSymbol){
                AddressSymbol addressSymbol = (AddressSymbol)symbol;
                if(variableName.equals(addressSymbol.getVariableName())){
                    return symbol;
                }
            }else if(symbol instanceof HeapObjectSymbol){
                HeapObjectSymbol heapObjectSymbol = (HeapObjectSymbol)symbol;
                if(variableName.equals(heapObjectSymbol.getVariableName())){
                    return symbol;
                }
            }else if(symbol instanceof HeapArraySymbol){
                HeapArraySymbol heapArraySymbol = (HeapArraySymbol)symbol;
                if(variableName.equals(heapArraySymbol.getVariableName())){
                    return symbol;
                }
            }else if(symbol instanceof CharSymbol){
                CharSymbol charSymbol = (CharSymbol)symbol;
                if(variableName.equals(charSymbol.getVariableName())){
                    return symbol;
                }
            }else if(symbol instanceof BooleanSymbol){
                BooleanSymbol booleanSymbol = (BooleanSymbol)symbol;
                if(variableName.equals(booleanSymbol.getVariableName())){
                    return symbol;
                }
            }else if(symbol instanceof IntSymbol){
                IntSymbol intSymbol = (IntSymbol)symbol;
                if(variableName.equals(intSymbol.getVariableName())){
                    return symbol;
                }
            }
        }
        return null;
    }


    //region
    public Symbol createStaticIntSymbol(Token lexeme) {
        Symbol symbol = StaticIntSymbol.createStaticIntSymbol(lexeme);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createStaticCharSymbol(Token lexeme) {
        Symbol symbol = StaticCharSymbol.createStaticCharSymbol(lexeme);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createStaticBooleanSymbol(Token lexeme) {
        Symbol symbol = StaticBooleanSymbol.createStaticBooleanSymbol(lexeme);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createStaticStringSymbol(Token lexeme) {
        Symbol symbol = StaticStringSymbol.createStaticStringSymbol(lexeme);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createAddressSymbol(Token lexeme, String name, String structTypeName, String variableName) {
        name = defaultNameHandler(name);
        Symbol symbol = AddressSymbol.createAddressSymbol(lexeme, name, structTypeName, variableName, this.offsetIndex++);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createIntSymbol(Token lexeme, String name, String variableName) {
        name = defaultNameHandler(name);
        Symbol symbol = IntSymbol.createIntSymbol(lexeme, name, variableName, this.offsetIndex++);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createCharSymbol(Token lexeme, String name, String variableName) {
        name = defaultNameHandler(name);
        Symbol symbol = CharSymbol.createCharSymbol(lexeme, name, variableName, this.offsetIndex++);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createBooleanSymbol(Token lexeme, String name, String variableName) {
        name = defaultNameHandler(name);
        Symbol symbol = BooleanSymbol.createBooleanSymbol(lexeme, name, variableName, this.offsetIndex++);
        this.addSymbol(symbol);
        return symbol;
    }

    private String defaultNameHandler(String name) {
        if(name != null){
            return name;
        }
        return "p" + this.tempIndex++;
    }

    public Symbol createNewObjectSymbol(Token lexeme, String structTypeName) {
        Symbol symbol = NewObjectSymbol.createNewObjectSymbol(lexeme, structTypeName);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createNewArraySymbol(Token lexeme, String structTypeName, Symbol arrayLengthSymbol) {
        Symbol symbol = NewArraySymbol.createNewArraySymbol(lexeme, structTypeName, arrayLengthSymbol);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createHeapObjectSymbol(Token lexeme, String name, String structTypeName, String variableName) {
        name = defaultNameHandler(name);
        Symbol symbol = HeapObjectFieldSymbol.createHeapObjectSymbol(lexeme, name, variableName, structTypeName, this.offsetIndex++);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createHeapArraySymbol(Token lexeme, String structTypeName, String variableName) {
        Symbol symbol = HeapArraySymbol.createHeapArraySymbol(lexeme,"p" + this.tempIndex++, variableName, structTypeName, this.offsetIndex++);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createNullSymbol(Token lexeme) {
        Symbol symbol = NullSymbol.createNullSymbol(lexeme);
        this.addSymbol(symbol);
        return symbol;
    }
    public Symbol createFunctionLabelSymbol(Token lexeme, String functionName) {
        Symbol symbol = FunctionLabelSymbol.createFunctionLabelSymbol(lexeme, functionName);
        this.addSymbol(symbol);
        return symbol;
    }
    //endregion

    //region
    public boolean existsFunction(String functionName) {
        Symbol symbol = getSymbolByFunctionName(this, functionName);
        return symbol != null;
    }
    public Symbol getSymbolByFunctionName(String functionName) {
        return getSymbolByFunctionName(this, functionName);
    }
    private Symbol getSymbolByFunctionName(SymbolTable symbolTable, String functionName) {
        for(Symbol symbol : symbolTable.symbols){
            if(symbol instanceof FunctionLabelSymbol){
                FunctionLabelSymbol functionLabelSymbol = (FunctionLabelSymbol)symbol;
                if(functionLabelSymbol.getFunctionName().equals(functionName)){
                    return symbol;
                }
            }
        }
        if(symbolTable.parent != null) {
            return getSymbolByFunctionName(symbolTable.parent, functionName);
        }
        return null;
    }
    //end region


    public void setOffsetIndex(int offsetIndex) {
        this.offsetIndex = offsetIndex;
    }

    public int getOffsetIndex() {
        return offsetIndex;
    }
}

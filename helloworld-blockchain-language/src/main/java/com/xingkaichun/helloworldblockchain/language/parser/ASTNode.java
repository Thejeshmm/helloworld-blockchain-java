package com.xingkaichun.helloworldblockchain.language.parser;

import com.google.gson.Gson;
import com.xingkaichun.helloworldblockchain.language.lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {

    protected ASTNodeTypes type;
    protected Token lexeme;
    protected List<ASTNode> children = new ArrayList<>();

    public ASTNode() {
    }

    public ASTNode(ASTNodeTypes type) {
        this.type = type;
    }


    protected void addChild(ASTNode node) {
        children.add(node);
    }

    public Token getLexeme() {
        return lexeme;
    }

    public void setLexeme(Token lexeme) {
        this.lexeme = lexeme;
    }

    public ASTNode getChild(int index) {
        if(index >= this.children.size()) {
            return null;
        }
        return this.children.get(index);
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ASTNode> children) {
        this.children = children;
    }


    public ASTNodeTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public boolean isValueType() {
        return this.type == ASTNodeTypes.VARIABLE || this.type == ASTNodeTypes.SCALAR;
    }

}

package com.xingkaichun.helloworldblockchain.language.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PriorityTable {

    private static final List<List<String>> table = new ArrayList<>();

    static {
        table.add(Arrays.asList("==", "!=", ">", ">=", "<", "<=", "||", "&&"));
        table.add(Arrays.asList("+", "-"));
        table.add(Arrays.asList("*", "/", "%"));
    }


    public int level(){
        return table.size();
    }

    public List<String> get(int level) {
        return table.get(level);
    }

}

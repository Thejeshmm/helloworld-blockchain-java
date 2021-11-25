package com.xingkaichun.helloworldblockchain.language.util;

import com.xingkaichun.helloworldblockchain.language.parser.FunctionDeclareStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xingkaichun@ceair.com
 */
public class FunctionDeclareStatementCache {

    private static Map<String, FunctionDeclareStatement> cache = new HashMap<>();

    public static void cache(String functionName, FunctionDeclareStatement func) {
        cache.put(functionName,func);
    }

    public static FunctionDeclareStatement getByName(String functionName){
        return cache.get(functionName);
    }

}

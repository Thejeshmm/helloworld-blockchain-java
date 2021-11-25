package com.xingkaichun.helloworldblockchain.language.util;

import java.util.List;

/**
 * @author xingkaichun@ceair.com
 */
public class StringUtil {

    public static String join(List<String> values, String separationValue) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String value:values){
            stringBuilder.append(value).append(separationValue);
        }
        return stringBuilder.toString();
    }

    public static boolean equals(String string, String anotherString){
        return string.equals(anotherString);
    }

}

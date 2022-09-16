package com.thf.common.utils;

import java.util.Arrays;

public class StringFix {
    public static Object[] stringToArray(String str){
        str=str.substring(1,str.length()-1);
        return Arrays.stream(str.split(",")).toArray();
    }
    public static String arrayToString(Object[] obj){
        return Arrays.toString(obj);
    }
}

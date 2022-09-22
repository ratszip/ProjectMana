package com.thf.common.utils;

import java.util.Arrays;

public class StringFix {
    public static long[] stringToArray(String str){
        str=str.substring(1,str.length()-1);
        String[] sl=str.split(",");
        long[] lar=Arrays.stream(sl).mapToLong(Long::parseLong).toArray();
        return lar;
    }
    public static String arrayToString(Object[] obj){
        return Arrays.toString(obj);
    }
}

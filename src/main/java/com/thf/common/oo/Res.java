package com.thf.common.oo;

public class Res {
//    private String msg;
//    private int code;
//    private Object data;
//
    public static ResultVO res(int code,String msg,Object data){
        return new ResultVO(code,msg,data);
    }
}

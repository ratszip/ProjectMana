package com.thf.common.utils;

import java.util.Random;

public class PMUtils {

    /**
     * 生成n位数的随机验证码
     */
    private String createVerifyCode(int n){
        Random r = new Random();
        StringBuffer sb =new StringBuffer();
        for(int i = 0;i < n;i ++){
            int ran1 = r.nextInt(10);
            sb.append(String.valueOf(ran1));
        }
//        System.out.println(sb);
        return sb.toString();
    }

}

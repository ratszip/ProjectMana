package com.thf.common;

public class GloableVar {
    //JWT密钥
    public static String secretKey="PMproject6qweqwewqeqwePMproject6qweqwewqeqwe123";
    //密码正则
    public static String pwdReg="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    //邮箱正则
    public static String emailReg="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    //手机正则
    public static String phoneReg="[\\+]?[0-9]{6,16}";
    //过期时长-毫秒
    public static long expireTime=30*24*60*60*1000L;
    //验证码失效时间
    public static int codeExTime=5*60;
}

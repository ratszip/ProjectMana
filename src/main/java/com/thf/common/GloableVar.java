package com.thf.common;

public class GloableVar {
    //JWT密钥
    public static String secretKey="PMproject6qweqwewqeqwePMproject6qweqwewqeqwe123";
    //覆盖密钥
    public static String unsecretKey="PMproject6q&%daflPweqwewqeqwe-loj%%%djdk123ld";
    //密码正则
    public static String pwdReg="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    //邮箱正则
    public static String emailReg="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    //手机正则
    public static String phoneReg="[\\+]?[0-9]{6,16}";
    //过期时长-毫秒
    public static long expireTime=7*24*60*60*1000L;
    //验证码失效时间
    public static int codeExTime=5*60;
    public static String SmsSdkAppId="1400706366";
    public static String appKey="2a8422eb07dba81c831aece5886f829e";
    public static String tencentSecretId="AKIDaz7yYsvgBQO53IPFn5D6i4AzXqwmKdfN";
    public static String tencentSecretKey="atviEUAZjdMp20h7PQ9E3bssb2C4g190";
    public static String ali_appcode = "babed87d938c48e1bf7aaddaccd5ad7b";
}

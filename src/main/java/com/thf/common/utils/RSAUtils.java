package com.thf.common.utils;

import org.apache.commons.codec.binary.Base64;
//import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
//import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
//import java.util.HashMap;
//import java.util.Map;

/**
 * Hello RSA!
 *
 */
public class RSAUtils
{

    public static void main(String[] args) throws Exception {
        System.out.println(encrypt("1234567a",publicKey));
        System.out.println(decrypt("INiZpUmUFW2dG1AP8ZOGzJhUBjshtY5uH8t6vaoL1Qk0uzIIuuVoqZLU1N8UIrewBQ7ARm3bL3TdheHAsZnHKHQsKlFQgf7hrtq5owaKM9n5K9clGtSgfs+kOyWQiVA5arPyuLR68UJq3tKnRSObT5PCmN3irSefBN4BkC2yypc="));
    }
    static String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZ6PSpdV0ORwjzDHRNlpGnkE63LVHmdR0FHwHSUHdVAsO7Gfd3LdAAUN8HzXgrhX+lk7wcR40+/BHkb1Be7mrS80TiadsPEIYRzRXB71btBfy2kLiZGgUK0NEqarAhtzcqeBoD2FHZ8mehbHGL6Fa+IafNjWajY8jQsa+wjzOdwQIDAQAB";
   public static String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJno9Kl1XQ5HCPMMdE2WkaeQTrctUeZ1HQUfAdJQd1UCw7sZ93ct0ABQ3wfNeCuFf6WTvBxHjT78EeRvUF7uatLzROJp2w8QhhHNFcHvVu0F/LaQuJkaBQrQ0SpqsCG3Nyp4GgPYUdnyZ6FscYvoVr4hp82NZqNjyNCxr7CPM53BAgMBAAECgYEAh7unkY78iyvgW6CfLRHd0H32vZsNQIYGGs8WpyjppKI0hdxHnuZbKvwnRVVSZmdshFS+r932VhatnN0Cb0YqFBOcAB2l6bQOnyTeLhOC/Gzb5r+fCTuLPHzh8I8KEkOBOMX0lHZ6NM8Ubxf41nEK6HOXkkor2vg/Qw7U8mNN2fECQQDPeIgUfMN7WnXbveqdYYmBiiGj/W9VMTxIGgPcsuYoZ6LGM7OSR0oP2FkISoocczSCDfGnsUI7q7eAZ55s3+QtAkEAvekry+IEOhSX+NBcyz3CYDgLwGY0mtKBIa8Ge3IDnQEgyc60HTWEHpkEgYukqYQUZBxtIE2S7H6LxgavQf/4ZQJAP9SCcWMtNU9QgWm9u+vdRVh9HxoC3cAHHtu6InV78CRqZPZVIgUC2TsjsTRVp4keN4EYvxH/IaKkE0JN8Is1hQJASxQEeXzn+OItVnNhTAQldQ6TLsP5Jf/0hxF/uYvQe+B3SvEJ6cY/bi1GR8SiG1YZDZ7jUXL5k79PmMEJxkjgtQJBAMTPvHrRGcpYArJhL9F0hxzx89eHvj4HTaLRg5iMJiX8pgz+vXguBW8gLoL1KU8Kst4ya+5F3JlwzOAW/38PXE0=";
    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static Map<Integer,String> getKeyPair() throws NoSuchAlgorithmException {
        //KeyPairGenerator类用于生成公钥和密钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        //生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();//得到私钥
        PublicKey publicKey = keyPair.getPublic();//得到公钥
        //得到公钥字符串
        String publicKeyString=new String(Base64.encodeBase64(publicKey.getEncoded()));
        //得到私钥字符串
        String privateKeyString=new String(Base64.encodeBase64(privateKey.getEncoded()));
        //将公钥和私钥保存到Map
        Map keyMap=new HashMap<>();
        keyMap.put(0,publicKeyString);//0表示公钥
        keyMap.put(1,privateKeyString);//1表示私钥
        return keyMap;
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt(String str,String publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey= null;
            pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RAS加密
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE,pubKey);
            String outStr=Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
            return outStr;
    }

    //base64编码的公钥,    用base64处理下主要是将字符串内的不可见字符转换成可见字符，防止不同机器处理错误
//        byte[] decoded = Base64.getDecoder().decode(publicKey);
//        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
//        //RSA加密
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
//        //这里没用像解密  new String  主要还是这个是要传输的，所以用base64编码的，防止错误
//        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
//        return outStr;
    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        //Base64解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //Base64编码的私钥
        byte[] decoded = Base64.decodeBase64(RSAUtils.privateKey);
        PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,priKey);
        String outStr=new String(cipher.doFinal(inputByte));
        return outStr;

    }

}


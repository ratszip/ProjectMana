package com.thf.common.utils;

import com.thf.common.GloableVar;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    public static Claims parseToken(String token){
        JwtParser parser = Jwts.parser();
        parser.setSigningKey(GloableVar.secretKey); //解析token的SigningKey必须和生成token时设置密码一致
        //如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims;
    }
    public static Boolean isTokenExpired(Claims claims) {
        //不管是否过期，都返回claims对象
//        Claims claims = parseToken(token);
        Date expiration = claims.getExpiration();
        //和当前时间进行对比来判断是否过期
        return new Date(System.currentTimeMillis()).after(expiration);
    }

    /**
     * 生成token
     * @param map
     * @param subject
     * @return
     */
    public static String generateToken(Map map, String subject,long exipretime){
        JwtBuilder builder = Jwts.builder();

        String token = builder.setSubject(subject)                     //主题，就是token中携带的数据
                .setId(map.get("uid")+"")               //设置用户id为token  id
                .setClaims(map)                                     //map中可以存放用户的角色权限信息
                .setExpiration(new Date(System.currentTimeMillis() + exipretime))//设置token过期时间
                .signWith(SignatureAlgorithm.HS256, GloableVar.secretKey)
                .compact();
        return token;
    }

}

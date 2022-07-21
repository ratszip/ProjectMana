package com.thf.common.utils;

import com.thf.common.GloableVar;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import java.util.Date;

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

}

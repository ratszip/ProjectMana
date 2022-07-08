package com.thf.common.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.thf.common.oo.ResultVO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }
        String token = request.getHeader("token");
        if(token == null){
            ResultVO resultVO = new ResultVO(4001, "请先登录！", null);
            doResponse(response,resultVO);
        }else{
            try {
                Claims claims=Jwts.parserBuilder()
                        .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
//                JwtParser parser = Jwts.parser();
//                parser.setSigningKey("PM666"); //解析token的SigningKey必须和生成token时设置密码一致
                //如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
//                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            }catch (ExpiredJwtException e){
                ResultVO resultVO = new ResultVO(4002, "登录过期，请重新登录！", null);
                doResponse(response,resultVO);
            }catch (UnsupportedJwtException e){
                ResultVO resultVO = new ResultVO(4003, "Token不合法！", null);
                doResponse(response,resultVO);
            }catch (Exception e){
                ResultVO resultVO = new ResultVO(4001, "请先登录！", null);
                doResponse(response,resultVO);
            }
        }
        return false;
    }

    private void doResponse(HttpServletResponse response,ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        out.print(s);
        out.flush();
        out.close();
    }

}

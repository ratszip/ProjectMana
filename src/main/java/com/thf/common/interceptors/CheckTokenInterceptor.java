package com.thf.common.interceptors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.thf.common.GloableVar;
import com.thf.common.oo.ResultVO;
import com.thf.common.utils.JwtUtil;
import io.jsonwebtoken.*;
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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }
        String token = request.getHeader("token");
        if (token == null) {
            ResultVO resultVO = new ResultVO(4001, "请先登录！");
            doResponse(response, resultVO);
        } else {
            try {
                JwtParser parser = Jwts.parser();
                parser.setSigningKey(GloableVar.secretKey); //解析token的SigningKey必须和生成token时设置密码一致
                //如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
                Claims claims = parser.parseClaimsJws(token).getBody();

                return true;
            } catch (ExpiredJwtException e) {
                if (JwtUtil.isTokenExpired(e.getClaims())) {
                    ResultVO resultVO = new ResultVO(4002, "token已过期");
                    doResponse(response, resultVO);
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private void doResponse(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(resultVO);
        out.print(s);
        out.flush();
        out.close();
    }

}

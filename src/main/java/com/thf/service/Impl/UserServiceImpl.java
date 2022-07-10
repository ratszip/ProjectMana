package com.thf.service.Impl;

import com.thf.common.GloableVar;
import com.thf.common.oo.ResultVO;
import com.thf.dao.UserDAO;
import com.thf.entity.User;
import com.thf.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    public User insertUser(User user) {
        int i = userDAO.insertUser(user);
        if (i > 0) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User searchUserEmail(String key) {
        return userDAO.searchEmail(key);
    }

    @Override
    public User searchUserPhone(String key) {
        return userDAO.searchPhone(key);
    }

    @Override
    public ResultVO searchById(Integer id) {
        return new ResultVO(2000,"",userDAO.searchById(id));
    }

    @Override
    public ResultVO checkLogin(String key, String pwd, int type) {
        User user;
        if (type == 1) {
            user =userDAO.searchEmail(key);
            if (userDAO.searchEmail(key) == null) {
                return new ResultVO(2000, "用户不存在");
            } else {
                if (userDAO.searchEmail(key).getPassword().equals(pwd)) {
                    JwtBuilder builder= Jwts.builder();
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("id",user.getUserId());
                    map.put("usertype",user.getUserType());
//                    Key key1y = Keys.secretKeyFor(SignatureAlgorithm.HS256);

//                    SecretKey secretKey= Keys.hmacShaKeyFor(Decoders.BASE64.decode("asdfasdPMOIEWUJQJROJASDOFWJEFLDJSL948JLASDsladjfasdfsda"));
//                    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

                    long nowMillis = System.currentTimeMillis();
                    Date now = new Date(nowMillis);



                    String token = builder.setSubject(key)                     //主题，就是token中携带的数据
                            .setIssuedAt(new Date())                            //设置token的生成时间
                            .setId(user.getUserId()+"")               //设置用户id为token  id
                            .setClaims(map)                                     //map中可以存放用户的角色权限信息
                            .setExpiration(new Date(System.currentTimeMillis() + 7*24*60*60))//设置token过期时间
                            .signWith(SignatureAlgorithm.HS256, GloableVar.secretKey)     //设置加密方式和加密密码
                            .compact();
                    return new ResultVO(2000,"登录成功",token);
                }else {
                    return new ResultVO(2000,"密码错误");
                }

            }
        }else if(type==2){
            user =userDAO.searchPhone(key);
            if (userDAO.searchPhone(key) == null) {
                return new ResultVO(2000, "用户不存在");
            } else {
                if (userDAO.searchPhone(key).getPassword().equals(pwd)) {
                    JwtBuilder builder= Jwts.builder();
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("id",user.getUserId());
                    map.put("usertype",user.getUserType());
                    String token = builder.setSubject(key)                     //主题，就是token中携带的数据
                            .setIssuedAt(new Date())                            //设置token的生成时间
                            .setId(user.getUserId()+"")               //设置用户id为token  id
                            .setClaims(map)                                     //map中可以存放用户的角色权限信息
                            .setExpiration(new Date(System.currentTimeMillis() + 24*60*60))//设置token过期时间
                            .signWith(SignatureAlgorithm.HS256,GloableVar.secretKey)
                            .compact();

                    return new ResultVO(2000,"登录成功",token);
                }else {
                    return new ResultVO(2000,"密码错误");
                }
            }
        }
        return new ResultVO(2000,"type错误");
    }
}

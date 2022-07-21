package com.thf.service.Impl;

import com.thf.common.GloableVar;
import com.thf.common.oo.Res;
import com.thf.common.oo.ResultVO;
import com.thf.common.utils.JwtUtil;
import com.thf.common.utils.RegExpUtils;
import com.thf.dao.UserDAO;
import com.thf.entity.User;
import com.thf.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
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
    public User searchById(int userId) {
        return userDAO.searchById(userId);
    }

    @Override
    public ResultVO checkLogin(String key, String pwd, int type) {
        User user;
        if (type == 1) {
            user = userDAO.searchEmail(key);
            if (userDAO.searchEmail(key) == null) {
                return Res.res(2000, "用户不存在");
            } else {
                if (userDAO.searchEmail(key).getPassword().equals(pwd)) {
                    JwtBuilder builder = Jwts.builder();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", user.getUserId());
                    map.put("usertype", user.getUserType());
                    String token = builder.setSubject(key)                     //主题，就是token中携带的数据
                            .setIssuedAt(new Date())                            //设置token的生成时间
                            .setId(user.getUserId() + "")               //设置用户id为token  id
                            .setClaims(map)                                     //map中可以存放用户的角色权限信息
                            .setExpiration(new Date(System.currentTimeMillis() + GloableVar.expireTime))//设置token过期时间
                            .signWith(SignatureAlgorithm.HS256, GloableVar.secretKey)     //设置加密方式和加密密码
                            .compact();
                    return Res.res(2000, "登录成功", token);
                } else {
                    return Res.res(2000, "密码错误");
                }

            }
        } else if (type == 2) {
            user = userDAO.searchPhone(key);
            if (userDAO.searchPhone(key) == null) {
                return Res.res(2000, "用户不存在");
            } else {
                if (userDAO.searchPhone(key).getPassword().equals(pwd)) {
                    JwtBuilder builder = Jwts.builder();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", user.getUserId());
                    map.put("usertype", user.getUserType());
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.add(Calendar.DATE,30);
                    String token = builder.setSubject(key)                     //主题，就是token中携带的数据
//                            .setIssuedAt(new Date())                            //设置token的生成时间
                            .setId(user.getUserId() + "")               //设置用户id为token  id
                            .setClaims(map)                                     //map中可以存放用户的角色权限信息
//                            .setExpiration(calendar.getTime())
                            .setExpiration(new Date(System.currentTimeMillis() + 20*1000))//设置token过期时间
                            .signWith(SignatureAlgorithm.HS256, GloableVar.secretKey)
                            .compact();

                    return Res.res(2000, "登录成功", token);
                } else {
                    return Res.res(2000, "密码错误");
                }
            }
        }
        return Res.res(2000, "type错误");
    }

    @Override
    public ResultVO userRegister(String userPhone, String userEmail, String userPwd, String verifyCode, int verifyType) {
        String password = userPwd.trim();
        User user = new User();
        if (password.trim() == "") {
            return Res.res(2000, "密码不能为空", null);
        } else if (verifyCode.trim() == "") {
            return Res.res(2000, "验证码不能为空", "");
        } else if (!RegExpUtils.useRegexp(password, GloableVar.pwdReg)) {
            return Res.res(2000, "密码必须到8-16位数字或字母", null);
        }

        if (verifyType == 1) {
            String email = userEmail.trim();
            if (email == "") {
                return Res.res(2000, "邮箱不能空", "");
            } else if (!RegExpUtils.useRegexp(email, GloableVar.emailReg)) {
                return Res.res(2000, "请输入正确的邮箱格式", null);
            }
            user.setPhone("");
            if (searchUserEmail(email) == null) {
                user.setEmail(email);
                user.setPassword(password);
                user.setRegisterTime(System.currentTimeMillis());
                if (insertUser(user) != null) {
                    return Res.res(2000, "注册成功", null);
                } else {
                    return Res.res(2000, "注册失败", null);
                }
            } else {
                return Res.res(2000, "用户已存在，请登录", null);
            }
        } else if (verifyType == 2) {
            String phone = userPhone.trim();
            if (phone == "") {
                return Res.res(2000, "手机不能空", null);
            } else if (!RegExpUtils.useRegexp(phone, GloableVar.phoneReg)) {
                return Res.res(2000, "请输入正确的手机格式", null);
            }
            user.setEmail("");
            if (searchUserPhone(phone) == null) {
                user.setPhone(phone);
                user.setPassword(password);
                if (insertUser(user) != null) {
                    return Res.res(2000, "注册成功", null);
                } else {
                    return Res.res(2000, "注册失败", null);
                }
            } else {
                return Res.res(2000, "用户已存在，请登录", null);
            }
        } else
            return Res.res(2000, "verifyType有误", null);
    }

    @Override
    public ResultVO updateInfo(String username, String userIntroduce, String userAddress, String token) {
        int id = (int) JwtUtil.parseToken(token).get("id");
        User user = searchById(id);
        user.setUsername(username);
        user.setUserIntroduce(userIntroduce);
        user.setUserAddress(userAddress);
        if (userDAO.updateUsers(user) > 0) {
            return Res.res(2000, "资料修改成功", searchById(user.getUserId()));
        }
        return Res.res(5000, "更新失败");
    }

    @Override
    public ResultVO getInfo(String token) {
        int id = (int) JwtUtil.parseToken(token).get("id");
        User user = searchById(id);
        if (user == null) {
            return Res.res(2000, "无该用户信息", null);
        }
        return Res.res(2000, "", user);
    }

    @Override
    public ResultVO resetPwd(String password, String token) {
        Integer id = (Integer) JwtUtil.parseToken(token).get("id");
        User user = searchById(id);
        String oldPwd = user.getPassword();
        if (RegExpUtils.useRegexp(password, GloableVar.pwdReg)) {
            if (oldPwd.equals(password)) {
                return Res.res(2000, "新密码与旧密码不能相同");
            } else {
                user.setPassword(password);
                if (userDAO.resetPassword(user) > 0) {
                    return Res.res(2000, "修改成功");
                }
            }
        }else {
            return Res.res(2000, "密码必须为为8-16位数字字母组合");
        }
        return Res.res(5000, "未知错误");
    }
}

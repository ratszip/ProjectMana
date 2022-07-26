package com.thf.service.Impl;

import com.thf.common.GloableVar;
import com.thf.common.oo.Res;
import com.thf.common.oo.ResultVO;
import com.thf.common.utils.JwtUtil;
import com.thf.common.utils.RSAUtils;
import com.thf.common.utils.RegExpUtils;
import com.thf.dao.UserDAO;
import com.thf.entity.User;
import com.thf.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
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
        String passwd = null;
        if (type == 1) {
            user = userDAO.searchEmail(key);
            if (userDAO.searchEmail(key) == null) {
                return Res.res(2000, "用户不存在");
            } else {
                try {
                    passwd = RSAUtils.decrypt(pwd);
                } catch (Exception e) {
                    e.printStackTrace();
                    Res.res(5000, "服务器密码解析错误");
                }
              String oldpass= userDAO.searchEmail(key).getPassword();
                Boolean b=new BCryptPasswordEncoder().matches(passwd, oldpass);
                if (b) {
                    JwtBuilder builder = Jwts.builder();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("uid", user.getUserId());
                    map.put("usertype", user.getUserType());
                    String token = JwtUtil.generateToken(map, key, GloableVar.expireTime);
                    stringRedisTemplate.opsForValue().set(user.getUserId() + "", user.getUserId() + "", System.currentTimeMillis() + GloableVar.expireTime, TimeUnit.MILLISECONDS);
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
                try {
                    passwd = RSAUtils.decrypt(pwd);
                } catch (Exception e) {
                    e.printStackTrace();
                    Res.res(5000, "服务器密码解析错误");
                }
                if (new BCryptPasswordEncoder().matches(passwd, userDAO.searchEmail(key).getPassword())) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("uid", user.getUserId());
                    map.put("usertype", user.getUserType());
                    String token = JwtUtil.generateToken(map, key, GloableVar.expireTime);
                    stringRedisTemplate.opsForValue().set(user.getUserId() + "", user.getUserId() + "", System.currentTimeMillis() + GloableVar.expireTime, TimeUnit.MILLISECONDS);
                    return Res.res(2000, "登录成功", token);
                } else {
                    return Res.res(2000, "密码错误");
                }
            }
        }
        return Res.res(2000, "type错误");
    }

    @Override
    public ResultVO userRegister(String key, String userPwd, String verifyCode, int verifyType) {
        String password = userPwd.trim();
        String regKey = key.trim();
        User user = new User();
        if (password.trim() == "") {
            return Res.res(2000, "密码不能为空", null);
        } else if (verifyCode.trim() == "") {
            return Res.res(2000, "验证码不能为空", "");
        } else if (regKey == "") {
            return Res.res(2000, "邮箱或手机不能空", "");
        }

        if (verifyType == 1) {
            if (!RegExpUtils.useRegexp(regKey, GloableVar.emailReg)) {
                return Res.res(2000, "请输入正确的邮箱格式", null);
            }
            if (searchUserEmail(regKey) == null) {
                user.setEmail(regKey);
                try {
                    password = RSAUtils.decrypt(password);
                    if (!RegExpUtils.useRegexp(password, GloableVar.pwdReg)) {
                        return Res.res(2000, "密码必须到8-16位数字或字母", null);
                    }
                    password = new BCryptPasswordEncoder().encode(password);
                } catch (Exception e) {
                    e.printStackTrace();
                    Res.res(5000, "服务器密码处理异常");
                }
                user.setPassword(password);
                user.setRegisterTime(System.currentTimeMillis());
                if (insertUser(user) != null) {

                    return Res.res(2000, "注册成功", null);
                } else {
                    return Res.res(5000, "注册失败", null);
                }
            } else {
                return Res.res(2000, "用户已存在，请登录", null);
            }
        } else if (verifyType == 2) {
            if (!RegExpUtils.useRegexp(regKey, GloableVar.phoneReg)) {
                return Res.res(2000, "请输入正确的手机格式", null);
            }
            if (searchUserPhone(regKey) == null) {
                user.setPhone(regKey);
                try {
                    password = RSAUtils.decrypt(password);
                    if (!RegExpUtils.useRegexp(password, GloableVar.pwdReg)) {
                        return Res.res(2000, "密码必须到8-16位数字或字母", null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Res.res(5000, "服务器密码处理异常");
                }
                password = new BCryptPasswordEncoder().encode(password);
                user.setPassword(password);
                user.setRegisterTime(System.currentTimeMillis());
                if (insertUser(user) != null) {
                    return Res.res(2000, "注册成功", null);
                } else {
                    return Res.res(5000, "注册失败", null);
                }
            } else {
                return Res.res(2000, "用户已存在，请登录", null);
            }
        } else
            return Res.res(4000, "type有误", null);
    }

    @Override
    public ResultVO updateInfo(String username, String userIntro, String userAddress, String token) {
        int id = (int) JwtUtil.parseToken(token).get("uid");
        User user = searchById(id);
        user.setUsername(username);
        user.setUserIntro(userIntro);
        user.setUserAddress(userAddress);
        if (userDAO.updateUsers(user) > 0) {
            return Res.res(2000, "资料修改成功", searchById(user.getUserId()));
        }
        return Res.res(5000, "更新失败");
    }

    @Override
    public ResultVO getInfo(String token) {
        int id = (int) JwtUtil.parseToken(token).get("uid");
        User user = searchById(id);
        if (user == null) {
            return Res.res(2000, "无该用户信息", null);
        }
        return Res.res(2000, "获取用户信息成功", user);
    }

    @Override
    public ResultVO changePwd(String password, String token) {
        Integer id = (Integer) JwtUtil.parseToken(token).get("uid");
        User user = searchById(id);
        String oldPwd = user.getPassword();
        try {
            password = RSAUtils.decrypt(password);
            if (!RegExpUtils.useRegexp(password, GloableVar.pwdReg)) {
                return Res.res(2000, "密码必须到8-16位数字和字母", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Res.res(5000, "服务器处理异常");
        }
        if (new BCryptPasswordEncoder().matches(password, oldPwd)) {
            return Res.res(2000, "新密码与旧密码不能相同");
        } else {
            password=new BCryptPasswordEncoder().encode(password);
            user.setPassword(password);
            if (userDAO.resetPassword(user) > 0) {
                stringRedisTemplate.expire(id + "", stringRedisTemplate.getExpire(id + "") + 1, TimeUnit.MILLISECONDS);
                return Res.res(2000, "修改成功");
            }
        }
        return Res.res(5000, "未知错误");
    }
}

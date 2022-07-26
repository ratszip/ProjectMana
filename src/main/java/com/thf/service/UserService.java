package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.User;

public interface UserService {
    User insertUser(User user);
    User searchUserEmail(String key);
    User searchUserPhone(String key);
    //根据id查询用户信息
    User searchById(long id);
    //用户登录
    ResultVO checkLogin(String name, String pwd,int type);
    //用户注册
    ResultVO userRegister(String key,String userPwd,String verifyCode, int verifyType);
    //修改资料
    ResultVO updateInfo(User user,String token);
    //获取用户信息
    ResultVO getInfo(String token);
    //重置密码
    ResultVO changePwd(String password,String token);
    //重置手机或邮箱
    ResultVO resetContact(String header,int type,String key);
}

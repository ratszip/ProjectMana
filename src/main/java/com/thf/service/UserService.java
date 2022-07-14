package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.User;

public interface UserService {
    User insertUser(User user);
    User searchUserEmail(String key);
    User searchUserPhone(String key);
    //根据id查询用户信息
    User searchById(int id);
    //用户登录
    ResultVO checkLogin(String name, String pwd,int type);
    //用户注册
    ResultVO userRegister(String userPhone, String userEmail,String userPwd,String verifyCode, int verifyType);
    //修改资料
    ResultVO updateInfo(String userName, String userIntroduce, String userAddress,String token);
    //获取用户信息
    ResultVO getInfo(String token);
    //重置密码
    ResultVO resetPwd(String password,String token);
}

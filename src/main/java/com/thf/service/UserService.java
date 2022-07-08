package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.User;

public interface UserService {
    User insertUser(User user);
    User searchUserEmail(String key);
    User searchUserPhone(String key);
    //用户登录
    ResultVO checkLogin(String name, String pwd,int type);
}

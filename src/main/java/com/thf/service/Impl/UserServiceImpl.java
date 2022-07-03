package com.thf.service.Impl;

import com.thf.dao.UserDAO;
import com.thf.entity.User;
import com.thf.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        return userDAO.searchUsers(key);
    }
}

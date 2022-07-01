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
        userDAO.insertUser(user);
        return null;
    }


    @Override
    public User searchUser(User user) {
        return null;
    }
}

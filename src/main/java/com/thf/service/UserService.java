package com.thf.service;

import com.thf.entity.User;

public interface UserService {
    User insertUser(User user);
    User searchUser(User user);

}

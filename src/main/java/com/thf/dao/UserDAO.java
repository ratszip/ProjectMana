package com.thf.dao;

import com.thf.entity.User;

public interface UserDAO {
    int insertUser(User user);

    User searchUser(String key);

    int updateUser(User user);
}

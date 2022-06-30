package com.thf.dao;

import com.thf.entity.Users;

public interface UsersDAO {
    int insertUser(Users users);

    Users searchUser(String key);

    int updateUser(Users users);
}

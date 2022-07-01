package com.thf.dao;

import com.thf.entity.User;

public interface UserDAO {
    public int insertUser(User user);

    public User searchUsers(String key);

    public int updateUsers(User user);
}

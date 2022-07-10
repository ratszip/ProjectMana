package com.thf.dao;

import com.thf.entity.User;

public interface UserDAO {
    public int insertUser(User user);

    public User searchEmail(String key);
    public User searchPhone(String key);
    public int updateUsers(User user);
    public User searchById(Integer id);
}

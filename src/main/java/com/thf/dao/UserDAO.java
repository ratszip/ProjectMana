package com.thf.dao;

import com.thf.entity.User;

public interface UserDAO {
    public int insertUser(User user);

    public User searchEmail(String email);
    public User searchPhone(String phone);
    public int updateUsers(User user);
    public User searchById(int userId);
    public int resetPassword(User user);
}

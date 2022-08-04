package com.thf.dao;

import com.thf.entity.User;

public interface UserDAO {
     int insertUser(User user);
     User searchEmail(String email);
     User searchPhone(String phone);
     int updateUsers(User user);
     User searchById(long userId);
     int resetPassword(User user);
     int resetContact(User user);
     User searchEmailAndPhone(String key);
}

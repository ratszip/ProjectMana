package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int userId;
    private int userType;
    private String username;
    private String password;
    private String userIntroduce;
    private String registerTime;
    private String phone;
    private String email;
    private String userAddress;
    private String userImg;
}

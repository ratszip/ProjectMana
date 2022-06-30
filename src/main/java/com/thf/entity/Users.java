package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {
    private int userId;
    private char userType;
    private String userName;
    private String userPwd;
    private String userIntroduce;
    private String registerTime;
    private String userPhone;
    private String userEmail;
    private String userAddress;
}

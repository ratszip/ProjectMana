package com.thf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Integer userId;
    private Integer userType;
    private String username;
    @JsonIgnore
    private String password;
    private String userIntroduce;
    private long registerTime;
    private String phone;
    private String email;
    private String userAddress;
    private String userImg;
}

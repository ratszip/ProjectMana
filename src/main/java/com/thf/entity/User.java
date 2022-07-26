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
    private Long userId;
    private Integer userType;
    private String username;
    @JsonIgnore
    private String password;
    private Integer gender;
    private String userIntro;
    private Long registerTime;
    private String phone;
    private String email;
    private String address;
    private String userImg;
    private String career;
}

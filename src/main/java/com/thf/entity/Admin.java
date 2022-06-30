package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin {
    private int admin_id;
    private String admin_name;
    private String admin_nickname;
    private String admin_password;
    private String admin_profile_picture_src;
}

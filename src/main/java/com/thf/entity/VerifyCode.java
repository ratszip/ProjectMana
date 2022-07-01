package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyCode {
    private String verifyCode;
    //1.手机，2.邮箱
    private int verifyType;
}

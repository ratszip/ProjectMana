package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    private Long mid;
    private String mname;
    private String mdes;
    private Integer mstatus;
    private Integer mprogress;
    private Long mstartTime;
    private Long mendTime;
    private Long pid;
    private Long mactEndTime;
    private Long mcreateTime;
    private Long mcreateUser;
    private Long mrelateUser;
    List<Function> functionList;
}

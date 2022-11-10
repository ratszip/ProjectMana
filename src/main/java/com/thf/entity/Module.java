package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    private Date mstartTime;
    private Date mendTime;
    private Long pid;
    private Date mactEndTime;
    private Date mcreateTime;
    private Long mcreateUser;
    private Long mrelateUser;
    List<Function> functionList;
}

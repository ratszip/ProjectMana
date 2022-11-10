package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Function {
    private Long fid;
    private String fname;
    private String fdes;
    private int fstatus;
    private long fcreateUser;
    private Date fstartTime;
    private Date fendTime;
    private Date factEndTime;
    private int fprogress;
    private Long mid;
    private Date fcreateTime;
}

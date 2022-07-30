package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Function {
    private Long fid;
    private String fname;
    private String fdes;
    private int fstatus;
    private long fcreateUser;
    private Long fstartTime;
    private Long fendTime;
    private Long factEndTime;
    private int fprogress;
    private Long mid;
    private Long fcreateTime;
}

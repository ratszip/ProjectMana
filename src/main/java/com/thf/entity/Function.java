package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Function {
    private int fId;
    private String fName;
    private String fDes;
    private int fStatus;
    private long fStartTime;
    private long fEndTime;
    private long fActTime;
    private int fProgress;
    private int mId;
}

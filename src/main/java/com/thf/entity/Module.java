package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    private int mId;
    private String mName;
    private String mDes;
    private Integer mStatus;
    private Integer mProgress;
    private long mStartTime;
    private long mEndTime;
    private int pId;
    private long mActTime;
}

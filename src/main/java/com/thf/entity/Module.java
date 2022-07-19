package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    private Integer mId;
    private String mName;
    private String mDes;
    private Integer mStatus;
    private Integer mProgress;
    private long mStartTime;
    private long mEndTime;
    private Integer pId;
    private long mActTime;
    private List<Function> functionList;
}

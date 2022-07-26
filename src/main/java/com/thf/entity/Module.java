package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    private Long mId;
    private String mName;
    private String mDes;
    private Integer mStatus;
    private Integer mProgress;
    private Long mStartTime;
    private Long mEndTime;
    private Long pId;
    private Long mActTime;
    private Long mCreateTime;
    List<Function> functionList;
}

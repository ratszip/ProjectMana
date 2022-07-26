package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Function {
    private Long fId;
    private String fName;
    private String fDes;
    private int fStatus;
    private Long fStartTime;
    private Long fEndTime;
    private Long fActTime;
    private int fProgress;
    private Long mId;
    private Long fCreateTime;
}

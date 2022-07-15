package com.thf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    private int projectId;
    private String projectName;
    private String describe;
    private int projectStatus;
    private int createUser;
    private long createTime;
    private int relateUser;
    private long startTime;
    private long endTime;
    private int progress;
}

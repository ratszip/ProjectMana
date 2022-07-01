package com.thf.entity;

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
    private String projectDesc;
    private int projectStatus;
    private int createdUser;
    private String createdTime;
    private int relateUser;
    private String updateTime;
}

package com.thf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private long projectId;
    private String projectName;
    private String describe;
    private int projectStatus;
    private long createUser;
    private long createTime;
    private long relateUser;
    private long startTime;
    private long endTime;
    private int progress;
    private List<Module> moduleList;
}

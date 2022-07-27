package com.thf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @NotNull
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
    private long actEndTime;
    private List<Module> moduleList;
}

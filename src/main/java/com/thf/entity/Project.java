package com.thf.entity;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
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
    private JSONArray relateUserList;
    private Date createTime;
    private long relateUser;
//    private Long[] users;
    private Date startTime;
    private Date endTime;
    private int progress;
    private Date actEndTime;
    private List<Module> moduleList;
}

package com.thf.service;

import com.thf.entity.Project;

public interface ProjectService {
    int insertProject(Project project);
    int updateProject(Project project);
    Project searchProject(Project project);
}

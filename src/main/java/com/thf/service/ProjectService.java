package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.Project;

public interface ProjectService {
    ResultVO createProject(String token,Project project);
    int updateProject(Project project);
    Project searchProject(Project project);
}

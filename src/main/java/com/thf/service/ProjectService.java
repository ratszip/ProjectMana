package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.Project;

public interface ProjectService {
    ResultVO createProject(String token,Project project);
    ResultVO updateProject(String token,Project project);
    ResultVO searchById(int id);
    ResultVO deleteProject(String token,Project project);
}

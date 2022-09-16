package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.Project;

public interface ProjectService {
    ResultVO createProject(String token, Project project);
    ResultVO updateProject(String token,Project project);
    ResultVO searchById(long id,String token);
    ResultVO searchMyKey(String token,String key);
    ResultVO deleteProject(String token,long[] pidArr);
    ResultVO getAllProjectDetail(String token,int type);
    ResultVO getList(String token,int type);
    ResultVO getProjectDetail(String token,long pid);
}

package com.thf.service.Impl;

import com.thf.dao.ProjectDAO;
import com.thf.entity.Project;
import com.thf.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Resource
    ProjectDAO projectDAO;

    @Override
    public int insertProject(Project project) {
        return 0;
    }

    @Override
    public int updateProject(Project project) {
        return 0;
    }

    @Override
    public Project searchProject(Project project) {
        return null;
    }
}

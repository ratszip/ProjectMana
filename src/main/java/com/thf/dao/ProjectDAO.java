package com.thf.dao;

import com.thf.entity.Project;

public interface ProjectDAO {
    public int insertProject(Project project);
    public int updateProject();
    public Project searchProject();
    public Project getInfo();
}

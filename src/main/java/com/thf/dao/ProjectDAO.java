package com.thf.dao;

import com.thf.entity.Project;

public interface ProjectDAO {
    public int insertProject();
    public int updateProject();
    public Project searchProject();
}

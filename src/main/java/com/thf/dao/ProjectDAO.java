package com.thf.dao;

import com.thf.entity.Project;

import java.util.List;

public interface ProjectDAO {
     int insertProject(Project project);
     int updateProject(Project project);
     Project searchProject(int id);
     Project getInfo();
     List<Project> getList(int uid);
}

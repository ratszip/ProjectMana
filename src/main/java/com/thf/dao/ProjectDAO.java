package com.thf.dao;

import com.thf.entity.Project;

import java.util.List;

public interface ProjectDAO {
     int insertProject(Project project);
     int updateProject(Project project);
     Project searchById(long id);
     List<Project> searchMyKeyD(Project project);
     List<Project> getAllProjectDet(Project project);
     int delete(long id);
}

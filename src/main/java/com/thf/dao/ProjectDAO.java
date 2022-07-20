package com.thf.dao;

import com.thf.common.oo.ProjectVO;
import com.thf.entity.Project;

import java.util.List;

public interface ProjectDAO {
     int insertProject(Project project);
     int updateProject(Project project);
     Project searchById(int id);
     List<Project> getAllProject(Project project);
}

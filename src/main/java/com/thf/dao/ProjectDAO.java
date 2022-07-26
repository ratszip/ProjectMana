package com.thf.dao;

import com.thf.common.oo.ProjectVO;
import com.thf.entity.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectDAO {
     int insertProject(Project project);
     int updateProject(Project project);
     Project searchById(long id);
     List<Project> searchKey(@Param("key") String key);
     List<Project> getAllProject(Project project);
}

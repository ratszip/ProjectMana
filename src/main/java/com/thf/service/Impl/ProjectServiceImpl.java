package com.thf.service.Impl;

import com.thf.common.oo.ResultVO;
import com.thf.common.utils.JwtUtil;
import com.thf.common.utils.PMUtils;
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
    public ResultVO createProject(String token,Project project) {
        Integer id= (Integer) JwtUtil.parseToken(token).get("id");
        project.setCreateUser(id);
        project.setCreateTime(System.currentTimeMillis());
        if(projectDAO.insertProject(project)>0){
            return new ResultVO(2000,"创建成功",project);
        }

        return null;
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

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

        return new ResultVO(5000,"创建失败",null);
    }

    @Override
    public ResultVO updateProject(String token, Project project) {
       // Integer uid= (Integer) JwtUtil.parseToken(token).get("id");
       int pid= project.getProjectId();
       if(projectDAO.searchById(pid)!=null){
           if(projectDAO.updateProject(project)>0){
               Project pro= projectDAO.searchById(pid);
               return new ResultVO(2000,"更新资料成功",pro);
           }
           return new ResultVO(5000,"更新失败");
       }
        return null;
    }

    @Override
    public ResultVO searchById(int id) {
        Project project=projectDAO.searchById(id);
        if(project!=null){
            return new ResultVO(2000,"搜索成功",project);
        }
        return new ResultVO(5000,"没有数据");
    }

    @Override
    public ResultVO deleteProject(String token, Project project) {
        Integer id= (Integer) JwtUtil.parseToken(token).get("id");
        return null;
    }
}

package com.thf.service.Impl;

import com.thf.common.oo.ProjectVO;
import com.thf.common.oo.Res;
import com.thf.common.oo.ResultVO;
import com.thf.common.utils.JwtUtil;
import com.thf.dao.FunctionDAO;
import com.thf.dao.ModuleDAO;
import com.thf.dao.ProjectDAO;
import com.thf.entity.Function;
import com.thf.entity.Module;
import com.thf.entity.Project;
import com.thf.service.FunctionService;
import com.thf.service.ModuleService;
import com.thf.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Resource
    ProjectDAO projectDAO;
    @Resource
    ModuleDAO moduleDAO;
    @Resource
    FunctionDAO functionDAO;
    @Resource
    ModuleService moduleService;

    @Override
    public ResultVO createProject(String token, Project project) {
        Integer id = (Integer) JwtUtil.parseToken(token).get("uid");
        project.setCreateUser(id);
        project.setCreateTime(System.currentTimeMillis());
        if (projectDAO.insertProject(project) > 0) {
            return Res.res(2000, "创建成功", project);
        }

        return Res.res(5000, "创建失败", null);
    }

    @Override
    public ResultVO updateProject(String token, Project project) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        long pid = project.getProjectId();
        if (projectDAO.searchById(pid) != null) {
            long cid = projectDAO.searchById(pid).getCreateUser();
            long rid = projectDAO.searchById(pid).getRelateUser();
            if (cid == uid || rid == uid) {
                if (projectDAO.updateProject(project) > 0) {
                    Project pro = projectDAO.searchById(pid);
                    return Res.res(2000, "更新资料成功", pro);
                }
                return Res.res(5000, "更新失败");
            }
            return Res.res(2000, "项目与用户不匹配");
        }
        return Res.res(2000, "找不到对应的项目id");
    }

    @Override
    public ResultVO searchById(long pid, String token) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        Project project = projectDAO.searchById(pid);
        if (project != null) {
            long cid = projectDAO.searchById(pid).getCreateUser();
            long rid = projectDAO.searchById(pid).getRelateUser();
            if (cid == uid || rid == uid) {
                return Res.res(2000, "搜索成功", project);
            } else {
                return Res.res(2000, "项目与用户不匹配");
            }
        }
        return Res.res(5000, "没有数据");
    }

    @Override
    public ResultVO getList(String token, int type) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        Project project;
        if (type == 3) {
            return searchMyKey(token, null);
        } else if (type == 1) {
            project = new Project();
            project.setCreateUser(uid);
            List<Project> list = projectDAO.searchMyKeyD(project);
            return Res.res(2000, "获取成功", new ProjectVO(list, list.size()));
        } else if (type == 2) {
            project = new Project();
            project.setRelateUser(uid);
            List<Project> list = projectDAO.searchMyKeyD(project);
            return Res.res(2000, "获取成功", new ProjectVO(list, list.size()));
        }
        return Res.res(4000, "输入正确的type值");
    }

    @Override
    public ResultVO searchMyKey(String token, String key) {
        if (key != null) {
            if (key.trim().equals("")) {
                return Res.res(2000, "搜索内容不能为空");
            }
        }
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        Project project = new Project();
        project.setCreateUser(uid);
        project.setRelateUser(uid);
        project.setProjectName(key);
        List<Project> projectList = projectDAO.searchMyKeyD(project);
        ProjectVO projectVO = new ProjectVO(projectList, projectList.size());
        if (projectList.size() > 0) {
            return Res.res(2000, "获取成功", projectVO);
        }
        return Res.res(2000, "没有数据", projectVO);
    }

    @Override
    public ResultVO deleteProject(String token, long[] pidArr) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        for (long pid:pidArr) {
            Project project=null;
            if((project=projectDAO.searchById(pid))==null){
                return Res.res(4000,"没有对应的项目pid:"+pid);
            }
           List<Module> moduleList= moduleDAO.searchAllModule(pid);
//            moduleService.deleteModule(token);
        }
        return null;
    }

    @Override
    public ResultVO getAllProjectDetail(String token, int type) {
        long id = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        Project project = new Project();
        if (type == 1) {
            project.setCreateUser(id);
        } else if (type == 2) {
            project.setRelateUser(id);
        } else if (type == 3) {
            project.setCreateUser(id);
            project.setRelateUser(id);
        } else {
            return Res.res(4000, "type值错误");
        }
        List<Project> projectList = projectDAO.getAllProjectDet(project);
        for (Project po : projectList) {
            List<Module> moduleList = moduleDAO.searchAllModule(po.getProjectId());
            for (Module mo : moduleList) {
                List<Function> functionList = functionDAO.searchAllFunction(mo.getMid());
                mo.setFunctionList(functionList);
            }
            po.setModuleList(moduleList);
        }
        ProjectVO projectVO = new ProjectVO(projectList, projectList.size());
        return Res.res(2000, "获取项目列表成功", projectVO);
    }

    @Override
    public ResultVO getProjectDetail(String token, long pid) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        List<Module> moduleList = moduleDAO.searchAllModule(pid);
        for (Module mo : moduleList) {
            List<Function> functionList = functionDAO.searchAllFunction(mo.getMid());
            mo.setFunctionList(functionList);
        }
        Project projectDB=projectDAO.searchById(pid);
        long rid=projectDB.getRelateUser();
        long cid=projectDB.getCreateUser();
        if(uid==rid||cid==uid){
            projectDB.setModuleList(moduleList);
            return Res.res(2000, "获取项目详情成功", projectDB);
        }
       return Res.res(2000,"项目不属于对应用户");
    }
}


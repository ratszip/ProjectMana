package com.thf.service.Impl;

import com.alibaba.fastjson.JSONArray;
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
import com.thf.service.ModuleService;
import com.thf.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
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
//        project.setRelateUserList(StringFix.arrayToString(project.getUsers()));
//        project.setUsers(null);
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
//            String ridstr = projectDAO.searchById(pid).getRelateUserList();
            JSONArray ridar=projectDAO.searchById(pid).getRelateUserList();
//            ridar = StringFix.stringToArray(ridstr);
            Long[] longs =new Long[]{};
            longs=ridar.toJavaList(long.class).toArray(longs);
            if (cid == uid || Arrays.stream(longs).anyMatch(rid->rid==uid)) {
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
//            String ridstr = projectDAO.searchById(pid).getRelateUserList();
//            long[] ridset =  StringFix.stringToArray(ridstr);
//            long[] ridset = projectDAO.searchById(pid).getRelateUserList();
            JSONArray ridar=projectDAO.searchById(pid).getRelateUserList();
//            ridar = StringFix.stringToArray(ridstr);
            Long[] longs =new Long[]{};
            longs=ridar.toJavaList(long.class).toArray(longs);
            if (cid == uid || Arrays.stream(longs).anyMatch(rid->rid==uid)) {
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
        for (long pid : pidArr) {
            Project project = null;
            if ((project = projectDAO.searchById(pid)) == null) {
                return Res.res(4000, "没有对应的项目pid:" + pid);
            }
            long cid = projectDAO.searchById(pid).getCreateUser();
//            String ridstr = projectDAO.searchById(pid).getRelateUserList();
//            long[] ridset= StringFix.stringToArray(ridstr);
//            long[] ridset = projectDAO.searchById(pid).getRelateUserList();
            JSONArray ridar=projectDAO.searchById(pid).getRelateUserList();
//            ridar = StringFix.stringToArray(ridstr);
            Long[] longs =new Long[]{};
            longs=ridar.toJavaList(long.class).toArray(longs);
            if(cid!=uid&&!Arrays.stream(longs).anyMatch(rid->rid==uid)){
                return Res.res(4000,"只能删除自己的项目");
            }
        }
        for (long pid:pidArr) {
            List<Module> moduleList = moduleDAO.searchAllModule(pid);
            long[] midArr = new long[moduleList.size()];
            for (int i = 0; i < moduleList.size(); i++) {
                midArr[i] = moduleList.get(i).getMid();
            }
            moduleService.deleteModule(token, midArr);
            projectDAO.delete(pid);
            if (pidArr[pidArr.length - 1] == pid) {
                return Res.res(2000, "删除成功");
            }
        }
        return Res.res(5000, "服务器错误，删除失败");
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
        Project projectDB;
        if((projectDB = projectDAO.searchById(pid))==null){
            return Res.res(4000,"该项目不存在");
        }
//        String ridstr = projectDAO.searchById(pid).getRelateUserList();
//        long[] ridarr=StringFix.stringToArray(ridstr);
        JSONArray ridar=projectDAO.searchById(pid).getRelateUserList();
//            ridar = StringFix.stringToArray(ridstr);
        Long[] longs =new Long[]{};
        longs=ridar.toJavaList(long.class).toArray(longs);
        long cid = projectDB.getCreateUser();
        if (Arrays.stream(longs).anyMatch(rid->rid==uid) || cid == uid) {
            projectDB.setModuleList(moduleList);
            return Res.res(2000, "获取项目详情成功", projectDB);
        }
        return Res.res(2000, "项目不属于对应用户");
    }
}


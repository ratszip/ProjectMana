package com.thf.service.Impl;

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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Resource
    ModuleDAO moduleDAO;
    @Resource
    ProjectDAO projectDAO;
    @Resource
    FunctionDAO functionDAO;

    @Override
    public ResultVO createModule(String token, long pid, Module module) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        Project project = new Project();
        project.setCreateUser(uid);
        List<Project> lp = projectDAO.getAllProjectDet(project);
        if (lp == null) {
            Res.res(2000, "请先建立项目");
        } else {
            for (Project po : lp) {
                if (pid == po.getProjectId()) {
                    module.setPid(pid);
                    module.setMcreateTime(System.currentTimeMillis());
                    module.setMcreateUser(uid);
                    if (moduleDAO.insert(module) > 0) {
                        return Res.res(2000, "新建成功", moduleDAO.searchBymId(module.getMid()));
                    }
                    return Res.res(5000, "新建失败");
                }
            }
            return Res.res(2000, "用户没有对应的项目id");
        }
        return Res.res(5000, "新建模块失败");
    }

    @Override
    public ResultVO updateModule(String token, Module module) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        Module moduleDB = moduleDAO.searchBymId(module.getMid());
        long cid = projectDAO.searchById(moduleDB.getPid()).getCreateUser();
        long rid = projectDAO.searchById(moduleDB.getPid()).getRelateUser();
        if (cid == uid || rid == uid) {
            if (moduleDAO.update(module) > 0) {
                return Res.res(2000, "修改成功", moduleDAO.searchBymId(module.getMid()));
            }
            return Res.res(5000, "修改失败");
        }
        return Res.res(4000, "仅可更改自己账户下的模块");
    }

    @Override
    public ResultVO deleteModule(String token, long[] midArr) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        long cid;
        long rid;
        Module module;
        for (long mid : midArr) {
            if ((module = moduleDAO.searchBymId(mid)) == null) {
                return Res.res(2000, "模块不存在，mid:" + mid);
            }
            Project project = projectDAO.searchById(module.getPid());
            cid = project.getCreateUser();
            rid = project.getRelateUser();
            if (cid == uid || rid == uid) {
                break;
            } else {
                return Res.res(4000, "只能删除自己名下的模块");
            }
        }
        for (long mid : midArr) {
            functionDAO.deleteAll(mid);
            moduleDAO.delete(mid);
            if (midArr[midArr.length - 1] == mid) {
                return Res.res(2000, "删除成功");
            }
        }

        return Res.res(5000, "服务器错误");
    }


    @Override
    public ResultVO searchByIdDtl(String token, long mid) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        Module module = null;
        if ((module = moduleDAO.searchBymId(mid)) != null) {
            List<Function> functionList=functionDAO.searchAllFunction(mid);
            module.setFunctionList(functionList);
            return Res.res(2000, "成功", module);
        }
        return Res.res(5000, "没有找到对应模块");
    }

    @Override
    public ResultVO searchByPid(String token, long pid) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        long rid = projectDAO.searchById(pid).getRelateUser();
        long cid = projectDAO.searchById(pid).getCreateUser();
        if (rid == uid || cid == uid) {
            List<Module> moduleList = null;
            if ((moduleList = moduleDAO.searchAllModule(pid)) != null) {
                for (Module mo:moduleList) {
                    List<Function> functionList=functionDAO.searchAllFunction(mo.getMid());
                    mo.setFunctionList(functionList);
                }
                return Res.res(2000, "获取成功", moduleList);
            }
            return Res.res(5000, "没有找到对应项目模块");
        }
        return Res.res(4000, "只能搜索自己名下的模块");
    }
}

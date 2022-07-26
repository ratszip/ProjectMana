package com.thf.service.Impl;

import com.thf.common.oo.Res;
import com.thf.common.oo.ResultVO;
import com.thf.common.utils.JwtUtil;
import com.thf.dao.FunctionDAO;
import com.thf.dao.ModuleDAO;
import com.thf.dao.ProjectDAO;
import com.thf.dao.UserDAO;
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
        long uid = (long) JwtUtil.parseToken(token).get("uid");
        Project project = new Project();
        project.setCreateUser(uid);
        List<Project> lp = projectDAO.getAllProject(project);
        if (lp == null) {
            Res.res(2000, "请先建立项目");
        } else {
            for (Project po : lp) {
                if (pid == po.getProjectId()) {
                    module.setPId(pid);
                    module.setMCreateTime(System.currentTimeMillis());
                    if (moduleDAO.insert(module) > 0) {
                        return Res.res(2000, "新建成功", moduleDAO.searchBymId(module.getMId()));
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
        long uid = (long) JwtUtil.parseToken(token).get("uid");
        if (projectDAO.searchById(module.getPId()).getCreateUser() == uid) {
            if (moduleDAO.update(module) > 0) {
                return Res.res(2000, "修改成功", moduleDAO.searchBymId(module.getMId()));
            }
            return Res.res(5000, "修改失败");
        }
        return Res.res(4000, "仅可更改自己账户下的模块");
    }

    @Override
    public ResultVO deleteModule(String token, long[] midArr) {
        long uid = (long) JwtUtil.parseToken(token).get("uid");
        for (long mid : midArr) {
            Project project = projectDAO.searchById(moduleDAO.searchBymId(mid).getPId());
            long uiid = project.getCreateUser();
            if (uiid == uid) {
                functionDAO.deleteAll(mid);
                moduleDAO.delete(mid);
                if (midArr[midArr.length - 1] == mid) {
                    return Res.res(2000, "删除成功");
                }
            }
        }
        return Res.res(5000, "服务器错误");
    }


    @Override
    public ResultVO searchById(long mid) {
        Module module = moduleDAO.searchBymId(mid);
        if (module != null) {
            return Res.res(2000, "成功", module);
        }
        return Res.res(5000, "没有找到对应模块");
    }
}

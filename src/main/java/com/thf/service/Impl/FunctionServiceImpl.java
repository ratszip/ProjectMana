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
import com.thf.service.FunctionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FunctionServiceImpl implements FunctionService {

    @Resource
    FunctionDAO functionDAO;
    @Resource
    ProjectDAO projectDAO;
    @Resource
    ModuleDAO moduleDAO;

    @Override
    public ResultVO createFunc(String token, int mid, Function function) {
        int uid = (int) JwtUtil.parseToken(token).get("uid");
        Project project = new Project();
        project.setCreateUser(uid);
        List<Project> lp = null;
        List<Module> lm = null;
        if ((lp = projectDAO.getAllProject(project)) == null) {
            Res.res(2000, "请先创建项目");
        } else {
            for (Project po : lp) {
                lm = moduleDAO.searchAllModule(po.getProjectId());
                for (Module mo : lm) {
                    if (mo.getMId() == mid) {
                        function.setMId(mid);
                        function.setFCreateTime(System.currentTimeMillis());
                        if (functionDAO.insert(function) > 0) {
                            return Res.res(2000, "新增功能成功", functionDAO.searchById(function.getFId()));
                        } else {
                            return Res.res(5000, "新增失败");
                        }
                    }
                }
                return Res.res(2000, "请先创建模块");
            }
        }
        return Res.res(5000, "新增失败");
    }

    @Override
    public ResultVO updateFunc(String token, Function function) {
        int uid = (int) JwtUtil.parseToken(token).get("uid");
        int mpid = moduleDAO.searchBymId(function.getMId()).getPId();
        if (projectDAO.searchById(mpid).getCreateUser() == uid) {
            if (functionDAO.update(function) > 0) {
                return Res.res(2000, "修改成功", functionDAO.searchById(function.getFId()));
            }
            return Res.res(5000, "修改失败");
        }
        return Res.res(4000, "仅可更改自己账户下的功能");
    }

    @Override
    public ResultVO deleteFunc(String token, int[] fidArr) {
        int uid = (int) JwtUtil.parseToken(token).get("uid");
        for (int fid:fidArr) {
            int mpid = moduleDAO.searchBymId(functionDAO.searchById(fid).getMId()).getPId();
            if (projectDAO.searchById(mpid).getCreateUser() == uid) {
                functionDAO.delete(fid);
                    if(fidArr[fidArr.length-1]==fid) {
                        return Res.res(2000, "删除成功");
                }
            }
        }
        return Res.res(5000, "服务器内部错误");
    }

    @Override
    public ResultVO searchFunc(int fid) {
        Function f = functionDAO.searchById(fid);
        if (f != null) {
            return Res.res(2000, "搜索成功", f);
        }
        return Res.res(2000, "搜索失败");
    }
}

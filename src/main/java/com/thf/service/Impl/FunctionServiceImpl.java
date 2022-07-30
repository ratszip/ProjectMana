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
    public ResultVO createFunc(String token, long mId, Function function) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        Project project = new Project();
        project.setCreateUser(uid);
        project.setRelateUser(uid);
        List<Project> lp = null;
        List<Module> lm = null;
        if ((lp = projectDAO.searchMyKeyD(project)) == null) {
            Res.res(2000, "请先创建项目");
        } else {
            for (Project po : lp) {
                lm = moduleDAO.searchAllModule(po.getProjectId());
                for (Module mo : lm) {
                    if (mo.getMid() == mId) {
                        function.setMid(mId);
                        function.setFcreateUser(uid);
                        function.setFcreateTime(System.currentTimeMillis());
                        if (functionDAO.insert(function) > 0) {
                            return Res.res(2000, "新增功能成功", functionDAO.searchById(function.getFid()));
                        } else {
                            return Res.res(5000, "新增失败");
                        }
                    }
                }
            }
            return Res.res(2000, "请先创建模块");
        }
        return Res.res(5000, "新增失败");
    }

    @Override
    public ResultVO updateFunc(String token, Function function) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        long mid = functionDAO.searchById(function.getFid()).getMid();
        long mpid = moduleDAO.searchBymId(mid).getPid();
        long cid = projectDAO.searchById(mpid).getCreateUser();
        long rid = projectDAO.searchById(mpid).getRelateUser();
        if (cid == uid || rid == uid) {
            if (functionDAO.update(function) > 0) {
                return Res.res(2000, "修改成功", functionDAO.searchById(function.getFid()));
            }
            return Res.res(5000, "修改失败");
        }
        return Res.res(4000, "仅可更改自己账户下的功能");
    }

    @Override
    public ResultVO deleteFunc(String token, long[] fidArr) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        long rid;
        long cid;
        long mpid;
        Function function;
        for (long fid : fidArr) {
            if((function= functionDAO.searchById(fid))==null){
                return Res.res(4000,"对应功能不存在,fid:"+fid);
            }
            mpid = moduleDAO.searchBymId(function.getMid()).getPid();
            rid = projectDAO.searchById(mpid).getRelateUser();
            cid = projectDAO.searchById(mpid).getCreateUser();
            if (cid == uid || rid == uid) {
               break;
            }else {
                return Res.res(4000, "只能删除自己名下的功能");
            }
        }
        for (long fid:fidArr) {
            functionDAO.delete(fid);
            if (fidArr[fidArr.length - 1] == fid) {
                return Res.res(2000, "删除成功");
            }
        }
        return Res.res(5000, "服务器内部错误");
    }

    @Override
    public ResultVO searchFunc(String token,long fid) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        Function f ;
        if ((f=functionDAO.searchById(fid)) == null) {
            return Res.res(2000, "找不到对应的功能ID");
        }
        Project project=projectDAO.searchById(moduleDAO.searchBymId(f.getMid()).getPid());
        long cid=project.getCreateUser();
        long rid=project.getRelateUser();
        if(cid==uid||rid==cid){
            return Res.res(2000,"获取成功",f);
        }
        return Res.res(4000, "只能查看自己名下的功能信息");
    }

    @Override
    public ResultVO searchAllFunc(String token,long mid) {
        long uid = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        Module m ;
        if ((m=moduleDAO.searchBymId(mid)) == null) {

            return Res.res(2000, "找不到对应的功能ID");
        }
        Project project=projectDAO.searchById(m.getPid());
        long cid=project.getCreateUser();
        long rid=project.getRelateUser();
        if(cid==uid||rid==cid){
            List<Function> functionList=functionDAO.searchAllFunction(mid);
            return Res.res(2000,"获取成功",functionList);
        }
        return Res.res(4000, "只能查看自己名下的功能信息");
    }
}

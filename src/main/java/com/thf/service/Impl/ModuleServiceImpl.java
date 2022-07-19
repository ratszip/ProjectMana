package com.thf.service.Impl;

import com.thf.common.oo.ResultVO;
import com.thf.dao.ModuleDAO;
import com.thf.entity.Module;
import com.thf.service.ModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Resource
    ModuleDAO moduleDAO;

    @Override
    public ResultVO createModule(int pid, Module module) {
        module.setPId(pid);
        if(moduleDAO.insert(module)!=null){
            return new ResultVO(2000,"新建成功",searchById(module.getMId()));
        }
        return new ResultVO(2000,"新建模块失败");
    }

    @Override
    public ResultVO updateModule(Module module) {
        if(moduleDAO.update(module)>0){
            return new ResultVO(2000,"修改成功",searchById(module.getMId()));
        }
        return new ResultVO(2000,"修改失败");
    }

    @Override
    public ResultVO deleteModule(int mid) {
        if (moduleDAO.delete(mid) > 0) {
            return new ResultVO(2000,"删除成功");
        }
        return new ResultVO(2000,"删除失败");
    }

    @Override
    public ResultVO deleteAllModule(int pid) {

        return null;
    }

    @Override
    public ResultVO searchById(int mid) {
        Module module= moduleDAO.searchBymId(mid);
        if(module!=null){
            return new ResultVO(2000,"成功",module);
        }
        return new ResultVO(5000,"没有找到对应模块");
    }
}

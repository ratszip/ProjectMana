package com.thf.service.Impl;

import com.thf.common.oo.Res;
import com.thf.common.oo.ResultVO;
import com.thf.dao.FunctionDAO;
import com.thf.entity.Function;
import com.thf.service.FunctionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FunctionServiceImpl implements FunctionService {

    @Resource
    FunctionDAO functionDAO;
    @Override
    public ResultVO createFunc(int mid, Function function) {
        function.setMId(mid);
        if(functionDAO.insert(function)!=null){
            return new ResultVO(2000,"新建成功",searchFunc(function.getFId()));
        }
        return new ResultVO(2000,"新增功能失败");
    }

    @Override
    public ResultVO updateFunc(Function function) {
        if(functionDAO.update(function)>0){
            return new ResultVO(2000,"修改成功",searchFunc(function.getFId()));
        }
        return new ResultVO(2000,"修改失败");
    }

    @Override
    public ResultVO deleteFunc(int fid) {
        if(functionDAO.delete(fid)>0){
           return Res.res(2000,"删除成功",null);
        }
        return Res.res(2000,"删除失败",null);
    }

    @Override
    public ResultVO searchFunc(int fid) {
        Function f=functionDAO.searchById(fid);
        if(f!=null){
            return new ResultVO(2000,"搜索成功",f);
        }
        return new ResultVO(2000,"搜索失败");
    }
}

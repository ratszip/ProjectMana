package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.Function;

public interface FunctionService {
    ResultVO createFunc(String token,int mid, Function function);
    ResultVO updateFunc(Function function);
    ResultVO deleteFunc(int fid);
    ResultVO searchFunc(int fid);
}

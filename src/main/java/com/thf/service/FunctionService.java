package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.Function;

public interface FunctionService {
    ResultVO createFunc(String token,int mid, Function function);
    ResultVO updateFunc(String token,Function function);
    ResultVO deleteFunc(String token,int[] fidlist);
    ResultVO searchFunc(int fid);
}

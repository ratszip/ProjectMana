package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.Function;

public interface FunctionService {
    ResultVO createFunc(String token,long mid, Function function);
    ResultVO updateFunc(String token,Function function);
    ResultVO deleteFunc(String token,long[] fidlist);
    ResultVO searchFunc(long fid);
}

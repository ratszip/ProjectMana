package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.Module;

public interface ModuleService {
    ResultVO createModule(String token,long pid ,Module module);
    ResultVO updateModule(String token,Module module);
    ResultVO deleteModule(String token,long[] midlist);

    ResultVO searchByIdDtl(String token, long mid);

    ResultVO searchByPid(String token, long pid);
}

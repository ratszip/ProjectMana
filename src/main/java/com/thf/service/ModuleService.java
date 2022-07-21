package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.Module;

public interface ModuleService {
    ResultVO createModule(String token,int pid ,Module module);
    ResultVO updateModule(String token,Module module);
    ResultVO deleteModule(String token,int[] midlist);
    ResultVO searchById(int mid);
}

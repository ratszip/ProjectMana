package com.thf.service;

import com.thf.common.oo.ResultVO;
import com.thf.entity.Module;

public interface ModuleService {
    ResultVO createModule(int pid ,Module module);
    ResultVO updateModule(Module module);
    ResultVO deleteModule(int mid);
    ResultVO deleteAllModule(int pid);
    ResultVO searchById(int mid);
}

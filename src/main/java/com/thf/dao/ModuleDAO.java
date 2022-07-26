package com.thf.dao;

import com.thf.entity.Module;

import java.util.List;

public interface ModuleDAO {
    int insert(Module module);
    Module searchBymId(long id);
    List<Module> searchAllModule(long pid);
    int update(Module module);
    int delete(long id);
}

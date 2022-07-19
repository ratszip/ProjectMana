package com.thf.dao;

import com.thf.entity.Module;

import java.util.List;

public interface ModuleDAO {
    Module insert(Module module);
    Module searchBymId(int id);
    List<Module> searchAll(int pid);
    int update(Module module);
    int delete(int id);
}

package com.thf.dao;

import com.thf.entity.Function;

import java.util.List;

public interface FunctionDAO {
    Function insert(Function function);
    int update(Function function);
    Function searchById(int id);
    int delete(int id);
    List<Function> getList(int mid);
}

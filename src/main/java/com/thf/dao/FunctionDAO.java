package com.thf.dao;

import com.thf.entity.Function;

import java.util.List;

public interface FunctionDAO {
    int insert(Function function);
    int update(Function function);
    Function searchById(int fId);
    int delete(int id);
    List<Function> searchAllFunction(int mid);
}

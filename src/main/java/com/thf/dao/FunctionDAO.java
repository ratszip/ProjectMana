package com.thf.dao;

import com.thf.entity.Function;

import java.util.List;

public interface FunctionDAO {
    int insert(Function function);
    int update(Function function);
    Function searchById(long fId);
    int delete(long id);
    int deleteAll(long mid);
    List<Function> searchAllFunction(long mid);
}

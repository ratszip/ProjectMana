package com.thf.dao;

import com.thf.entity.Province;

import java.util.List;

public interface ProvinceDAO {
    List<Province> selectAll();
    Province selectById(int provinceId);
}

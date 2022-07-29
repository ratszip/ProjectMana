package com.thf.dao;

import com.thf.entity.City;

import java.util.List;

public interface CityDAO {
    List<City> selectAllCity();
    City selectById(int cid);
    List<City> selectBySId(int sid);
}

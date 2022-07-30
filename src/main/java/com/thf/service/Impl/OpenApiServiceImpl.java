package com.thf.service.Impl;

import com.thf.common.oo.Res;
import com.thf.common.oo.ResultVO;
import com.thf.dao.CityDAO;
import com.thf.dao.ProvinceDAO;
import com.thf.entity.City;
import com.thf.entity.Province;
import com.thf.service.OpenApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OpenApiServiceImpl implements OpenApiService {

    @Resource
    ProvinceDAO provinceDAO;
    @Resource
    CityDAO cityDAO;

    @Override
    public ResultVO searchAllProvince() {
        List<Province> provinceList= provinceDAO.selectAll();

        return Res.res(2000,"获取省份成功",provinceList);
    }

    @Override
    public ResultVO searchAllCity() {
        List<City> cityList=cityDAO.selectAllCity();
        return Res.res(2000,"获取城市成功",cityList);
    }

    @Override
    public ResultVO searchSCity(int provinceId) {
        List<City> cityList=cityDAO.selectBySId(provinceId);
        Province province=provinceDAO.selectById(provinceId);
        province.setCityList(cityList);

        return Res.res(2000,"获取省市成功",province);
    }
}

package com.thf.service;

import com.thf.common.oo.ResultVO;

public interface OpenApiService {
    ResultVO searchAllProvince();
    ResultVO searchAllCity();
    ResultVO searchSCity(int sId);
}

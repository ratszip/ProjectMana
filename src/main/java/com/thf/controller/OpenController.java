package com.thf.controller;

import com.thf.common.oo.ResultVO;
import com.thf.config.MultiRequestBody;

import com.thf.service.OpenApiService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/open")
@ResponseBody
@CrossOrigin
public class OpenController {

    @Resource
    OpenApiService openApiService;

    @RequestMapping("/province")
    public ResultVO searchPList() {
        return openApiService.searchAllProvince();
    }

    @RequestMapping("/city")
    public ResultVO searchCList() {
        return openApiService.searchAllCity();
    }

    @RequestMapping("/province/city")
    public ResultVO searchPC(@MultiRequestBody int id) {
        return openApiService.searchSCity(id);
    }

}

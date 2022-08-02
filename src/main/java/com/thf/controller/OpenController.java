package com.thf.controller;

import com.thf.common.oo.ResultVO;
import com.thf.config.MultiRequestBody;
import com.thf.entity.Function;
import com.thf.service.FunctionService;
import com.thf.service.OpenApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@Controller
@RequestMapping("/open")
@ResponseBody
@Api(value = "公开", tags = "开放")
public class OpenController {

    @Resource
    OpenApiService openApiService;


    @ApiOperation(value = "搜索省份", httpMethod = "POST")
    @RequestMapping("/province")
    public ResultVO searchPList() {
        return openApiService.searchAllProvince();
    }

    @ApiOperation(value = "搜索城市", httpMethod = "POST")
    @RequestMapping("/city")
    public ResultVO searchCList() {
        return openApiService.searchAllCity();
    }
    @ApiImplicitParam(name = "id", value = "省份id", dataType = "int", paramType = "body", required = true)
    @ApiOperation(value = "根据省份搜索城市", httpMethod = "POST")
    @RequestMapping("/province/city")
    public ResultVO searchPC(@MultiRequestBody int id) {
        return openApiService.searchSCity(id);
    }

}

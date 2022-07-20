package com.thf.controller;

import com.thf.common.oo.ResultVO;
import com.thf.config.MultiRequestBody;
import com.thf.entity.Function;
import com.thf.entity.Module;
import com.thf.service.FunctionService;
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
@RequestMapping("/function")
@ResponseBody
@Api(value = "功能",tags = "功能")
public class FunctionController {

    @Resource
    FunctionService functionService;
    @ApiImplicitParams({
            @ApiImplicitParam(name="mid",value ="模块id",required = true,dataType = "int",paramType = "body"),
            @ApiImplicitParam(name="fName",value ="功能名称",required = true,dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="fDes",value ="描述",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="fStartTime",value ="开始时间",dataType = "long",paramType = "body"),
            @ApiImplicitParam(name="fEndTime",value ="结束时间",dataType = "long",paramType = "body"),
    })
    @ApiOperation(value = "创建项目",httpMethod = "POST")
    @RequestMapping("/create")
    public ResultVO createModule(@RequestHeader String token,
                                 @MultiRequestBody int mid,
                                 @ApiIgnore @MultiRequestBody Function function){
        ResultVO resultVO= functionService.createFunc(token,mid,function);
        return resultVO;
    }
}
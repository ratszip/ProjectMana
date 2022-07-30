package com.thf.controller;

import com.thf.common.oo.ResultVO;
import com.thf.config.MultiRequestBody;
import com.thf.entity.Module;
import com.thf.service.ModuleService;
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
@RequestMapping("/module")
@ResponseBody
@Api(value = "模块",tags = "模块")
public class ModuleController {
    @Resource
    ModuleService moduleService;
    @ApiImplicitParams({
            @ApiImplicitParam(name="pId",value ="项目id",required = true,dataType = "int",paramType = "body"),
            @ApiImplicitParam(name="mName",value ="模块名称",required = true,dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="mDes",value ="描述",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="mStartTime",value ="开始时间",dataType = "long",paramType = "body"),
            @ApiImplicitParam(name="mEndTime",value ="结束时间",dataType = "long",paramType = "body")
    })
    @ApiOperation(value = "创建模块",httpMethod = "POST")
    @RequestMapping("/create")
    public ResultVO createModule(@RequestHeader String token,
                                  @MultiRequestBody int pId,
                                  @ApiIgnore @MultiRequestBody Module module){
        ResultVO resultVO= moduleService.createModule(token,pId,module);
        return resultVO;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="mId",value ="模块id",required = true,dataType = "long",paramType = "body"),
            @ApiImplicitParam(name="mName",value ="模块名称",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="mDes",value ="描述",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="mStartTime",value ="开始时间",dataType = "long",paramType = "body"),
            @ApiImplicitParam(name="mEndTime",value ="结束时间",dataType = "long",paramType = "body"),
            @ApiImplicitParam(name="mProgress",value ="进度",dataType = "int",paramType = "body"),
            @ApiImplicitParam(name="mStatus",value ="状态",dataType = "int",paramType = "body")
    })
    @ApiOperation(value = "更新模块",httpMethod = "POST")
    @RequestMapping("/update")
    public ResultVO updateModule(@RequestHeader String token,
                                 @ApiIgnore @MultiRequestBody Module module){
        ResultVO resultVO= moduleService.updateModule(token,module);
        return resultVO;
    }

    @ApiImplicitParam(name = "midArr", value = "模块id数组", required = true, dataType = "long[]", paramType = "body")
    @ApiOperation(value = "删除模块", httpMethod = "POST")
    @RequestMapping("/delete")
    public ResultVO deleteModule(@RequestHeader String token,
                                   @ApiIgnore @MultiRequestBody long[] midArr) {
        ResultVO resultVO = moduleService.deleteModule(token,midArr);
        return resultVO;
    }


    @ApiImplicitParam(name = "pid", value = "项目id", required = true, dataType = "long", paramType = "body")
    @ApiOperation(value = "删除模块", httpMethod = "POST")
    @RequestMapping("/list/detail")
    public ResultVO getMList(@RequestHeader String token,
                                 @ApiIgnore @MultiRequestBody long pid) {

        ResultVO resultVO = moduleService.searchByPid(token,pid);
        return resultVO;
    }

    @ApiImplicitParam(name = "mid", value = "模块id", required = true, dataType = "long", paramType = "body")
    @ApiOperation(value = "删除模块", httpMethod = "POST")
    @RequestMapping("/detail")
    public ResultVO getMDtl(@RequestHeader String token,
                             @ApiIgnore @MultiRequestBody long mid) {
        ResultVO resultVO = moduleService.searchByIdDtl(token,mid);
        return resultVO;
    }

}

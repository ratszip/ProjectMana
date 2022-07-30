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
@Api(value = "功能", tags = "功能")
public class FunctionController {

    @Resource
    FunctionService functionService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "mId", value = "模块id", required = true, dataType = "long", paramType = "body"),
            @ApiImplicitParam(name = "fName", value = "功能名称", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "fDes", value = "描述", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "fStartTime", value = "开始时间", dataType = "long", paramType = "body"),
            @ApiImplicitParam(name = "fEndTime", value = "结束时间", dataType = "long", paramType = "body")
    })
    @ApiOperation(value = "创建功能", httpMethod = "POST")
    @RequestMapping("/create")
    public ResultVO createModule(@RequestHeader String token,
                                 @MultiRequestBody long mId,
                                 @ApiIgnore @MultiRequestBody Function function) {
        ResultVO resultVO = functionService.createFunc(token, mId, function);
        return resultVO;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "fId", value = "功能id", required = true, dataType = "long", paramType = "body"),
            @ApiImplicitParam(name = "fName", value = "功能名称", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "fDes", value = "描述", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "fStartTime", value = "开始时间", dataType = "long", paramType = "body"),
            @ApiImplicitParam(name = "fEndTime", value = "结束时间", dataType = "long", paramType = "body"),
            @ApiImplicitParam(name = "fStatus", value = "状态", dataType = "int", paramType = "body"),
            @ApiImplicitParam(name = "fProgress", value = "进度", dataType = "int", paramType = "body"),
            @ApiImplicitParam(name = "fActEndTime", value = "实际完成时间", dataType = "long", paramType = "body")
    })
    @ApiOperation(value = "更新功能", httpMethod = "POST")
    @RequestMapping("/update")
    public ResultVO updateFunction(@RequestHeader String token,
                                   @ApiIgnore @MultiRequestBody Function function) {
        ResultVO resultVO = functionService.updateFunc(token, function);
        return resultVO;
    }


    @ApiImplicitParam(name = "fidArr", value = "功能id数组", required = true, dataType = "long[]", paramType = "body")
    @ApiOperation(value = "删除功能", httpMethod = "POST")
    @RequestMapping("/delete")
    public ResultVO deleteFunction(@RequestHeader String token,
                                   @ApiIgnore @MultiRequestBody long[] fidArr) {
        ResultVO resultVO = functionService.deleteFunc(token, fidArr);
        return resultVO;
    }

    @ApiImplicitParam(name = "mid", value = "模块id", required = true, dataType = "", paramType = "body")
    @ApiOperation(value = "删除功能", httpMethod = "POST")
    @RequestMapping("/list")
    public ResultVO listFunc(@RequestHeader String token,
                                   @ApiIgnore @MultiRequestBody long mid) {
        ResultVO resultVO = functionService.searchAllFunc(token, mid);
        return resultVO;
    }

}

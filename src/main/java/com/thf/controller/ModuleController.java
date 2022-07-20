package com.thf.controller;

import com.thf.common.oo.ResultVO;
import com.thf.config.MultiRequestBody;
import com.thf.entity.Module;
import com.thf.entity.Project;
import com.thf.service.ModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@Controller
@RequestMapping("/module")
@ResponseBody
@Api(value = "项目模块",tags = "项目模块")
public class ModuleController {
    @Resource
    ModuleService moduleService;
    @ApiImplicitParams({
            @ApiImplicitParam(name="pid",value ="项目id",required = true,dataType = "int",paramType = "body"),
            @ApiImplicitParam(name="mName",value ="模块名称",required = true,dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="mDes",value ="描述",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="mStartTime",value ="开始时间",dataType = "long",paramType = "body"),
            @ApiImplicitParam(name="mEndTime",value ="结束时间",dataType = "long",paramType = "body"),
    })
    @ApiOperation(value = "创建项目",httpMethod = "POST")
    @RequestMapping("/create")
    public ResultVO createModule(@RequestHeader String token,
                                  @MultiRequestBody int pid,
                                  @ApiIgnore @MultiRequestBody Module module){
        ResultVO resultVO= moduleService.createModule(token,pid,module);
        return resultVO;
    }
}

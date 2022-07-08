package com.thf.controller;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.thf.common.oo.ResultVO;
import com.thf.entity.Project;
import com.thf.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@Controller
@RequestMapping("/project")
@ResponseBody
@Api(value = "项目管理",tags = "项目管理模块")
public class ProjectController {
    @Resource
    private ProjectService projectService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="projectName",value ="项目名",required = true,dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="describe",value ="描述",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="relateUser",value ="关联人",dataType = "int",paramType = "body")
    })
    @ApiOperation(value = "创建项目",httpMethod = "POST")
    @RequestMapping("/create")
    public ResultVO createProject(@ApiIgnore Project project){

        return null;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="projectName",value ="项目名",required = true,dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="describe",value ="描述",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="relateUser",value ="关联人",dataType = "int",paramType = "body"),
            @ApiImplicitParam(name="projectStatus",value ="项目进度",dataType = "int",paramType = "body")
    })
    @ApiOperation(value = "修改项目",httpMethod = "POST")
    @RequestMapping("/update")
    public ResultVO updateProject(@ApiIgnore Project project ){

        return null;
    }

    @ApiImplicitParam(name="key",value ="搜索内容",dataType = "String",paramType = "body")
    @ApiOperation(value = "搜索项目",httpMethod = "POST")
    @RequestMapping("/search")
    public ResultVO searchProject(String key){

        return null;
    }
}

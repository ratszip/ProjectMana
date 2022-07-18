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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
            @ApiImplicitParam(name="relateUser",value ="关联人",dataType = "int",paramType = "body"),
            @ApiImplicitParam(name="startTime",value ="开始时间",dataType = "int",paramType = "body"),
            @ApiImplicitParam(name="endTime",value ="结束时间",dataType = "int",paramType = "body")
    })
    @ApiOperation(value = "创建项目",httpMethod = "POST")
    @RequestMapping("/create")
    public ResultVO createProject(@RequestHeader String token, @ApiIgnore @RequestBody Project project){
       ResultVO resultVO= projectService.createProject(token,project);
        return resultVO;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="projectName",value ="项目名",required = true,dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="describe",value ="描述",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="relateUser",value ="关联人",dataType = "int",paramType = "body"),
            @ApiImplicitParam(name="projectStatus",value ="项目状态",dataType = "int",paramType = "body"),
            @ApiImplicitParam(name="startTime",value ="项目开始时间",dataType = "int",paramType = "body"),
            @ApiImplicitParam(name="endTime",value ="计划结束时间",dataType = "int",paramType = "body"),
            @ApiImplicitParam(name="progress",value ="项目进度",dataType = "int",paramType = "body")
    })
    @ApiOperation(value = "修改项目",httpMethod = "POST")
    @RequestMapping("/update")
    public ResultVO updateProject(@RequestHeader String token, @ApiIgnore @RequestBody Project project ){
        return projectService.updateProject(token,project);
    }

    @ApiImplicitParam(name="id",value ="搜索内容",dataType = "String",paramType = "body")
    @ApiOperation(value = "搜索项目",httpMethod = "POST")
    @RequestMapping("/search")
    public ResultVO searchProject(@RequestBody int id){
        return projectService.searchById(id);
    }
}

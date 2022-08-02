package com.thf.controller;

import com.thf.common.oo.Res;
import com.thf.common.oo.ResultVO;
import com.thf.config.MultiRequestBody;
import com.thf.entity.Project;
import com.thf.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@Controller
@RequestMapping("/project")
@ResponseBody
@Api(value = "项目", tags = "项目")
public class ProjectController {
    @Resource
    ProjectService projectService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectName", value = "项目名", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "describe", value = "描述", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "relateUser", value = "关联人", dataType = "long", paramType = "body"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "long", paramType = "body"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "long", paramType = "body")
    })
    @ApiOperation(value = "创建项目", httpMethod = "POST")
    @RequestMapping("/create")
    public ResultVO createProject(@RequestHeader String token, @ApiIgnore @RequestBody Project project) {
        return projectService.createProject(token, project);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", dataType = "String",required = true, paramType = "header"),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, dataType = "long", paramType = "body"),
            @ApiImplicitParam(name = "projectName", value = "项目名", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "describe", value = "描述", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "relateUser", value = "关联人", dataType = "long", paramType = "body"),
            @ApiImplicitParam(name = "projectStatus", value = "项目状态", dataType = "int", paramType = "body"),
            @ApiImplicitParam(name = "startTime", value = "项目开始时间", dataType = "int", paramType = "body"),
            @ApiImplicitParam(name = "endTime", value = "计划结束时间", dataType = "int", paramType = "body"),
            @ApiImplicitParam(name = "progress", value = "项目进度", dataType = "int", paramType = "body")
    })
    @ApiOperation(value = "修改项目", httpMethod = "POST")
    @RequestMapping("/update")
    public ResultVO updateProject(@RequestHeader String token, @Validated @RequestBody Project project) {
        return projectService.updateProject(token, project);
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "projectid", dataType = "long", paramType = "body"),
            @ApiImplicitParam(name = "token", value = "用户token", dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "搜索项目", httpMethod = "POST")
    @RequestMapping("/search/pid")
    public ResultVO searcByid(@MultiRequestBody long pid,@RequestHeader String token) {
        return projectService.searchById(pid,token);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "key", value = "搜索内容", dataType = "String", paramType = "body")
    })
    @ApiOperation(value = "搜索项目", httpMethod = "POST")
    @RequestMapping("/search/mine")
    public ResultVO searchourkey(@RequestHeader String token,
                                 @MultiRequestBody String key) {
        if (key == null) {
            return Res.res(4000, "关键字不能为空");
        }
        return projectService.searchMyKey(token, key);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header", required = true),
            @ApiImplicitParam(name = "type", value = "搜索方式", dataType = "int", paramType = "body", required = true)
    })
    @ApiOperation(value = "项目详细列表", httpMethod = "POST")
    @RequestMapping("/list/detail")
    public ResultVO projectListDtl(@RequestHeader String token, @MultiRequestBody int type) {
        return projectService.getAllProjectDetail(token, type);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header", required = true),
            @ApiImplicitParam(name = "type", value = "搜索方式", dataType = "int", paramType = "body")
    })
    @ApiOperation(value = "项目列表", httpMethod = "POST")
    @RequestMapping("/list")
    public ResultVO projectList(@RequestHeader String token, @MultiRequestBody int type) {
        return projectService.getList(token, type);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header", required = true),
            @ApiImplicitParam(name = "pid", value = "项目id", dataType = "long", paramType = "body", required = true)
    })
    @ApiOperation(value = "项目详情", httpMethod = "POST")
    @RequestMapping("/detail")
    public ResultVO projectDtl(@RequestHeader String token, @MultiRequestBody long pid) {
        return projectService.getProjectDetail(token, pid);
    }

    @ApiImplicitParam(name = "pidArr", value = "项目id数组", required = true, dataType = "long[]", paramType = "body")
    @ApiOperation(value = "删除项目", httpMethod = "POST")
    @RequestMapping("/delete")
    public ResultVO deleteProject(@RequestHeader String token,
                                  @MultiRequestBody long[] pidArr) {
        ResultVO resultVO = projectService.deleteProject(token,pidArr);
        return resultVO;
    }
}

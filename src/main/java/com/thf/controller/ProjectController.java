package com.thf.controller;

import com.thf.common.oo.Res;
import com.thf.common.oo.ResultVO;
import com.thf.config.MultiRequestBody;
import com.thf.entity.Project;
import com.thf.service.ProjectService;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/project")
@ResponseBody
@CrossOrigin
public class ProjectController {
    @Resource
    ProjectService projectService;


    @RequestMapping("/create")
    public ResultVO createProject(@RequestHeader String token, @RequestBody Project project) {
        return projectService.createProject(token, project);
    }


    @RequestMapping("/update")
    public ResultVO updateProject(@RequestHeader String token, @Validated @RequestBody Project project) {
        return projectService.updateProject(token, project);
    }

    @RequestMapping("/search/pid")
    public ResultVO searcByid(@MultiRequestBody long pid,@RequestHeader String token) {
        return projectService.searchById(pid,token);
    }


    @RequestMapping("/search/mine")
    public ResultVO searchourkey(@RequestHeader String token,
                                 @MultiRequestBody String key) {
        if (key == null) {
            return Res.res(4000, "关键字不能为空");
        }
        return projectService.searchMyKey(token, key);
    }


    @RequestMapping("/list/detail")
    public ResultVO projectListDtl(@RequestHeader String token, @MultiRequestBody int type) {
        return projectService.getAllProjectDetail(token, type);
    }

    @RequestMapping("/list")
    public ResultVO projectList(@RequestHeader String token, @MultiRequestBody int type) {
        return projectService.getList(token, type);
    }


    @RequestMapping("/detail")
    public ResultVO projectDtl(@RequestHeader String token, @MultiRequestBody long pid) {
        return projectService.getProjectDetail(token, pid);
    }

    @RequestMapping("/delete")
    public ResultVO deleteProject(@RequestHeader String token,
                                  @MultiRequestBody long[] pidArr) {
        ResultVO resultVO = projectService.deleteProject(token,pidArr);
        return resultVO;
    }
}

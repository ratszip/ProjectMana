package com.thf.controller;

import com.thf.entity.Users;
import com.thf.common.oo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/users")
@ResponseBody
@Api(value = "用户管理",tags = "用户注册登录修改")
public class UsersController {
    @Resource
    private Users users;

    @ApiOperation(value = "用户注册接口",httpMethod = "POST")
    @RequestMapping("/register")
    public ResultVO regist(Users users){

        return null;
    }

    @ApiOperation(value = "搜索用户接口",httpMethod = "POST")
    @RequestMapping("/searchUser")
    public ResultVO search(Users users){

        return null;
    }
}

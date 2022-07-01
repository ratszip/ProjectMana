package com.thf.controller;

import com.thf.config.MultiRequestBody;
import com.thf.entity.User;
import com.thf.common.oo.ResultVO;
import com.thf.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/users")
@ResponseBody
@Api(value = "用户管理",tags = "用户注册登录修改")
public class UserController {
    @Resource
    private UserService userService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="userPhone",value ="手机号",required = false,dataType = "String"),
            @ApiImplicitParam(name="userEmail",value ="邮箱",required = true,dataType = "String"),
            @ApiImplicitParam(name = "userPwd",value ="密码",required = true,dataType = "String"),
            @ApiImplicitParam(name = "verifyCode",value ="验证码",required = true,dataType = "int"),
            @ApiImplicitParam(name = "regType",value ="注册方式",required = true,dataType = "int")
    })
    @ApiOperation(value = "用户注册接口",httpMethod = "POST",tags = "邮箱或者手机号必填一个,regType为注册方式：1,手机 2,邮箱")
    @RequestMapping("/register")
    public ResultVO regist(@MultiRequestBody User user, @MultiRequestBody int verifyCode, @MultiRequestBody int regType){

        return null;
    }

    @ApiOperation(value = "搜索用户接口",httpMethod = "POST")
    @RequestMapping("/searchUser")
    public ResultVO search(User user){

        return null;
    }
}

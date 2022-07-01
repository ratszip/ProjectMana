package com.thf.controller;

import com.thf.common.oo.ErrorCode;
import com.thf.common.oo.RV;
import com.thf.config.MultiRequestBody;
import com.thf.entity.User;
import com.thf.common.oo.ResultVO;
import com.thf.entity.VerifyCode;
import com.thf.service.UserService;
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
@RequestMapping("/users")
@ResponseBody
@Api(value = "用户管理",tags = "用户管理模块")
public class UserController {
    @Resource
    private UserService userService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="userPhone",value ="手机号",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="userEmail",value ="邮箱和手机号必填一个",required = true,dataType = "String",paramType = "body"),
            @ApiImplicitParam(name = "userPwd",value ="密码",required = true,dataType = "String",paramType = "body"),
            @ApiImplicitParam(name = "verifyCode",value ="验证码",required = true,dataType = "int",paramType = "body"),
            @ApiImplicitParam(name = "verifyType",value ="注册方式",required = true,dataType = "int",paramType = "body")
    })
    @ApiOperation(value = "用户注册",httpMethod = "POST")
    @RequestMapping("/register")
    public ResultVO regist(@ApiIgnore @MultiRequestBody User user, @MultiRequestBody VerifyCode verifyCode){

        return RV.success(null);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="userName",value ="用户名",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="userEmail",value ="邮箱",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="userIntroduce",value ="简介",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="userAddress",value ="地址",dataType = "String",paramType = "body")
    })
    @ApiOperation(value = "修改个人资料",httpMethod = "POST")
    @RequestMapping("/update")
    public ResultVO update(@ApiIgnore User user){

        return RV.fail(ErrorCode.PARAM_ERROR);
    }

    @ApiOperation(value = "发送验证码",httpMethod = "POST")
    @RequestMapping("/verify")
    public ResultVO sendVerifyCode(){

        return null;
    }

    @ApiImplicitParam(name="key",value ="搜索内容",dataType = "String",paramType = "body")
    @ApiOperation(value = "发送验证码",httpMethod = "POST")
    @RequestMapping("/search")
    public ResultVO search(String key){

        return null;
    }

    @ApiOperation(value = "获取用户信息",httpMethod = "POST")
    @RequestMapping("/info")
    public ResultVO userInfo(){

        return null;
    }

    @ApiImplicitParam(name="newPwd",value ="新密码",dataType = "String",paramType = "body")
    @ApiOperation(value = "重置密码",httpMethod = "POST")
    @RequestMapping("/reset")
    public ResultVO resetPwd(String userPwd){

        return null;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="newContact",value ="新的手机或邮箱",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name="type",value ="1是手机2是邮箱",dataType = "int",paramType = "body")
    })
    @ApiOperation(value = "修改邮箱或手机",httpMethod = "POST")
    @RequestMapping("/resetcontact")
    public ResultVO resetContact(int type,String newContact){

        return null;
    }


}
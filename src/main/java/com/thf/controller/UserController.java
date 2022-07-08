package com.thf.controller;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.thf.common.utils.RegExpUtils;
import com.thf.config.MultiRequestBody;
import com.thf.entity.User;
import com.thf.common.oo.ResultVO;
import com.thf.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
            @ApiImplicitParam(name="userEmail",value ="邮箱和手机号必填一个",dataType = "String",paramType = "body"),
            @ApiImplicitParam(name = "userPwd",value ="密码",required = true,dataType = "String",paramType = "body"),
            @ApiImplicitParam(name = "verifyCode",value ="验证码",required = true,dataType = "String",paramType = "body"),
            @ApiImplicitParam(name = "verifyType",value ="1.邮箱2手机",required = true,dataType = "Integer",paramType = "body")
    })
    @ApiOperation(value = "用户注册",httpMethod = "POST")
    @RequestMapping("/register")
    public ResultVO regist(@ApiIgnore @MultiRequestBody User user, @MultiRequestBody String verifyCode,@MultiRequestBody Integer verifyType){
        //System.out.println("--------------"+user.getEmail()+","+user.getPassword()+","+verifyCode+","+verifyType);
        String password=user.getPassword().trim();
        user.setRegisterTime(String.valueOf(System.currentTimeMillis()));
        if(password.trim()==""){
            return new ResultVO(2000, "密码不能为空", null);
        }else if(verifyCode.trim()==""){
            return new ResultVO(2000, "验证码不能为空", "");
        }else if(!RegExpUtils.useRegexp(password,"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")){
            return new ResultVO(2000, "密码必须到8-16位数字或字母", null);
        }

        if(verifyType==1){
            String email=user.getEmail().trim();
            if (email== "") {
                return new ResultVO(2000, "邮箱不能空", "");
            }else if(!RegExpUtils.useRegexp(email,"^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")){
                return new ResultVO(2000, "请输入正确的邮箱格式", null);
            }
            user.setPhone("");
            if (userService.searchUserEmail(email) == null) {
                user.setEmail(email);
                user.setPassword(password);
                if (userService.insertUser(user) != null) {
                    return new ResultVO(2000, "注册成功", user);
                } else {
                    return new ResultVO(2000, "注册失败", null);
                }
            } else {
                return new ResultVO(2000, "用户已存在，请登录", null);
            }
        }else if(verifyType==2){
            String phone=user.getPhone().trim();
            if (phone == "") {
                return new ResultVO(2000, "手机不能空", null);
            }else if(!RegExpUtils.useRegexp(phone,"[\\+]?[0-9]{6,16}")){
                return new ResultVO(2000, "请输入正确的手机格式", null);
            }
            user.setEmail("");
            if (userService.searchUserPhone(phone) == null) {
                user.setPhone(phone);
                user.setPassword(password);
                if (userService.insertUser(user) != null) {
                    return new ResultVO(2000, "注册成功", user);
                } else {
                    return new ResultVO(2000, "注册失败", null);
                }
            } else {
                return new ResultVO(2000, "用户已存在，请登录", null);
            }
        }else
        return new ResultVO(2000, "verifyType有误", null);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "key", value = "用户登录账号",required = true),
            @ApiImplicitParam(dataType = "string",name = "password", value = "用户登录密码",required = true),
            @ApiImplicitParam(dataType = "Integer",name = "type", value = "1邮箱2手机",required = true)
    })
    @ApiOperation(value = "用户登录",httpMethod = "POST")
    @RequestMapping("/login")
    public ResultVO login( @MultiRequestBody String key, @MultiRequestBody String password,@MultiRequestBody Integer type){
//        System.out.println("--------"+key+"----"+password+"--"+type);
        ResultVO resultVO = userService.checkLogin(key,password,type);
//        System.out.println(resultVO.getMsg());
        return resultVO;
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

        return null;
    }

//    @ApiOperation(value = "发送验证码",httpMethod = "POST")
//    @RequestMapping("/verifycode")
//    public ResultVO sendVerifyCode(){
//        String verifyCode=PMUtils.createVerifyCode(6);
//        return RV.result(ErrorCode.SUCCESS,"");
//    }

    @ApiImplicitParam(name="key",value ="搜索内容",dataType = "String",paramType = "body")
    @ApiOperation(value = "搜索用户",httpMethod = "POST")
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
    public ResultVO resetPwd(String newPwd){
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

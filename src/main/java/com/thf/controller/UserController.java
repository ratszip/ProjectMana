package com.thf.controller;

import com.thf.common.GloableVar;
import com.thf.common.utils.JwtUtil;
import com.thf.common.utils.PMUtils;
import com.thf.common.utils.RegExpUtils;
import com.thf.config.MultiRequestBody;
import com.thf.common.oo.ResultVO;
import com.thf.entity.User;
import com.thf.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/users")
@ResponseBody
@Api(value = "用户管理", tags = "用户管理模块")
public class UserController {
    @Resource
    private UserService userService;
    @Autowired
    JavaMailSender mailSender;


    /**
     * 注册接口
     *
     * @param phone
     * @param email
     * @param phone
     * @param verifyCode
     * @param verifyType
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "email", value = "邮箱和手机号必填一个", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "verifyType", value = "1.邮箱2手机", required = true, dataType = "Integer", paramType = "body")
    })
    @ApiOperation(value = "用户注册", httpMethod = "POST")
    @RequestMapping("/register")
    public ResultVO regist(HttpServletRequest request,
                           @MultiRequestBody String phone,
                           @MultiRequestBody String email,
                           @MultiRequestBody @NotNull String password,
                           @MultiRequestBody @NotNull String verifyCode,
                           @MultiRequestBody @NotNull Integer verifyType) {
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(email);
        if (session.getAttribute(email) == null) {
            return new ResultVO(2000, "请重新发送验证码");
        }
        if (!code.equals(verifyCode)) {
            return new ResultVO(2000, "验证码错误");
        }
        ResultVO resultVO = userService.userRegister(phone, email, password, verifyCode, verifyType);
        return resultVO;
    }


    /**
     * 登录接口
     *
     * @param key
     * @param password
     * @param type
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "key", value = "用户登录账号", required = true),
            @ApiImplicitParam(dataType = "string", name = "password", value = "用户登录密码", required = true),
            @ApiImplicitParam(dataType = "Integer", name = "type", value = "1邮箱2手机", required = true)
    })
    @ApiOperation(value = "用户登录", httpMethod = "POST")
    @RequestMapping("/login")
    public ResultVO login(@MultiRequestBody @NotNull String key,
                          @MultiRequestBody @NotNull String password,
                          @MultiRequestBody @NotNull Integer type) {
        ResultVO resultVO = userService.checkLogin(key, password, type);
        return resultVO;
    }


    /**
     * 修改个人信息
     *
     * @param username
     * @param userIntroduce
     * @param userAddress
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "userIntroduce", value = "简介", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "userAddress", value = "地址", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header", required = true)
    })
    @ApiOperation(value = "修改个人资料", httpMethod = "POST")
    @RequestMapping("/update")
    public ResultVO update(@MultiRequestBody String username,
                           @MultiRequestBody String userIntroduce,
                           @MultiRequestBody String userAddress,
                           @RequestHeader String token) {

        ResultVO resultVO = userService.updateInfo(username, userIntroduce, userAddress, token);
        return resultVO;
    }


    /**
     * 发送验证码接口
     *
     * @param type
     * @return ResultVO
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "1邮箱2手机", dataType = "Integer", paramType = "body"),
            @ApiImplicitParam(name = "key", value = "手机或邮箱", dataType = "String", paramType = "body")
    })
    @ApiOperation(value = "发送验证码", httpMethod = "POST")
    @RequestMapping("/verifycode")
    public ResultVO sendVerifyCode(HttpServletRequest request,
                                   @MultiRequestBody @NotNull int type,
                                   @MultiRequestBody @NotNull String email) {
        String verifyCode = PMUtils.createVerifyCode(6);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(email, verifyCode);
        httpSession.setMaxInactiveInterval(GloableVar.codeExTime);
        try {
            if (RegExpUtils.useRegexp(email.trim(), GloableVar.emailReg)) {
                if (userService.searchUserEmail(email) == null) {
//                    PMUtils.sendMail(email, "PMAPP验证码");
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setSubject("PMAPP验证码");
                    message.setText("code：" + verifyCode);
                    message.setTo(email);
                    message.setFrom("351659704@qq.com");
                    mailSender.send(message);
                    return new ResultVO(2000, "验证码发送成功", null);
                } else {
                    return new ResultVO(2000, "邮箱已注册", null);
                }
            } else {
                return new ResultVO(2000, "邮箱格式错误", null);
            }
        } catch (MailException e) {
            return new ResultVO(2000, "验证码发送失败", null);
        }
    }


    /**
     * 搜索用户接口
     *
     * @param id
     * @return
     */
    @ApiImplicitParam(name = "id", value = "id", dataType = "Integer", paramType = "body", required = true)
    @ApiOperation(value = "根据id搜索用户", httpMethod = "POST")
    @RequestMapping("/search")
    public ResultVO search(@MultiRequestBody Integer id) {
        User user = userService.searchById(id);
        if (user == null) {
            return new ResultVO(2000, "没有该用户");
        }
        return new ResultVO(2000, "搜索成功", user);
    }

    @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header", required = true)
    @ApiOperation(value = "获取用户信息", httpMethod = "POST")
    @RequestMapping("/info")
    public ResultVO userInfo(@RequestHeader String token) {
        return userService.getInfo(token);
    }

    /**
     * 重置密码接口
     *
     * @param password
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "password", value = "新密码", dataType = "String", paramType = "body")})
    @ApiOperation(value = "重置密码", httpMethod = "POST")
    @RequestMapping("/reset/password")
    public ResultVO resetPwd(@MultiRequestBody String password,
                             @RequestHeader String token) {
        ResultVO resultVO = userService.resetPwd(password, token);
        return resultVO;
    }

    /**
     * 修改手机和邮箱接口
     *
     * @param type
     * @param newContact
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newContact", value = "新的手机或邮箱", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "type", value = "1是邮箱2是手机", dataType = "int", paramType = "body")
    })
    @ApiOperation(value = "修改邮箱或手机", httpMethod = "POST")
    @RequestMapping("/reset/contact")
    public ResultVO resetContact(int type, String newContact) {

        return null;
    }
}

package com.thf.controller;

import com.thf.common.GloableVar;
import com.thf.common.oo.Res;
import com.thf.common.utils.PMUtils;
import com.thf.common.utils.RegExpUtils;
import com.thf.common.utils.SendSMS;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/users")
@ResponseBody
@Api(value = "用户", tags = "用户")
public class UserController {
    @Resource
    private UserService userService;
    @Autowired
    JavaMailSender mailSender;


    /**
     * 注册接口
     *
     * @param key
     * @param password
     * @param code
     * @param type
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "邮箱或手机号", dataType = "String", required = true, paramType = "body"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "type", value = "1.邮箱2手机", required = true, dataType = "Integer", paramType = "body")
    })
    @ApiOperation(value = "用户注册", httpMethod = "POST")
    @RequestMapping("/register")
    public ResultVO regist(HttpServletRequest request,
                           @MultiRequestBody @NotNull String key,
                           @MultiRequestBody @NotNull String password,
                           @MultiRequestBody @NotNull String code,
                           @MultiRequestBody @NotNull Integer type) {
        HttpSession session = request.getSession();
        String vcode = (String) session.getAttribute(key);
        if (session.getAttribute(key) == null) {
            return new ResultVO(4000, "验证码过期,请重新发送");
        }
        if (!code.equals(code)) {
            return new ResultVO(2000, "验证码错误");
        }
        ResultVO resultVO = userService.userRegister(key, password, vcode, type);
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
     * @param userIntro
     * @param userAddress
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "userIntro", value = "简介", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "userAddress", value = "地址", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "header", required = true)
    })
    @ApiOperation(value = "修改个人资料", httpMethod = "POST")
    @RequestMapping("/update")
    public ResultVO update(@MultiRequestBody String username,
                           @MultiRequestBody String userIntro,
                           @MultiRequestBody String userAddress,
                           @RequestHeader String token) {

        ResultVO resultVO = userService.updateInfo(username, userIntro, userAddress, token);
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
                                   @MultiRequestBody @NotNull String key) {
        String verifyCode = PMUtils.createVerifyCode(6);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(key, verifyCode);
        httpSession.setMaxInactiveInterval(GloableVar.codeExTime);
        if (type == 1) {
            try {
                if (RegExpUtils.useRegexp(key.trim(), GloableVar.emailReg)) {
//                    PMUtils.sendMail(email, "PMAPP验证码");
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setSubject("PMAPP验证码");
                        message.setText("验证码为：" + verifyCode + "，5分钟内有效，请勿向他人泄露验证码信息");
                        message.setTo(key);
                        message.setFrom("351659704@qq.com");
                        mailSender.send(message);
                        return new ResultVO(2000, "验证码发送成功", null);

                } else {
                    return new ResultVO(4000, "邮箱格式错误", null);
                }
            } catch (MailException e) {
                return new ResultVO(5000, "验证码发送失败", null);
            }
        } else if (type == 2) {
            Boolean b = SendSMS.sendSMS(verifyCode, key, GloableVar.ali_appcode);
            if (b) {
                return Res.res(2000, "发送成功");
            } else {
                return Res.res(5000, "发送失败");
            }
        }
        return Res.res(4000, "请输入正确的type");
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
     * 修改密码接口
     *
     * @param password
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", dataType = "String",required = true, paramType = "header"),
            @ApiImplicitParam(name = "password", value = "新密码", dataType = "String", required = true,paramType = "body")})
    @ApiOperation(value = "修改密码", httpMethod = "POST")
    @RequestMapping("/change/password")
    public ResultVO resetPwd(@MultiRequestBody String password,
                             @RequestHeader String token) {
        ResultVO resultVO = userService.changePwd(password, token);
        return resultVO;
    }

    /**
     * 修改手机和邮箱接口
     *
     * @param type
     * @param key
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "新的手机或邮箱", dataType = "String",required = true, paramType = "body"),
            @ApiImplicitParam(name = "type", value = "1是邮箱2是手机", dataType = "int",required = true, paramType = "body"),
            @ApiImplicitParam(name = "token", value = "token", dataType = "String",required = true, paramType = "header"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "body")
    })
    @ApiOperation(value = "修改邮箱或手机", httpMethod = "POST")
    @RequestMapping("/change/contact")
    public ResultVO resetContact(HttpServletRequest request,
                                 @MultiRequestBody int type,
                                 @MultiRequestBody String key,
                                 @RequestHeader String token,
                                @MultiRequestBody String code) {
        HttpSession session = request.getSession();
        String vcode = (String) session.getAttribute(key);
        if (session.getAttribute(key) == null) {
            return new ResultVO(4000, "验证码过期,请重新发送");
        }
        if (!code.equals(code)) {
            return new ResultVO(4000, "验证码错误");
        }
        return userService.resetContact(token, type, key);
    }
}

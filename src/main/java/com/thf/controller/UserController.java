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
@CrossOrigin
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
     * @return
     */

    @RequestMapping("/login")
    public ResultVO login(@MultiRequestBody @NotNull String key,
                          @MultiRequestBody @NotNull String password) {
        ResultVO resultVO = userService.checkLogin(key, password);
        return resultVO;
    }


    /**
     * 修改个人资料
     * @param user
     * @param token
     * @return
     */

    @RequestMapping("/update")
    public ResultVO update(@MultiRequestBody User user,
                           @RequestHeader String token) {

        ResultVO resultVO = userService.updateInfo(user,token);
        return resultVO;
    }


    /**
     * 发送验证码接口
     *
     * @param type
     * @return ResultVO
     */

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

    @RequestMapping("/search")
    public ResultVO search(@MultiRequestBody Integer id) {
        User user = userService.searchById(id);
        if (user == null) {
            return new ResultVO(2000, "没有该用户");
        }
        return new ResultVO(2000, "搜索成功", user);
    }


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

    /**
     * 找回密码
     *
     * @param type
     * @param key
     * @return
     */

    @RequestMapping("/find/password")
    public ResultVO findpwd(HttpServletRequest request,
                                 @MultiRequestBody int type,
                                 @MultiRequestBody String key,
                                 @MultiRequestBody String password,
                                 @MultiRequestBody String code) {
        HttpSession session = request.getSession();
        String vcode = (String) session.getAttribute(key);
        if (session.getAttribute(key) == null) {
            return new ResultVO(4000, "验证码过期,请重新发送");
        }
        if (!code.equals(code)) {
            return new ResultVO(4000, "验证码错误");
        }
        return userService.resetPwd(key, type, password);
    }
}

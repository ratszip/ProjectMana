package com.thf.controller;

import com.thf.common.GloableVar;
import com.thf.common.oo.Res;
import com.thf.common.utils.JwtUtil;
import com.thf.common.utils.PMUtils;
import com.thf.common.utils.RegExpUtils;
import com.thf.common.utils.SendSMS;
import com.thf.config.MultiRequestBody;
import com.thf.common.oo.ResultVO;
import com.thf.entity.User;
import com.thf.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.UUID;

@Controller
@RequestMapping("/users")
@ResponseBody
//@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
public class UserController {
    @Resource
    private UserService userService;
    @Autowired
    JavaMailSender mailSender;
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

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
        String vmsg = (String) session.getAttribute(key);
        if(vmsg==null){
            return Res.res(4000,"验证码已失效，请重新发送");
        }
        String vcode=vmsg.split(",")[0];
        if (!code.equals(vcode)) {
            return new ResultVO(2000, "验证码错误");
        }
        ResultVO resultVO = userService.userRegister(key, password, vcode, type);
        return resultVO;
    }


    @RequestMapping("/profile/photo")
    public ResultVO userProfile(@RequestParam(name = "file") MultipartFile file,
                                @RequestHeader String token){
        Long id = ((Number) JwtUtil.parseToken(token).get("uid")).longValue();
        long time=System.currentTimeMillis();
        if (file == null) {
            return Res.res(4000, "请选择要上传的图片");
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            return Res.res(4000, "文件大小不能大于10M");
        }
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            return Res.res(4000, "请选择jpg,jpeg,gif,png格式的图片");
        }
        String savePath = UPLOAD_FOLDER;
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdir();
        }
        //通过UUID生成唯一文件名
        String picname=id.toString()+"-"+String.valueOf(time);

        String filename = picname.replaceAll("-","") + "." + suffix;
        try {
            //将文件保存指定目录
            file.transferTo(new File(savePath + filename));
        } catch (Exception e) {
            e.printStackTrace();
            return Res.res(5000, "保存文件异常");
        }
        //返回文件名称
        return Res.res(2000, "上传头像成功",filename );
    }
    /**
     * 登录接口
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
     *
     * @param user
     * @param token
     * @return
     */

    @RequestMapping("/update")
    public ResultVO update(@MultiRequestBody User user,
                           @RequestHeader String token) {

        ResultVO resultVO = userService.updateInfo(user, token);
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
                                   @MultiRequestBody @NotNull String key,
                                   @MultiRequestBody @NotNull int use
    ) {
        if (key.trim() == "") {
            return Res.res(4000, "请输入账号在发送验证码");
        }
        //1注册2重置3登录
        if (use == 1) {
            if (userService.searchEandP(key) != null) {
                return Res.res(4000, "账号已注册");
            }
        } else if (use == 2 || use == 3) {
            if (userService.searchEandP(key) == null) {
                return Res.res(4000, "账号未注册");
            }
        }

        String verifyCode = PMUtils.createVerifyCode(6);
        HttpSession httpSession = request.getSession();

        if(httpSession.getAttribute(key)!=null){
            String attribute = (String) httpSession.getAttribute(key);
            String stime = attribute.split(",")[1];
            long l = System.currentTimeMillis() - Long.valueOf(stime);
            if(l<60000){
                return Res.res(2000,"60秒后再重新发送");
            }
        }
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
                    httpSession.setAttribute(key, verifyCode+","+System.currentTimeMillis());
                    httpSession.setMaxInactiveInterval(GloableVar.codeExTime);
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
                httpSession.setAttribute(key, verifyCode+","+System.currentTimeMillis());
                httpSession.setMaxInactiveInterval(GloableVar.codeExTime);
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
        String vmsg=(String) session.getAttribute(key);
        if (vmsg == null) {
            return new ResultVO(4000, "验证码过期,请重新发送");
        }
        String vcode = vmsg.split(",")[0];
        if (!code.equals(vcode)) {
            return new ResultVO(4000, "验证码错误");
        }
        return userService.resetContact(token, type, key);
    }

    /**
     * 找回密码
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
        String vmsg = (String) session.getAttribute(key);
        if (vmsg == null) {
            return new ResultVO(4000, "验证码过期,请重新发送");
        }
        String vcode=vmsg.split(",")[0];

        if (!code.equals(vcode)) {
            return new ResultVO(4000, "验证码错误");
        }
        return userService.resetPwd(key, type, password);
    }
}

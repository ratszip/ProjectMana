package com.thf.controller;

import com.thf.entity.Admin;
import com.thf.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin")
@ResponseBody
public class AdminController {
    @Resource
    private AdminService adminService;

    @RequestMapping("/regist")
    public Admin regist(Admin admin){
        return adminService.register(admin);
    }
}

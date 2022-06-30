package com.thf.service.Impl;

import com.thf.dao.AdminDAO;
import com.thf.entity.Admin;
import com.thf.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDAO adminDAO;

    @Override
    public Admin register(Admin admin) {
        int i=adminDAO.insertAdmin(admin);
        if(i>0){
            return admin;
        }else {
            return null;
        }
    }
}

package com.zhfly021.service.impl;

import com.zhfly021.entity.Admin;
import com.zhfly021.mapper.AdminMapper;
import com.zhfly021.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-14 10:00
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin findAdminByUsername(String username) {
        return adminMapper.findAdminByUsername(username);
    }

    @Override
    public Admin findAdminById(Integer id) {
        return adminMapper.findAdminById(id);
    }

    @Override
    public int updatePassword(String username,String password) {
        return adminMapper.updatePassword(username, password);
    }

    @Override
    public List<Admin> queryAdminByCommittee(Admin admin) {
        return adminMapper.queryAdminByCommittee(admin);
    }

    @Override
    public List<Admin> queryAdminByAdministrator(Admin admin) {
        return adminMapper.queryAdminByAdministrator(admin);
    }

    @Override
    public List<Admin> queryAdminOfSuper(Admin admin) {
        return adminMapper.queryAdminOfSuper(admin);
    }

    @Override
    public List<Admin> queryAdminOfFaculty(Admin admin) {
        return adminMapper.queryAdminOfFaculty(admin);
    }

    @Override
    public List<Admin> queryAdminOfStu(Admin admin) {
        return adminMapper.queryAdminOfStu(admin);
    }

    @Override
    public int addAdmin(Admin admin) {
        return adminMapper.addAdmin(admin);
    }

    @Override
    public int updateAdmin(Admin admin) {
        return adminMapper.updateAdmin(admin);
    }

    @Override
    public int deleteAdmin(Integer id) {
        return adminMapper.deleteAdmin(id);
    }

    @Override
    public int batchDeleteAdmin(String[] ids) {
        return adminMapper.batchDeleteAdmin(ids);
    }
}

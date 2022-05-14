package com.zhfly021.service.impl;

import com.zhfly021.entity.Role;
import com.zhfly021.mapper.RoleMapper;
import com.zhfly021.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-26 17:52
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public String queryPermissionId(Integer id) {
        return roleMapper.queryPermissionId(id);
    }

    @Override
    public List<Role> queryRole(Role role) {
        return roleMapper.queryRole(role);
    }

    @Override
    public List<String> queryRoleName() {
        return roleMapper.queryRoleName();
    }

    @Override
    public List<String> queryRoleNameOfSuper() {
        return roleMapper.queryRoleNameOfSuper();
    }

    @Override
    public List<String> queryRoleNameOfFaculty() {
        return roleMapper.queryRoleNameOfFaculty();
    }

    @Override
    public List<String> queryRoleNameOfTeaAndStu() {
        return roleMapper.queryRoleNameOfTeaAndStu();
    }

    @Override
    public int addRole(Role role) {
        return roleMapper.addRole(role);
    }

    @Override
    public Role queryRoleById(Integer id) {
        return roleMapper.queryRoleById(id);
    }

    @Override
    public Integer queryRoleIdByName(String roleName) {
        return roleMapper.queryRoleIdByName(roleName);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    @Override
    public int delRole(Integer id) {
        return roleMapper.delRole(id);
    }

    @Override
    public int batchDelRole(String[] ids) {
        return roleMapper.batchDelRole(ids);
    }
}

package com.zhfly021.service;

import com.zhfly021.entity.Admin;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-14 10:00
 */
public interface AdminService {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public Admin findAdminByUsername(String username);


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public Admin findAdminById(Integer id);


    /**
     * 根据用户名修改密码
     *        如果不是超级、院级管理员，则需要在控制层执行修改学生表或教师表对应的密码
     * @param username
     * @return
     */
    public int updatePassword(String username,String password);

    /**
     * 班委登录查询管理员信息
     * @param admin
     * @return
     */
    public List<Admin> queryAdminByCommittee(Admin admin);

    /**
     * 管理员登录查询管理员信息，除超管查询所有外，其他的都是院管查询一个学院的管理员--即班委
     * @param admin
     * @return
     */
    public List<Admin> queryAdminByAdministrator(Admin admin);

    /**
     * 超级管理员查询管理员用户
     * @param admin
     * @return
     */
    public List<Admin> queryAdminOfSuper(Admin admin);

    /**
     * 院级管理员查询管理员用户
     * @param admin
     * @return
     */
    public List<Admin> queryAdminOfFaculty(Admin admin);

    /**
     * 学生管理员查询管理员用户
     * @param admin
     * @return
     */
    public List<Admin> queryAdminOfStu(Admin admin);

    /**
     * 增加管理员信息：用户名（默认密码同用户名）、工作单位
     * @param admin
     * @return
     */
    public int addAdmin(Admin admin);

    /**
     * 更新管理员信息：工作单位、密码
     * @param admin
     * @return
     */
    public int updateAdmin(Admin admin);

    /**
     * 删除管理员
     * @param id
     * @return
     */
    public int deleteAdmin(Integer id);

    /**
     * 批量删除管理员
     * @param ids
     * @return
     */
    public int batchDeleteAdmin(String[] ids);
}

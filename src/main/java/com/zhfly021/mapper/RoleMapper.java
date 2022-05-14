package com.zhfly021.mapper;

import com.zhfly021.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-26 17:41
 */
@Repository
public interface RoleMapper {

    /**
     * 根据id查询权限【资源】id
     * @param id
     * @return
     */
    public String queryPermissionId(Integer id);


    /**
     * 查询所有角色
     * @return
     */
    public List<Role> queryRole(Role role);

    /**
     * 查询所有角色类别
     * @return
     */
    public List<String> queryRoleName();

    /**
     * 超级管理员查询所有角色类别
     * @return
     */
    public List<String> queryRoleNameOfSuper();

    /**
     * 院级管理员查询所有角色类别
     * @return
     */
    public List<String> queryRoleNameOfFaculty();

    /**
     * 其他管理员查询所有角色类别
     * @return
     */
    public List<String> queryRoleNameOfTeaAndStu();

    /**
     * 增加角色
     * @param role
     * @return
     */
    public int addRole(Role role);

    /**
     * 根据id查询角色信息
     * @param id
     * @return
     */
    public Role queryRoleById(Integer id);

    /**
     * 根据角色名称查询角色ID
     * @param roleName
     * @return
     */
    public Integer queryRoleIdByName(String roleName);

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    public int updateRole(Role role);

    /**
     * 删除一条角色信息
     * @param id
     * @return
     */
    public int delRole(Integer id);

    /**
     * 批量删除角色信息
     * @param ids
     * @return
     */
    public int batchDelRole(String[] ids);
}

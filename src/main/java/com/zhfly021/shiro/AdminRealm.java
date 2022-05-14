package com.zhfly021.shiro;

import com.zhfly021.entity.Admin;
import com.zhfly021.service.AdminService;
import com.zhfly021.service.PermissionService;
import com.zhfly021.service.RoleService;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-14 9:57
 */
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        return null;

        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 添加资源的授权字符串
//        info.addStringPermission("home:kzt");

        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        String ids = roleService.queryPermissionId(admin.getRoleId());
        String[] fields = ids.split(",");
        List<String> strings = permissionService.queryResource(fields);

        for (String permission:strings) {
            info.addStringPermission(permission);
        }

        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //编写shiro判断逻辑，判断用户名和密码
        //1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        Admin admin = adminService.findAdminByUsername(token.getUsername());

        if(admin == null){
            //用户名不存在
            return null;//shiro底层会抛出UnKnowAccountException
        }

        //2.判断密码
        return new SimpleAuthenticationInfo(admin,admin.getPassword(),"");
    }
}

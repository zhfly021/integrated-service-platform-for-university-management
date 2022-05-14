package com.zhfly021.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-14 9:56
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //添加Shiro内置过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *    常用的过滤器：
         *       anon: 无需认证（登录）可以访问
         *       authc: 必须认证才可以访问
         *       user: 如果使用rememberMe的功能可以直接访问
         *       perms： 该资源必须得到资源权限才可以访问
         *       role: 该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterMap = new LinkedHashMap<String,String>();
		/*filterMap.put("/add", "authc");
		filterMap.put("/update", "authc");*/

//        filterMap.put("/testThymeleaf", "anon");
        //放行login登录操作
        filterMap.put("/admin/login", "anon");
        filterMap.put("/", "anon");

        //授权过滤器
        //注意：当前授权拦截后，shiro会自动跳转到未授权页面
        // 主页
        filterMap.put("/back/home/console", "perms[home:kzt]");
        filterMap.put("/back/home/homepage2", "perms[home:sjkb]");
        // 班级任务
        filterMap.put("/bjrw/admin/inform", "perms[task:bjtz]");
        filterMap.put("/bbtx/admin/toBbtxList", "perms[task:bbtxrwfb]");
        filterMap.put("/bbtx/admin/toBbtxData", "perms[task:bbtxsjlb]");
        filterMap.put("/zhcp/admin/toZhcpList", "perms[task:zhcprwfb]");
        filterMap.put("/zhcp/admin/toZhcpData", "perms[task:zhcpsjlb]");
        filterMap.put("/zhcp/admin/toZhcpSummaryOfAdmin", "perms[task:zhcpsjhz]");
        // 班级留言板
        filterMap.put("/bjlyb/admin/bjjs", "perms[advise:bjjsyj]");
        filterMap.put("/bjlyb/admin/bwgz", "perms[advise:bwgzfk]");
        filterMap.put("/bjlyb/admin/bwmydcpList", "perms[advise:bwmydcprwfb]");
        filterMap.put("/bjlyb/admin/bwmydcpData", "perms[advise:bwmydcpsjlb]");
        // 信息维护
        filterMap.put("/xxwh/admin/toStuInfo", "perms[xxwh:xsjl]");
        filterMap.put("/xxwh/admin/toEvidenceInfo", "perms[xxwh:zmcl]");
        // 用户管理
        filterMap.put("/back/user/user/stu_list", "perms[user:student]");
        filterMap.put("/back/user/user/teacher_list", "perms[user:teacher]");
        filterMap.put("/back/user/administrators/list", "perms[user:administrator]");
        filterMap.put("/back/user/administrators/role", "perms[user:role]");
        // 我的设置
        filterMap.put("/back/set/user/info", "perms[me:jbzl]");
        filterMap.put("/back/set/user/password", "perms[me:xgmm]");


        filterMap.put("/*", "authc");

        //修改调整的登录页面
        shiroFilterFactoryBean.setLoginUrl("/admin/toLogin");
        //设置未授权提示页面
//        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);


        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("adminRealm")AdminRealm adminRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(adminRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name="adminRealm")
    public AdminRealm getAdminRealm(){
        return new AdminRealm();
    }

    /**
     * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }



}

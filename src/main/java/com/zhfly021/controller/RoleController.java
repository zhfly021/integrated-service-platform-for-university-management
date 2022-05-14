package com.zhfly021.controller;

import com.zhfly021.entity.Bjjs;
import com.zhfly021.entity.Role;
import com.zhfly021.service.RoleService;
import com.zhfly021.utils.PageUtil;
import com.zhfly021.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-26 21:30
 */
@Controller
public class RoleController {
    @Autowired
    RoleService roleService;


    @RequestMapping(value = "/back/user/administrators/role",method = RequestMethod.GET)
    public String user_admin3(Model model){
        List<String> roleName = roleService.queryRoleName();
        model.addAttribute("allRoleName", roleName);
        return "back/user/administrators/role";
    }

    @RequestMapping("/role/admin/queryRole")
    @ResponseBody
    public RestResponse queryRole(Integer page, Integer limit, @Nullable Role role){
        List<Role> roles = roleService.queryRole(role);
        if(!roles.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(roles, page, limit, roles.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    @RequestMapping(value = "/back/user/administrators/toAdd",method = RequestMethod.GET)
    public String toAdd(){
        return "back/user/administrators/roleform_add";
    }

    @RequestMapping("/role/admin/addRole")
    @ResponseBody
    public RestResponse addRole(Role role){
        String perId = "";
        for (String str:role.getPermissionIds()) {
            if(str != null){
                perId += str + ",";
            }
        }
        String substring = perId.substring(0, perId.length() - 1);
        role.setPermissionId(substring);
        int i = roleService.addRole(role);
        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"创建新的后台管理员角色失败...");
        }
    }

    @RequestMapping("/role/admin/deleteRole")
    @ResponseBody
    public RestResponse deleteRole(Integer id){
        int i = roleService.delRole(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"后台管理员角色删除失败...");
        }
    }

    @RequestMapping("/role/admin/bacthDeleteRole")
    @ResponseBody
    public RestResponse bacthDeleteRole(String data){
        String[] fields = data.split(",");
        int i = roleService.batchDelRole(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"后台管理员角色删除失败...");
        }
    }


    @RequestMapping(value = "/back/user/administrators/toEdit/{id}",method = RequestMethod.GET)
    public String toEdit(@PathVariable("id") Integer id, Model model){
        Role role = roleService.queryRoleById(id);
        model.addAttribute("roleInfo",role);
        return "back/user/administrators/roleform_edit";
    }

    @RequestMapping("/role/admin/updateRole")
    @ResponseBody
    public RestResponse updateRole(Role role){
        String perId = "";
        for (String str:role.getPermissionIds()) {
            if(str != null){
                perId += str + ",";
            }
        }
        String substring = perId.substring(0, perId.length() - 1);
        role.setPermissionId(substring);
        int i = roleService.updateRole(role);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"后台管理员角色信息更新失败...");
        }
    }
}

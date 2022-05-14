package com.zhfly021.controller;

import com.zhfly021.entity.Admin;
import com.zhfly021.entity.Role;
import com.zhfly021.entity.Student;
import com.zhfly021.service.AdminService;
import com.zhfly021.service.RoleService;
import com.zhfly021.service.StudentService;
import com.zhfly021.utils.MD5;
import com.zhfly021.utils.PageUtil;
import com.zhfly021.utils.RestResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-11 21:23
 */
@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    StudentService studentService;

    @Autowired
    RoleService roleService;

    @RequestMapping("/admin/toLogin")
    public String toLogin(){
        return "back/user/login";
    }


    @RequestMapping("/admin/login")
    public String login(@Param("username") String username, @Param("password") String password, @Param("verifycode") String verifycode,
                        Map<String,Object> map, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();

        Admin admin = adminService.findAdminByUsername(username);
        if(admin != null){
            if(admin.getUsername().equals(admin.getPassword())){

            }else {
                password = MD5.EncoderByMd5(password);
            }

            Role role = roleService.queryRoleById(admin.getRoleId());
            String roleName = role.getRoleName();

            /*if(username.startsWith("admin")){
                if(admin.getEmployer() == null){
                    session.setAttribute("adminEmployer","0"); // 超级管理员
                }else{
                    session.setAttribute("adminEmployer","1"); // 院级管理员
                }
            }else if(username.startsWith("20")){
                Student student = studentService.queryByUsername(username);
                session.setAttribute("studentCommittee",student.getCommittee());
                if("团支书,班长,组织委员,宣传委员,权益委员".contains(student.getCommittee())){
                    session.setAttribute("adminEmployer","3"); // 班级管理员(团支部）
                }else{
                    session.setAttribute("adminEmployer","4"); // 班级管理员(非团支部）
                }
            }else {
                session.setAttribute("adminEmployer","2"); // 辅导员管理员
            }*/

            // 设置这个的主要原因是因为有些页面的增删改查只有某些特定角色才可以进行，
            // 但是这种写法和方法并不是很好，可以理解为这种写法是写死的，因为添加的角色全部默认为4级管理员
            // 后期还需要进一步的改进，让他活起来
            if("超级管理员".equals(roleName)){
                session.setAttribute("adminEmployer","0"); // 超级管理员
            }else if("院级管理员".equals(roleName)){
                session.setAttribute("adminEmployer","1"); // 院级管理员
            }else if("辅导员".equals(roleName)){
                session.setAttribute("adminEmployer","2"); // 辅导员管理员
            }else if("团支书,班长,组织委员,宣传委员,权益委员".contains(roleName)){
                session.setAttribute("adminEmployer","3"); // 班级管理员(团支部）
            }else {
                session.setAttribute("adminEmployer","4"); // 班级管理员(非团支部）
            }


        }



        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        // 3.执行登录方法
        String session_key = (String) session.getAttribute("SESSION_KEY");
        if (!(session_key.equalsIgnoreCase(verifycode))){
            map.put("admin_msg","验证码错误...");
            return "back/user/login";
        }else {
            try {
                subject.login(token);
                session.setAttribute("adminName",username);
//                Admin admin = adminService.findAdminByUsername(username);
                session.setAttribute("admin",admin);
                return "back/index";
            }catch (UnknownAccountException e){
                map.put("admin_msg","用户名不存在...");
                return "back/user/login";
            }catch (IncorrectCredentialsException e){
                map.put("admin_msg","密码错误...");
                return "back/user/login";
            }
        }

    }

    @RequestMapping("/admin/updatePassword")
    @ResponseBody
    public RestResponse<Object> modifyPassword(String oldPassword, String newPassword, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Admin admin = (Admin) session.getAttribute("admin");
        String username = admin.getUsername();
        String pwd = admin.getPassword();
        if(pwd.equals(username)){  // 初始密码

        }else{  // 已修改过初始密码
            oldPassword = MD5.EncoderByMd5(oldPassword);
        }
        if(oldPassword.equals(pwd)){
            adminService.updatePassword(username,MD5.EncoderByMd5(newPassword));
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"旧密码错误，请重试...");
        }
    }

    @RequestMapping(value = "/back/set/user/info",method = RequestMethod.GET)
    public String set_user1(Model model,HttpSession session){
        Admin adminLogin = (Admin) session.getAttribute("admin");
        Student student = studentService.queryByUsername(adminLogin.getUsername());
        Role role = roleService.queryRoleById(adminLogin.getRoleId());
        model.addAttribute("roleNameLogin" , role.getRoleName());
        model.addAttribute("student", student);
        model.addAttribute("admin", adminLogin);

        return "back/set/user/info";
    }

    @RequestMapping("/administrators/admin/updateAdminInfo")
    @ResponseBody
    public RestResponse updateInfo(Student student){
        int i = studentService.updateInfo(student);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"基本资料修改失败...");
        }
    }





    @RequestMapping("/admin/logout")
    public String logout(){
        return "back/user/login";
    }




    // 后台管理员业务
    @RequestMapping(value = "/back/user/administrators/list",method = RequestMethod.GET)
    public String user_admin2(){
        return "back/user/administrators/admin_list";
    }

    @RequestMapping("/administrators/admin/admin_list")
    @ResponseBody
    public RestResponse queryAdmin(Integer page, Integer limit, @Nullable Admin admin, HttpSession session){
        Admin adminLogin = (Admin) session.getAttribute("admin");
        Role role = roleService.queryRoleById(adminLogin.getRoleId());
        String roleName = role.getRoleName();

        if("".equals(admin.getUsername())){
            admin.setUsername(null);
        }
        if("".equals(admin.getEmployer())){
            admin.setEmployer(null);
        }
        if("".equals(admin.getFaculty())){
            admin.setFaculty(null);
        }
        /*if(username.startsWith("admin")){
            // 管理员
            if(adminLogin.getEmployer() == null){

            }else {
                admin.setEmployer(adminLogin.getEmployer());
            }
            admins = adminService.queryAdminByAdministrator(admin);
        }else if(username.startsWith("20")){
            // 学生
            admin.setEmployer(adminLogin.getEmployer());
            admins = adminService.queryAdminByCommittee(admin);
        }else {
            // 教师
            String[] fields = adminLogin.getEmployer().split(",");
//            students = studentService.queryStuForTeacher(fields);
        }*/
        List<Admin> admins = new ArrayList<>();
        if("超级管理员".equals(roleName)){
            // 超级管理员
            admins = adminService.queryAdminOfSuper(admin);
        }else if("院级管理员".equals(roleName)){
            // 院级管理员
            admin.setFaculty(adminLogin.getEmployer());
            admins = adminService.queryAdminOfFaculty(admin);
        }else if("辅导员".equals(roleName)){
            // 辅导员管理员   --- 未实现
            String[] fields = adminLogin.getEmployer().split(",");
//            students = studentService.queryStuForTeacher(fields);
        }else {
            // 班级管理员
            admin.setEmployer(adminLogin.getEmployer());
            admins = adminService.queryAdminOfStu(admin);
        }

        for (Admin admin1:admins) {
            Role role1 = roleService.queryRoleById(admin1.getRoleId());
            admin1.setRoleCategory(role1.getRoleName());
        }


        if(!admins.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(admins, page, limit, admins.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    // 班级管理员只有组织委员有权限 增删改
    @RequestMapping(value = "/back/user/administrators/adminform_add",method = RequestMethod.GET)
    public String user_admin1(Model model,HttpSession session){
        Admin adminLogin = (Admin) session.getAttribute("admin");
        Role role = roleService.queryRoleById(adminLogin.getRoleId());
        String loginRoleName = role.getRoleName();
        if("超级管理员".equals(loginRoleName)){
            List<String> roleName = roleService.queryRoleName();
            model.addAttribute("allRoleName", roleName);
            return "back/user/administrators/adminform_add";
        }else if("院级管理员".equals(loginRoleName)){
            List<String> roleName = roleService.queryRoleNameOfFaculty();
            model.addAttribute("allRoleName", roleName);
            return "back/user/administrators/adminform_add";
        }else{
            List<String> roleName = roleService.queryRoleNameOfTeaAndStu();
            model.addAttribute("allRoleName", roleName);
            return "back/user/administrators/adminform_add2";
        }

    }

    @RequestMapping("/administrators/admin/addAdmin")
    @ResponseBody
    public RestResponse addAdmin(Admin admin, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Admin adminLogin = (Admin) session.getAttribute("admin");
        Role role = roleService.queryRoleById(adminLogin.getRoleId());
        String loginRoleName = role.getRoleName();

        String password = admin.getPassword();
        if("".equals(password)){
            // 未设置密码则设置初始密码
            admin.setPassword(admin.getUsername());
        }else{
            password = MD5.EncoderByMd5(password);
            admin.setPassword(password);
        }

        Integer integer = roleService.queryRoleIdByName(admin.getRoleCategory());
        admin.setRoleId(integer);

        // 添加不同类型的角色
        if("管理员".equals(admin.getFaculty())){
            // 添加管理员类型的角色
            admin.setFaculty(admin.getEmployer());
        }else if("教师".equals(admin.getFaculty())){
            // 添加辅导员类型的角色   ---  因为数据原因，暂时未实现教师相关功能
//            admin.setFaculty(null);
        }else{
            // 添加学生类型的角色
            Student student = studentService.queryByUsername(admin.getUsername());
            if(student == null){
                return RestResponse.fail(201,"学校没有这位同学...");
            }

            if("超级管理员".equals(loginRoleName)){
                admin.setEmployer(student.getStuClass());
                admin.setFaculty(student.getFaculty());
            }else{
                // 根据需要添加的学号去查询当前登录的用户是否有权限添加该用户
                Student student1 = new Student();
                if("院级管理员".equals(loginRoleName)){
                    student1.setFaculty(adminLogin.getEmployer());
                }else if("辅导员".equals(loginRoleName)){
                    // 辅导员执行增加学生用户操作
                }else{
                    student1.setStuClass(adminLogin.getEmployer());
                }
                student1.setStuNo(admin.getUsername());
                Student stuOfAddAdmin = studentService.queryStuOfAddAdmin(student1);
                if(stuOfAddAdmin == null){
                    return RestResponse.fail(201,"您没有权限添加该学生...");
                }else{
                    admin.setEmployer(stuOfAddAdmin.getStuClass());
                    admin.setFaculty(stuOfAddAdmin.getFaculty());
                }
            }


        }

        int i = adminService.addAdmin(admin);
        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"管理员用户信息提交失败...");
        }
    }

    @RequestMapping(value = "/administrators/admin/toAdminEdit/{id}",method = RequestMethod.GET)
    public String toEdit(@PathVariable("id") Integer id, Model model,HttpSession session){
        Admin adminLogin = (Admin) session.getAttribute("admin");
        Admin admin = adminService.findAdminById(id);
        Role roleLogin = roleService.queryRoleById(adminLogin.getRoleId());
        Role role = roleService.queryRoleById(admin.getRoleId());
        String loginRoleName = roleLogin.getRoleName();
        String queryRoleName = role.getRoleName();
        admin.setRoleCategory(roleService.queryRoleById(admin.getRoleId()).getRoleName());

        if("超级管理员,院级管理员".contains(queryRoleName)){
            admin.setFaculty("管理员");
        }else if("辅导员".equals(queryRoleName)){
            admin.setFaculty("教师");
        }else{
            admin.setFaculty("学生");
        }
        model.addAttribute("admin",admin);
        if("超级管理员".equals(loginRoleName)){
            List<String> roleName = roleService.queryRoleName();
            model.addAttribute("allRoleName", roleName);
            return "back/user/administrators/adminform_edit";
        }else if("院级管理员".equals(loginRoleName)){
            List<String> roleName = roleService.queryRoleNameOfFaculty();
            model.addAttribute("allRoleName", roleName);
            return "back/user/administrators/adminform_edit";
        }else{
            List<String> roleName = roleService.queryRoleNameOfTeaAndStu();
            model.addAttribute("allRoleName", roleName);
            return "back/user/administrators/adminform_edit2";
        }
    }

    @RequestMapping("/administrators/admin/updateAdmin")
    @ResponseBody
    public RestResponse updateAdmin(Admin admin) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String password = admin.getPassword();
        if("".equals(admin.getPassword())){
            admin.setPassword(null);
        }else{
            password = MD5.EncoderByMd5(password);
            admin.setPassword(password);
        }
        Integer integer = roleService.queryRoleIdByName(admin.getRoleCategory());
        admin.setRoleId(integer);

        // 添加不同类型的角色
        if("管理员".equals(admin.getFaculty())){
            // 添加管理员类型的角色
//            admin.setFaculty(null);
            admin.setFaculty(admin.getEmployer());
        }else if("教师".equals(admin.getFaculty())){
            // 添加辅导员类型的角色   ---  因为数据原因，暂时未实现教师相关功能
//            admin.setFaculty(null);
        }else{
            // 添加学生类型的角色
            Student student = studentService.queryByUsername(admin.getUsername());
            admin.setEmployer(student.getStuClass());
            admin.setFaculty(student.getFaculty());
        }

        int i = adminService.updateAdmin(admin);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"管理员用户信息修改失败...");
        }
    }

    @RequestMapping("/administrators/admin/delAdmin")
    @ResponseBody
    public RestResponse delAdmin(Integer id){
        int i = adminService.deleteAdmin(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"管理员用户信息删除失败...");
        }
    }

    @RequestMapping("/administrators/admin/bacthDelAdmin")
    @ResponseBody
    public RestResponse bacthDelAdmin(String data){
        String[] fields = data.split(",");
        int i = adminService.batchDeleteAdmin(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"管理员用户信息删除失败...");
        }
    }











}

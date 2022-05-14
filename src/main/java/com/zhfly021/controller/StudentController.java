package com.zhfly021.controller;

import com.zhfly021.entity.Admin;
import com.zhfly021.entity.Bjjs;
import com.zhfly021.entity.Role;
import com.zhfly021.entity.Student;
import com.zhfly021.service.RoleService;
import com.zhfly021.service.StudentService;
import com.zhfly021.utils.MD5;
import com.zhfly021.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-11-27 11:51
 */
@Controller
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    RoleService roleService;


    @RequestMapping("/stu/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        @RequestParam("verifycode") String verifycode,
                        Map<String,Object> map, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Student student = studentService.queryByUsername(username);
        String session_key = (String) session.getAttribute("SESSION_KEY");
        if (!(session_key.equalsIgnoreCase(verifycode))){
            map.put("stu_msg","验证码错误...");
            return "front/user/login";
        }else {
            if (student != null){
                String pwd = student.getPassword();
                String stuID = student.getStuID();
                if(pwd.equals(stuID)){  // 初始密码
                    pwd = pwd.substring(pwd.length() - 6);
                }else{  // 已修改初始密码密码
                    password = MD5.EncoderByMd5(password);
                }
                if(password.equals(pwd)){
                    session.setAttribute("student",student);
                    session.setAttribute("stuName",student.getStuName());
                    session.setAttribute("stuNo",student.getStuNo());
                    session.setAttribute("stuClass",student.getStuClass());
                    return "front/index";
                }else {
                    map.put("stu_msg","用户名或密码错误...");
                    return "front/user/login";
                }
            }else {
                map.put("stu_msg","用户名不存在...");
                return "front/user/login";
            }
        }
    }

    @RequestMapping("/stu/logout")
    public String logout(){
        return "front/user/login";
    }




    @RequestMapping(value = "/front/user/info",method = RequestMethod.GET)
    public String toStuInfo(Model model,HttpSession session){
        Student student = (Student) session.getAttribute("student");
        String stuNo = student.getStuNo();
        Student stu = studentService.queryByUsername(stuNo);

        model.addAttribute("student", stu);
        return "front/user/info";
    }

    @PostMapping("/user/stu/updateStuInfo")
    @ResponseBody
    public RestResponse updateStuInfo(Student student){
        int i = studentService.updateStuInfo(student);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"基本资料修改失败...");
        }
    }

    @RequestMapping("/stu/modifyPass")
    @ResponseBody
    public RestResponse<Object> modifyPassword(String oldPassword,String newPassword,HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Student student = (Student) session.getAttribute("student");
        String pwd = student.getPassword();
        String stuID = student.getStuID();
        if(pwd.equals(stuID)){  // 初始密码
            pwd = pwd.substring(pwd.length() - 6);
        }else{  // 已修改初始密码密码
            oldPassword = MD5.EncoderByMd5(oldPassword);
        }
        if(oldPassword.equals(pwd)){
            studentService.modifyPassword(student.getStuNo(),MD5.EncoderByMd5(newPassword));
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"旧密码错误，请重试...");
        }
    }


    @RequestMapping("/stu/tzb")
    public String tzb(HttpSession session, Model model){
        Student student = (Student) session.getAttribute("student");
        String stuClass = student.getStuClass();
        List<Student> students = studentService.queryCategory(stuClass, "tzb");
        // 设置默认照片
        for (Student stu:students) {
            if(stu.getPhoto() == null){
                stu.setPhoto("../../img/me.JPG");
            }
        }
        model.addAttribute("tzb",students);
        return "front/committee/tzb";
    }
    @RequestMapping("/stu/bwh")
    public String bwh(HttpSession session, Model model){
        Student student = (Student) session.getAttribute("student");
        String stuClass = student.getStuClass();
        List<Student> students = studentService.queryCategory(stuClass, "bwh");
        // 设置默认照片
        for (Student stu:students) {
            if(stu.getPhoto() == null){
                stu.setPhoto("../../img/me.JPG");
            }
        }
        model.addAttribute("bwh",students);
        return "front/committee/bwh";
    }
    @RequestMapping("/stu/qsz")
    public String qsz(HttpSession session, Model model){
        Student student = (Student) session.getAttribute("student");
        String stuClass = student.getStuClass();
        List<Student> students = studentService.queryQsz(stuClass);
        // 设置默认照片
        for (Student stu:students) {
            if(stu.getPhoto() == null){
                stu.setPhoto("../../img/me.JPG");
            }
        }
        model.addAttribute("qsz",students);
        return "front/committee/qsz";
    }



    @RequestMapping("/stu/admin/addStudent")
    @ResponseBody
    public RestResponse addStudent(Student student, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Admin adminLogin = (Admin) session.getAttribute("admin");
        Student studentLogin = studentService.queryByUsername(adminLogin.getUsername());
        Role role = roleService.queryRoleById(adminLogin.getRoleId());
        // 目前登录的角色
        String roleNameLogin = role.getRoleName();

        String password = student.getPassword();
        if("".equals(password)){
            // 未设置密码则设置初始密码
            student.setPassword(student.getStuID());
        }else{
            password = MD5.EncoderByMd5(password);
            student.setPassword(password);
        }

        if("团支书,班长,组织委员,宣传委员,权益委员".contains(student.getCommittee())){
            student.setCategory("tzb");
        } else if("无".equals(student.getCommittee())){
            student.setCategory(null);
        } else{
            student.setCategory("bwh");
        }

        if("团支书".equals(student.getCommittee())){
            student.setPosition(1);
        } else if("班长".equals(student.getCommittee())){
            student.setPosition(2);
        } else if("组织委员".equals(student.getCommittee())){
            student.setPosition(3);
        } else if("宣传委员".equals(student.getCommittee())){
            student.setPosition(4);
        } else if("权益委员".equals(student.getCommittee())){
            student.setPosition(5);
        } else if("学习委员".equals(student.getCommittee())){
            student.setPosition(1);
        } else if("生活委员".equals(student.getCommittee())){
            student.setPosition(2);
        } else if("科创委员".equals(student.getCommittee())){
            student.setPosition(3);
        } else if("心理委员".equals(student.getCommittee())){
            student.setPosition(4);
        } else if("文艺委员".equals(student.getCommittee())){
            student.setPosition(5);
        } else if("体育委员".equals(student.getCommittee())){
            student.setPosition(6);
        }




        if("超级管理员".equals(roleNameLogin)){
        }else  if("院级管理员,辅导员".contains(roleNameLogin)){
            student.setFaculty(adminLogin.getFaculty());
        }else {
            student.setFaculty(studentLogin.getFaculty());
            student.setSeries(studentLogin.getSeries());
            student.setProfession(studentLogin.getProfession());
            student.setStuClass(studentLogin.getStuClass());
        }


        int i = studentService.addStuOfAdmin(student);

        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"学生用户信息提交失败...");
        }
    }

    @RequestMapping("/stu/admin/delStudent")
    @ResponseBody
    public RestResponse delStudent(Integer id){
        int i = studentService.delStuById(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"学生用户信息删除失败...");
        }
    }

    @RequestMapping("/stu/admin/bacthDelStudent")
    @ResponseBody
    public RestResponse bacthDelStudent(String data){
        String[] fields = data.split(",");
        int i = studentService.batchDelStu(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"学生用户信息删除失败...");
        }
    }


    @RequestMapping(value = "/stu/admin/toStudentEdit/{id}",method = RequestMethod.GET)
    public String toEdit(@PathVariable("id") Integer id, Model model){
        Student studentEdit = studentService.queryStuById(id);
        model.addAttribute("studentEdit",studentEdit);
        return "back/user/user/stu_edit";
    }

    @RequestMapping("/stu/admin/updateStudent")
    @ResponseBody
    public RestResponse updateStudent(Student student, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // 因为文件上传的接口还没有写，所以这里先给photo设置为null，待后期实现了文件上传接口再把这个行语句注释
//        student.setPhoto(null);

        Admin adminLogin = (Admin) session.getAttribute("admin");
        Student studentLogin = studentService.queryByUsername(adminLogin.getUsername());
        Role role = roleService.queryRoleById(adminLogin.getRoleId());
        // 目前登录的角色
        String roleNameLogin = role.getRoleName();

        String password = student.getPassword();
        if("".equals(password)){
            // 未设置密码则设置初始密码
            student.setPassword(null);
        }else{
            password = MD5.EncoderByMd5(password);
            student.setPassword(password);
        }

        if("团支书,班长,组织委员,宣传委员,权益委员".contains(student.getCommittee())){
            student.setCategory("tzb");
        } else if("无".equals(student.getCommittee())){
            student.setCategory(null);
        } else{
            student.setCategory("bwh");
        }

        if("团支书".equals(student.getCommittee())){
            student.setPosition(1);
        } else if("班长".equals(student.getCommittee())){
            student.setPosition(2);
        } else if("组织委员".equals(student.getCommittee())){
            student.setPosition(3);
        } else if("宣传委员".equals(student.getCommittee())){
            student.setPosition(4);
        } else if("权益委员".equals(student.getCommittee())){
            student.setPosition(5);
        } else if("学习委员".equals(student.getCommittee())){
            student.setPosition(1);
        } else if("生活委员".equals(student.getCommittee())){
            student.setPosition(2);
        } else if("科创委员".equals(student.getCommittee())){
            student.setPosition(3);
        } else if("心理委员".equals(student.getCommittee())){
            student.setPosition(4);
        } else if("文艺委员".equals(student.getCommittee())){
            student.setPosition(5);
        } else if("体育委员".equals(student.getCommittee())){
            student.setPosition(6);
        }




        if("超级管理员".equals(roleNameLogin)){
        }else  if("院级管理员,辅导员".contains(roleNameLogin)){
            student.setFaculty(adminLogin.getFaculty());
        }else {
            student.setFaculty(studentLogin.getFaculty());
            student.setSeries(studentLogin.getSeries());
            student.setProfession(studentLogin.getProfession());
            student.setStuClass(studentLogin.getStuClass());
        }

        int i = studentService.updateStuByIdOfAdmin(student);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"学生用户信息修改失败...");
        }
    }
}

package com.zhfly021.controller;

import com.zhfly021.entity.Admin;
import com.zhfly021.entity.Bjjs;
import com.zhfly021.entity.Student;
import com.zhfly021.service.StudentService;
import com.zhfly021.utils.PageUtil;
import com.zhfly021.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-14 22:18
 */
@Controller
public class UserController {

    @Autowired
    StudentService studentService;


    @RequestMapping("/admin/user/stu_list")
    @ResponseBody
    public RestResponse queryStuForAdmin(Integer page, Integer limit, @Nullable Student student, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        String username = admin.getUsername();
        List<Student> students = new ArrayList<>();
        if("".equals(student.getStuNo())){
            student.setStuNo(null);
        }
        if("".equals(student.getStuName())){
            student.setStuName(null);
        }
        if("".equals(student.getStuClass())){
            student.setStuClass(null);
        }
        if("".equals(student.getDormitory())){
            student.setDormitory(null);
        }
        if("".equals(student.getFaculty())){
            student.setFaculty(null);
        }
        if("".equals(student.getCommittee())){
            student.setCommittee(null);
        }
        if("".equals(student.getSex())){
            student.setSex(null);
        }


        if(username.startsWith("admin")){
            student.setFaculty(admin.getEmployer());
            students = studentService.queryStuForAdmin(student);
        }else if(username.startsWith("20")){
            student.setStuClass(admin.getEmployer());
            students = studentService.queryStuForCommittee(student);
        }else {
            String[] fields = admin.getEmployer().split(",");
            students = studentService.queryStuForTeacher(fields);
        }

        // 设置默认照片
        for (Student stu:students) {
            if(stu.getPhoto() == null){
                stu.setPhoto("../../../img/me.JPG");
            }
            if(stu.getShepherd() != null && stu.getShepherd() == 1){
                String committee = stu.getCommittee();
                if(committee != null){
                    stu.setCommittee(committee + ",寝室长");
                }else {
                    stu.setCommittee("寝室长");
                }
            }
            if(stu.getCommittee() == null){
                stu.setCommittee("无");
            }
        }

        if(!students.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(students, page, limit, students.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }

}

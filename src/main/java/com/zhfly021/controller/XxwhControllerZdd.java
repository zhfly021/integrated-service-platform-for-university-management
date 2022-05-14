package com.zhfly021.controller;

import com.zhfly021.entity.Admin;
import com.zhfly021.entity.Evidence;
import com.zhfly021.entity.Resume;
import com.zhfly021.entity.Student;
import com.zhfly021.service.AdminService;
import com.zhfly021.service.BbtxSevice;
import com.zhfly021.service.StudentService;
import com.zhfly021.service.XxwhServiceZdd;
import com.zhfly021.service.impl.XxwhServiceImplZdd;
import com.zhfly021.utils.CreateID;
import com.zhfly021.utils.EmailUtils;
import com.zhfly021.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description 学生信息维护（学生简历）控制类
 * @create 2021-03-17 22:35
 */
@Controller
public class XxwhControllerZdd {

    @Autowired
    XxwhServiceZdd xxwhService;

    @Autowired
    StudentService studentService;

    @Autowired
    AdminService adminService;

    @Autowired
    EmailUtils emailUtils;



    // *****************   学生简历维护--前台   *****************



    /**
     *  跳转至（前台）学生简历页面
     * @return
     */
    @RequestMapping(value = "/xxwh/stu/zbxx",method = RequestMethod.GET)
    public String xxwh(HttpSession session, Model model){
        Student student = (Student) session.getAttribute("student");
        Resume resume = xxwhService.queryByIdService(student.getStuNo());
        if (resume == null){
            resume = new Resume();
            resume.setSex("");
            resume.setAge("");
            resume.setPhone("");
            resume.setNation("");
            resume.setStartDate("");
            resume.setEndDate("");
            resume.setEducationBackground("");
            resume.setWorkTime1("");
            resume.setWorkTime2("");
            resume.setWorkTime3("");
            resume.setWorkCompany1("");
            resume.setWorkCompany2("");
            resume.setWorkCompany3("");
            resume.setWorkExperience1("");
            resume.setWorkExperience2("");
            resume.setWorkExperience3("");
            resume.setHonor("");
            resume.setSelfAssessment("");
            resume.setSubmitInstructions("");
        }
        model.addAttribute("resume",resume);
        model.addAttribute("student",student);
        return "front/info/xxwh";
    }

    @ResponseBody
    @RequestMapping(value = "/xxwh/stu/holdxxwh",method = RequestMethod.POST)
    public RestResponse holdXxwh(HttpSession session,Resume resume){
        Student student = (Student) session.getAttribute("student");
        Resume stu = xxwhService.queryByIdService(student.getStuNo());
        if (stu != null){
            Integer integer = xxwhService.updateResumeService(resume);
            if(integer != 0){
                return RestResponse.ok();
            }else{
                return RestResponse.fail(200,"用户信息存储失败...");
            }
        }else{
            resume.setStatus("已暂存");
            resume.setResumeId(CreateID.create());
            Integer integer = xxwhService.holdXxwhService(resume);
            if(integer != 0){
                return RestResponse.ok();
            }else{
                return RestResponse.fail(200,"用户信息存储失败...");
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/xxwh/stu/submitXxwh",method = RequestMethod.POST)
    public RestResponse submitXxwh(HttpSession session,Resume resume){
        Student student = (Student) session.getAttribute("student");
        Resume stu = xxwhService.queryByIdService(student.getStuNo());
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        resume.setSubmitTime(evaTime);
        Evidence evid = new Evidence();
        evid.setStuNo(resume.getStuNo());
        evid.setStuName(resume.getStuName());
        evid.setStuClass(resume.getStuClass());
        evid.setStuFaculty(resume.getStuFaculty());
        evid.setInformationID(CreateID.create());
        evid.setStatus("未提交");
        Evidence evidence = xxwhService.queryByIdEvidenceService(resume.getStuNo());
        if (stu != null){
            resume.setStatus("待审核");
            Integer integer = xxwhService.updateResumeService(resume);
            if(integer != 0){
                if(evidence == null){
                    xxwhService.insertEvidenceXxwh(evid);
                }else{
                    xxwhService.updateEvidenceStatus("未提交",resume.getStuNo());
                }
                return RestResponse.ok();
            }else{
                return RestResponse.fail(200,"用户信息存储失败...");
            }
        }else{
            resume.setStatus("待审核");
            resume.setResumeId(CreateID.create());
            Integer integer = xxwhService.holdXxwhService(resume);
            if(integer != 0){
                if(evidence == null){
                    xxwhService.insertEvidenceXxwh(evid);
                }else{
                    xxwhService.updateEvidenceStatus("未提交",resume.getStuNo());
                }
                return RestResponse.ok();
            }else{
                return RestResponse.fail(200,"用户信息存储失败...");
            }
        }
    }

    /**
     * @Description:
     * @Author: 张豆豆
     * @Date: 2021-03-24 21:15:47
     */
    @RequestMapping(value = "/xxwh/stu/yulanXxwh/{stuNo}")
    public String yulanXxwh(HttpSession session, @PathVariable("stuNo") String stuNo, Model model){
        Student student = (Student) session.getAttribute("student");

        Resume resume = xxwhService.queryByIdService(stuNo);

        model.addAttribute("resume",resume);
        model.addAttribute("student",student);
        model.addAttribute("front",resume == null ? null : resume.getVerifyMarks());
        return "front/info/resume";
    }

    /**
     * @Description:
     * @Author: 张豆豆
     * @Date: 2021-03-24 21:15:47
     */
    @RequestMapping(value = "/xxwh/stu/back/yulanXxwh/{stuNo}")
    public String yulanXxwhBack(HttpSession session, @PathVariable("stuNo") String stuNo, Model model){
        Student student = (Student) session.getAttribute("student");
        Resume resume = xxwhService.queryByIdService(stuNo);
        model.addAttribute("resume",resume);
        model.addAttribute("student",student);
        model.addAttribute("back","back");
        return "front/info/resume";
    }


    // *****************   学生简历维护--后台   *****************
    /**
     * @Description:
     * @Author: 张豆豆
     * @Date: 2021-03-24 21:15:47
     */
    @RequestMapping(value = "/xxwh/stu/success/{stuNo}/{id}")
    @ResponseBody
    public RestResponse success(HttpSession session, @PathVariable("stuNo") String stuNo,@PathVariable("id") Integer id, String verifyMarks,Model model){
        Resume resume = xxwhService.queryByIdService(stuNo);
        Admin adminById = adminService.findAdminById(id);
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        resume.setVerifier(adminById.getUsername());
        resume.setVerifyTime(evaTime);
        resume.setVerifyMarks(verifyMarks);
        resume.setStatus("已完成");
        Integer integer = xxwhService.updateResumeService(resume);
        if(1 != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"请重试...");
        }
    }

    /**
     * @Description:
     * @Author: 张豆豆
     * @Date: 2021-03-28 20:04:11
     */
    @RequestMapping(value = "/xxwh/stu/reject/{stuNo}/{id}")
    @ResponseBody
    public RestResponse reject(HttpSession session, @PathVariable("stuNo") String stuNo,@PathVariable("id") Integer id, String verifyMarks,Model model){
        Resume resume = xxwhService.queryByIdService(stuNo);
        Admin adminById = adminService.findAdminById(id);
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        resume.setVerifier(adminById.getUsername());
        resume.setVerifyTime(evaTime);
        resume.setVerifyMarks(verifyMarks);
        resume.setStatus("已驳回");
        Integer integer = xxwhService.updateResumeService(resume);
        if(1 != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"请重试...");
        }
    }

}


package com.zhfly021.controller;

import com.zhfly021.entity.Admin;
import com.zhfly021.entity.Student;
import com.zhfly021.service.StudentService;
import com.zhfly021.service.XxwhService;
import com.zhfly021.utils.File.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-11-27 11:26
 */
@Controller
public class PageController {

    @Autowired
    StudentService studentService;

    @RequestMapping("/")
    public String index() {
        return "front/user/login";
    }

    @RequestMapping(value = "/{pagename}",method = RequestMethod.GET)
    public String toPage(@PathVariable String pagename){
        return pagename;
    }

    @RequestMapping(value = "/front/home/console",method = RequestMethod.GET)
    public String toConsolePage(){
        return "front/home/console";
    }




    @RequestMapping(value = "/front/user/modify",method = RequestMethod.GET)
    public String toPassword(){
        return "front/user/modify";
    }


    @RequestMapping(value = "/front/advise/bjjs",method = RequestMethod.GET)
    public String test(){
        return "front/advise/bjjs";
    }

    @RequestMapping(value = "/front/advise/toBjjsAdd",method = RequestMethod.GET)
    public String test2(){
        return "front/advise/bjjs_add";
    }

    /*@RequestMapping(value = "/front/advise/toBjjsEdit",method = RequestMethod.GET)
    public String test3(){
        return "front/advise/bjjs_edit";
    }*/



    @RequestMapping(value = "/back/home/homepage1",method = RequestMethod.GET)
    public String adminHome2(){
        return "back/home/homepage1";
    }




    @RequestMapping(value = "/back/user/user/stu_list",method = RequestMethod.GET)
    public String user_user_stu1(){
        return "back/user/user/stu_list";
    }
    @RequestMapping(value = "/back/user/user/stu_userform",method = RequestMethod.GET)
    public String user_user_stu2(){
        return "back/user/user/stu_userform";
    }
    @RequestMapping(value = "/back/user/user/teacher_list",method = RequestMethod.GET)
    public String user_user_teacher1(){
        return "back/user/user/teacher_list";
    }
    @RequestMapping(value = "/back/user/user/teacher_userform",method = RequestMethod.GET)
    public String user_user_teacher2(){
        return "back/user/user/teacher_userform";
    }



    @RequestMapping(value = "/back/set/user/password",method = RequestMethod.GET)
    public String set_user2(){
        return "back/set/user/password";
    }

//    @RequestMapping(value = "/back/system/about",method = RequestMethod.GET)
//    public String system_about(){
//        return "back/system/about";
//    }





    // 评奖评优
    @RequestMapping(value = "/pjpy/admin/toPjpyList",method = RequestMethod.GET)
    public String pjpyList(){
        return "back/task/pjpyList";
    }
    @RequestMapping(value = "/pjpy/admin/toPjpyData",method = RequestMethod.GET)
    public String pjpyData(){
        return "back/task/pjpyData";
    }




    //图片上传
    @RequestMapping("/upload/images")
    @ResponseBody
    public Map<String,Object> uploadFile(MultipartFile file)throws IOException {

        UploadUtils upload = new UploadUtils();
        Map<String, Object> stringObjectMap = upload.uploadImage(file);

        return stringObjectMap;

    }




}

package com.zhfly021.controller;

import com.zhfly021.entity.Admin;
import com.zhfly021.entity.Bjjs;
import com.zhfly021.entity.Student;
import com.zhfly021.service.BjjsService;
import com.zhfly021.service.StudentService;
import com.zhfly021.utils.PageUtil;
import com.zhfly021.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-07 22:54
 */
@Controller
public class BjjsController {

    @Autowired
    BjjsService bjjsService;

    @Autowired
    StudentService studentService;

    @RequestMapping("/bjjs/queryBystuNo")
    @ResponseBody
    public RestResponse queryBystuNo(Integer page, Integer limit, @Nullable Bjjs bjjs,HttpSession session){
        bjjs.setSendNo(session.getAttribute("stuNo").toString());
        List<Bjjs> bjjsList = bjjsService.queryBystuNo(bjjs);
        if(!bjjsList.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bjjsList, page, limit, bjjsList.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }

    @RequestMapping("/bjjs/addBjjs")
    @ResponseBody
    public RestResponse addBjjs(Bjjs bjjs, HttpSession session){
        bjjs.setSendNo(session.getAttribute("stuNo").toString());
        bjjs.setSender(session.getAttribute("stuName").toString());
        bjjs.setSendClass(session.getAttribute("stuClass").toString());
        bjjs.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        bjjs.setResponser("");
        bjjs.setResponseTime("");
        bjjs.setResponseContent("");
        bjjs.setStatus("未回复");
        bjjs.setEvaluateContent("");
        bjjs.setEvaluate("");
        if("on".equals(bjjs.getMark())){
            bjjs.setMark("匿名");
        }else{
            bjjs.setMark("实名");
        }
        int i = bjjsService.addBjjs(bjjs);
        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"班级建设意见提交失败...");
        }
    }

    @RequestMapping("/bjjs/stu/deleteBjjs")
    @ResponseBody
    public RestResponse deleteBjjs(Integer id){
        int i = bjjsService.deleteBjjs(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级建设意见删除失败...");
        }
    }

    @RequestMapping("/bjjs/stu/bacthDeleteBjjs")
    @ResponseBody
    public RestResponse bacthDeleteBjjs(String data){
        String[] fields = data.split(",");
        int i = bjjsService.batchDeleteBjjs(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级建设意见删除失败...");
        }
    }


    @RequestMapping(value = "/bjjs/front/advise/toBjjsEdit/{id}",method = RequestMethod.GET)
    public String toEdit(@PathVariable("id") Integer id, Model model){
        Bjjs bjjsInfo = bjjsService.queryById(id);
        model.addAttribute("bjjsInfo",bjjsInfo);
        return "front/advise/bjjs_edit";
    }

    @RequestMapping("/bjjs/stu/updateBjjs")
    @ResponseBody
    public RestResponse updateBjjs(Bjjs bjjs){
        bjjs.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        if("on".equals(bjjs.getMark())){
            bjjs.setMark("匿名");
        }else{
            bjjs.setMark("实名");
        }
        int i = bjjsService.updateBjjs(bjjs);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级建设意见更新失败...");
        }
    }

    @RequestMapping(value = "/bjjs/front/advise/toBjjsDetail/{id}",method = RequestMethod.GET)
    public String toDetail(@PathVariable("id") Integer id, Model model){
        Bjjs bjjsInfo = bjjsService.queryById(id);
        model.addAttribute("bjjsInfo",bjjsInfo);
        return "front/advise/bjjs_detail";
    }


    @RequestMapping("/bjjs/stu/evaluateBjjs")
    @ResponseBody
    public RestResponse evaluateBjjs(Bjjs bjjs){
        bjjs.setStatus("已评价");
        int i = bjjsService.evaluateBjjs(bjjs);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级建设意见回复评价失败...");
        }
    }




//    ———————————————————————————————  管理员业务  —————————————————————————————————————————-
    @RequestMapping(value = "/bjlyb/admin/bjjs",method = RequestMethod.GET)
    public String bjjs(){
    return "back/advise/bjjs";
    }


    /**
     * 只有部分班委、辅导员【暂不考虑】看的到，用班级进行查询
     * 20-12-17: 目前只考虑有权限的对应班委查看班级建设意见列表，其他的角色不考虑
     * @param page
     * @param limit
     * @param bjjs
     * @param session
     * @return
     */
    @RequestMapping("/bjjs/queryBjjs")
    @ResponseBody
    public RestResponse queryBjjs(Integer page, Integer limit, @Nullable Bjjs bjjs,HttpSession session){
//        Student student = (Student) session.getAttribute("student");
        Admin admin = (Admin) session.getAttribute("admin");
        bjjs.setSendClass(admin.getEmployer());
        List<Bjjs> bjjsList = bjjsService.queryBjjs(bjjs);
        for (Bjjs bjjsData:bjjsList) {
            if("匿名".equals(bjjsData.getMark())){
                bjjsData.setSender("匿名");
            }
        }
        if(!bjjsList.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bjjsList, page, limit, bjjsList.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }

    /**
     * 管理员端回复班级建设意见--跳转至回复页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/bjlyb/admin/bjjs/toBjjsResponse/{id}",method = RequestMethod.GET)
    public String toResponse(@PathVariable("id") Integer id, Model model){
        Bjjs bjjs = bjjsService.queryById(id);
        model.addAttribute("bjjsResponse",bjjs);
        return "back/advise/bjjs_response";
    }

    @RequestMapping("/bjjs/admin/responseBjjs")
    @ResponseBody
    public RestResponse responseBjjs(Bjjs bjjs,HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Student student = studentService.queryByUsername(admin.getUsername());
        bjjs.setResponser(student.getStuName());
        bjjs.setResponseTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        bjjs.setStatus("已回复");
        int i = bjjsService.responseBjjs(bjjs);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级建设意见回复失败...");
        }
    }

    @RequestMapping(value = "/bjlyb/admin/bjjs/toBjjsResponseDetail/{id}",method = RequestMethod.GET)
    public String toBjjsResponseDetail(@PathVariable("id") Integer id, Model model){
        Bjjs bjjs = bjjsService.queryById(id);
        model.addAttribute("bjjsResponseDetail",bjjs);
        return "back/advise/bjjs_detail";
    }


}

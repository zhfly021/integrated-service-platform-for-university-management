package com.zhfly021.controller;

import com.zhfly021.entity.Admin;
import com.zhfly021.entity.Bjjs;
import com.zhfly021.entity.Bwgz;
import com.zhfly021.entity.Student;
import com.zhfly021.service.BwgzService;
import com.zhfly021.service.StudentService;
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
 * @create 2020-12-09 11:30
 */
@Controller
public class BwgzfkController {

    @Autowired
    BwgzService bwgzService;

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/front/advise/bwgzfk",method = RequestMethod.GET)
    public String test2(){
        return "front/advise/bwgz";
    }

    @RequestMapping("/bwgzfk/stu/queryBystuNo")
    @ResponseBody
    public RestResponse queryBystuNo(Integer page, Integer limit, @Nullable Bwgz bwgz, HttpSession session){
        bwgz.setSendNo(session.getAttribute("stuNo").toString());
        if("".equals(bwgz.getFeedback())){
            bwgz.setFeedback(null);
        }
        List<Bwgz> bwgzList = bwgzService.queryBystuNo(bwgz);
        if(!bwgzList.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bwgzList, page, limit, bwgzList.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    @RequestMapping(value = "/bwgzfk/toBwgzfkAdd",method = RequestMethod.GET)
    public String toBwgzfkAdd(){
        return "front/advise/bwgz_add";
    }

    @RequestMapping("/bwgzfk/stu/addBwgzfk")
    @ResponseBody
    public RestResponse addBwgz(Bwgz bwgz, HttpSession session){
        bwgz.setSendNo(session.getAttribute("stuNo").toString());
        bwgz.setSender(session.getAttribute("stuName").toString());
        bwgz.setSendClass(session.getAttribute("stuClass").toString());
        bwgz.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        bwgz.setResponser("");
        bwgz.setResponseTime("");
        bwgz.setResponseContent("");
        bwgz.setStatus("未回复");
        bwgz.setEvaluateContent("");
        bwgz.setEvaluate("");
        if("on".equals(bwgz.getMark())){
            bwgz.setMark("匿名");
        }else{
            bwgz.setMark("实名");
        }
        int i = bwgzService.addBwgz(bwgz);
        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"班委工作反馈提交失败...");
        }
    }

    @RequestMapping("/bwgzfk/stu/deleteBwgzfk")
    @ResponseBody
    public RestResponse deleteBwgz(Integer id){
        int i = bwgzService.deleteBwgz(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班委工作反馈删除失败...");
        }
    }

    @RequestMapping("/bwgzfk/stu/bacthDeleteBwgzfk")
    @ResponseBody
    public RestResponse bacthDeleteBwgz(String data){
        String[] fields = data.split(",");
        int i = bwgzService.batchDeleteBwgz(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班委工作反馈删除失败...");
        }
    }

    @RequestMapping(value = "/bwgzfk/toBwgzfkEdit/{id}",method = RequestMethod.GET)
    public String toEdit(@PathVariable("id") Integer id, Model model){
        Bwgz bwgzInfo = bwgzService.queryById(id);
        model.addAttribute("bwgzInfo",bwgzInfo);
        return "front/advise/bwgz_edit";
    }

    @RequestMapping("/bwgzfk/stu/updateBwgzfk")
    @ResponseBody
    public RestResponse updateBwgk(Bwgz bwgz){
        bwgz.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        if("on".equals(bwgz.getMark())){
            bwgz.setMark("匿名");
        }else{
            bwgz.setMark("实名");
        }
        int i = bwgzService.updateBwgz(bwgz);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班委工作反馈删除失败...");
        }
    }

    @RequestMapping(value = "/bwgzfk/toBwgzfkDetail/{id}",method = RequestMethod.GET)
    public String toDetail(@PathVariable("id") Integer id, Model model){
        Bwgz bwgzInfo = bwgzService.queryById(id);
        model.addAttribute("bwgzInfo",bwgzInfo);
        return "front/advise/bwgz_detail";
    }


    @RequestMapping("/bwgzfk/stu/evaluateBwgzfk")
    @ResponseBody
    public RestResponse evaluateBwgzfk(Bwgz bwgz){
        bwgz.setStatus("已评价");
        int i = bwgzService.evaluateBwgz(bwgz);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班委工作反馈回复评价失败...");
        }
    }




    //    ———————————————————————————————  管理员业务  —————————————————————————————————————————-
    @RequestMapping(value = "/bjlyb/admin/bwgz",method = RequestMethod.GET)
    public String bwgz(){
        return "back/advise/bwgz";
    }


    /**
     * 只有部分班委、辅导员【暂不考虑】看的到，用班级进行查询
     * 20-12-18: 目前只考虑有权限的对应班委查看班级建设意见列表，其他的角色不考虑
     * @param page
     * @param limit
     * @param bwgz
     * @param session
     * @return
     */
    @RequestMapping("/bwgzfk/queryBwgzfk")
    @ResponseBody
    public RestResponse queryBwgzfk(Integer page, Integer limit, @Nullable Bwgz bwgz, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        bwgz.setSendClass(admin.getEmployer());
        if("".equals(bwgz.getLetterTitle())){
            bwgz.setLetterTitle(null);
        }
        if("".equals(bwgz.getFeedback())){
            bwgz.setFeedback(null);
        }
        if("".equals(bwgz.getStatus())){
            bwgz.setStatus(null);
        }

        Student student = studentService.queryByUsername(admin.getUsername());
        if(!"团支书,班长,组织委员,宣传委员,权益委员".contains(student.getCommittee())){
            bwgz.setFeedback(student.getCommittee());
        }


        List<Bwgz> bwgzList = bwgzService.queryBwgzfk(bwgz);
        for (Bwgz bwgzData:bwgzList) {
            if("匿名".equals(bwgzData.getMark())){
                bwgzData.setSender("匿名");
            }
        }

        if(!bwgzList.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bwgzList, page, limit, bwgzList.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    /**
     * 管理员端回复班委工作反馈--跳转至回复页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/bjlyb/admin/bwgzfk/toBwgzfkResponse/{id}",method = RequestMethod.GET)
    public String toResponse(@PathVariable("id") Integer id, Model model){
        Bwgz bwgz = bwgzService.queryById(id);
        model.addAttribute("bwgzResponse",bwgz);
        return "back/advise/bwgz_response";
    }

    @RequestMapping("/bwgzfk/admin/responseBwgzfk")
    @ResponseBody
    public RestResponse responseBwgzfk(Bwgz bwgz,HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Student student = studentService.queryByUsername(admin.getUsername());
        bwgz.setResponser(student.getStuName());
        bwgz.setResponseTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        bwgz.setStatus("已回复");
        int i = bwgzService.responseBwgzfk(bwgz);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班委工作反馈意见回复失败...");
        }
    }

    @RequestMapping(value = "/bjlyb/admin/bjjs/toBwgzfkResponseDetail/{id}",method = RequestMethod.GET)
    public String toBwgzfkResponseDetail(@PathVariable("id") Integer id, Model model){
        Bwgz bwgz = bwgzService.queryById(id);
        model.addAttribute("bwgzfkResponseDetail",bwgz);
        return "back/advise/bwgz_detail";
    }


}

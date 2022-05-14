package com.zhfly021.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.zhfly021.entity.*;
import com.zhfly021.service.BjtzService;
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
import java.util.*;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-03 13:14
 */
@Controller
public class BjtzController {
    @Autowired
    BjtzService bjtzService;

    @Autowired
    StudentService studentService;

    @RequestMapping("/bjtz/stu/queryBjtzByClass")
    public String queryBjtzByClass(Model model, HttpSession session){
        String stuClass = (String) session.getAttribute("stuClass");
        List<Bjtz> bjtzs = bjtzService.queryByClass(stuClass);

        for (int i=0; i<bjtzs.size(); i++){
            if ( null != bjtzs.get(i).getImages() && bjtzs.get(i).getImages().contains(",")){
                String[] list = bjtzs.get(i).getImages().split(",");
                bjtzs.get(i).setImageList(list);
            }else {
                String[] list = {"无图片"};
                bjtzs.get(i).setImageList(list);
            }
        }

        model.addAttribute("bjtzs",bjtzs);
        return "front/home/console";
    }







//    -----------------管理员业务————————————————----------

    // 学生测评数据的业务处理
    @RequestMapping(value = "/bjrw/admin/inform",method = RequestMethod.GET)
    public String bwmydcpData(){
        return "back/inform/inform";
    }

    @RequestMapping("/bjtz/admin/queryBjtz")
    @ResponseBody
    public RestResponse queryBjtz(Integer page, Integer limit, @Nullable Bjtz bjtz, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");

        String adminEmployer = (String) session.getAttribute("adminEmployer");

        bjtz.setPublisherClass(admin.getEmployer());

        if("".equals(bjtz.getPublisher())){
            bjtz.setPublisher(null);
        }
        if("".equals(bjtz.getPublisherCommittee())){
            bjtz.setPublisherCommittee(null);
        }
        if("".equals(bjtz.getReleaseTimestamp())){
            bjtz.setReleaseTimestamp(null);
        }

        String stamp1 = null;
        String stamp2 = null;
        if(bjtz.getReleaseTimestamp() != null){
            String releaseTime = bjtz.getReleaseTimestamp();
            stamp1 = String.valueOf(new Date(releaseTime.substring(0,releaseTime.length()/2)).getTime());
            stamp2 = String.valueOf(new Date(releaseTime.substring(releaseTime.length()/2 + 2)).getTime());
        }
        bjtz.setStamp1(stamp1);
        bjtz.setStamp2(stamp2);

        if("4".equals(adminEmployer)){
            Student student = studentService.queryByUsername(admin.getUsername());
            bjtz.setPublisherCommittee(student.getCommittee());
        }

        List<Bjtz> bjtzs = bjtzService.queryBjtz(bjtz);
//        for (Bjtz data:bjtzs) {
//            Date releaseTime = data.getReleaseTime();
//            data.setReleaseTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(releaseTime));
//        }
        if(!bjtzs.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bjtzs, page, limit, bjtzs.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }

    @RequestMapping(value = "/bjtz/admin/toAdd",method = RequestMethod.GET)
    public String toAdd(){
        return "back/inform/inform_add";
    }

    @RequestMapping(value = "/bjtz/admin/addBjtz",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse addBjtz(Bjtz bjtz, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Student student = studentService.queryByUsername(admin.getUsername());
        bjtz.setPublisher(student.getStuName());
        bjtz.setPublisherNo(student.getStuNo());
        bjtz.setPublisherClass(student.getStuClass());
        bjtz.setPublisherCommittee(student.getCommittee());
//        bjtz.setReleaseTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        Date date = new Date();
        bjtz.setReleaseTime(date);
        bjtz.setReleaseTimestamp(String.valueOf(date.getTime()));
        int i = bjtzService.addBjtz(bjtz);
        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"班级通知发布失败...");
        }
    }

    @RequestMapping(value = "/bjtz/admin/toBjtzEdit/{id}",method = RequestMethod.GET)
    public String toEdit(@PathVariable("id") Integer id, Model model){
        Bjtz bjtzInfo = bjtzService.queryBjtzById(id);

        System.out.println(bjtzInfo.getImages().contains(","));
        if ( null != bjtzInfo.getImages() && bjtzInfo.getImages().contains(",")){
            String[] list = bjtzInfo.getImages().split(",");
            bjtzInfo.setImageList(list);
        }else {
            String[] list = {"无图片"};
            bjtzInfo.setImageList(list);
        }

        model.addAttribute("bjtzInfo",bjtzInfo);
        return "back/inform/inform_edit";
    }

    @RequestMapping(value = "/bjtz/admin/updateBjjs", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse updateBjjs(Bjtz bjtz, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Student student = studentService.queryByUsername(admin.getUsername());
        bjtz.setPublisher(student.getStuName());
        bjtz.setPublisherNo(student.getStuNo());
        bjtz.setPublisherClass(student.getStuClass());
        bjtz.setPublisherCommittee(student.getCommittee());
//        bjtz.setReleaseTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        Date date = new Date();
        bjtz.setReleaseTime(date);
        bjtz.setReleaseTimestamp(String.valueOf(date.getTime()));

        if ("" == bjtz.getImages() ){
            bjtz.setImages("无图片");
        }

        int i = bjtzService.updateBjtzById(bjtz);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级通知更新失败...");
        }
    }

    @RequestMapping("/bjtz/admin/deleteBjtz")
    @ResponseBody
    public RestResponse deleteBwmydcpData(Integer id, HttpSession session){
//        Admin admin = (Admin) session.getAttribute("admin");
        int i = bjtzService.deleteBjtz(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级通知删除失败...");
        }
    }

    @RequestMapping("/bjtz/admin/bacthDeleteBjtz")
    @ResponseBody
    public RestResponse bacthDeleteBwmydcpData(String data){
        String[] fields = data.split(",");
        int i = bjtzService.batchDeleteBjtz(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级通知删除失败...");
        }
    }
}

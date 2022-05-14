package com.zhfly021.controller;

import com.zhfly021.entity.*;
import com.zhfly021.service.BwmydcpService;
import com.zhfly021.service.StudentService;
import com.zhfly021.utils.PageUtil;
import com.zhfly021.utils.RestResponse;
import org.apache.ibatis.annotations.Param;
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
 * @create 2020-12-10 8:07
 */
@Controller
public class BwmydcpController {

    @Autowired
    BwmydcpService bwmydcpService;

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/front/advise/bwmydcp",method = RequestMethod.GET)
    public String test2(){
        return "front/advise/bwmydcp";
    }


    /*@RequestMapping(value = "/bwmydcp/toBwmydcpDetail/{id}",method = RequestMethod.GET)
    public String toDetail(@PathVariable("id") Integer id, Model model, HttpSession session){
        String stuNo = session.getAttribute("stuNo").toString();
        String projectID = bwmydcpService.queryProjectIdByID(id);
        BwmydcpData bwmydcpData = bwmydcpService.queryOne(projectID, stuNo);
        model.addAttribute("bwmydcpData",bwmydcpData);
        return "front/advise/bwmydcp_detail";
    }

    @RequestMapping(value = "/bwmydcp/toBwmydcpEdit/{id}",method = RequestMethod.GET)
    public String toEdit(@PathVariable("id") Integer id, Model model, HttpSession session){
        String stuNo = session.getAttribute("stuNo").toString();
        String projectID = bwmydcpService.queryProjectIdByID(id);
        String status = bwmydcpService.queryStatus(projectID, stuNo);
        if("未完成".equals(status)){
            model.addAttribute("projectID",projectID);
            return "front/advise/bwmydcp_edit";
        }else {
            return "";
        }
    }*/

    @RequestMapping(value = "/bwmydcp/stu/toBwmydcpWindow/{id}",method = RequestMethod.GET)
    public String toBwmydcpWindow(@PathVariable("id") Integer id, Model model, HttpSession session){
        String stuNo = session.getAttribute("stuNo").toString();
        String projectID = bwmydcpService.queryProjectIdByID(id);
        String status = bwmydcpService.queryStatus(projectID, stuNo);
        if("未完成".equals(status)){
            model.addAttribute("projectID",projectID);
            return "front/advise/bwmydcp_edit";
        }else if("已完成".equals(status)){
            BwmydcpData bwmydcpData = bwmydcpService.queryOne(projectID, stuNo);
            model.addAttribute("bwmydcpData",bwmydcpData);
            return "front/advise/bwmydcp_detail";
        }else{  // 已逾期
            BwmydcpData bwmydcpData = bwmydcpService.queryOne(projectID, stuNo);
            model.addAttribute("bwmydcpReason",bwmydcpData);
            return "front/advise/bwmydcp_reason";
        }
    }




    @RequestMapping("/bwmydcp/stu/addBwmydcpData")
    @ResponseBody
    public RestResponse addBwmydcpData(BwmydcpData bwmydcpData, HttpSession session){
        String stuNo = session.getAttribute("stuNo").toString();
        if("on".equals(bwmydcpData.getEvaMark())){
            bwmydcpData.setEvaMark("匿名");
        }else{
            bwmydcpData.setEvaMark("实名");
        }
        bwmydcpData.setStuNo(stuNo);
        bwmydcpData.setEvaTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        bwmydcpData.setEvaStatus("已完成");

        String eva8 = "";
        for (String str:bwmydcpData.getEvaluation8s()) {
            if(str != null){
                eva8 += str + ",";
            }
        }
        String substring1 = eva8.substring(0, eva8.length() - 1);
        bwmydcpData.setEvaluation8(substring1);

        String eva15 = "";
        for (String str:bwmydcpData.getEvaluation15s()) {
            if(str != null){
                eva15 += str + ",";
            }
        }
        String substring2 = eva15.substring(0, eva15.length() - 1);
        bwmydcpData.setEvaluation15(substring2);

        String eva16 = "";
        for (String str:bwmydcpData.getEvaluation16s()) {
            if(str != null){
                eva16 += str + ",";
            }
        }
        String substring3 = eva16.substring(0, eva16.length() - 1);
        bwmydcpData.setEvaluation16(substring3);





        int i = bwmydcpService.addBwmydcpData(bwmydcpData);
        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"班委满意度测评提交失败...");
        }
    }


    @RequestMapping("/bwmydcp/stu/queryBwmydcpList")
    @ResponseBody
    public RestResponse queryBwmydcpList(Integer page, Integer limit, @Nullable String project, @Nullable String status, HttpSession session){
        String stuNo = session.getAttribute("stuNo").toString();
        List<BwmydcpListAndData> bwmydcpListAndDataList = bwmydcpService.queryByStuClass(stuNo,project, status);

        // 仍然存在问题，需要学生登录之后才可以刷新状态，后期结合管理员进行状态刷新，用户这边只需要刷新状态之前判断一下数据库中的状态是否已经是已逾期，如果是则用户这边不进行修改，否则用户这边执行修改操作
        for (BwmydcpListAndData bwmydcpListAndData:bwmydcpListAndDataList) {
            String deadline = bwmydcpListAndData.getDeadline();
            long nowTiem = new Date().getTime();
            Long deadlineTime = Long.valueOf(deadline);
            if((nowTiem - deadlineTime) >= 0){
                String projectID = bwmydcpListAndData.getProjectID();
                BwmydcpData bwmydcpData = bwmydcpService.queryOne(projectID, stuNo);
                int id = bwmydcpData.getIdd();
                if("未完成".equals(bwmydcpData.getEvaStatus())){
                    bwmydcpService.updateStatusOfYYQ(id);
                }
            }
        }
        bwmydcpListAndDataList = bwmydcpService.queryByStuClass(stuNo,project, status);

        for (BwmydcpListAndData bwmydcpListAndData:bwmydcpListAndDataList) {
            String deadline = bwmydcpListAndData.getDeadline();
            bwmydcpListAndData.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(new Long(deadline))));
        }
        if(!bwmydcpListAndDataList.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bwmydcpListAndDataList, page, limit, bwmydcpListAndDataList.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }



    //    ———————————————————————————————  管理员业务  —————————————————————————————————————————-
    @RequestMapping(value = "/bjlyb/admin/bwmydcpList",method = RequestMethod.GET)
    public String bwgz(){
        return "back/advise/bwmydcpList";
    }

    @RequestMapping("/bwmydcpList/queryBwmydcpList")
    @ResponseBody
    public RestResponse queryBwmydcpList(Integer page, Integer limit, @Nullable BwmydcpList bwmydcpList, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        bwmydcpList.setPublisherClass(admin.getEmployer());
        if("".equals(bwmydcpList.getProject())){
            bwmydcpList.setProject(null);
        }
        if("".equals(bwmydcpList.getStatus())){
            bwmydcpList.setStatus(null);
        }

        List<BwmydcpList> bwmydcpLists = bwmydcpService.queryBwmydcpList(bwmydcpList);

        for (BwmydcpList bwmydcp:bwmydcpLists) {
            String deadline = bwmydcp.getDeadline();
            long nowTime = new Date().getTime();
            Long deadlineTime = Long.valueOf(deadline);
            if((nowTime - deadlineTime) > 0){
                String projectID = bwmydcp.getProjectID();
                bwmydcpService.updateListStatusOfYYQ(projectID);
                bwmydcpService.updateDataStatusOfYYQ(projectID);
            }
        }

        bwmydcpLists = bwmydcpService.queryBwmydcpList(bwmydcpList);
        for (BwmydcpList bwmydcp:bwmydcpLists) {
            String deadline = bwmydcp.getDeadline();
            bwmydcp.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(new Long(deadline))));
        }

        if(!bwmydcpLists.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bwmydcpLists, page, limit, bwmydcpLists.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }

    @RequestMapping(value = "/bjlyb/admin/addBwmydcpList",method = RequestMethod.GET)
    public String toAdd(){
        return "back/advise/bwmydcpList_add";
    }


    /**
     * 后台生成测评项目相关数据
     */
    @RequestMapping("/bwmydcp/admin/addBwmydcpList")
    @ResponseBody
    public RestResponse addBwmydcpList(BwmydcpList bwmydcpList, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Student stuCommittee = studentService.queryByUsername(admin.getUsername());
        Date release = new Date();  // 发布时间
        // list
        bwmydcpList.setReleaseTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(release));
        bwmydcpList.setPublisher(stuCommittee.getStuName());
        bwmydcpList.setPublisherNo(stuCommittee.getStuNo());
        bwmydcpList.setPublisherClass(stuCommittee.getStuClass());
        bwmydcpList.setPublisherCommittee(stuCommittee.getCommittee());
        String projectID = String.valueOf(release.getTime());
        bwmydcpList.setProjectID(projectID);
        // 对前端传来的时间进行格式化操作
        String deadline = bwmydcpList.getDeadline();
        bwmydcpList.setDeadline(String.valueOf(new Date(deadline).getTime()));

        long nowTime = new Date().getTime();
        Long deadlineTime = Long.valueOf(String.valueOf(new Date(deadline).getTime()));
        if((nowTime - deadlineTime) > 0){
            bwmydcpList.setStatus("已结束");
        }else {
            bwmydcpList.setStatus("已开始");
        }
        bwmydcpService.addBwmydcpList(bwmydcpList);

        // data
        BwmydcpData bwmydcpData = new BwmydcpData();
        List<Student> students = studentService.queryByStuClass(admin.getEmployer());
        for (Student student:students) {
            bwmydcpData.setStuName(student.getStuName());
            bwmydcpData.setStuNo(student.getStuNo());
            bwmydcpData.setStuClass(student.getStuClass());
            bwmydcpData.setEvaProject(bwmydcpList.getProject());
            bwmydcpData.setEvaProjectID(projectID);
            bwmydcpData.setEvaStatus("未完成");
            bwmydcpService.updateBwmydcpDataByInit(bwmydcpData);
        }

        if(students != null){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"班委满意度测评创建失败...");
        }

    }

    @RequestMapping(value = "/bwmydcp/admin/toBwmydcpEdit/{id}",method = RequestMethod.GET)
    public String toEdit(@PathVariable("id") Integer id, Model model){
        BwmydcpList bwmydcpList = bwmydcpService.queryBwmydcpListOfOne(id);
        String deadline = bwmydcpList.getDeadline();
        bwmydcpList.setDeadline(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(new Long(deadline))));
        model.addAttribute("bwmydcpListInfo",bwmydcpList);
        return "back/advise/bwmydcpList_edit";
    }

    @RequestMapping("/bwmydcp/admin/updateBwmydcp")
    @ResponseBody
    public RestResponse updateBwmydcp(BwmydcpList bwmydcpList){

        // 对前端传来的时间进行格式化操作
        String deadline = bwmydcpList.getDeadline();
        bwmydcpList.setDeadline(String.valueOf(new Date(deadline).getTime()));

        long nowTime = new Date().getTime();
        Long deadlineTime = Long.valueOf(String.valueOf(new Date(deadline).getTime()));
        if((nowTime - deadlineTime) > 0){
            bwmydcpList.setStatus("已结束");
        }else {
            bwmydcpList.setStatus("已开始");
        }

        int i = bwmydcpService.updateBwmydcpList(bwmydcpList);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班委满意度测评项目更新失败...");
        }
    }


    @RequestMapping("/bwmydcp/admin/deleteBwmydcp")
    @ResponseBody
    public RestResponse deleteBwmydcp(Integer id, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        String projectId = bwmydcpService.queryProjectIdByID(id);
        bwmydcpService.delBwmydcpData(projectId, admin.getEmployer());
        int i = bwmydcpService.deleteBwmydcpList(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级满意度测评删除失败...");
        }
    }

    @RequestMapping("/bwmydcp/admin/bacthDeleteBwmydcp")
    @ResponseBody
    public RestResponse bacthDeleteBwmydcp(String data, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");

        String[] fields = data.split(",");
        for (String field:fields) {
            String projectId = bwmydcpService.queryProjectIdByID(Integer.valueOf(field));
            bwmydcpService.delBwmydcpData(projectId, admin.getEmployer());
        }
        int i = bwmydcpService.batchDelBwmydcpList(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级满意度测评删除失败...");
        }
    }


    // 学生测评数据的业务处理
    @RequestMapping(value = "/bjlyb/admin/bwmydcpData",method = RequestMethod.GET)
    public String bwmydcpData(Model model, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        List<String> evaProjects = bwmydcpService.queryEvaProjectByStuClass(admin.getEmployer());
        model.addAttribute("evaProjects", evaProjects);  // 与 ResponseBody 注解冲突，不生效
        return "back/advise/bwmydcpData";
    }

    @RequestMapping("/bwmydcpList/queryBwmydcpData")
    @ResponseBody
    public RestResponse queryBwmydcpData(Integer page, Integer limit, @Nullable BwmydcpData bwmydcpData, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        bwmydcpData.setStuClass(admin.getEmployer());

        if("".equals(bwmydcpData.getStuName())){
            bwmydcpData.setStuName(null);
        }
        if("".equals(bwmydcpData.getEvaProject())){
            bwmydcpData.setEvaProject(null);
        }
        if("".equals(bwmydcpData.getEvaStatus())){
            bwmydcpData.setEvaStatus(null);
        }
        List<BwmydcpData> bwmydcpDatas = bwmydcpService.queryBwmydcpData(bwmydcpData);

        if(!bwmydcpDatas.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bwmydcpDatas, page, limit, bwmydcpDatas.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }

    @RequestMapping(value = "/bjlyb/admin/detailBwmydcpData/{idd}",method = RequestMethod.GET)
    public String toDetail(@PathVariable("idd") Integer idd, Model model){
        BwmydcpData bwmydcpData = bwmydcpService.queryBwmydcpDataById(idd);
        model.addAttribute("bwmydcpDataInfo", bwmydcpData);
        return "back/advise/bwmydcpData_detail";
    }

    @RequestMapping("/bwmydcp/admin/deleteBwmydcpData")
    @ResponseBody
    public RestResponse deleteBwmydcpData(Integer idd, HttpSession session){
//        Admin admin = (Admin) session.getAttribute("admin");
        int i = bwmydcpService.delBwmydcpDataById(idd);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级满意度测评数据删除失败...");
        }
    }

    @RequestMapping("/bwmydcp/admin/bacthDeleteBwmydcpData")
    @ResponseBody
    public RestResponse bacthDeleteBwmydcpData(String data){
        String[] fields = data.split(",");
        int i = bwmydcpService.batchDelBwmydcpDataById(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"班级满意度测评数据删除失败...");
        }
    }




    // 班委满意度测评数据分析
    @RequestMapping(value = "/bjlyb/admin/toBwmydcpAnalysis",method = RequestMethod.GET)
    public String toBwmydcpAnalysis(){
        return "back/advise/bwmydcpData_detail";
    }


}

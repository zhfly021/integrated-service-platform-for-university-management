package com.zhfly021.controller;

import com.zhfly021.entity.*;
import com.zhfly021.mapper.ZhcpMapper;
import com.zhfly021.service.StudentService;
import com.zhfly021.service.ZhcpService;
import com.zhfly021.utils.EmailUtils;
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
import java.util.*;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-22 9:42
 */
@Controller
public class ZhcpController {

    @Autowired
    ZhcpService zhcpService;

    @Autowired
    EmailUtils emailUtils;

    @Autowired
    StudentService studentService;

    //----------------  前台业务  ------------------

    // 测评任务
    @RequestMapping("/zhcp/stu/toStuZhcp")
    public String toStuZhcp(Model model, HttpSession session){
        Student student = (Student) session.getAttribute("student");
        List<String> project = zhcpService.queryProjectByClass(student.getStuClass());
        model.addAttribute("zhcpListProjectOfStu", project);
        return "front/task/zhcp";
    }


    /*
    // V1.0
    @RequestMapping("/zhcp/stu/queryZhcpList")
    @ResponseBody
    public RestResponse queryZhcpList(Integer page, Integer limit, @Nullable String project, @Nullable String status, HttpSession session){
        Student student = (Student) session.getAttribute("student");
        if("".equals(project)){
            project = null;
        }
        if("".equals(status)){
            status = null;
        }
        List<ZhcpListAndData> zhcpListAndDataList = zhcpService.queryByStu(student.getStuNo(), project, status);

        for (ZhcpListAndData zhcpListAndData :zhcpListAndDataList) {
            String deadline = zhcpListAndData.getDeadline();
            long nowTiem = new Date().getTime();
            Long deadlineTime = Long.valueOf(deadline);
            String projectID = zhcpListAndData.getProjectID();
            ZhcpData zhcpData = zhcpService.queryZhcpDataByProjectIDAndStuNo(projectID, student.getStuNo());
            ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(projectID, student.getStuNo());
            ZhcpSummary zhcpSummary = zhcpService.queryZhcpSummaryByProjectIDAndStuNo(projectID, student.getStuNo());
            if((nowTiem - deadlineTime) >= 0){
                if(zhcpData != null && "未完成".equals(zhcpData.getEvaStatus())){
                    zhcpService.updateDataStatusOfStu(projectID,"已逾期",student.getStuNo());
                }
                if(zhcpDataReview != null && "未完成".equals(zhcpDataReview.getEvaStatus())){
                    zhcpService.updateDataReviewStatusOfStu(projectID,"已逾期",student.getStuNo());
                }
                if(zhcpSummary != null && "未完成".equals(zhcpSummary.getEvaStatus())){
                    zhcpService.updateSummaryStatusOfStu(projectID,"已逾期",student.getStuNo());
                }
            }else{
                if(zhcpData != null && "已逾期".equals(zhcpData.getEvaStatus())){
                    zhcpService.updateDataStatusOfStu(projectID,"未完成",student.getStuNo());
                }
                if(zhcpDataReview != null && "已逾期".equals(zhcpDataReview.getEvaStatus())){
                    zhcpService.updateDataReviewStatusOfStu(projectID,"未完成",student.getStuNo());
                }
                if(zhcpSummary != null && "已逾期".equals(zhcpSummary.getEvaStatus())){
                    zhcpService.updateSummaryStatusOfStu(projectID,"未完成",student.getStuNo());
                }
            }
        }
        zhcpListAndDataList = zhcpService.queryByStu(student.getStuNo(), project, status);
        for (ZhcpListAndData zhcpListAndData:zhcpListAndDataList) {
            String deadline = zhcpListAndData.getDeadline();
            zhcpListAndData.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(new Long(deadline))));
        }
        if(!zhcpListAndDataList.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(zhcpListAndDataList, page, limit, zhcpListAndDataList.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }

*/


    // V2.0
    @RequestMapping("/zhcp/stu/queryZhcpList")
    @ResponseBody
    public RestResponse queryZhcpList(Integer page, Integer limit, @Nullable String project, @Nullable String status, HttpSession session){
        Student student = (Student) session.getAttribute("student");
        if("".equals(project)){
            project = null;
        }
        if("".equals(status)){
            status = null;
        }
        List<ZhcpListAndData> zhcpListAndDataList = zhcpService.queryByStu(student.getStuNo(), project, status);

        for (ZhcpListAndData zhcpListAndData :zhcpListAndDataList) {
            String deadline = zhcpListAndData.getDeadline();
            long nowTiem = new Date().getTime();
            Long deadlineTime = Long.valueOf(deadline);
            if((nowTiem - deadlineTime) >= 0){
                if(!"已完成".equals(zhcpListAndData.getZhcpData().getEvaStatus())){
                    zhcpListAndData.getZhcpData().setEvaStatus("已结束");
                }
            }
        }
        for (ZhcpListAndData zhcpListAndData:zhcpListAndDataList) {
            String deadline = zhcpListAndData.getDeadline();
            zhcpListAndData.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(new Long(deadline))));
        }
        if(!zhcpListAndDataList.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(zhcpListAndDataList, page, limit, zhcpListAndDataList.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }



    @RequestMapping("/zhcp/stu/toZhcpEdit/{id}")
    public String toZhcpEdit(@PathVariable("id")Integer id, Model model,HttpSession session){
        /*ZhcpData zhcpData = zhcpService.queryZhcpDataById(id);
        if("已逾期".equals(zhcpData.getEvaStatus())){
            return "front/task/zhcp_reason";
        }else {
            return "front/task/zhcp_edit";
        }*/

        Student student = (Student) session.getAttribute("student");
        model.addAttribute("dataId",id);
        String stuClass = student.getStuClass();
        String stuName = student.getStuName();
        String zhcpTime = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        model.addAttribute("zhcpStuClass",stuClass);
        model.addAttribute("zhcpStuName",stuName);
        model.addAttribute("zhcpTime",zhcpTime);
        ZhcpList zhcpList = zhcpService.queryZhcpListById(id);
        ZhcpData zhcpData = zhcpService.queryZhcpDataByProjectIDAndStuNo(zhcpList.getProjectID(), student.getStuNo());
        ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(zhcpList.getProjectID(), student.getStuNo());
        if(zhcpData != null){
            model.addAttribute("zhcpData",zhcpData);
        }
        if(zhcpDataReview != null){
            model.addAttribute("zhcpDataReviewOfInit",zhcpDataReview);
        }


        return "front/task/zhcp_edit";
    }

    @RequestMapping("/zhcp/stu/addZhcpData")
    @ResponseBody
    public RestResponse addZhcpData(ZhcpData zhcpData,HttpSession session){
        Student student = (Student) session.getAttribute("student");
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

        // 德育素质考核
        double moralSum = 0.0, moralInit = 0.0, moralAdd = 0.0, moralMinus = 0.0;
        moralInit = zhcpData.getMoralInit();
        moralAdd = zhcpData.getMoralAdd1() + zhcpData.getMoralAdd2() + zhcpData.getMoralAdd3() + zhcpData.getMoralAdd4() + zhcpData.getMoralAdd5() +
                zhcpData.getMoralAdd6() + zhcpData.getMoralAdd7() + zhcpData.getMoralAdd8();
        moralMinus = zhcpData.getMoralMinus1() + zhcpData.getMoralMinus2() + zhcpData.getMoralMinus3() + zhcpData.getMoralMinus4() + zhcpData.getMoralMinus5() +
                zhcpData.getMoralMinus6() + zhcpData.getMoralMinus7() + zhcpData.getMoralMinus8() + zhcpData.getMoralMinus9();
        moralSum = moralInit + moralAdd - moralMinus;
        if(moralSum > 100){
            moralSum = 100;
        }else if(moralSum < 0){
            moralSum = 0;
        }
        // 智育素质考核
        double intellectualSum = 0.0, intellectualYear = 0.0, intellectualCredit = 0.0;
        intellectualYear = zhcpData.getIntellectualYear();
        intellectualCredit = zhcpData.getIntellectualCredit();
        intellectualSum = intellectualYear + intellectualCredit;
        if(intellectualSum > 100){
            intellectualSum = 100;
        }else if(intellectualSum < 0){
            intellectualSum = 0;
        }
        // 体育素质考核
        double physicalSum = 0.0, physicalInit = 0.0, physicalAdd = 0.0, physicalMinus = 0.0;
        physicalInit = zhcpData.getPhysicalInit();
        physicalAdd = zhcpData.getPhysicalAdd1() + zhcpData.getPhysicalAdd2() + zhcpData.getPhysicalAdd3() + zhcpData.getPhysicalAdd4();
        physicalMinus = zhcpData.getPhysicalMinus1() + zhcpData.getPhysicalMinus2() + zhcpData.getPhysicalMinus3();
        physicalSum = physicalInit + physicalAdd - physicalMinus;
        if(physicalSum > 100){
            physicalSum = 100;
        }else if(physicalSum < 0){
            physicalSum = 0;
        }
        // 能力素质考核
        double competenceSum = 0.0, competenceInit = 0.0, competenceAdd = 0.0, competenceMinus = 0.0;
        competenceInit = zhcpData.getCompetenceInit();
        competenceAdd = zhcpData.getCompetenceAdd1() + zhcpData.getCompetenceAdd2() + zhcpData.getCompetenceAdd3() + zhcpData.getCompetenceAdd4() +
                zhcpData.getCompetenceAdd5() + zhcpData.getCompetenceAdd6() + zhcpData.getCompetenceAdd7();
        competenceMinus = zhcpData.getCompetenceMinus1() + zhcpData.getCompetenceMinus2() + zhcpData.getCompetenceMinus3();
        competenceSum = competenceInit + competenceAdd - competenceMinus;
        if(competenceSum > 100){
            competenceSum = 100;
        }else if(competenceSum < 0){
            competenceSum = 0;
        }

        zhcpData.setMoralSum(moralSum);
        zhcpData.setIntellectualSum(intellectualSum);
        zhcpData.setPhysicalSum(physicalSum);
        zhcpData.setCompetenceSum(competenceSum);

        double summary = 0.0;
        String evaProjectID = zhcpService.queryZhcpListById(zhcpData.getId()).getProjectID();
        ZhcpList zhcpList = zhcpService.queryZhcpListByProjectID(evaProjectID, student.getStuClass());

        String zcObject = zhcpList.getZcObject();
        if("大一,大二".contains(zcObject)){
            summary = (0.3 * moralSum) + (0.5 * intellectualSum) + (0.1 * physicalSum) + (0.1 * competenceSum);
        }else if("大三,大四".contains(zcObject)){
            summary = (0.3 * moralSum) + (0.4 * intellectualSum) + (0.1 * physicalSum) + (0.2 * competenceSum);
        }
        zhcpData.setSummary(summary);
//        zhcpData.setEvaStatus("待审核");
        ZhcpDataReview zhcpDataReview = new ZhcpDataReview();

//        zhcpDataReview.setEvaStatus("待审核");
//        zhcpDataReview.setCultural(zhcpData.getCultural());
        zhcpDataReview.setEvaProjectID(evaProjectID);
        zhcpDataReview.setStuNo(student.getStuNo());
        zhcpDataReview.setEvaMarks(zhcpData.getEvaMarks());
        zhcpData.setEvaProjectID(evaProjectID);
        zhcpData.setStuNo(student.getStuNo());


        ZhcpDataReview dataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(evaProjectID, student.getStuNo());

        if(dataReview.getEvaTime() == null){
            // 未提交过数据
            zhcpData.setEvaTime(evaTime);
            zhcpDataReview.setEvaTime(evaTime);
        }else{
            // 已提交过数据
            zhcpData.setEvaTime(dataReview.getEvaTime());
            zhcpDataReview.setEvaTime(dataReview.getEvaTime());
        }
        zhcpData.setEvaTime(evaTime);
        zhcpDataReview.setEvaTime(evaTime);

        if(dataReview.getReviewsSubmit() == null){
            // 未提交过复核申请
            zhcpData.setEvaStatus("待审核");
            zhcpDataReview.setEvaStatus("待审核");
        }else{
            // 提交过复核申请
            zhcpData.setEvaStatus("待复核");
            zhcpDataReview.setEvaStatus("待复核");
        }


        int i = zhcpService.addZhcpData(zhcpData);
        int j = zhcpService.updateZhcpDataReviewByInit(zhcpDataReview);
        if((i != 0) && (j != 0)){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"综合测评数据提交失败...");
        }
    }

    /**
     * 学生端暂存综合测评数据
     * @param zhcpData
     * @param session
     * @return
     */
    @RequestMapping("/zhcp/stu/storageZhcpData")
    @ResponseBody
    public RestResponse storageZhcpData(ZhcpData zhcpData,HttpSession session){
        Student student = (Student) session.getAttribute("student");

        // 德育素质考核
        double moralSum = 0.0, moralInit = 0.0, moralAdd = 0.0, moralMinus = 0.0;
        moralInit = zhcpData.getMoralInit();
        moralAdd = zhcpData.getMoralAdd1() + zhcpData.getMoralAdd2() + zhcpData.getMoralAdd3() + zhcpData.getMoralAdd4() + zhcpData.getMoralAdd5() +
                zhcpData.getMoralAdd6() + zhcpData.getMoralAdd7() + zhcpData.getMoralAdd8();
        moralMinus = zhcpData.getMoralMinus1() + zhcpData.getMoralMinus2() + zhcpData.getMoralMinus3() + zhcpData.getMoralMinus4() + zhcpData.getMoralMinus5() +
                zhcpData.getMoralMinus6() + zhcpData.getMoralMinus7() + zhcpData.getMoralMinus8() + zhcpData.getMoralMinus9();
        moralSum = moralInit + moralAdd - moralMinus;
        if(moralSum > 100){
            moralSum = 100;
        }else if(moralSum < 0){
            moralSum = 0;
        }
        // 智育素质考核
        double intellectualSum = 0.0, intellectualYear = 0.0, intellectualCredit = 0.0;
        intellectualYear = zhcpData.getIntellectualYear();
        intellectualCredit = zhcpData.getIntellectualCredit();
        intellectualSum = intellectualYear + intellectualCredit;
        if(intellectualSum > 100){
            intellectualSum = 100;
        }else if(intellectualSum < 0){
            intellectualSum = 0;
        }
        // 体育素质考核
        double physicalSum = 0.0, physicalInit = 0.0, physicalAdd = 0.0, physicalMinus = 0.0;
        physicalInit = zhcpData.getPhysicalInit();
        physicalAdd = zhcpData.getPhysicalAdd1() + zhcpData.getPhysicalAdd2() + zhcpData.getPhysicalAdd3() + zhcpData.getPhysicalAdd4();
        physicalMinus = zhcpData.getPhysicalMinus1() + zhcpData.getPhysicalMinus2() + zhcpData.getPhysicalMinus3();
        physicalSum = physicalInit + physicalAdd - physicalMinus;
        if(physicalSum > 100){
            physicalSum = 100;
        }else if(physicalSum < 0){
            physicalSum = 0;
        }
        // 能力素质考核
        double competenceSum = 0.0, competenceInit = 0.0, competenceAdd = 0.0, competenceMinus = 0.0;
        competenceInit = zhcpData.getCompetenceInit();
        competenceAdd = zhcpData.getCompetenceAdd1() + zhcpData.getCompetenceAdd2() + zhcpData.getCompetenceAdd3() + zhcpData.getCompetenceAdd4() +
                zhcpData.getCompetenceAdd5() + zhcpData.getCompetenceAdd6() + zhcpData.getCompetenceAdd7();
        competenceMinus = zhcpData.getCompetenceMinus1() + zhcpData.getCompetenceMinus2() + zhcpData.getCompetenceMinus3();
        competenceSum = competenceInit + competenceAdd - competenceMinus;
        if(competenceSum > 100){
            competenceSum = 100;
        }else if(competenceSum < 0){
            competenceSum = 0;
        }

        zhcpData.setMoralSum(moralSum);
        zhcpData.setIntellectualSum(intellectualSum);
        zhcpData.setPhysicalSum(physicalSum);
        zhcpData.setCompetenceSum(competenceSum);

        double summary = 0.0;
        String evaProjectID = zhcpService.queryZhcpListById(zhcpData.getId()).getProjectID();
        ZhcpList zhcpList = zhcpService.queryZhcpListByProjectID(evaProjectID, student.getStuClass());

        String zcObject = zhcpList.getZcObject();
        if("大一,大二".contains(zcObject)){
            summary = (0.3 * moralSum) + (0.5 * intellectualSum) + (0.1 * physicalSum) + (0.1 * competenceSum);
        }else if("大三,大四".contains(zcObject)){
            summary = (0.3 * moralSum) + (0.4 * intellectualSum) + (0.1 * physicalSum) + (0.2 * competenceSum);
        }
        zhcpData.setSummary(summary);
        zhcpData.setEvaStatus("已暂存");
        zhcpData.setEvaProjectID(evaProjectID);
        zhcpData.setStuNo(student.getStuNo());
        int i = zhcpService.addZhcpData(zhcpData);
        int j = zhcpService.updateDataReviewStatusOfStu(evaProjectID, "已暂存", student.getStuNo());
        if((i != 0)){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"综合测评数据暂存失败...");
        }
    }



    @RequestMapping("/zhcp/stu/toDetail/{id}")
    public String toDetail(@PathVariable("id")Integer id, Model model, HttpSession session){
        Student student = (Student) session.getAttribute("student");
        String evaProjectID = zhcpService.queryZhcpListById(id).getProjectID();
        ZhcpData zhcpData = zhcpService.queryZhcpDataByProjectIDAndStuNo(evaProjectID, student.getStuNo());
        ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(evaProjectID, student.getStuNo());

        // 数据格式化
        String zhcpDataEvaTime = zhcpData.getEvaTime();
        zhcpData.setEvaTime(zhcpDataEvaTime.substring(0,10).replace("-", "."));
        model.addAttribute("zhcpDataOfInit",zhcpData);
        model.addAttribute("zhcpDataReviewOfInit",zhcpDataReview);
        return "front/task/zhcp_detail";
    }


/*

    // V1.0 -- 学生端确认综测数据
    @RequestMapping("/zhcp/stu/toConfirm")
    @ResponseBody
    public RestResponse toConfirm(Integer id, HttpSession session){
        int i,j,k;
        Student student = (Student) session.getAttribute("student");
        ZhcpList zhcpList = zhcpService.queryZhcpListById(id);
        ZhcpData zhcpData = zhcpService.queryZhcpDataByProjectIDAndStuNo(zhcpList.getProjectID(), student.getStuNo());
        ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(zhcpList.getProjectID(), student.getStuNo());
        ZhcpSummary zhcpSummary = zhcpService.queryZhcpSummaryByProjectIDAndStuNo(zhcpList.getProjectID(), student.getStuNo());
        if(zhcpData != null && "已完成".equals(zhcpData.getEvaStatus()) && zhcpDataReview != null &&  "已完成".equals(zhcpDataReview.getEvaStatus()) && zhcpSummary != null && "已完成".equals(zhcpSummary.getEvaStatus())){
            return RestResponse.fail(201,"综合测评信息已确认，无需重复确认...");
        }else{
            i = zhcpService.updateDataStatusOfStu(zhcpData.getEvaProjectID(), "已完成",student.getStuNo());
            j = zhcpService.updateDataReviewStatusOfStu(zhcpData.getEvaProjectID(), "已完成", student.getStuNo());
            zhcpService.updateSummaryStatusOfStu(zhcpData.getEvaProjectID(), "已完成", student.getStuNo());
        }

        if((i != 0) && (j != 0)){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"综合测评信息确认失败...");
        }
    }
*/

    // V2.0 -- 学生端确认综测数据
    @RequestMapping("/zhcp/stu/toConfirm")
    @ResponseBody
    public RestResponse toConfirm(Integer id, HttpSession session){
        int i,j,k;
        Student student = (Student) session.getAttribute("student");
        ZhcpList zhcpList = zhcpService.queryZhcpListById(id);

        // 更新数据记录的状态
        i = zhcpService.updateDataStatusOfStu(zhcpList.getProjectID(), "已完成",student.getStuNo());
        j = zhcpService.updateDataReviewStatusOfStu(zhcpList.getProjectID(), "已完成", student.getStuNo());
        k = zhcpService.updateSummaryStatusOfStu(zhcpList.getProjectID(), "已完成", student.getStuNo());

        // 更新数据确认时间
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        zhcpService.upadZhcpDataConfirmTime(nowTime,zhcpList.getProjectID(), student.getStuNo());
        zhcpService.upadZhcpDataReviewConfirmTime(nowTime,zhcpList.getProjectID(), student.getStuNo());

        if((i != 0) && (j != 0) &&  (k != 0) ){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"综合测评信息确认失败...");
        }
    }



    /**
     * 综合测评复核申请页面跳转
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/zhcp/stu/toZhcpReview/{id}")
    public String toZhcpReview(@PathVariable("id")Integer id, Model model,HttpSession session){
        Student student = (Student) session.getAttribute("student");
        String evaProjectID = zhcpService.queryZhcpListById(id).getProjectID();
        ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(evaProjectID, student.getStuNo());
        model.addAttribute("zhcpDataReview",zhcpDataReview);
        return "front/task/zhcp_review";
    }

    @RequestMapping("/zhcp/stu/reviewZhcpData")
    @ResponseBody
    public RestResponse reviewZhcpData(ZhcpDataReview zhcpDataReview,HttpSession session){
        Student student = (Student) session.getAttribute("student");
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        /*ZhcpDataReview dataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(zhcpDataReview.getEvaProjectID(), student.getStuNo());
        if(dataReview.getReviewsSubmit() == null){
            // 初次提交复核申请信息
            zhcpDataReview.setReviewsSubmit(evaTime);
        }else{
            // 非初次提交复核申请信息
            zhcpDataReview.setReviewsSubmit(dataReview.getReviewsSubmit());
        }*/
        zhcpDataReview.setReviewsSubmit(evaTime);
        zhcpDataReview.setStuNo(student.getStuNo());
        zhcpDataReview.setEvaStatus("待复核");
        int i = zhcpService.upadteZhcpDataReviewOfStu(zhcpDataReview);
        int j = zhcpService.updateDataStatusOfStu(zhcpDataReview.getEvaProjectID(), "待复核", student.getStuNo());
        if((i != 0) && (j != 0)){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"综合测评复核申请提交失败...");
        }
    }

    // 综合测评汇总
    @RequestMapping("/zhcp/stu/toStuZhcpSummaryOfStu")
    public String toStuZhcpSummaryOfStu(Model model, HttpSession session){
        Student student = (Student) session.getAttribute("student");
        List<String> project = zhcpService.queryProjectByClass(student.getStuClass());
        model.addAttribute("zhcpListProjectOfStu", project);
        return "front/task/zhcpSummary";
    }

    /*
    // V1.0
    @RequestMapping("/zhcp/stu/queryZhcpSummary")
    @ResponseBody
    public RestResponse queryZhcpSummary(Integer page, Integer limit, @Nullable ZhcpSummary zhcpSummary, HttpSession session){
        Student student = (Student) session.getAttribute("student");
        zhcpSummary.setStuNo(student.getStuNo());
        if("".equals(zhcpSummary.getEvaProject())){
            zhcpSummary.setEvaProject(null);
        }
        if("".equals(zhcpSummary.getEvaStatus())){
            zhcpSummary.setEvaStatus(null);
        }
        List<ZhcpSummary> zhcpSummaries = zhcpService.querySummaryByStu(zhcpSummary);
        if(!zhcpSummaries.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(zhcpSummaries, page, limit, zhcpSummaries.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }
*/

    // V2.0
    @RequestMapping("/zhcp/stu/queryZhcpSummary")
    @ResponseBody
    public RestResponse queryZhcpSummary(Integer page, Integer limit, @Nullable ZhcpSummary zhcpSummary, HttpSession session){
        Student student = (Student) session.getAttribute("student");
        zhcpSummary.setStuNo(student.getStuNo());
        if("".equals(zhcpSummary.getEvaProject())){
            zhcpSummary.setEvaProject(null);
        }
        if("".equals(zhcpSummary.getEvaStatus())){
            zhcpSummary.setEvaStatus(null);
        }
        List<ZhcpSummary> zhcpSummaries = zhcpService.querySummaryByStu(zhcpSummary);

        for (ZhcpSummary zhcpSum :zhcpSummaries) {
            String evaProjectID = zhcpSum.getEvaProjectID();
            ZhcpList zhcpList = zhcpService.queryZhcpListByProjectID(evaProjectID, student.getStuClass());
            String deadline = zhcpList.getDeadline();
            long nowTiem = new Date().getTime();
            Long deadlineTime = Long.valueOf(deadline);
            if((nowTiem - deadlineTime) >= 0){
                if(!"已完成".equals(zhcpSum.getEvaStatus())){
                    zhcpSum.setEvaStatus("已结束");
                }
            }
        }

        if(!zhcpSummaries.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(zhcpSummaries, page, limit, zhcpSummaries.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }

    @RequestMapping("/zhcp/stu/toZhcpSummaryDetailOfStu/{id}")
    public String toZhcpSummaryDetailOfStu(@PathVariable("id")Integer id, Model model, HttpSession session){
        Student student = (Student) session.getAttribute("student");

        ZhcpSummary zhcpSummary = zhcpService.querySummaryById(id);
        List<ZhcpSummary> zhcpSummaries = zhcpService.querySummaryByStuClass(zhcpSummary.getEvaProjectID(), student.getStuClass());

        ZhcpList zhcpList = zhcpService.queryZhcpListByProjectID(zhcpSummary.getEvaProjectID(), student.getStuClass());


        Collections.sort(zhcpSummaries, new Comparator<ZhcpSummary>() {
            @Override
            public int compare(ZhcpSummary o1, ZhcpSummary o2) {
                /*double v = o2.getSummary() - o1.getSummary();
                if(v > 0 && v<1){
                    return (int) (v+1);
                }else{
                    return (int) v;
                }*/
                return (int) ((o2.getSummary()*100000) - (o1.getSummary()*100000));
            }
        });
        int i = 1;
        for (ZhcpSummary zhcpSummary1:zhcpSummaries) {
            zhcpSummary1.setComprehensive(i++);
        }

       /*
        // 当前项目的状态
        String status = zhcpList.getStatus();
        if("已结束".equals(status)){
            // 将排序后的结果写入数据库

        }else{
            // 排序后的结果不显示，仅作为页面展示

        }*/

        model.addAttribute("zhcpSummaries", zhcpSummaries);
        model.addAttribute("faculty", student.getFaculty());
        model.addAttribute("stuClass", student.getStuClass());
        return "front/task/zhcpSummary_detail";
    }










    //----------------  后台业务  ------------------
    // 综合测评任务列表
    @RequestMapping(value = "/zhcp/admin/toZhcpList",method = RequestMethod.GET)
    public String toZhcpList(Model model, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        List<String> project = zhcpService.queryProjectByClass(admin.getEmployer());
        model.addAttribute("zhcpListProject", project);
        return "back/task/zhcpList";
    }

    /*  V1.0


    @RequestMapping("/zhcp/admin/queryZhcpList")
    @ResponseBody
    public RestResponse queryZhcpList(Integer page, Integer limit, @Nullable ZhcpList zhcpList, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        zhcpList.setReleaseClass(admin.getEmployer());
        if("".equals(zhcpList.getProject())){
            zhcpList.setProject(null);
        }
        if("".equals(zhcpList.getStatus())){
            zhcpList.setStatus(null);
        }

        List<ZhcpList> zhcpLists = zhcpService.queryZhcpList(zhcpList);
        List<Student> students = studentService.queryByStuClass(admin.getEmployer());

        for (ZhcpList zhcp:zhcpLists) {
            String deadline = zhcp.getDeadline();
            long nowTime = new Date().getTime();
            Long deadlineTime = Long.valueOf(deadline);
            if((nowTime - deadlineTime) > 0){
                String projectId = zhcp.getProjectID();
                zhcpService.updateListStatus(projectId, "已结束", zhcp.getReleaseClass());
                for (Student student:students) {
                    ZhcpData zhcpData = zhcpService.queryZhcpDataByProjectIDAndStuNo(projectId, student.getStuNo());
                    ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(projectId, student.getStuNo());
                    ZhcpSummary zhcpSummary = zhcpService.queryZhcpSummaryByProjectIDAndStuNo(projectId, student.getStuNo());
                    if(zhcpData!=null && "未完成".equals(zhcpData.getEvaStatus())){
                        zhcpService.updateDataStatusOfStu(projectId, "已逾期", student.getStuNo());
                    }
                    if(zhcpDataReview!=null && "未完成".equals(zhcpDataReview.getEvaStatus())){
                        zhcpService.updateDataReviewStatusOfStu(projectId, "已逾期", student.getStuNo());
                    }
                    if(zhcpSummary!=null && "未完成".equals(zhcpSummary.getEvaStatus())){
                        zhcpService.updateSummaryStatusOfStu(projectId, "已逾期", student.getStuNo());
                    }
                }
            }else{
                String projectId = zhcp.getProjectID();
                for (Student student:students) {
                    ZhcpData zhcpData = zhcpService.queryZhcpDataByProjectIDAndStuNo(projectId, student.getStuNo());
                    ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(projectId, student.getStuNo());
                    ZhcpSummary zhcpSummary = zhcpService.queryZhcpSummaryByProjectIDAndStuNo(projectId, student.getStuNo());
                    if(zhcpData!=null && "已逾期".equals(zhcpData.getEvaStatus())){
                        zhcpService.updateDataStatusOfStu(projectId, "未完成", student.getStuNo());
                    }
                    if(zhcpDataReview!=null && "已逾期".equals(zhcpDataReview.getEvaStatus())){
                        zhcpService.updateDataReviewStatusOfStu(projectId, "未完成", student.getStuNo());
                    }
                    if(zhcpSummary != null && "已逾期".equals(zhcpSummary.getEvaStatus())){
                        zhcpService.updateSummaryStatusOfStu(projectId, "未完成", student.getStuNo());
                    }
                }
            }
        }
        zhcpLists = zhcpService.queryZhcpList(zhcpList);
        for (ZhcpList zhcp:zhcpLists) {
            String deadline = zhcp.getDeadline();
            zhcp.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(new Long(deadline))));
        }

        if(!zhcpLists.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(zhcpLists, page, limit, zhcpLists.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }*/


    // V2.0
    @RequestMapping("/zhcp/admin/queryZhcpList")
    @ResponseBody
    public RestResponse queryZhcpList(Integer page, Integer limit, @Nullable ZhcpList zhcpList, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        zhcpList.setReleaseClass(admin.getEmployer());
        if("".equals(zhcpList.getProject())){
            zhcpList.setProject(null);
        }
        if("".equals(zhcpList.getStatus())){
            zhcpList.setStatus(null);
        }

        List<ZhcpList> zhcpLists = zhcpService.queryZhcpList(zhcpList);

        long nowTime = new Date().getTime();
        for (ZhcpList zhcp:zhcpLists) {
            String deadline = zhcp.getDeadline();
            Long deadlineTime = Long.valueOf(deadline);
            if((nowTime - deadlineTime) > 0){
                String projectId = zhcp.getProjectID();
                zhcpService.updateListStatus(projectId, "已结束", zhcp.getReleaseClass());
            }
        }
        zhcpLists = zhcpService.queryZhcpList(zhcpList);
        for (ZhcpList zhcp:zhcpLists) {
            String deadline = zhcp.getDeadline();
            zhcp.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(new Long(deadline))));
        }

        if(!zhcpLists.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(zhcpLists, page, limit, zhcpLists.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    @RequestMapping(value = "/zhcp/admin/toAddZhcpList",method = RequestMethod.GET)
    public String toAdd(){
        return "back/task/zhcpList_add";
    }

    /**
     * 后台生成测评项目相关数据
     */
    @RequestMapping("/zhcp/admin/addZhcpList")
    @ResponseBody
    public RestResponse addZhcpList(ZhcpList zhcpList, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Student stuCommittee = studentService.queryByUsername(admin.getUsername());
        Date release = new Date();  // 发布时间
        // list
        zhcpList.setReleaseTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(release));
        zhcpList.setReleaser(stuCommittee.getStuName());
        zhcpList.setReleaseNo(stuCommittee.getStuNo());
        zhcpList.setReleaseClass(stuCommittee.getStuClass());
        zhcpList.setReleaseCommittee(stuCommittee.getCommittee());
        String projectID = String.valueOf(release.getTime());
        zhcpList.setProjectID(projectID);
        // 对前端传来的时间进行格式化操作
        String deadline = zhcpList.getDeadline();
        zhcpList.setDeadline(String.valueOf(new Date(deadline).getTime()));

        long nowTime = new Date().getTime();
        Long deadlineTime = Long.valueOf(String.valueOf(new Date(deadline).getTime()));
        if((nowTime - deadlineTime) > 0){
            zhcpList.setStatus("已结束");
        }else {
            zhcpList.setStatus("已开始");
        }
        zhcpService.addZhcpList(zhcpList);

        // data、dataReview  summary数据初始化
        ZhcpData zhcpData = new ZhcpData();
        ZhcpDataReview zhcpDataReview = new ZhcpDataReview();
        ZhcpSummary zhcpSummary = new ZhcpSummary();
        List<Student> students = studentService.queryByStuClass(admin.getEmployer());
        for (Student student:students) {
            // 综合测评数据（data）初始化 -- 自我测评
            zhcpData.setStuNo(student.getStuNo());
            zhcpData.setStuName(student.getStuName());
            zhcpData.setStuClass(student.getStuClass());
            zhcpData.setEvaProject(zhcpList.getProject());
            zhcpData.setEvaProjectID(projectID);
            zhcpData.setEvaStatus("未提交");
            // 综合测评数据（dataReview）初始化 -- 班级测评
            zhcpDataReview.setStuNo(student.getStuNo());
            zhcpDataReview.setStuName(student.getStuName());
            zhcpDataReview.setStuClass(student.getStuClass());
            zhcpDataReview.setEvaProject(zhcpList.getProject());
            zhcpDataReview.setEvaProjectID(projectID);
            zhcpDataReview.setEvaStatus("未提交");
            // 综测汇总数据初始化
            zhcpSummary.setStuNo(student.getStuNo());
            zhcpSummary.setStuName(student.getStuName());
            zhcpSummary.setStuClass(student.getStuClass());
            zhcpSummary.setEvaProject(zhcpList.getProject());
            zhcpSummary.setEvaProjectID(projectID);
            zhcpSummary.setEvaStatus("未提交");

            zhcpService.addZhcpDataByInit(zhcpData);
            zhcpService.addZhcpDataReviewByInit(zhcpDataReview);
            zhcpService.addZhcpSummaryByInit(zhcpSummary);
        }

        if(students != null){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"综合测评任务发布失败...");
        }

    }

    @RequestMapping(value = "/zhcp/admin/toZhcpEdit/{id}",method = RequestMethod.GET)
    public String toEdit(@PathVariable("id") Integer id, Model model){
        ZhcpList zhcpList = zhcpService.queryZhcpListById(id);
        String deadline = zhcpList.getDeadline();
        zhcpList.setDeadline(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(new Long(deadline))));
        model.addAttribute("zhcpListInfo",zhcpList);
        return "back/task/zhcpList_edit";
    }

/*

    // V1.0
    @RequestMapping("/zhcp/admin/updateZhcp")
    @ResponseBody
    public RestResponse updateZhcp(ZhcpList zhcpList){

        // 对前端传来的时间进行格式化操作
        String deadline = zhcpList.getDeadline();
        zhcpList.setDeadline(String.valueOf(new Date(deadline).getTime()));

        long nowTime = new Date().getTime();
        Long deadlineTime = Long.valueOf(String.valueOf(new Date(deadline).getTime()));

        List<Student> students = studentService.queryByStuClass(zhcpList.getReleaseClass());

        if((nowTime - deadlineTime) > 0){
            zhcpList.setStatus("已结束");
            String projectId = zhcpList.getProjectID();
            for (Student student:students) {
                ZhcpData zhcpData = zhcpService.queryZhcpDataByProjectIDAndStuNo(projectId, student.getStuNo());
                ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(projectId, student.getStuNo());
                ZhcpSummary zhcpSummary = zhcpService.queryZhcpSummaryByProjectIDAndStuNo(projectId, student.getStuNo());
                if(zhcpData!=null && "未完成".equals(zhcpData.getEvaStatus())){
                    zhcpService.updateDataStatusOfStu(projectId, "已逾期", student.getStuNo());
                }
                if(zhcpDataReview!=null && "未完成".equals(zhcpDataReview.getEvaStatus())){
                    zhcpService.updateDataReviewStatusOfStu(projectId, "已逾期", student.getStuNo());
                }
                if(zhcpSummary!=null && "未完成".equals(zhcpSummary.getEvaStatus())){
                    zhcpService.updateSummaryStatusOfStu(projectId, "已逾期", student.getStuNo());
                }
            }
        }else {
            zhcpList.setStatus("已开始");
            String projectId = zhcpList.getProjectID();
            for (Student student:students) {
                ZhcpData zhcpData = zhcpService.queryZhcpDataByProjectIDAndStuNo(projectId, student.getStuNo());
                ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(projectId, student.getStuNo());
                ZhcpSummary zhcpSummary = zhcpService.queryZhcpSummaryByProjectIDAndStuNo(projectId, student.getStuNo());
                if(zhcpData!=null && "已逾期".equals(zhcpData.getEvaStatus())){
                    zhcpService.updateDataStatusOfStu(projectId, "未完成", student.getStuNo());
                }
                if(zhcpDataReview!=null && "已逾期".equals(zhcpDataReview.getEvaStatus())){
                    zhcpService.updateDataReviewStatusOfStu(projectId, "未完成", student.getStuNo());
                }
                if(zhcpSummary != null && "已逾期".equals(zhcpSummary.getEvaStatus())){
                    zhcpService.updateSummaryStatusOfStu(projectId, "未完成", student.getStuNo());
                }
            }
        }

        int i = zhcpService.updateZhcpList(zhcpList);
        int j = zhcpService.updateDataProject(zhcpList.getProject(), zhcpList.getProjectID(), zhcpList.getReleaseClass());
        int k = zhcpService.updateDataReviewProject(zhcpList.getProject(), zhcpList.getProjectID(), zhcpList.getReleaseClass());
        int l = zhcpService.updateSummaryProject(zhcpList.getProject(), zhcpList.getProjectID(), zhcpList.getReleaseClass());
        if((i != 0)&&(j != 0)&&(k != 0)&&(l != 0)){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"综合测评任务更新失败...");
        }
    }

*/


    // V2.0
    @RequestMapping("/zhcp/admin/updateZhcp")
    @ResponseBody
    public RestResponse updateZhcp(ZhcpList zhcpList){

        // 对前端传来的时间进行格式化操作
        String deadline = zhcpList.getDeadline();
        zhcpList.setDeadline(String.valueOf(new Date(deadline).getTime()));

        long nowTime = new Date().getTime();
        Long deadlineTime = Long.valueOf(String.valueOf(new Date(deadline).getTime()));

        List<Student> students = studentService.queryByStuClass(zhcpList.getReleaseClass());

        if((nowTime - deadlineTime) > 0){
            zhcpList.setStatus("已结束");
        }else {
            zhcpList.setStatus("已开始");
        }

        int i = zhcpService.updateZhcpList(zhcpList);
        int j = zhcpService.updateDataProject(zhcpList.getProject(), zhcpList.getProjectID(), zhcpList.getReleaseClass());
        int k = zhcpService.updateDataReviewProject(zhcpList.getProject(), zhcpList.getProjectID(), zhcpList.getReleaseClass());
        int l = zhcpService.updateSummaryProject(zhcpList.getProject(), zhcpList.getProjectID(), zhcpList.getReleaseClass());
        if((i != 0)&&(j != 0)&&(k != 0)&&(l != 0)){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"综合测评任务更新失败...");
        }
    }



    @RequestMapping("/zhcp/admin/deleteZhcp")
    @ResponseBody
    public RestResponse deleteZhcp(Integer id, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        String projectId = zhcpService.queryProjectIdByID(id);
        zhcpService.delZhcpData(projectId, admin.getEmployer());
        zhcpService.delZhcpDataReview(projectId, admin.getEmployer());
        zhcpService.delZhcpSummary(projectId, admin.getEmployer());
        int i = zhcpService.deleteZhcpList(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"综合测评任务删除失败...");
        }
    }

    @RequestMapping("/zhcp/admin/bacthDeleteZhcp")
    @ResponseBody
    public RestResponse bacthDeleteZhcp(String data, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");

        String[] fields = data.split(",");
        for (String field:fields) {
            String projectId = zhcpService.queryProjectIdByID(Integer.valueOf(field));
            zhcpService.delZhcpData(projectId, admin.getEmployer());
            zhcpService.delZhcpDataReview(projectId, admin.getEmployer());
            zhcpService.delZhcpSummary(projectId, admin.getEmployer());
        }
        int i = zhcpService.batchDelZhcpList(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"综合测评任务删除失败...");
        }
    }


    // ---------  学生测评数据的业务处理  ---------
    @RequestMapping(value = "/zhcp/admin/toZhcpData",method = RequestMethod.GET)
    public String toZhcpData(Model model, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        List<String> project = zhcpService.queryProjectByClass(admin.getEmployer());
        model.addAttribute("zhcpDataProject", project);
        return "back/task/zhcpData";
    }


    @RequestMapping("/zhcp/admin/queryZhcpData")
    @ResponseBody
    public RestResponse queryZhcpData(Integer page, Integer limit, @Nullable ZhcpData zhcpData, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        zhcpData.setStuClass(admin.getEmployer());

        if("".equals(zhcpData.getStuName())){
            zhcpData.setStuName(null);
        }
        if("".equals(zhcpData.getEvaProject())){
            zhcpData.setEvaProject(null);
        }
        if("".equals(zhcpData.getEvaStatus())){
            zhcpData.setEvaStatus(null);
        }
        List<ZhcpData> zhcpDatas = zhcpService.queryZhcpData(zhcpData);

        if(!zhcpDatas.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(zhcpDatas, page, limit, zhcpDatas.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }

    /**
     * 综合测评数据审核
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/zhcp/admin/toZhcpDataEdit/{id}",method = RequestMethod.GET)
    public String toReview(@PathVariable("id") Integer id, Model model){
        ZhcpData zhcpData = zhcpService.queryZhcpDataById(id);
        // 数据格式化
        String zhcpDataEvaTime = zhcpData.getEvaTime();
        zhcpData.setEvaTime(zhcpDataEvaTime.substring(0,10).replace("-", "."));
        model.addAttribute("zhcpDataOfInit",zhcpData);

        ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(zhcpData.getEvaProjectID(), zhcpData.getStuNo());
        if(zhcpDataReview != null){
            model.addAttribute("zhcpDataReview",zhcpDataReview);
        }

        return "back/task/zhcpData_review";
    }

    /**
     * 提交综测审核数据
     * @param zhcpDataReview
     * @param session
     * @return
     */
    @RequestMapping("/zhcp/admin/addZhcpDataReview")
    @ResponseBody
    public RestResponse addZhcpDataReview(ZhcpDataReview zhcpDataReview,HttpSession session){
        zhcpDataReview.setReviewTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        Admin admin = (Admin) session.getAttribute("admin");
        Student student = studentService.queryByUsername(admin.getUsername());

        // 德育素质考核
        double moralSum = 0.0, moralInit = 0.0, moralAdd = 0.0, moralMinus = 0.0;
        moralInit = zhcpDataReview.getMoralInit();
        moralAdd = zhcpDataReview.getMoralAdd1() + zhcpDataReview.getMoralAdd2() + zhcpDataReview.getMoralAdd3() + zhcpDataReview.getMoralAdd4() + zhcpDataReview.getMoralAdd5() +
                zhcpDataReview.getMoralAdd6() + zhcpDataReview.getMoralAdd7() + zhcpDataReview.getMoralAdd8();
        moralMinus = zhcpDataReview.getMoralMinus1() + zhcpDataReview.getMoralMinus2() + zhcpDataReview.getMoralMinus3() + zhcpDataReview.getMoralMinus4() + zhcpDataReview.getMoralMinus5() +
                zhcpDataReview.getMoralMinus6() + zhcpDataReview.getMoralMinus7() + zhcpDataReview.getMoralMinus8() + zhcpDataReview.getMoralMinus9();
        moralSum = moralInit + moralAdd - moralMinus;
        if(moralSum > 100){
            moralSum = 100;
        }else if(moralSum < 0){
            moralSum = 0;
        }
        // 智育素质考核
        double intellectualSum = 0.0, intellectualYear = 0.0, intellectualCredit = 0.0;
        intellectualYear = zhcpDataReview.getIntellectualYear();
        intellectualCredit = zhcpDataReview.getIntellectualCredit();
        intellectualSum = intellectualYear + intellectualCredit;
        if(intellectualSum > 100){
            intellectualSum = 100;
        }else if(intellectualSum < 0){
            intellectualSum = 0;
        }
        // 体育素质考核
        double physicalSum = 0.0, physicalInit = 0.0, physicalAdd = 0.0, physicalMinus = 0.0;
        physicalInit = zhcpDataReview.getPhysicalInit();
        physicalAdd = zhcpDataReview.getPhysicalAdd1() + zhcpDataReview.getPhysicalAdd2() + zhcpDataReview.getPhysicalAdd3() + zhcpDataReview.getPhysicalAdd4();
        physicalMinus = zhcpDataReview.getPhysicalMinus1() + zhcpDataReview.getPhysicalMinus2() + zhcpDataReview.getPhysicalMinus3();
        physicalSum = physicalInit + physicalAdd - physicalMinus;
        if(physicalSum > 100){
            physicalSum = 100;
        }else if(physicalSum < 0){
            physicalSum = 0;
        }
        // 能力素质考核
        double competenceSum = 0.0, competenceInit = 0.0, competenceAdd = 0.0, competenceMinus = 0.0;
        competenceInit = zhcpDataReview.getCompetenceInit();
        competenceAdd = zhcpDataReview.getCompetenceAdd1() + zhcpDataReview.getCompetenceAdd2() + zhcpDataReview.getCompetenceAdd3() + zhcpDataReview.getCompetenceAdd4() +
                zhcpDataReview.getCompetenceAdd5() + zhcpDataReview.getCompetenceAdd6() + zhcpDataReview.getCompetenceAdd7();
        competenceMinus = zhcpDataReview.getCompetenceMinus1() + zhcpDataReview.getCompetenceMinus2() + zhcpDataReview.getCompetenceMinus3();
        competenceSum = competenceInit + competenceAdd - competenceMinus;
        if(competenceSum > 100){
            competenceSum = 100;
        }else if(competenceSum < 0){
            competenceSum = 0;
        }

        zhcpDataReview.setMoralSum(moralSum);
        zhcpDataReview.setIntellectualSum(intellectualSum);
        zhcpDataReview.setPhysicalSum(physicalSum);
        zhcpDataReview.setCompetenceSum(competenceSum);

        double summary = 0.0;

        // 这里dataReview的id实际上是data的id
        String evaProjectID = zhcpService.queryZhcpDataById(zhcpDataReview.getId()).getEvaProjectID();
        ZhcpList zhcpList = zhcpService.queryZhcpListByProjectID(evaProjectID, student.getStuClass());
        String zcObject = zhcpList.getZcObject();
        if("大一,大二".contains(zcObject)){
            summary = (0.3 * moralSum) + (0.5 * intellectualSum) + (0.1 * physicalSum) + (0.1 * competenceSum);
        }else if("大三,大四".contains(zcObject)){
            summary = (0.3 * moralSum) + (0.4 * intellectualSum) + (0.1 * physicalSum) + (0.2 * competenceSum);
        }
        zhcpDataReview.setSummary(summary);


        zhcpDataReview.setReviewNo(student.getStuNo());
        zhcpDataReview.setReviewName(student.getStuName());
        zhcpDataReview.setReviewClass(student.getStuClass());
        zhcpDataReview.setReviewCommittee(student.getCommittee());
        zhcpDataReview.setEvaStatus("已审核");

        ZhcpSummary zhcpSummary = new ZhcpSummary();
        ZhcpData zhcpData = zhcpService.queryZhcpDataByProjectIDAndStuNo(evaProjectID, zhcpDataReview.getStuNo());
        zhcpSummary.setEvaTime(zhcpData.getEvaTime());
        zhcpSummary.setMoral(moralSum);
        zhcpSummary.setIntellectual(intellectualSum);
        zhcpSummary.setPhysical(physicalSum);
        zhcpSummary.setCompetence(competenceSum);
        zhcpSummary.setSummary(summary);
        zhcpSummary.setCultural(zhcpDataReview.getCultural());
        zhcpSummary.setRemarks(zhcpDataReview.getReviewDescription());
        zhcpSummary.setEvaStatus("待确认");
        zhcpSummary.setEvaProjectID(zhcpDataReview.getEvaProjectID());
        zhcpSummary.setStuNo(zhcpDataReview.getStuNo());


        int i = zhcpService.addZhcpDataReview(zhcpDataReview);
        int j = zhcpService.updateDataStatusOfStu(evaProjectID, "已过审", zhcpDataReview.getStuNo());
        int k = zhcpService.addZhcpSummary(zhcpSummary);
//        int k = zhcpService.updateSummaryStatusOfStu(evaProjectID, "待确认", zhcpDataReview.getStuNo());


        //-------------------------张豆豆-------------------------
        Student student1 = studentService.queryByUsername(zhcpDataReview.getStuNo());
        String tailTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        if(student1.getEmail() == null || "".equals(student1.getEmail())){
            return RestResponse.fail(200,"用户未填写邮箱！！！");
        }
        emailUtils.allTestCheckPass(student1.getEmail(),student1.getStuName(),time,zhcpDataReview.getEvaProject(),tailTime);
        //--------------------------------------------------------


        if((i != 0) && (j != 0) && (k != 0)){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"审核失败...");
        }
    }

    /**
     * 暂存综测审核数据
     * @param zhcpDataReview
     * @param session
     * @return
     */
    @RequestMapping("/zhcp/admin/storageZhcpDataReview")
    @ResponseBody
    public RestResponse storageZhcpDataReview(ZhcpDataReview zhcpDataReview,HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Student student = studentService.queryByUsername(admin.getUsername());

        // 德育素质考核
        double moralSum = 0.0, moralInit = 0.0, moralAdd = 0.0, moralMinus = 0.0;
        moralInit = zhcpDataReview.getMoralInit();
        moralAdd = zhcpDataReview.getMoralAdd1() + zhcpDataReview.getMoralAdd2() + zhcpDataReview.getMoralAdd3() + zhcpDataReview.getMoralAdd4() + zhcpDataReview.getMoralAdd5() +
                zhcpDataReview.getMoralAdd6() + zhcpDataReview.getMoralAdd7() + zhcpDataReview.getMoralAdd8();
        moralMinus = zhcpDataReview.getMoralMinus1() + zhcpDataReview.getMoralMinus2() + zhcpDataReview.getMoralMinus3() + zhcpDataReview.getMoralMinus4() + zhcpDataReview.getMoralMinus5() +
                zhcpDataReview.getMoralMinus6() + zhcpDataReview.getMoralMinus7() + zhcpDataReview.getMoralMinus8() + zhcpDataReview.getMoralMinus9();
        moralSum = moralInit + moralAdd - moralMinus;
        if(moralSum > 100){
            moralSum = 100;
        }else if(moralSum < 0){
            moralSum = 0;
        }
        // 智育素质考核
        double intellectualSum = 0.0, intellectualYear = 0.0, intellectualCredit = 0.0;
        intellectualYear = zhcpDataReview.getIntellectualYear();
        intellectualCredit = zhcpDataReview.getIntellectualCredit();
        intellectualSum = intellectualYear + intellectualCredit;
        if(intellectualSum > 100){
            intellectualSum = 100;
        }else if(intellectualSum < 0){
            intellectualSum = 0;
        }
        // 体育素质考核
        double physicalSum = 0.0, physicalInit = 0.0, physicalAdd = 0.0, physicalMinus = 0.0;
        physicalInit = zhcpDataReview.getPhysicalInit();
        physicalAdd = zhcpDataReview.getPhysicalAdd1() + zhcpDataReview.getPhysicalAdd2() + zhcpDataReview.getPhysicalAdd3() + zhcpDataReview.getPhysicalAdd4();
        physicalMinus = zhcpDataReview.getPhysicalMinus1() + zhcpDataReview.getPhysicalMinus2() + zhcpDataReview.getPhysicalMinus3();
        physicalSum = physicalInit + physicalAdd - physicalMinus;
        if(physicalSum > 100){
            physicalSum = 100;
        }else if(physicalSum < 0){
            physicalSum = 0;
        }
        // 能力素质考核
        double competenceSum = 0.0, competenceInit = 0.0, competenceAdd = 0.0, competenceMinus = 0.0;
        competenceInit = zhcpDataReview.getCompetenceInit();
        competenceAdd = zhcpDataReview.getCompetenceAdd1() + zhcpDataReview.getCompetenceAdd2() + zhcpDataReview.getCompetenceAdd3() + zhcpDataReview.getCompetenceAdd4() +
                zhcpDataReview.getCompetenceAdd5() + zhcpDataReview.getCompetenceAdd6() + zhcpDataReview.getCompetenceAdd7();
        competenceMinus = zhcpDataReview.getCompetenceMinus1() + zhcpDataReview.getCompetenceMinus2() + zhcpDataReview.getCompetenceMinus3();
        competenceSum = competenceInit + competenceAdd - competenceMinus;
        if(competenceSum > 100){
            competenceSum = 100;
        }else if(competenceSum < 0){
            competenceSum = 0;
        }

        zhcpDataReview.setMoralSum(moralSum);
        zhcpDataReview.setIntellectualSum(intellectualSum);
        zhcpDataReview.setPhysicalSum(physicalSum);
        zhcpDataReview.setCompetenceSum(competenceSum);

        double summary = 0.0;

        // 这里dataReview的id实际上是data的id
        String evaProjectID = zhcpService.queryZhcpDataById(zhcpDataReview.getId()).getEvaProjectID();
        ZhcpList zhcpList = zhcpService.queryZhcpListByProjectID(evaProjectID, student.getStuClass());
        String zcObject = zhcpList.getZcObject();
        if("大一,大二".contains(zcObject)){
            summary = (0.3 * moralSum) + (0.5 * intellectualSum) + (0.1 * physicalSum) + (0.1 * competenceSum);
        }else if("大三,大四".contains(zcObject)){
            summary = (0.3 * moralSum) + (0.4 * intellectualSum) + (0.1 * physicalSum) + (0.2 * competenceSum);
        }
        zhcpDataReview.setSummary(summary);


        zhcpDataReview.setReviewNo(student.getStuNo());
        zhcpDataReview.setReviewName(student.getStuName());
        zhcpDataReview.setReviewClass(student.getStuClass());
        zhcpDataReview.setReviewCommittee(student.getCommittee());
        zhcpDataReview.setEvaStatus("审核中");
        int i = zhcpService.addZhcpDataReview(zhcpDataReview);
        int j = zhcpService.updateDataStatusOfStu(evaProjectID, "审核中", zhcpDataReview.getStuNo());
        if((i != 0) && (j != 0)){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"暂存失败...");
        }
    }

    /**
     * 驳回综测审核数据
     * @param session
     * @return
     */
    @RequestMapping("/zhcp/admin/rejectZhcpDataReview")
    @ResponseBody
    public RestResponse rejectZhcpDataReview(ZhcpDataReview zhcpDataReview,HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Student student = studentService.queryByUsername(admin.getUsername());
        // 这里dataReview的id实际上是data的id
        String evaProjectID = zhcpService.queryZhcpDataById(zhcpDataReview.getId()).getEvaProjectID();
        zhcpDataReview.setReviewNo(student.getStuNo());
        zhcpDataReview.setReviewName(student.getStuName());
        zhcpDataReview.setReviewClass(student.getStuClass());
        zhcpDataReview.setReviewCommittee(student.getCommittee());
        zhcpDataReview.setEvaStatus("已驳回");
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        zhcpDataReview.setReviewTime(evaTime);
        int i = zhcpService.addZhcpDataReview(zhcpDataReview);
        int j = zhcpService.updateDataStatusOfStu(evaProjectID, "已驳回", zhcpDataReview.getStuNo());

        //-------------------------张豆豆-------------------------
        Student student1 = studentService.queryByUsername(zhcpDataReview.getStuNo());
        String tailTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        if(student1.getEmail() == null || "".equals(student1.getEmail())){
            return RestResponse.fail(200,"用户未填写邮箱！！！");
        }
        emailUtils.allTestCheckReject(student1.getEmail(),student1.getStuName(),time,zhcpDataReview.getEvaProject(),tailTime);
        //--------------------------------------------------------

        if((i != 0) && (j != 0)){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"驳回失败...");
        }
    }



    /**
     * 综合测评数据复核
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/zhcp/admin/toZhcpDataReview2/{id}",method = RequestMethod.GET)
    public String toReview2(@PathVariable("id") Integer id, Model model){
        ZhcpData zhcpData = zhcpService.queryZhcpDataById(id);
        // 数据格式化
        String zhcpDataEvaTime = zhcpData.getEvaTime();
        zhcpData.setEvaTime(zhcpDataEvaTime.substring(0,10).replace("-", "."));
        model.addAttribute("zhcpDataOfInit",zhcpData);

        ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(zhcpData.getEvaProjectID(), zhcpData.getStuNo());
        model.addAttribute("zhcpDataReview",zhcpDataReview);
        return "back/task/zhcpData_review2";
    }

    /**
     * 提交综测复核数据
     * @param zhcpDataReview
     * @param session
     * @return
     */
    @RequestMapping("/zhcp/admin/upadteZhcpDataReviewOfAdminOfAccess")
    @ResponseBody
    public RestResponse upadteZhcpDataReviewOfAdminOfAccess(ZhcpDataReview zhcpDataReview,HttpSession session){
        zhcpDataReview.setReviewsTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        Admin admin = (Admin) session.getAttribute("admin");
        Student student = studentService.queryByUsername(admin.getUsername());

        // 德育素质考核
        double moralSum = 0.0, moralInit = 0.0, moralAdd = 0.0, moralMinus = 0.0;
        moralInit = zhcpDataReview.getMoralInit();
        moralAdd = zhcpDataReview.getMoralAdd1() + zhcpDataReview.getMoralAdd2() + zhcpDataReview.getMoralAdd3() + zhcpDataReview.getMoralAdd4() + zhcpDataReview.getMoralAdd5() +
                zhcpDataReview.getMoralAdd6() + zhcpDataReview.getMoralAdd7() + zhcpDataReview.getMoralAdd8();
        moralMinus = zhcpDataReview.getMoralMinus1() + zhcpDataReview.getMoralMinus2() + zhcpDataReview.getMoralMinus3() + zhcpDataReview.getMoralMinus4() + zhcpDataReview.getMoralMinus5() +
                zhcpDataReview.getMoralMinus6() + zhcpDataReview.getMoralMinus7() + zhcpDataReview.getMoralMinus8() + zhcpDataReview.getMoralMinus9();
        moralSum = moralInit + moralAdd - moralMinus;
        if(moralSum > 100){
            moralSum = 100;
        }else if(moralSum < 0){
            moralSum = 0;
        }
        // 智育素质考核
        double intellectualSum = 0.0, intellectualYear = 0.0, intellectualCredit = 0.0;
        intellectualYear = zhcpDataReview.getIntellectualYear();
        intellectualCredit = zhcpDataReview.getIntellectualCredit();
        intellectualSum = intellectualYear + intellectualCredit;
        if(intellectualSum > 100){
            intellectualSum = 100;
        }else if(intellectualSum < 0){
            intellectualSum = 0;
        }
        // 体育素质考核
        double physicalSum = 0.0, physicalInit = 0.0, physicalAdd = 0.0, physicalMinus = 0.0;
        physicalInit = zhcpDataReview.getPhysicalInit();
        physicalAdd = zhcpDataReview.getPhysicalAdd1() + zhcpDataReview.getPhysicalAdd2() + zhcpDataReview.getPhysicalAdd3() + zhcpDataReview.getPhysicalAdd4();
        physicalMinus = zhcpDataReview.getPhysicalMinus1() + zhcpDataReview.getPhysicalMinus2() + zhcpDataReview.getPhysicalMinus3();
        physicalSum = physicalInit + physicalAdd - physicalMinus;
        if(physicalSum > 100){
            physicalSum = 100;
        }else if(physicalSum < 0){
            physicalSum = 0;
        }
        // 能力素质考核
        double competenceSum = 0.0, competenceInit = 0.0, competenceAdd = 0.0, competenceMinus = 0.0;
        competenceInit = zhcpDataReview.getCompetenceInit();
        competenceAdd = zhcpDataReview.getCompetenceAdd1() + zhcpDataReview.getCompetenceAdd2() + zhcpDataReview.getCompetenceAdd3() + zhcpDataReview.getCompetenceAdd4() +
                zhcpDataReview.getCompetenceAdd5() + zhcpDataReview.getCompetenceAdd6() + zhcpDataReview.getCompetenceAdd7();
        competenceMinus = zhcpDataReview.getCompetenceMinus1() + zhcpDataReview.getCompetenceMinus2() + zhcpDataReview.getCompetenceMinus3();
        competenceSum = competenceInit + competenceAdd - competenceMinus;
        if(competenceSum > 100){
            competenceSum = 100;
        }else if(competenceSum < 0){
            competenceSum = 0;
        }

        zhcpDataReview.setMoralSum(moralSum);
        zhcpDataReview.setIntellectualSum(intellectualSum);
        zhcpDataReview.setPhysicalSum(physicalSum);
        zhcpDataReview.setCompetenceSum(competenceSum);

        double summary = 0.0;

        // 这里dataReview的id实际上是data的id
        String evaProjectID = zhcpService.queryZhcpDataById(zhcpDataReview.getId()).getEvaProjectID();
        ZhcpList zhcpList = zhcpService.queryZhcpListByProjectID(evaProjectID, student.getStuClass());
        String zcObject = zhcpList.getZcObject();
        if("大一,大二".contains(zcObject)){
            summary = (0.3 * moralSum) + (0.5 * intellectualSum) + (0.1 * physicalSum) + (0.1 * competenceSum);
        }else if("大三,大四".contains(zcObject)){
            summary = (0.3 * moralSum) + (0.4 * intellectualSum) + (0.1 * physicalSum) + (0.2 * competenceSum);
        }
        zhcpDataReview.setSummary(summary);


        zhcpDataReview.setReviewsNo(student.getStuNo());
        zhcpDataReview.setReviewsName(student.getStuName());
        zhcpDataReview.setReviewsClass(student.getStuClass());
        zhcpDataReview.setReviewsCommittee(student.getCommittee());
        zhcpDataReview.setEvaStatus("已过审");
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        zhcpDataReview.setReviewsTime(evaTime);

        ZhcpSummary zhcpSummary = new ZhcpSummary();
        ZhcpData zhcpData = zhcpService.queryZhcpDataByProjectIDAndStuNo(evaProjectID, zhcpDataReview.getStuNo());
        zhcpSummary.setEvaTime(zhcpData.getEvaTime());
        zhcpSummary.setMoral(moralSum);
        zhcpSummary.setIntellectual(intellectualSum);
        zhcpSummary.setPhysical(physicalSum);
        zhcpSummary.setCompetence(competenceSum);
        zhcpSummary.setSummary(summary);
        zhcpSummary.setCultural(zhcpDataReview.getCultural());
        zhcpSummary.setRemarks(zhcpDataReview.getReviewDescription());
        zhcpSummary.setEvaStatus("待确认");
        zhcpSummary.setEvaProjectID(zhcpDataReview.getEvaProjectID());
        zhcpSummary.setStuNo(zhcpDataReview.getStuNo());


        int i = zhcpService.upadteZhcpDataReviewOfAdminOfAccess(zhcpDataReview);
        int j = zhcpService.updateDataStatusOfStu(evaProjectID, "已过审", zhcpDataReview.getStuNo());
        int k = zhcpService.addZhcpSummary(zhcpSummary);

        //-------------------------张豆豆-------------------------
        Student student1 = studentService.queryByUsername(zhcpDataReview.getStuNo());
        String tailTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        if(student1.getEmail() == null || "".equals(student1.getEmail())){
            return RestResponse.fail(200,"用户未填写邮箱！！！");
        }
        emailUtils.appraisalDateRePass(student1.getEmail(),student1.getStuName(),time,zhcpDataReview.getEvaProject(),tailTime);
        //--------------------------------------------------------

//        int k = zhcpService.updateSummaryStatusOfStu(evaProjectID, "待确认", zhcpDataReview.getStuNo());
        if((i != 0) && (j != 0) && (k != 0)){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"审核失败...");
        }
    }

    /**
     * 驳回综测审核数据
     * @param session
     * @return
     */
    @RequestMapping("/zhcp/admin/upadteZhcpDataReviewOfAdminOfReject")
    @ResponseBody
    public RestResponse upadteZhcpDataReviewOfAdminOfReject(ZhcpDataReview zhcpDataReview,HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        Student student = studentService.queryByUsername(admin.getUsername());
        // 这里dataReview的id实际上是data的id
        String evaProjectID = zhcpService.queryZhcpDataById(zhcpDataReview.getId()).getEvaProjectID();
        zhcpDataReview.setReviewsNo(student.getStuNo());
        zhcpDataReview.setReviewsName(student.getStuName());
        zhcpDataReview.setReviewsClass(student.getStuClass());
        zhcpDataReview.setReviewsCommittee(student.getCommittee());
        zhcpDataReview.setEvaStatus("已驳回");
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        zhcpDataReview.setReviewsTime(evaTime);

        //-------------------------张豆豆-------------------------
        Student student1 = studentService.queryByUsername(zhcpDataReview.getStuNo());
        String tailTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        if(student1.getEmail() == null || "".equals(student1.getEmail())){
            return RestResponse.fail(200,"用户未填写邮箱！！！");
        }
        emailUtils.appraisaDateReject(student1.getEmail(),student1.getStuName(),time,zhcpDataReview.getEvaProject(),tailTime);
        //--------------------------------------------------------


        int i = zhcpService.upadteZhcpDataReviewOfAdminOfReject(zhcpDataReview);
        int j = zhcpService.updateDataStatusOfStu(evaProjectID, "已驳回", zhcpDataReview.getStuNo());
        if((i != 0) && (j != 0)){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"驳回失败...");
        }
    }


    @RequestMapping(value = "/zhcp/admin/toZhcpDataDetail/{id}",method = RequestMethod.GET)
    public String toDetail(@PathVariable("id") Integer id, Model model){
        ZhcpData zhcpData = zhcpService.queryZhcpDataById(id);
        // 数据格式化
        String zhcpDataEvaTime = zhcpData.getEvaTime();
        zhcpData.setEvaTime(zhcpDataEvaTime.substring(0,10).replace("-", "."));
        model.addAttribute("zhcpDataOfInit",zhcpData);

        ZhcpDataReview zhcpDataReview = zhcpService.queryZhcpDataReviewByProjectIDAndStuNo(zhcpData.getEvaProjectID(), zhcpData.getStuNo());
        model.addAttribute("zhcpDataReviewOfInit",zhcpDataReview);
        return "back/task/zhcpData_detail";
    }

    @RequestMapping("/zhcp/admin/deleteZhcpData")
    @ResponseBody
    public RestResponse deleteZhcpData(Integer id){
        ZhcpData zhcpData = zhcpService.queryZhcpDataById(id);
        String evaProjectID = zhcpData.getEvaProjectID();
        String stuNo = zhcpData.getStuNo();
        int i = zhcpService.delZhcpDataReviewByStu(evaProjectID, stuNo);
        int k = zhcpService.delZhcpSummaryByStu(evaProjectID, stuNo);
        int j = zhcpService.delZhcpDataById(id);
        if((i != 0) && (j != 0) && (k != 0)){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"综合测评数据删除失败...");
        }
    }

    @RequestMapping("/zhcp/admin/bacthDeleteZhcpData")
    @ResponseBody
    public RestResponse bacthDeleteZhcpData(String data){
        String[] fields = data.split(",");
        int i = 0;
        for (String field:fields) {
            ZhcpData zhcpData = zhcpService.queryZhcpDataById(Integer.valueOf(field));
            String evaProjectID = zhcpData.getEvaProjectID();
            String stuNo = zhcpData.getStuNo();
            zhcpService.delZhcpDataById(Integer.valueOf(field));
            zhcpService.delZhcpDataReviewByStu(evaProjectID, stuNo);
            zhcpService.delZhcpSummaryByStu(evaProjectID, stuNo);
        }
        if(true){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"综合测评数据删除失败...");
        }
    }


    // 综合测评汇总
    @RequestMapping("/zhcp/admin/toZhcpSummaryOfAdmin")
    public String toZhcpSummaryOfAdmin(Model model, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        List<String> project = zhcpService.queryProjectByClass(admin.getEmployer());
        model.addAttribute("zhcpListProjectOfAdmin", project);
        return "back/task/zhcpSummary";
    }

    @RequestMapping("/zhcp/admin/queryZhcpSummaryList")
    @ResponseBody
    public RestResponse queryZhcpSummaryList(Integer page, Integer limit, @Nullable ZhcpList zhcpList, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        zhcpList.setReleaseClass(admin.getEmployer());
        if("".equals(zhcpList.getProject())){
            zhcpList.setProject(null);
        }
        if("".equals(zhcpList.getStatus())){
            zhcpList.setStatus(null);
        }
        List<ZhcpList> zhcpLists = zhcpService.queryZhcpList(zhcpList);
        for (ZhcpList zhcp:zhcpLists) {
            String deadline = zhcp.getDeadline();
            zhcp.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(new Long(deadline))));
        }
        if(!zhcpLists.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(zhcpLists, page, limit, zhcpLists.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    /*
    // V1.0
    @RequestMapping("/zhcp/admin/toZhcpSummaryDetailOfAdmin/{id}")
    public String toZhcpSummaryDetailOfAdmin(@PathVariable("id")Integer id, Model model, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        ZhcpList zhcpList = zhcpService.queryZhcpListById(id);
        Student student = studentService.queryByUsername(admin.getUsername());
        List<ZhcpSummary> zhcpSummaries = zhcpService.querySummaryByStuClass(zhcpList.getProjectID(), admin.getEmployer());

        Collections.sort(zhcpSummaries, new Comparator<ZhcpSummary>() {
            @Override
            public int compare(ZhcpSummary o1, ZhcpSummary o2) {
                *//*double v = o2.getSummary() - o1.getSummary();
                if(v > 0 && v<1){
                    return (int) (v+1);
                }else{
                    return (int) v;]
                }*//*
                return (int) ((o2.getSummary()*100000) - (o1.getSummary()*100000));
            }
        });
        int i = 1;
        for (ZhcpSummary zhcpSummary1:zhcpSummaries) {
            zhcpSummary1.setComprehensive(i++);
        }

        // 当前项目的状态
        String status = zhcpList.getStatus();
        if("已结束".equals(status)){
            // 将排序后的结果写入数据库
            for (ZhcpSummary zhcpSummary1:zhcpSummaries) {
                if(zhcpSummary1.getEvaTime() != null){
                    zhcpService.updateSummaryOfSummary(zhcpSummary1);
                }
            }
        }else{
            // 排序后的结果不显示，仅作为页面展示

        }

        model.addAttribute("zhcpSummaries", zhcpSummaries);
        model.addAttribute("faculty", student.getFaculty());
        model.addAttribute("stuClass", student.getStuClass());
        return "back/task/zhcpSummary_detail";
    }*/

    // V2.0
    @RequestMapping("/zhcp/admin/toZhcpSummaryDetailOfAdmin/{id}")
    public String toZhcpSummaryDetailOfAdmin(@PathVariable("id")Integer id, Model model, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        ZhcpList zhcpList = zhcpService.queryZhcpListById(id);
        Student student = studentService.queryByUsername(admin.getUsername());
        List<ZhcpSummary> zhcpSummaries = zhcpService.querySummaryByStuClass(zhcpList.getProjectID(), admin.getEmployer());

        Collections.sort(zhcpSummaries, new Comparator<ZhcpSummary>() {
            @Override
            public int compare(ZhcpSummary o1, ZhcpSummary o2) {
                /*double v = o2.getSummary() - o1.getSummary();
                if(v > 0 && v<1){
                    return (int) (v+1);
                }else{
                    return (int) v;]
                }*/
                return (int) ((o2.getSummary()*100000) - (o1.getSummary()*100000));
            }
        });
        int i = 1;

        for (ZhcpSummary zhcpSummary1:zhcpSummaries) {
            if("已完成".equals(zhcpSummary1.getEvaStatus())){
                zhcpSummary1.setComprehensive(i++);
            }
        }
        model.addAttribute("zhcpSummaries", zhcpSummaries);
        model.addAttribute("faculty", student.getFaculty());
        model.addAttribute("stuClass", student.getStuClass());
        return "back/task/zhcpSummary_detail";
    }

    @RequestMapping("/zhcp/admin/finalAccess")
    @ResponseBody
    public RestResponse finalAccess(Integer id, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        ZhcpList zhcpList = zhcpService.queryZhcpListById(id);
        Student student = studentService.queryByUsername(admin.getUsername());
        List<ZhcpSummary> zhcpSummaries = zhcpService.querySummaryByStuClass(zhcpList.getProjectID(), admin.getEmployer());
        Collections.sort(zhcpSummaries, new Comparator<ZhcpSummary>() {
            @Override
            public int compare(ZhcpSummary o1, ZhcpSummary o2) {
                return (int) ((o2.getSummary()*100000) - (o1.getSummary()*100000));
            }
        });
        int i = 1;
        for (ZhcpSummary zhcpSummary1:zhcpSummaries) {
            zhcpSummary1.setComprehensive(i++);
        }
        for (ZhcpSummary zhcpSummary1:zhcpSummaries) {
            if(zhcpSummary1.getEvaTime() != null){
                zhcpService.updateSummaryOfSummary(zhcpSummary1);
            }
        }
        zhcpList.setFinalPerson(student.getStuName());
        zhcpList.setFinalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));


        int j = zhcpService.updateZhcpListOfFinal(zhcpList);

        if(j != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"综合测评数据删除失败...");
        }
    }


    // 综合测评数据分析
    @RequestMapping(value = "/zhcp/admin/toZhcpAnalysis",method = RequestMethod.GET)
    public String toZhcpAnalysis(){
        return "back/task/zhcpAnalysis";
    }

}

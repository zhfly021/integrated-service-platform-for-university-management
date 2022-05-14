package com.zhfly021.controller;

import com.zhfly021.entity.*;
import com.zhfly021.service.XxwhService;
import com.zhfly021.service.XxwhServiceZdd;
import com.zhfly021.utils.PageUtil;
import com.zhfly021.utils.RestResponse;
import com.zhfly021.utils.File.SplitStringUtil;
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
 * @Description 学生信息维护（学生简历）控制类
 * @create 2021-03-17 22:35
 */
@Controller
public class XxwhController {

    @Autowired
    XxwhService xxwhService;

    @Autowired
    XxwhServiceZdd xxwhServiceZdd;

    SplitStringUtil splitStringUtil = new SplitStringUtil();


    // *****************   学生简历维护--后台   *****************


    /**
     *  跳转至（后台）学生简历页面
      * @return
     */
    @RequestMapping(value = "/xxwh/admin/toStuInfo",method = RequestMethod.GET)
    public String toStuInfo(){
        return "back/info/stuInfo";
    }


    /**
     * 学生管理员登录，查看班级简历，老师、院级管理员可以查看多个班级、学院的
     * @param page
     * @param limit
     * @param resume
     * @param session
     * @return
     */
    @RequestMapping("/xxwh/admin/queryResumeList")
    @ResponseBody
    public RestResponse queryResumeList(Integer page, Integer limit, @Nullable Resume resume, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        String username = admin.getUsername();

        if(username.startsWith("20")){
            // 学生管理员用户
            resume.setStuClass(admin.getEmployer());
        }else if("admin".equals(username)){
            // 超级管理员用户      -- 查询所有

        }else if(username.startsWith("admin")){
            // 院级管理员用户      -- 根据学院查询
            resume.setStuFaculty(admin.getEmployer());
        }else{
            // 辅导员、教师管理员用户  -- 根据班级查询
            String data = admin.getEmployer();
            String[] fields = data.split(",");
            resume.setStuClasses(fields);
        }
        if("".equals(resume.getStuNo())){
            resume.setStuNo(null);
        }
        if("".equals(resume.getStuName())){
            resume.setStuName(null);
        }
        if("".equals(resume.getStuClass())){
            resume.setStuClass(null);
        }
        if("".equals(resume.getStuFaculty())){
            resume.setStuFaculty(null);
        }
        if("".equals(resume.getStatus())){
            resume.setStatus(null);
        }
        List<Resume> resumes = xxwhService.queryResumeList(resume);
        if(!resumes.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(resumes, page, limit, resumes.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    @RequestMapping("/xxwh/admin/deleteResumeList")
    @ResponseBody
    public RestResponse deleteResumeList(Integer id){
        int i = xxwhService.deleteResumeList(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"学生简历删除失败...");
        }
    }

    @RequestMapping("/xxwh/admin/bacthDeleteResumeList")
    @ResponseBody
    public RestResponse bacthDeleteResumeList(String data){
        String[] fields = data.split(",");
        int i = xxwhService.bacthDeleteResumeList(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"学生简历删除失败...");
        }
    }


    // *****************   学生简历维护--前台   *****************

    /**
     * 学生提交证明材料
     * @param session
     * @param evidence
     * @return
     */
    @PostMapping("/xxwh/stu/addEvidence")
    @ResponseBody
    public RestResponse addEvidence(HttpSession session,Evidence evidence){

        Student student = (Student) session.getAttribute("student");
        evidence.setStuNo(student.getStuNo());

        // 获取当前提交的时间
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        evidence.setSubmitTime(evaTime);

        evidence.setStatus("待审核");

        if (evidence.getPerFileName() == null || evidence.getPerFileName() == null){
            evidence.setPerFileUrl("未上传为文件");
            evidence.setPerFileName("未上传为文件");
        }
        if (evidence.getEduFileUrl() == null || evidence.getEduFileName() == null){
            evidence.setEduFileName("未上传为文件");
            evidence.setEduFileUrl("未上传为文件");
        }
        if (evidence.getWorkFileUrl() == null || evidence.getWorkFileName() == null){
            evidence.setWorkFileUrl("未上传为文件");
            evidence.setWorkFileName("未上传为文件");
        }
        if (evidence.getHonorFileUrl() == null || evidence.getHonorFileName() == null){
            evidence.setHonorFileUrl("未上传为文件");
            evidence.setHonorFileName("未上传为文件");
        }
        if (evidence.getEgoFileUrl() == null || evidence.getEgoFileName() == null){
            evidence.setEgoFileUrl("未上传为文件");
            evidence.setEgoFileName("未上传为文件");
        }

        int i = xxwhService.addEvidence(evidence);
        if (i > 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"证明材料上传失败");
        }

    }


    /**
     * 学生证明材料被驳回后的修改
     * @param evidence
     * @return
     */
    @PostMapping("/xxwh/stu/updateEvidence")
    @ResponseBody
    public RestResponse updateEvidence(Evidence evidence, HttpSession session){

        Student student = (Student) session.getAttribute("student");
        evidence.setStuNo(student.getStuNo());

        // 获取当前提交的时间
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        evidence.setSubmitTime(evaTime);

        evidence.setStatus("待审核");

        if (evidence.getPerFileName() == null || evidence.getPerFileName() == null){
            evidence.setPerFileUrl("未上传为文件");
            evidence.setPerFileName("未上传为文件");
        }
        if (evidence.getEduFileUrl() == null || evidence.getEduFileName() == null){
            evidence.setEduFileName("未上传为文件");
            evidence.setEduFileUrl("未上传为文件");
        }
        if (evidence.getWorkFileUrl() == null || evidence.getWorkFileName() == null){
            evidence.setWorkFileUrl("未上传为文件");
            evidence.setWorkFileName("未上传为文件");
        }
        if (evidence.getHonorFileUrl() == null || evidence.getHonorFileName() == null){
            evidence.setHonorFileUrl("未上传为文件");
            evidence.setHonorFileName("未上传为文件");
        }
        if (evidence.getEgoFileUrl() == null || evidence.getEgoFileName() == null){
            evidence.setEgoFileUrl("未上传为文件");
            evidence.setEgoFileName("未上传为文件");
        }

        int i = xxwhService.addEvidence(evidence);
        System.out.println("证明材料上传结果---"+i);
        if (i > 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"证明材料上传失败");
        }

    }


    // *****************   证明材料维护--后台   *****************

    /**
     *  跳转至（后台）证明材料页面
     * @return
     */
    @RequestMapping(value = "/xxwh/admin/toEvidenceInfo",method = RequestMethod.GET)
    public String toEvidenceInfo(){
        return "back/info/evidenceInfo";
    }

    /**
     *管理员审核证明材料的页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/xxwh/admin/toEvidenceInfoEdit/{id}",method = RequestMethod.GET)
    public String toEvidenceInfoEdit(@PathVariable("id") Integer id, Model model){

        Evidence evidence = xxwhService.queryEviById(id);
        String mark = "未上传文件";

        //文件的处理
        String perFileUrl = evidence.getPerFileUrl();
        String perFileName = evidence.getPerFileName();
        model.addAttribute("perFile",splitStringUtil.split(perFileUrl,perFileName,mark));

        String eduFileUrl =  evidence.getEduFileUrl();
        String eduFileName = evidence.getEduFileName();
        model.addAttribute("eduFile",splitStringUtil.split(eduFileUrl,eduFileName,mark));

        String workFileUrl = evidence.getWorkFileUrl();
        String workFileName = evidence.getWorkFileName();
        model.addAttribute("workFile",splitStringUtil.split(workFileUrl,workFileName,mark));

        String honorFileUrl = evidence.getHonorFileUrl();
        String honorFileName = evidence.getHonorFileName();
        model.addAttribute("honorFile",splitStringUtil.split(honorFileUrl,honorFileName,mark));

        String egoFileUrl = evidence.getEgoFileUrl();
        String egoFileName = evidence.getEgoFileName();
        model.addAttribute("egoFile",splitStringUtil.split(egoFileUrl,egoFileName,mark));


        // 这里可以根据拿到的id查询一条证明材料记录
        model.addAttribute("evidenceInfo",evidence);
        return "back/info/zmcl_edit";
    }

    /**
     * 证明材料审核的驳回和完成
     * @param evidence
     * @param session
     * @return
     */
    @PostMapping("/xxwh/admin/checkXxwhOfReject/{status}")
    @ResponseBody
    public RestResponse checkXxwhOfReject(Evidence evidence,HttpSession session,
                                          @PathVariable(name = "status") String status){
        Admin admin = (Admin) session.getAttribute("admin");
        Student student =xxwhService.queryStuById(admin.getUsername());

        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

        evidence.setVerifyTime(evaTime);
        evidence.setVerifier(student.getStuName());
        evidence.setStatus(status);

        int i = xxwhService.updateEviByAdmin(evidence);

        if (i > 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"证明材料审核提交失败...");
        }
    }

    @RequestMapping(value = "/xxwh/admin/toEvidenceInfoDetail/{id}",method = RequestMethod.GET)
    public String toEvidenceInfoDetail(@PathVariable("id") Integer id, Model model){
        Evidence evidence = xxwhService.queryEviById(id);

        String mark = "未上传文件";
        //文件的处理
        String perFileUrl = evidence.getPerFileUrl();
        String perFileName = evidence.getPerFileName();
        model.addAttribute("perFile",splitStringUtil.split(perFileUrl,perFileName,mark));

        String eduFileUrl =  evidence.getEduFileUrl();
        String eduFileName = evidence.getEduFileName();
        model.addAttribute("eduFile",splitStringUtil.split(eduFileUrl,eduFileName,mark));

        String workFileUrl = evidence.getWorkFileUrl();
        String workFileName = evidence.getWorkFileName();
        model.addAttribute("workFile",splitStringUtil.split(workFileUrl,workFileName,mark));

        String honorFileUrl = evidence.getHonorFileUrl();
        String honorFileName = evidence.getHonorFileName();
        model.addAttribute("honorFile",splitStringUtil.split(honorFileUrl,honorFileName,mark));

        String egoFileUrl = evidence.getEgoFileUrl();
        String egoFileName = evidence.getEgoFileName();
        model.addAttribute("egoFile",splitStringUtil.split(egoFileUrl,egoFileName,mark));

        // 这里可以根据拿到的id查询一条证明材料记录
        model.addAttribute("evidenceInfo",evidence);
        return "back/info/zmcl_detail";
    }

    /**
     * 学生管理员登录，查看班级简历证明材料，老师、院级管理员可以查看多个班级、学院的
     * @param page
     * @param limit
     * @param evidence
     * @param session
     * @return
     */
    @RequestMapping("/xxwh/admin/queryEvidenceList")
    @ResponseBody
    public RestResponse queryEvidenceList(Integer page, Integer limit, @Nullable Evidence evidence, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        String username = admin.getUsername();

        if(username.startsWith("20")){
            // 学生管理员用户
            evidence.setStuClass(admin.getEmployer());
        }else if("admin".equals(username)){
            // 超级管理员用户      -- 查询所有

        }else if(username.startsWith("admin")){
            // 院级管理员用户      -- 根据学院查询
            evidence.setStuFaculty(admin.getEmployer());
        }else{
            // 辅导员、教师管理员用户  -- 根据班级查询
            String data = admin.getEmployer();
            String[] fields = data.split(",");
            evidence.setStuClasses(fields);
        }
        if("".equals(evidence.getStuNo())){
            evidence.setStuNo(null);
        }
        if("".equals(evidence.getStuName())){
            evidence.setStuName(null);
        }
        if("".equals(evidence.getStuClass())){
            evidence.setStuClass(null);
        }
        if("".equals(evidence.getStuFaculty())){
            evidence.setStuFaculty(null);
        }
        if("".equals(evidence.getStatus())){
            evidence.setStatus(null);
        }
        List<Evidence> evidences = xxwhService.queryEvidenceList(evidence);
        if(!evidences.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(evidences, page, limit, evidences.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    @RequestMapping("/xxwh/admin/deleteEvidenceList")
    @ResponseBody
    public RestResponse deleteEvidenceList(Integer id){
        int i = xxwhService.deleteEvidenceList(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"学生简历删除失败...");
        }
    }

    @RequestMapping("/xxwh/admin/bacthDeleteEvidenceList")
    @ResponseBody
    public RestResponse bacthDeleteEvidenceList(String data){
        String[] fields = data.split(",");
        int i = xxwhService.bacthDeleteEvidenceList(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"学生简历删除失败...");
        }
    }



    // *****************   证明材料维护--前台   *****************
    /**
     *  跳转至（前台）证明材料提交页面
     * @return
     */
    @RequestMapping(value = "/xxwh/stu/toZmclInfo",method = RequestMethod.GET)
    public String toZmclInfo(HttpSession session, Model model){
        Student student = (Student) session.getAttribute("student");
        // 这里通过学号从evidence表中查询到该生的证明材料提交记录信息
        Evidence evidence = xxwhService.queryEviByStuNo(student.getStuNo());

        String mark = "未上传文件";

        if (evidence != null){
            //文件的处理
            String perFileUrl = evidence.getPerFileUrl();
            String perFileName = evidence.getPerFileName();
            model.addAttribute("perFile",splitStringUtil.split(perFileUrl,perFileName,mark));

            String eduFileUrl =  evidence.getEduFileUrl();
            String eduFileName = evidence.getEduFileName();
            model.addAttribute("eduFile",splitStringUtil.split(eduFileUrl,eduFileName,mark));

            String workFileUrl = evidence.getWorkFileUrl();
            String workFileName = evidence.getWorkFileName();
            model.addAttribute("workFile",splitStringUtil.split(workFileUrl,workFileName,mark));

            String honorFileUrl = evidence.getHonorFileUrl();
            String honorFileName = evidence.getHonorFileName();
            model.addAttribute("honorFile",splitStringUtil.split(honorFileUrl,honorFileName,mark));

            String egoFileUrl = evidence.getEgoFileUrl();
            String egoFileName = evidence.getEgoFileName();
            model.addAttribute("egoFile",splitStringUtil.split(egoFileUrl,egoFileName,mark));
        }

        // 这里可以根据拿到的id查询一条证明材料记录
        model.addAttribute("evidenceInfo",evidence);

        if (evidence == null || "未提交".equals(evidence.getStatus()))
            return "front/info/zmcl_add";      // 该学生的证明材料提交记录状态 —— 未提交（初次提交）
        else if ("已驳回".equals(evidence.getStatus()))
            return "front/info/zmcl_edit";            // 该学生的证明材料提交记录状态 —— 未提交（非初次提交）、已驳回
        else
            return "front/info/zmcl_detail";           // 该学生的证明材料提交记录状态 —— 待审核、已完成
    }


    /**
     * 学生端修改证明材料中---文件的删除
     * @param stuNo
     * @param fileName
     * @param filePath
     * @return
     */
    @PostMapping(value = "/xxwh/stu/stuDeleteEvFile")
    @ResponseBody
    public int stuDeleteEvFile(String stuNo, String fileName, String filePath, String url, String name){

        Evidence evidence = xxwhService.queryEviByStuNo(stuNo);

        if (url.equals("perFileUrl") && name.equals("perFileName") ){
            evidence.setPerFileUrl(filePath);
            evidence.setPerFileName(fileName);
        }else if (url.equals("eduFileUrl") && name.equals("eduFileName") ){
            evidence.setEduFileUrl(filePath);
            evidence.setEduFileName(fileName);
        }else if (url.equals("workFileUrl") && name.equals("workFileName") ){
            evidence.setWorkFileUrl(filePath);
            evidence.setWorkFileName(fileName);
        }else if (url.equals("honorFileUrl") && name.equals("honorFileName") ){
            evidence.setHonorFileUrl(filePath);
            evidence.setHonorFileName(fileName);
        }else if (url.equals("egoFileUrl") && name.equals("egoFileName") ){
            evidence.setEgoFileUrl(filePath);
            evidence.setEgoFileName(fileName);
        }

        int i = xxwhService.stuDeleteEvFile(evidence);

        return i;
    }

}



package com.zhfly021.controller;

import com.zhfly021.entity.*;
import com.zhfly021.mapper.BbtxMapper;
import com.zhfly021.service.BbtxSevice;
import com.zhfly021.service.StudentService;
import com.zhfly021.utils.EmailUtils;
import com.zhfly021.utils.PageUtil;
import com.zhfly021.utils.RestResponse;
import com.zhfly021.utils.response.RestResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.channels.SeekableByteChannel;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-08 22:14
 */
@Controller
public class BbtxController {
    @Autowired
    BbtxSevice bbtxSevice;

    @Autowired
    StudentService studentService;

    @Autowired
    EmailUtils emailUtils;



    //----------------  前台业务  ------------------
    // 报表填写任务列表
    // 报表填写任务列表--查看、下载附件

    @RequestMapping(value = "/bbtx/stu/toBbtxList",method = RequestMethod.GET)
    public String toBbtxListOfStu(){
        return "front/task/bbtxList";
    }


    /**
     * 学生端查看报表填写任务
     * @param page
     * @param limit
     * @param bbtxList
     * @param session
     * @return
     */
    @RequestMapping("/bbtx/stu/queryBbtxList")
    @ResponseBody
    public RestResponse queryBbtxListOfStu(Integer page, Integer limit, @Nullable BbtxList bbtxList, HttpSession session){
        Student student = (Student) session.getAttribute("student");

        String stuClass = student.getStuClass();
        bbtxList.setReleaseClass(stuClass);

        if("".equals(bbtxList.getProject())){
            bbtxList.setProject(null);
        }
        if("".equals(bbtxList.getStatus())){
            bbtxList.setStatus(null);
        }

        List<BbtxList> bbtxLists = bbtxSevice.queryBbtxList(bbtxList);

        for (BbtxList bbtx:bbtxLists) {
            String deadline = bbtx.getDeadline();
            bbtx.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(new Long(deadline))));
        }

        if(!bbtxLists.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bbtxLists, page, limit, bbtxLists.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }




    @RequestMapping(value = "/bbtx/stu/toBbtxData",method = RequestMethod.GET)
    public String toBbtxDataOfStu(){
        return "front/task/bbsc";
    }

    /**
     * 学生端查询报表填写数据
     * @param page
     * @param limit
     * @param session
     * @return
     */
    @RequestMapping("/bbtx/stu/queryBbtxData")
    @ResponseBody
    public RestResponse queryBbtxDataOfStu(Integer page, Integer limit, @Nullable String project, @Nullable String status, HttpSession session){
        Student student = (Student) session.getAttribute("student");

        String stuNo = student.getStuNo();

        if("".equals(project)){
            project = null;
        }

        if("".equals(status)){
            status = null;
        }

        List<BbtxListAndData> bbtxListAndDataList = bbtxSevice.queryBbtxData(stuNo,project,status);

        for (BbtxListAndData bbtx:bbtxListAndDataList) {
            String deadline = bbtx.getDeadline();
            bbtx.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(new Long(deadline))));

            if(bbtx.getBbtxData()==null){
                String bbtxStatus = bbtx.getStatus();
                BbtxData bbtxData = new BbtxData();
                bbtxData.setEvaStatus("null");
                if("已结束".equals(bbtxStatus)){
                    bbtxData.setEvaStatus("已逾期");
                }
                bbtx.setBbtxData(bbtxData);
            }
        }

        if(!bbtxListAndDataList.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bbtxListAndDataList, page, limit, bbtxListAndDataList.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    /**
     * 学生端跳转到报表填写页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/bbtx/stu/toAddBbtxData/{id}",method = RequestMethod.GET)
    public String toAddDataOfStu(@PathVariable("id") Integer id, Model model){
        BbtxList bbtxListInfo = bbtxSevice.queryBbtxListById(id);
        model.addAttribute("bbtxListInfo",bbtxListInfo);
        return "front/task/bbsc_add";
    }


    /**
     * 学生端提交报表填写数据：填写备注、提交文件、提交时间
     * @param bbtxData
     * @param session
     * @return
     */
    @RequestMapping("/bbtx/stu/addBbtxData")
    @ResponseBody
    public RestResponse addBbtxData(BbtxData bbtxData, HttpSession session){
        Student student = (Student) session.getAttribute("student");
        bbtxData.setStuNo(student.getStuNo());

        // 获取当前提交的时间
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        bbtxData.setEvaTime(evaTime);

        // 对学生提交的文件进行操作
        // 测试————为了保证数据库信息的完整性
        // 文件存储路径
        if(bbtxData.getUpload()==null){
            bbtxData.setUpload("未上传文件");
        }
        // 文件名称
        if(bbtxData.getUpFilename()==null){
            bbtxData.setUpFilename("未上传文件");
        }
        bbtxData.setEvaStatus("待审核");

        int i;
        // 查询该任务是否是全员任务
        int count = bbtxSevice.queryBbtxDataIsExist(student.getStuNo(), bbtxData.getEvaProjectID());
        if(count == 0){
            // 非全员任务
            bbtxData.setStuName(student.getStuName());
            bbtxData.setStuClass(student.getStuClass());
            bbtxData.setStuFaculty(student.getFaculty());

            i = bbtxSevice.addBbtxDataOfNotExist(bbtxData);
        }else{
            // 全员任务
            i = bbtxSevice.addBbtxDataOfExist(bbtxData);
        }


        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"报表填写任务发布失败...");
        }

    }


    @RequestMapping(value = "/bbtx/stu/toDetailBbtxData/{id}",method = RequestMethod.GET)
    public String toDetailDataOfStu(@PathVariable("id") Integer id, Model model, HttpSession session){
        Student student = (Student) session.getAttribute("student");

        BbtxList bbtxList = bbtxSevice.queryBbtxListById(id);

        BbtxData bbtxData = bbtxSevice.queryBbtxDataByProjectIDAndStuNo(bbtxList.getProjectID(), student.getStuNo());


        //文件的处理
        String paths = bbtxData.getUpload();
        String names = bbtxData.getUpFilename();

        List<String> pathsList = new ArrayList<>();
        List<String> namesList = new ArrayList<>();

        if (paths != null && paths != "未上传文件"){
            pathsList = Arrays.asList(paths.split(","));
            namesList = Arrays.asList(names.split(","));
        }

        String path = "";
        String name = "";
        Map<String, Object> map = new HashMap<>();

        for (int i=0; i<pathsList.size(); i++){
            path =  pathsList.get(i);
            name = namesList.get(i);
            map.put(name,path);
        }

        model.addAttribute("files",map);

        model.addAttribute("bbtxDataInfo",bbtxData);
        model.addAttribute("bbtxListInfo",bbtxList);

        return "front/task/bbsc_detail";
    }

    /**
     * 学生端修改报表中的文件的删除
     * @param projectID
     * @param fileName
     * @param filePath
     * @return
     */
    @PostMapping(value = "/bbtx/stu/stuDeleteBbFile")
    @ResponseBody
    public int stuDeleteBbFile(String projectID, String fileName, String filePath, HttpSession session){
        Student student = (Student) session.getAttribute("student");
        int i = bbtxSevice.stuDeleteBbFile(projectID, filePath, fileName, student.getStuNo());
        return i;
    }


    @RequestMapping(value = "/bbtx/stu/toEditBbtxData/{id}",method = RequestMethod.GET)
    public String toEditDataOfStu(@PathVariable("id") Integer id, Model model,HttpSession session){
        Student student = (Student) session.getAttribute("student");

        BbtxList bbtxList = bbtxSevice.queryBbtxListById(id);

        BbtxData bbtxData = bbtxSevice.queryBbtxDataByProjectIDAndStuNo(bbtxList.getProjectID(), student.getStuNo());model.addAttribute("bbtxDataInfo",bbtxData);

        //文件的处理
        String paths = bbtxData.getUpload();
        String names = bbtxData.getUpFilename();

        List<String> pathsList = new ArrayList<>();
        List<String> namesList = new ArrayList<>();

        if (paths != null){
            pathsList = Arrays.asList(paths.split(","));
            namesList = Arrays.asList(names.split(","));
        }

        String path = "";
        String name = "";
        Map<String, Object> map = new HashMap<>();

        for (int i=0; i<pathsList.size(); i++){
            path =  pathsList.get(i);
            name = namesList.get(i);
            map.put(name,path);
        }

        model.addAttribute("files",map);

        model.addAttribute("bbtxListInfo",bbtxList);
        model.addAttribute("bbtxDataInfo",bbtxData);
        return "front/task/bbsc_edit";
    }


    @RequestMapping("/bbtx/stu/updateBbtxData")
    @ResponseBody
    public RestResponse updateBbtx(BbtxData bbtxData, HttpSession session){
        Student student = (Student) session.getAttribute("student");
        bbtxData.setStuNo(student.getStuNo());
        bbtxData.setEvaStatus("待审核");



        // 新的文件路径、文件名称
        if(bbtxData.getUpload() == null){
            bbtxData.setUpload("--update--");
            bbtxData.setUpFilename("--update--");
        }


        // 根据项目id、学号修改
        int i = bbtxSevice.updateDataOfStu(bbtxData);

        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"报表填写任务更新失败...");
        }

    }


    // 跳转至多任务下载列表
    @RequestMapping(value = "/bbtx/stu/toDownloadBbtxList/{id}",method = RequestMethod.GET)
    public String toDownloadBbtxListOfStu(@PathVariable("id") Integer id, Model model){

        //单文件下载
        BbtxList bbtxList = bbtxSevice.selectBbtxFile(id);
        String paths = bbtxList.getDownload();
        String names = bbtxList.getFileName();

        List<String> pathsList = Arrays.asList(paths.split(","));
        List<String> namesList = Arrays.asList(names.split(","));

        String path = "";
        String name = "";
        Map<String, Object> map = new HashMap<>();

        for (int i=0; i<pathsList.size(); i++){
            path =  pathsList.get(i);
            name = namesList.get(i);

            map.put(name,path);
        }

        model.addAttribute("files",map);

        return "front/task/bbtxList_download";
    }



    //**********************  下载  ******************************




    //----------------  后台业务  ------------------
    // 报表填写任务列表
    // 报表填写

    @RequestMapping(value = "/bbtx/admin/toBbtxList",method = RequestMethod.GET)
    public String toBbtxList(){
        return "back/task/bbtxList";
    }


    /**
     * 学生管理员登录，只能查看其发布的任务，老师院级管理员可以查看一个班级、学院的
     * @param page
     * @param limit
     * @param bbtxList
     * @param session
     * @return
     */
    @RequestMapping("/bbtx/admin/queryBbtxList")
    @ResponseBody
    public RestResponse queryBbtxList(Integer page, Integer limit, @Nullable BbtxList bbtxList, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        String username = admin.getUsername();

        if(username.startsWith("20")){
            // 学生管理员用户
            bbtxList.setReleaseNo(admin.getUsername());
        }else if("admin".equals(username)){
            // 超级管理员用户      -- 查询所有

        }else if(username.startsWith("admin")){
            // 院级管理员用户      -- 根据学院查询
            bbtxList.setReleaseFaculty(admin.getFaculty());
        }else{
            // 辅导员、教师管理员用户  -- 根据班级查询
            String data = admin.getEmployer();
            String[] fields = data.split(",");
            bbtxList.setReleaseClasses(fields);
        }

        if("".equals(bbtxList.getProject())){
            bbtxList.setProject(null);
        }
        if("".equals(bbtxList.getStatus())){
            bbtxList.setStatus(null);
        }

        List<BbtxList> bbtxLists = bbtxSevice.queryBbtxList(bbtxList);

        long nowTime = new Date().getTime();

        for (BbtxList bbtx:bbtxLists) {
            String deadline = bbtx.getDeadline();
            Long deadlineTime = Long.valueOf(deadline);
            if((nowTime - deadlineTime) > 0){
                bbtxSevice.updateListStatus("已结束", bbtx.getProjectID(), bbtx.getReleaseClass());
            }
        }

        bbtxLists = bbtxSevice.queryBbtxList(bbtxList);

        for (BbtxList bbtx:bbtxLists) {
            String deadline = bbtx.getDeadline();
            bbtx.setDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(new Long(deadline))));
        }

        if(!bbtxLists.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bbtxLists, page, limit, bbtxLists.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    @RequestMapping(value = "/bbtx/admin/toAddBbtxList",method = RequestMethod.GET)
    public String toAdd(){
        return "back/task/bbtxList_add";
    }

    /**
     * 后台生成报表填写项目相关数据
     *      文件上传未完成--21.3.10（请在完成管理员发布任务时上传初始文件功能后删除本行注释）
     *          - 要求：前端已经写好文件上传按钮，若觉得不合适，可以进行更改
     */
    @RequestMapping("/bbtx/admin/addBbtxList")
    @ResponseBody
    public RestResponse addBbtxList(BbtxList bbtxList, HttpSession session){

        Admin admin = (Admin) session.getAttribute("admin");
        Student stuCommittee = studentService.queryByUsername(admin.getUsername());
        Date release = new Date();  // 发布时间
        // list
        bbtxList.setReleaseTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(release));
        bbtxList.setReleaser(stuCommittee.getStuName());
        bbtxList.setReleaseNo(stuCommittee.getStuNo());
        bbtxList.setReleaseClass(stuCommittee.getStuClass());
        bbtxList.setReleaseFaculty(stuCommittee.getFaculty());
        bbtxList.setReleaseCommittee(stuCommittee.getCommittee());
        String projectID = String.valueOf(release.getTime());
        bbtxList.setProjectID(projectID);

        // 文件上传
//        bbtxList.setDownload("--null--");

        if("on".equals(bbtxList.getClaim())){
            // 需全员填写
            bbtxList.setClaim("是");
        }else{
            // 无需全员填写
            bbtxList.setClaim("否");
        }



        // 对前端传来的时间进行格式化操作
        String deadline = bbtxList.getDeadline();
        bbtxList.setDeadline(String.valueOf(new Date(deadline).getTime()));

        long nowTime = new Date().getTime();
        Long deadlineTime = Long.valueOf(String.valueOf(new Date(deadline).getTime()));
        if((nowTime - deadlineTime) > 0){
            bbtxList.setStatus("已结束");
        }else {
            bbtxList.setStatus("已开始");
        }

        int i = bbtxSevice.addBbtxList(bbtxList);

        /*
        * data数据初始化
        * 只有在需要全员填写此报表任务的时候需要进行data数据的初始化，是为了方便后期查看还有哪些人没有提交任务
        *   目前只考虑已定班委发布报表填写任务
        * */

        if("是".equals(bbtxList.getClaim())){
            BbtxData bbtxData = new BbtxData();
            List<Student> students = studentService.queryByStuClass(admin.getEmployer());
            for (Student student:students) {
                // 报表填写数据（data）初始化
                bbtxData.setStuNo(student.getStuNo());
                bbtxData.setStuName(student.getStuName());
                bbtxData.setStuClass(student.getStuClass());
                bbtxData.setStuFaculty(student.getFaculty());

                bbtxData.setEvaProject(bbtxList.getProject());
                bbtxData.setEvaProjectID(projectID);
                bbtxData.setEvaStatus("未提交");

                bbtxSevice.addBbtxDataByInit(bbtxData);

            }
        }
        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"报表填写任务发布失败...");
        }

    }



    @RequestMapping(value = "/bbtx/admin/toBbtxEdit/{id}",method = RequestMethod.GET)
    public String toEdit(@PathVariable("id") Integer id, Model model){
        BbtxList bbtxList = bbtxSevice.queryBbtxListById(id);
        String deadline = bbtxList.getDeadline();
        bbtxList.setDeadline(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(new Long(deadline))));
        model.addAttribute("bbtxListInfo",bbtxList);

        //文件的处理
        String paths = bbtxList.getDownload();
        String names = bbtxList.getFileName();

        List<String> pathsList = new ArrayList<>();
        List<String> namesList = new ArrayList<>();

        if (paths != null){
            pathsList = Arrays.asList(paths.split(","));
            namesList = Arrays.asList(names.split(","));
        }

        String path = "";
        String name = "";
        Map<String, Object> map = new HashMap<>();

        for (int i=0; i<pathsList.size(); i++){
            path = pathsList.get(i);
            name = namesList.get(i);
            map.put(name,path);
        }

        model.addAttribute("files",map);
        model.addAttribute("exit",1);

        if (pathsList == null || namesList == null){
            model.addAttribute("exit",0);
        }

        return "back/task/bbtxList_edit";
    }

    @RequestMapping("bbtx/admin/updateFile")
    @ResponseBody
    public int updateFile( String projectID, String fileName, String filePath){
        int i = bbtxSevice.editBbtxFile(projectID,fileName,filePath);
        return i;

    }

    @RequestMapping("/bbtx/admin/updateBbtx")
    @ResponseBody
    public RestResponse updateBbtx(BbtxList bbtxList){
        if("on".equals(bbtxList.getClaim())){
            // 需全员填写
            bbtxList.setClaim("是");
        }else{
            // 无需全员填写
            bbtxList.setClaim("否");
        }

        // 对前端传来的时间进行格式化操作
        String deadline = bbtxList.getDeadline();
        bbtxList.setDeadline(String.valueOf(new Date(deadline).getTime()));

        long nowTime = new Date().getTime();
        Long deadlineTime = Long.valueOf(String.valueOf(new Date(deadline).getTime()));

        List<Student> students = studentService.queryByStuClass(bbtxList.getReleaseClass());

        String claim = bbtxList.getClaim();
        String projectId = bbtxList.getProjectID();

        if((nowTime - deadlineTime) > 0){
            bbtxList.setStatus("已结束");

            if("是".equals(claim)){
                for (Student student:students) {
                    String status = bbtxSevice.queryBbtxDataStatusByProjectIDAndStuNo(projectId, student.getStuNo());
                    if(status!=null && "未提交".equals(status)){
                        bbtxSevice.updateDataStatusOfStu(projectId, "已逾期", student.getStuNo());
                    }
                }
            }


        }else {
            bbtxList.setStatus("已开始");

            if("是".equals(claim)) {
                for (Student student : students) {
                    String status = bbtxSevice.queryBbtxDataStatusByProjectIDAndStuNo(projectId, student.getStuNo());
                    if (status != null && "已逾期".equals(status)) {
                        bbtxSevice.updateDataStatusOfStu(projectId, "未提交", student.getStuNo());
                    }
                }
            }
        }

        /*
            全员填写动态变化
                是 -->  否
                否 -->  是
         */
        int count = bbtxSevice.queryBbtxDataByProjectID(projectId, bbtxList.getReleaseClass());
        if("否".equals(claim) && count != 0) {
            bbtxSevice.delBbtxData(projectId);
        }else if("是".equals(claim) && count == 0) {

            BbtxData bbtxData = new BbtxData();
            for (Student student:students) {
                // 报表填写数据（data）初始化
                bbtxData.setStuNo(student.getStuNo());
                bbtxData.setStuName(student.getStuName());
                bbtxData.setStuClass(student.getStuClass());
                bbtxData.setStuFaculty(student.getFaculty());

                bbtxData.setEvaProject(bbtxList.getProject());
                bbtxData.setEvaProjectID(projectId);
                bbtxData.setEvaStatus("未完成");

                bbtxSevice.addBbtxDataByInit(bbtxData);

            }
        }

        int i = bbtxSevice.updateBbtxList(bbtxList);
        int j = bbtxSevice.updateDataProject(bbtxList.getProject(), bbtxList.getProjectID(), bbtxList.getReleaseClass());
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"报表填写任务更新失败...");
        }
    }


    @RequestMapping("/bbtx/admin/deleteBbtx")
    @ResponseBody
    public RestResponse deleteBbtx(Integer id, HttpSession session){
        BbtxList bbtxList = bbtxSevice.queryBbtxListById(id);
        String claim = bbtxList.getClaim();
//        if("是".equals(claim)){
            bbtxSevice.delBbtxData(bbtxList.getProjectID());
//        }

        int i = bbtxSevice.deleteBbtxList(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"报表填写任务删除失败...");
        }
    }

    @RequestMapping("/bbtx/admin/bacthDeleteBbtx")
    @ResponseBody
    public RestResponse bacthDeleteBbtx(String data, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");

        String[] fields = data.split(",");
        String claim;
        for (String field:fields) {
            BbtxList bbtxList = bbtxSevice.queryBbtxListById(Integer.valueOf(field));

            claim = bbtxList.getClaim();
//            if("是".equals(claim)){
                bbtxSevice.delBbtxData(bbtxList.getProjectID());
//            }
        }
        int i = bbtxSevice.batchDelBbtxList(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"报表填写任务删除失败...");
        }
    }






    // 报表填写
    @RequestMapping(value = "/bbtx/admin/toBbtxData",method = RequestMethod.GET)
    public String bbtxData(){
        return "back/task/bbtxData";
    }



    /**
     * 学生管理员登录，只能查看其发布的任务
     *          老师院级管理员可以查看一个班级、学院的
     * @param page
     * @param limit
     * @param session
     * @return
     */
    @RequestMapping("/bbtx/admin/queryBbtxData")
    @ResponseBody
    public RestResponse queryBbtxData(Integer page, Integer limit, @Nullable BbtxData bbtxData, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");

        if("".equals(bbtxData.getStuName())){
            bbtxData.setStuName(null);
        }
        if("".equals(bbtxData.getEvaProject())){
            bbtxData.setEvaProject(null);
        }
        if("".equals(bbtxData.getEvaStatus())){
            bbtxData.setEvaStatus(null);
        }

        bbtxData.setReleaseNo(admin.getUsername());
        List<BbtxData> bbtxDataList = bbtxSevice.queryBbtxDataOfAdmin(bbtxData);

        for (BbtxData bbtx:bbtxDataList) {
            if(bbtx.getEvaTime() == null){
                bbtx.setEvaTime("未填写");
            }

        }

        if(!bbtxDataList.isEmpty()){
            HashMap hashMap = PageUtil.PageByList(bbtxDataList, page, limit, bbtxDataList.size());
            return RestResponse.ok(hashMap);
        }
        return RestResponse.fail(200,"请求数据异常");
    }


    /**
     * 管理员端跳转到报表审核页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/bbtx/admin/toEditDataOfAdmin/{id}",method = RequestMethod.GET)
    public String toEditDataOfAdmin(@PathVariable("id") Integer id, Model model,HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");

        BbtxData bbtxData = bbtxSevice.queryBbtxDataById(id);
        BbtxList bbtxList = bbtxSevice.queryOneBbtxListByProjectID(bbtxData.getEvaProjectID(), admin.getUsername());

        //文件的处理
        String paths = bbtxData.getUpload();
        String names = bbtxData.getUpFilename();

        List<String> pathsList = new ArrayList<>();
        List<String> namesList = new ArrayList<>();

        if (paths != null && paths != "未上传文件"){
            pathsList = Arrays.asList(paths.split(","));
            namesList = Arrays.asList(names.split(","));
        }

        String path = "";
        String name = "";
        Map<String, Object> map = new HashMap<>();

        for (int i=0; i<pathsList.size(); i++){
            path =  pathsList.get(i);
            name = namesList.get(i);
            map.put(name,path);
        }
        model.addAttribute("files",map);

        model.addAttribute("bbtxDataInfo",bbtxData);
        model.addAttribute("bbtxListInfo",bbtxList);

        return "back/task/bbtxData_edit";
    }


    /**
     * 管理员端审核报表数据：填写审核说明----审核成功
     *
     *      !!! 这里需要给管理员提供下载对应报表文件功能
     * @param bbtxData
     * @param session
     * @return
     */
    @RequestMapping("/bbtx/admin/checkBbtxDataOfPass")
    @ResponseBody
    public RestResponse checkBbtxDataOfPass(BbtxData bbtxData, HttpSession session){
        bbtxData.setEvaStatus("已完成");

        //-------------------------张豆豆-------------------------
        BbtxData bbtxData1 = bbtxSevice.queryBbtxDataById(bbtxData.getId());
        Student student = studentService.queryByUsername(bbtxData1.getStuNo());
        String tailTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        if(student.getEmail() == null || "".equals(student.getEmail())){
            return RestResponse.fail(200,"用户未填写邮箱！！！");
        }
        emailUtils.checkPass(student.getEmail(),student.getStuName(),time,bbtxData.getEvaProject(),tailTime);
        //--------------------------------------------------------

        int i = bbtxSevice.checkBbtxDataById(bbtxData);
        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"报表审核数据提交失败...");
        }

    }

    /**
     * 管理员端审核报表数据：填写审核说明、（删报表文件【则对应文件名也要做变换】）----审核不通过，驳回
     *
     *      !!! 这里需要给管理员提供下载对应报表文件功能
     * @param bbtxData
     * @param session
     * @return
     */
    @RequestMapping("/bbtx/admin/checkBbtxDataOfReject")
    @ResponseBody
    public RestResponse checkBbtxDataOfReject(BbtxData bbtxData, HttpSession session){
        bbtxData.setEvaStatus("已驳回");

        //-------------------------张豆豆-------------------------
        BbtxData bbtxData1 = bbtxSevice.queryBbtxDataById(bbtxData.getId());
        Student student = studentService.queryByUsername(bbtxData1.getStuNo());
        String tailTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        if(student.getEmail() == null || "".equals(student.getEmail())){
            return RestResponse.fail(200,"用户未填写邮箱！！！");
        }
        emailUtils.checkReject(student.getEmail(),student.getStuName(),time,bbtxData.getEvaProject(),tailTime);
        //--------------------------------------------------------

        int i = bbtxSevice.checkBbtxDataById(bbtxData);
        if(i != 0){
            return RestResponse.ok();
        }else{
            return RestResponse.fail(200,"报表审核数据提交失败...");
        }

    }


    @RequestMapping(value = "/bbtx/admin/toDetailBbtxData/{id}",method = RequestMethod.GET)
    public String toDetailDataOfAdmin(@PathVariable("id") Integer id, Model model, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");

        BbtxData bbtxData = bbtxSevice.queryBbtxDataById(id);
        BbtxList bbtxList = bbtxSevice.queryOneBbtxListByProjectID(bbtxData.getEvaProjectID(), admin.getUsername());


        //文件的处理
        String paths = bbtxData.getUpload();
        String names = bbtxData.getUpFilename();

        List<String> pathsList = new ArrayList<>();
        List<String> namesList = new ArrayList<>();

        if (paths != null && paths != "未上传文件"){
            pathsList = Arrays.asList(paths.split(","));
            namesList = Arrays.asList(names.split(","));
        }

        String path = "";
        String name = "";
        Map<String, Object> map = new HashMap<>();

        for (int i=0; i<pathsList.size(); i++){
            path =  pathsList.get(i);
            name = namesList.get(i);
            map.put(name,path);
        }

        model.addAttribute("files",map);

        model.addAttribute("bbtxDataInfo",bbtxData);
        model.addAttribute("bbtxListInfo",bbtxList);
        return "back/task/bbtxData_detail";
    }



    /*@RequestMapping("/bbtx/admin/deleteBbtxData")
    @ResponseBody
    public RestResponse deleteBbtxData(Integer id, HttpSession session){
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

    @RequestMapping("/bbtx/admin/bacthDeleteBbtxData")
    @ResponseBody
    public RestResponse bacthDeleteBbtxData(String data, HttpSession session){
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
    }*/



    @RequestMapping("/bbtx/admin/deleteBbtxData")
    @ResponseBody
    public RestResponse deleteBbtxData(Integer id){
        int i = bbtxSevice.deleteBbtxData(id);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"报表数据删除失败...");
        }
    }

    @RequestMapping("/bbtx/admin/bacthDeleteBbtxData")
    @ResponseBody
    public RestResponse bacthDeleteBbtxData(String data){
        String[] fields = data.split(",");
        int i = bbtxSevice.bacthDeleteBbtxData(fields);
        if(i != 0){
            return RestResponse.ok();
        }else {
            return RestResponse.fail(200,"报表数据删除失败...");
        }
    }





}

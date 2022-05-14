package com.zhfly021.controller;

import com.zhfly021.entity.*;
import com.zhfly021.service.BbtxSevice;
import com.zhfly021.service.XxwhService;
import com.zhfly021.utils.File.SplitStringUtil;
import com.zhfly021.utils.File.ZipUtils;
import com.zhfly021.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author： wzz
 * @date： 2021-03-25 22:32
 */

@Controller
public class ZipController {

    @Autowired
    XxwhService xxwhService;
    @Autowired
    BbtxSevice bbtxSevice;

    SplitStringUtil splitStringUtil = new SplitStringUtil();

    /**
     * 打包压缩下载文件
     */
    @PostMapping(value = "/zip/stuDownLoadZipFile")
    @ResponseBody
    public RestResponse stuDownLoadZipFile(HttpServletResponse response,HttpSession session) throws IOException {

        Admin admin = (Admin) session.getAttribute("session");
        Student student = new Student();
        if (admin != null){
            student = student =xxwhService.queryStuById(admin.getUsername());
        }else {
            student = (Student) session.getAttribute("student");
        }

        //新建一个以人名命名的文件夹
//        File destfile = new File(System.getProperty("user.dir") +"static/zip/"+student.getStuName());
        File destfile = new File("static/zip/"+student.getStuName());
        File destfile1 = new File("static/zip/"+student.getStuName()+".zip");
        if(!destfile.exists()){
            destfile.mkdir();
        }else {
            SplitStringUtil.deleteDir("static/zip/"+student.getStuName());
//            destfile.delete();
            destfile.mkdirs();
        }

        if (destfile1.exists()){
            SplitStringUtil.deleteDir("static/zip/"+student.getStuName()+".zip");
//            destfile1.delete();
        }

        Evidence evidence = xxwhService.queryEviByStuNo(student.getStuNo());
        String mark = "未上传文件";
        //文件的处理
        String perFileUrl = evidence.getPerFileUrl();
        String perFileName = evidence.getPerFileName();
        String eduFileUrl =  evidence.getEduFileUrl();
        String eduFileName = evidence.getEduFileName();
        String workFileUrl = evidence.getWorkFileUrl();
        String workFileName = evidence.getWorkFileName();
        String honorFileUrl = evidence.getHonorFileUrl();
        String honorFileName = evidence.getHonorFileName();
        String egoFileUrl = evidence.getEgoFileUrl();
        String egoFileName = evidence.getEgoFileName();


        String path = "";
        path = splitStringUtil.splitZip(perFileUrl, perFileName, mark, student.getStuName(),"个人信息");
        path = splitStringUtil.splitZip(eduFileUrl, eduFileName, mark, student.getStuName(),"教育背景");
        path = splitStringUtil.splitZip(workFileUrl, workFileName, mark, student.getStuName(),"工作经验");
        path = splitStringUtil.splitZip(honorFileUrl, honorFileName, mark, student.getStuName(),"荣誉证书");
        path = splitStringUtil.splitZip(egoFileUrl, egoFileName, mark, student.getStuName(),"自我评价");


        ZipUtils.doCompress(path, path+".zip");

        File destfiles = new File("static/zip/"+student.getStuName()+".zip");
        if(destfiles.exists()){
//           return RestResponse.ok().message("/zip/"+student.getStuName()+".zip");
           return RestResponse.ok().message("/zip/"+student.getStuName()+".zip");
        }else {
            return RestResponse.fail(200,"解压失败");
        }
    }

    //管理员的报表打包下载
    @RequestMapping(value = "/zip/adminDownLoadZipFile/{id}")
    @ResponseBody
    public RestResponse adminBbListZip(@PathVariable(name = "id") int id) throws IOException {
        String name = "";
        String url = "";
        BbtxList bbtxList = bbtxSevice.queryBbtxListById(id);

        List<BbtxData> list = bbtxSevice.selectBbtxFileByProjectId(bbtxList.getProjectID());
        System.out.println(list);

        File destfile = new File("static/zip/"+bbtxList.getProject());
        File destfile1 = new File("static/zip/"+bbtxList.getProject()+".zip");

        if(!destfile.exists()){
            destfile.mkdir();
        }else {
            SplitStringUtil.deleteDir("static/zip/"+bbtxList.getProject());
            destfile.mkdirs();
        }

        if (destfile1.exists()){
            SplitStringUtil.deleteDir("static/zip/"+bbtxList.getProject()+".zip");
        }

        String mark = "--null--";
        //文件的处理
        String path = "";

        for (int i=0; i<list.size(); i++){
            if (null != list.get(i).getUpload() && null != list.get(i).getUpFilename()){
                name = list.get(i).getUpFilename();
                url = list.get(i).getUpload();
                path = splitStringUtil.splitZip(url, name, mark, bbtxList.getProject(),list.get(i).getStuName());
//                path = splitStringUtil.adminSplitZip(url, name, mark, bbtxList.getProject());
                System.out.println(path);
            }
        }


        ZipUtils.doCompress(path, path+".zip");

        File destfiles = new File("static/zip/"+bbtxList.getProject()+".zip");
        if(destfiles.exists()){
//            return  RestResponse.ok();
            return RestResponse.ok().message("/zip/"+bbtxList.getProject()+".zip");
        }else {
            return RestResponse.fail(200,"解压失败");
        }

    }


    /**
     * 打包压缩下载文件
     */
//    @PostMapping(value = "/zip/stuDownLoadZipFile")
//    public void stuDownLoadZipFile(HttpServletResponse response, HttpSession session) throws IOException {
//        Student student = (Student) session.getAttribute("student");
//        String zipName = "myfile.zip";
//
//        Evidence evidence = xxwhService.queryEviByStuNo(student.getStuNo());
//        String mark = "未上传文件";
//        //文件的处理
//        String perFileUrl = evidence.getPerFileUrl();
//        String perFileName = evidence.getPerFileName();

//        List<FileBean> fileList = splitStringUtil.splitZip(perFileUrl,perFileName,mark);

//        List<FileBean> fileList = fileService.getFileList();//查询数据库中记录


//        response.setContentType("APPLICATION/OCTET-STREAM");
//        response.setHeader("Content-Disposition","attachment; filename="+new String(zipName.getBytes(),"iso-8859-1"));
//        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
//        try {
//        for(Iterator<FileBean> it = fileList.iterator(); it.hasNext();){
//            FileBean file = it.next();
//                ZipUtils.doCompress(file.getFilePath()+file.getFileName(), out);
//                response.flushBuffer();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally{
//            out.close();
//        }
//    }

//}

}
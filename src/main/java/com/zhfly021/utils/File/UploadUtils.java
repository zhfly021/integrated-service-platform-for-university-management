package com.zhfly021.utils.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sun
 * @version 1.0
 * @date 2020/12/2 10:14
 */
//文件上传工具栏
@Configuration
public class UploadUtils {

    //上传图片
    public  Map<String,Object> uploadImage(MultipartFile file) throws IOException {

        String visionUploadPath = System.getProperty("user.dir") + "/static/images/";

        String TrialUploadPath = System.getProperty("user.dir") + "/static/images/";

        Map map = new HashMap();

        //实验资源图片上传
        //获取系统时间戳  作为文件名称  防止重复
        Long startTs = System.currentTimeMillis();  // 当前时间戳
        //获取图片后缀
        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //获取图片
        File file1 = new File(TrialUploadPath + startTs + substring);

        if (!file1.getParentFile().exists()){
            //没有就创建一个文件夹
            file1.getParentFile().mkdirs();
            file.transferTo(file1);
        }else {
            //存在文件夹直接放
            file.transferTo(file1);

            //表明视频信息上传资源文件
            map.put("msg","success");
            map.put("code",0);
            map.put("desPath","/images/" + startTs + substring);
        }

        return map;

    }


    //文件上传
    public Map<String,Object> uploadSource(MultipartFile file,Integer flag) throws Exception{

        String visionResource = System.getProperty("user.dir") + "/static/space/";
        String trialResource = System.getProperty("user.dir") + "/static/filesource/";

        Map map = new HashMap();

        if (flag == 0){
            //实验资源上传
            //实验资源图片上传
            //获取系统时间戳  作为文件名称  防止重复
            Long startTs = System.currentTimeMillis();  // 当前时间戳
            //获取图片后缀
            String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //获取图片
            File file1 = new File(trialResource + startTs + substring);

            if (!file1.getParentFile().exists()){
                //没有就创建一个文件夹
                file1.getParentFile().mkdirs();
                file.transferTo(file1);
            }else {
                //存在文件夹直接放
                file.transferTo(file1);

                String fileRealName = file.getOriginalFilename();//获得原始文件名;

                //表明视频信息上传资源文件
                map.put("msg","success");
                map.put("code",0);
                map.put("desPath","/filesource/" + startTs + substring);
                map.put("fileRealName",fileRealName);
            }
            return map;
        }else {
            //视频资源上传
            //获取系统时间戳  作为文件名称  防止重复
            Long startTs = System.currentTimeMillis();  // 当前时间戳
            //获取图片后缀
            String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //获取图片
            File file1 = new File(visionResource + startTs + substring);

            if (!file1.getParentFile().exists()){
                //没有就创建一个文件夹
                file1.getParentFile().mkdirs();
                file.transferTo(file1);
            }else {
                //存在文件夹直接放
                file.transferTo(file1);

                String fileRealName = file.getOriginalFilename();//获得原始文件名;

                //表明视频信息上传资源文件
                map.put("msg","success");
                map.put("code",0);
                map.put("desPath","/static/space" + startTs + substring);
                map.put("fileRealName",fileRealName);
            }
            return map;
        }
    }



}

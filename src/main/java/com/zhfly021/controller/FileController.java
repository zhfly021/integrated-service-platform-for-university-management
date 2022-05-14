package com.zhfly021.controller;

import com.zhfly021.utils.File.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author： wzz
 * @date： 2021-03-12 15:56
 */

@RequestMapping(value = "file/")
@Controller
@Slf4j
public class FileController {

    //文件上传
    @RequestMapping("upload")
    @ResponseBody
    public Map<String,Object> file(MultipartFile file) throws Exception {
        UploadUtils upload = new UploadUtils();
        Map<String , Object> stringObjectMap = upload.uploadSource(file,0);
        return stringObjectMap;
    }

    //图片上传
    @RequestMapping("uploadImage")
    @ResponseBody
    public Map<String,Object> uploadImage(MultipartFile file) throws Exception {
        UploadUtils upload = new UploadUtils();
        Map<String , Object> stringObjectMap = upload.uploadImage(file);
        return stringObjectMap;
    }

}

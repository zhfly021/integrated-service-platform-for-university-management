package com.zhfly021.controller;

import com.zhfly021.entity.Student;
import com.zhfly021.handler.TransactionHandler;
import com.zhfly021.service.StudentService;
import com.zhfly021.utils.File.DowloadUtil;
import com.zhfly021.utils.File.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "excelUpload/")
@Controller
public class ExcelController {

    @Autowired
    private TransactionHandler transactionHandler;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "addMoreStu", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public Map<String,String> addMoreStu(@RequestPart(value = "file") MultipartFile file) {
        Map<String, String> map=new HashMap<String, String>();    //！hashMap是无序的

        List<Student> list = ExcelUtil.readExcel(file);

        if (  list == null ){
            map.put("code","1");
            map.put("message","上传失败，请注意格式");
        }else{
            //遍历拿到的excel表格数据
            for (int i=0; i<list.size(); i++){
                //进行数据库的添加操作
                int s = studentService.addStuOfAdmin(list.get(i));
            }

            map.put("code","0");
            map.put("message","上传成功");
        }
        return map;
    }


    //下载导入学生信息模板
    @RequestMapping(value = "downloadExcel")
    @ResponseBody
    public void download( @RequestParam("fileName") String fileName) throws IOException {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        // 设置信息给客户端不解析
        String type = new MimetypesFileTypeMap().getContentType(fileName);
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        response.setHeader("Content-type",type);
        // 设置编码
        String hehe = new String(fileName.getBytes("utf-8"), "iso-8859-1");
        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
        response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        DowloadUtil.download(fileName, response);

    }

}

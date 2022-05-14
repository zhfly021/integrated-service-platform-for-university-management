package com.zhfly021.controller;

import com.zhfly021.entity.BbtxData;
import com.zhfly021.entity.BbtxList;
import com.zhfly021.entity.Student;
import com.zhfly021.entity.ZhcpData;
import com.zhfly021.service.*;
import com.zhfly021.utils.EmailUtils;
import com.zhfly021.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class EmailController {

    @Autowired
    StudentService studentService;

    @Autowired
    BbtxSevice bbtxSevice;

    @Autowired
    ZhcpService zhcpService;

    @Autowired
    EmailUtils emailUtils;

    @ResponseBody
    @RequestMapping(value = "/bbtx/admin/tipsubmit")
    public RestResponse NotSubmitted(Integer id){
        BbtxData bbtxData = bbtxSevice.queryBbtxDataById(id);
        Student student = studentService.queryByUsername(bbtxData.getStuNo());
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        if(student.getEmail() == null || "".equals(student.getEmail())){
            return RestResponse.fail(200,"用户未填写邮箱！！！");
        }
        if("未提交".equals(bbtxData.getEvaStatus())){
            emailUtils.tipNotSubmitted(student.getEmail(),student.getStuName(),bbtxData.getEvaProject(),evaTime);
            return RestResponse.ok();
        }else if("已驳回".equals(bbtxData.getEvaStatus())){
            emailUtils.tipReject(student.getEmail(),student.getStuName(),bbtxData.getEvaProject(),evaTime);
            return RestResponse.ok();
        }
        return RestResponse.fail(200,"发送失败，请重试！！！");
    }

    @ResponseBody
    @RequestMapping(value = "/zhcp/admin/tipsubmit")
    public RestResponse zhcpEmail(Integer id){
        ZhcpData zhcpData = zhcpService.queryZhcpDataById(id);
        Student student = studentService.queryByUsername(zhcpData.getStuNo());
        String evaTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        if(student.getEmail() == null || "".equals(student.getEmail())){
            return RestResponse.fail(200,"用户未填写邮箱！！！");
        }
        if("未提交".equals(zhcpData.getEvaStatus())){
            emailUtils.allTestTipNotSumbited(student.getEmail(),student.getStuName(),zhcpData.getEvaProject(),evaTime);
            return RestResponse.ok();
        }else if("已驳回".equals(zhcpData.getEvaStatus())){
            emailUtils.allTestTipReject(student.getEmail(),student.getStuName(),zhcpData.getEvaProject(),evaTime);
            return RestResponse.ok();
        }else if("已暂存".equals((zhcpData.getEvaStatus()))){
            emailUtils.allTestTipZanCun(student.getEmail(),student.getStuName(),zhcpData.getEvaProject(),evaTime);
            return RestResponse.ok();
        }
        return RestResponse.fail(200,"发送失败，请重试！！！");
    }

}

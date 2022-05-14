package com.zhfly021.utils;


import com.zhfly021.entity.ZhcpSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @ClassName: EmailUtils
 * @author: zyx
 * @E-mail: 1377631190@qq.com
 * @DATE: 2019/7/27 12:19
 */
@Slf4j
@Component
public class EmailUtils {

    @Value("${spring.mail.username}")
    private String form;

    @Autowired
    private JavaMailSender sender;

    /**
     * 发送注册确定邮件
     * @param to 接收方
     * @param title 邮件标题
     * @param html 邮件内容
     */
    @Async
    public void send(String to, String title, String html) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(form);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(html, true);
            sender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("error {}", e);
            log.error("邮件发送失败 {}", e.getMessage());
        }
    }

    @Async
    public void regEmail(String to, String name, String content,String date) {
        String html = regTemplate.replace("${name}", name).replace("${content}", content).replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }

    /**
     * @Description: 报表数据列表(提醒)——未提交
     * @Author: 张豆豆
     * @Date: 2021-03-29 21:30:57
     */
    public void tipNotSubmitted(String to, String name,String tackName,String date){
        String html = regTemplate.replace("${name}", name).replace("${content}", "你好，你在“高校班级管理一体化服务平台”中还有未提交的" +
                "报表任务（填报项目："+ tackName +"）。" + "详细信息请登录网站（www.zhfly.top）后查看。").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }


    /**
     * @Description:  报表数据列表(提醒)——已驳回
     * @Author: 张豆豆
     * @Date: 2021-03-29 21:31:27
     */
    public void tipReject(String to, String name,String tackName,String date){
        String html = regTemplate.replace("${name}", name).replace("${content}", "你好，你在“高校班级管理一体化服务平台”中还有未重新提交的已驳回" +
                "报表任务（填报项目："+ tackName +"）。" + "详细信息请登录网站（www.zhfly.top）后查看。").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }


    /**
     * @Description:  报表数据列表（审核）——通过
     * @Author: 张豆豆
     * @Date: 2021-03-29 21:39:38
     */
    public void checkPass(String to, String name,String time,String tackName,String date){
        String html = regTemplate.replace("${name}", name).replace("${content}", "你好，你于"+ time +"日在“高校班级管理一" +
                "体化服务平台”中提交的报表任务“ " + tackName + " ”已通过审核。详细信息请登录网站（www.zhfly.top）后查看。").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }

    /**
     * @Description:  报表数据列表（审核）——驳回
     * @Author: 张豆豆
     * @Date: 2021-03-29 21:39:38
     */
    public void checkReject(String to, String name,String time,String tackName,String date){
        String html = regTemplate.replace("${name}", name).replace("${content}", "你好，你于"+ time +"日在“高校班级管理一" +
                "体化服务平台”中提交的报表任务“ " + tackName + " ”未通过审核，请尽快登录网站重新提交您的报表信息。详细信息请登录网站（www.zhfly.top）后查看。").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }

    /**
     * @Description: 综测(提醒)——未提交
     * @Author: 张豆豆
     * @Date: 2021-03-29 21:43:53
     */
    public void allTestTipNotSumbited(String to, String name,String tackName,String date){
        String html = regTemplate.replace("${name}", name).replace("${content}", "你好，你在“高校班级管理一体化服务平台”中还有未提交的综合测评任务（测评项目："+ tackName +"）。" +
                "详细信息请登录网站（www.zhfly.top）后查看。").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }

    /**
     * @Description: 综测(提醒)--已暂存
     * @Author: 张豆豆
     * @Date: 2021-03-31 19:19:33
     */
    public void allTestTipZanCun(String to, String name,String tackName,String date){
        String html = regTemplate.replace("${name}", name).replace("${content}", "你好，你在“高校班级管理一体化服务平台”中还有未提交的已暂存综合测评任务（测评项目："+ tackName +"）。" +
                "详细信息请登录网站（www.zhfly.top）后查看。").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }


    /**
     * @Description: 综测(提醒)——驳回
     * @Author: 张豆豆
     * @Date: 2021-03-29 21:43:53
     */
    public void allTestTipReject(String to, String name,String tackName,String date){
        String html = regTemplate.replace("${name}", name).replace("${content}", "你好，你在“高校班级管理一体化服务平台”中还有未提交的已驳回综合测评任务（测评项目："+ tackName +"）。" +
                "详细信息请登录网站（www.zhfly.top）后查看。").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }

    /**
     * @Description:  综测(提醒)——通过
     * @Author: 张豆豆
     * @Date: 2021-03-29 21:55:43
     */
    public void allTestCheckPass(String to, String name,String time,String tackName,String date){
        String html = regTemplate.replace("${name}", name).replace("${content}", "你好，你于"+ time +"在“高校班级管理一" +
                "体化服务平台”中提交的综合测评任务“ " + tackName + " ”已通过审核。详细信息请登录网站（www.zhfly.top）后查看。").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }

    /**
     * @Description:  报表数据列表（审核）——驳回
     * @Author: 张豆豆
     * @Date: 2021-03-29 21:39:38
     */
    public void allTestCheckReject(String to, String name,String time,String tackName,String date){
        String html = regTemplate.replace("${name}", name).replace("${content}", "你好，你于"+ time +"在“高校班级管理一" +
                "体化服务平台”中提交的综合测评任务“ " + tackName + " ”未通过审核，已被管理员驳回，请尽快登录网站重新提交您的综合测评信息。详细信息请登录网站（www.zhfly.top）后查看。").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }

    /**
     * @Description: 测评数据列表（复核）--通过
     * @Author: 张豆豆
     * @Date: 2021-03-29 21:53:42
     */
    public void appraisalDateRePass(String to, String name,String time,String tackName,String date){
        String html = regTemplate.replace("${name}", name).replace("${content}", "你好，你于"+ time +"在“高校班级管理一" +
                "体化服务平台”中申请复核的综合测评任务“" + tackName + "”已通过审核。详细信息请登录网站（www.zhfly.top）后查看。").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }

    /***
     * @Description:  测评数据列表（复核）--驳回
     * @Author: 张豆豆
     * @Date: 2021-03-29 21:57:10
     */
    public void appraisaDateReject(String to, String name,String time,String tackName,String date){
        String html = regTemplate.replace("${name}", name).replace("${content}", "你好，你于"+ time +"在“高校班级管理一" +
                "体化服务平台”中申请复核的综合测评任务“" + tackName + "”未通过审核，已被管理员驳回，请尽快登录网站重新提交您的综合测评信息。详细信息请登录网站（www.zhfly.top）后查看。").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }

    /***
     * @Description:测评数据汇总（终审通过）
     * @Author: 张豆豆
     * @Date: 2021-03-29 21:57:10
     */
    public void Pass(String to, String tackName, String note, ZhcpSummary zhcpSummary, String date){
        String html = regTemplate.replace("${name}", zhcpSummary.getStuName()).replace("${content}", "同学你好，你在“高校班级管理一体化服务平台”中填报的综合测评任务“*************”已通过班级终审。" +
                "你本次的成绩如下：\n" +
                "德育素质得分："+ zhcpSummary.getMoral() +"\n" +
                "智育素质得分："+ zhcpSummary.getIntellectual() +"\n" +
                "体育素质得分："+ zhcpSummary.getPhysical() +"\n" +
                "能力素质得分："+ zhcpSummary.getCompetence() +"\n" +
                "综合素质得分："+ zhcpSummary.getSummary() +"\n" +
                "文化课名次："+ zhcpSummary.getCultural() +"\n" +
                "综合测评名次："+ zhcpSummary.getComprehensive() +"\n" +
                "备注："+ zhcpSummary.getRemarks() +"\n" +
                "成绩如若有误请及时与班级管理员联系，详细信息请登录网站（www.zhfly.top）后查看。\n").replace("${date}", date);
        send(to, "管理员的悄悄话", html);
    }


    private final String regTemplate = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "  <meta charset=\"UTF-8\">\n" +
            "  <meta name=\"viewport\"\n" +
            "        content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
            "  <title>管理员悄悄话</title>\n" +
            "  <style>\n" +
            "    @media screen and (min-width: 800px) {\n" +
            "      .box {\n" +
            "        width: 360px;\n" +
            "      }\n" +
            "    }\n" +
            "  </style>\n" +
            "</head>\n" +
            "<body style=\"margin: 0;\">\n" +
            "<div class=\"box\" style=\"margin: 0 auto; border: 1px solid #3d484c;\">\n" +
            "  <nav style=\"background-color: #3d484c;height: 45px;\">\n" +
            "    <div style=\"float: right;color: #ffffff;padding: 12px\">高校班级管理一体化服务平台</div>\n" +
            "  </nav>\n" +
            "  <div style=\"padding: 0 8px;\">\n" +
            "    <h3 style=\"margin: 15px 2px;\">\n" +
            "    <span style=\"color: #31bdeb;\">${name}</span> 同学：\n" +
            "    </h3>\n" +
            "    <p style=\"margin: 5px 0; font-size: 13px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${content}</p>\n" +
            "    <p style=\"margin: 15px 2px;\">\n" +
            "    <p style=\"margin: 5px 0; font-size: 13px;\">发送时间：${date}</p>\n" +
            "    </p>\n" +
            "    <div style=\"font-size: 12px; color: #000;\">\n" +
            "      <h4 style=\"font-size: 10px; text-align: right;\">高校班级管理一体化服务平台</h4>\n" +
            "    </div>\n" +
            "    <h4 style=\"font-size: 10px; color: #707070;margin: 15px 2px;\">此邮件为系统邮件，请勿直接回复。</h4>\n" +
            "  </div>\n" +
            "  <footer style=\"height: 5px;background-color: #3d484c;\"></footer>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
}

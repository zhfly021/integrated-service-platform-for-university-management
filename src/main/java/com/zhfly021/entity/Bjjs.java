package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description 班级建设意见实体类
 * @create 2020-12-07 22:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bjjs {
    private int id;
    private String sendNo;
    private String sender;
    private String sendClass;
    private String letterTitle;
    private String sendTime;
    private String letter;
    private String responser;
    private String responseTime;
    private String responseContent;
    private String status;
    private String evaluateContent;
    private String evaluate;
    private String mark;
}

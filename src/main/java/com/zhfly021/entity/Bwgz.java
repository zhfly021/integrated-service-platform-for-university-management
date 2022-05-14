package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-09 11:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bwgz {
    private int id;
    private String sendNo;
    private String sender;
    private String sendClass;
    private String feedback;
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

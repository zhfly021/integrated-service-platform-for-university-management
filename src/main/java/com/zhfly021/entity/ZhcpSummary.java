package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-22 8:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZhcpSummary {
    private int id;
    private String stuNo;
    private String stuName;
    private String stuClass;
    private String evaProject;
    private String evaProjectID;
    private String evaTime;
    private String evaStatus;
    private double moral;
    private double intellectual;
    private double physical;
    private double competence;
    private double summary;
    private int cultural;
    private int comprehensive;
    private String remarks;
    private String reviewRequest;
    private String reviewResult;


}

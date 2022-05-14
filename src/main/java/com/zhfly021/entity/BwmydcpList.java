package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-10 8:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BwmydcpList {
    private int id;
    private String releaseTime;
    private String publisher;
    private String publisherNo;
    private String publisherClass;
    private String publisherCommittee;
    private String project;
    private String projectID;
    private String remarks;
    private String deadline;
    private String status;
}

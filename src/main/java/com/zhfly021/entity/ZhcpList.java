package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-22 8:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZhcpList {
    private int id;
    private String releaseTime;
    private String releaser;
    private String releaseNo;
    private String releaseClass;
    private String releaseCommittee;
    private String project;
    private String projectID;
    private String zcObject;
    private String remarks;
    private String deadline;
    private String status;
    private String finalPerson;
    private String finalTime;
}

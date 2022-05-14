package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-08 22:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BbtxListAndData {
    private int id;
    private String releaseTime;
    private String releaser;
    private String releaseNo;
    private String releaseClass;
    private String releaseFaculty;
    private String releaseCommittee;
    private String project;
    private String projectID;
    private String claim;
    private String remarks;
    private String deadline;
    private String status;
    private String download;
    private String fileName;

    private BbtxData bbtxData;
}

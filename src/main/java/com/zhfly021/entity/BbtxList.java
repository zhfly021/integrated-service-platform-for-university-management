package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BbtxList {

  private int id;
  private String releaseTime;
  private String releaser;
  private String releaseNo;
  private String releaseClass;
  private String[] releaseClasses;
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


}

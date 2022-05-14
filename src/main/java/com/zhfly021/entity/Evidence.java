package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evidence {

  private long id;
  private String stuNo;
  private String stuName;
  private String stuClass;
  private String stuFaculty;
  private String status;
  private String informationID;
  private String evidenceName;
  private String evidenceId;
  private String submitTime;
  private String submitMarks;
  private String verifier;
  private String verifyTime;
  private String verifyMarks;

  private String perFileUrl;
  private String perFileName;

  private String eduFileUrl;
  private String eduFileName;

  private String workFileUrl;
  private String workFileName;

  private String honorFileUrl;
  private String honorFileName;

  private String egoFileUrl;
  private String egoFileName;

  // 教师查询简历证明信息专用字段
  private String[] stuClasses;


}

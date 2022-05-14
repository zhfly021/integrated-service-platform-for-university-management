package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resume {

  private long id;
  private String stuNo;
  private String stuName;
  private String stuClass;
  private String stuFaculty;
  private String status;
  private String resumeId;
  private String submitTime;
  private String submitMarks;
  private String verifier;
  private String verifyTime;
  private String verifyMarks;
  private String sex;
  private String age;
  private String phone;
  private String nation;
  private String startDate;
  private String endDate;
  private String educationSchool;
  private String educationBackground;
  private String workTime1;
  private String workTime2;
  private String workTime3;
  private String workCompany1;
  private String workCompany2;
  private String workCompany3;
  private String workExperience1;
  private String workExperience2;
  private String workExperience3;
  private String honor;
  private String selfAssessment;
  private String submitInstructions;


  // 教师查询简历信息专用字段
  private String[] stuClasses;


}

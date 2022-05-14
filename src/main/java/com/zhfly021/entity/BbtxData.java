package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BbtxData {

  private int id;
  private String stuNo;
  private String stuName;
  private String stuClass;
  private String stuFaculty;
  private String evaProject;
  private String evaProjectID;
  private String evaTime;
  private String evaStatus;
  private String upload;
  private String upFilename;
  private String marks;
  private String remarks;


  private String releaseNo;


}

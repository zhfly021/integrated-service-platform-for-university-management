package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-11-27 11:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;             // 编号
    private String stuNo;       // 学号
    private String stuName;     // 姓名
    private String password;    // 密码
    private String sex;         // 性别
    private String photo;       // 照片
    private String faculty;     // 院系
    private String series;      // 级数
    private String profession;  // 专业
    private String stuClass;    // 班级
    private String stuID;       // 身份证号
    private String dormitory;   // 宿舍
    private Integer shepherd;       // 舍长：0不是，1是
    private String committee;   // 职务
    private int position;       // 排序
    private String category;    // 分类
    private String tel;         // 电话
    private String email;       // 邮箱
    private String qq;          // qq

    public Student(String stuNo, String stuName, String sex, String faculty, String series, String profession, String stuClass, String stuID) {
        this.stuNo = stuNo;
        this.stuName = stuName;
        this.sex = sex;
        this.faculty = faculty;
        this.series = series;
        this.profession = profession;
        this.stuClass = stuClass;
        this.stuID = stuID;
    }
}

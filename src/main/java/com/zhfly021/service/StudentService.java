package com.zhfly021.service;

import com.zhfly021.entity.Student;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-11-27 11:44
 */
public interface StudentService {
    public Student queryByUsername(String username);
    public void modifyPassword(String stuNo,String password);
    public List<Student> queryByStuClass(String stuClass);
    public List<Student> queryCommittee(String stuClass);
    public List<Student> queryCategory(String stuClass,String category);
    public List<Student> queryQsz(String stuClass);
    public List<Student> queryStuForAdmin(Student student);
    public List<Student> queryStuForTeacher(String[] employers);
    public List<Student> queryStuForCommittee(Student student);

    /**
     * 添加管理员时判断登录用户是否有权限添加
     * @param student
     * @return
     */
    public Student queryStuOfAddAdmin(Student student);
    /**
     * 增加一条学生信息
     * @param student
     * @return
     */
    public int addStuOfAdmin(Student student);

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    public Student queryStuById(Integer id);

    /**
     * 根据学生id修改学生用户信息
     * @param student
     * @return
     */
    public int updateStuByIdOfAdmin(Student student);

    /**
     * 根据学号修改学生信息
     * @param student
     * @return
     */
    public int updateInfo(Student student);

    /**
     * 根据学号修改学生信息
     * @param student
     * @return
     */
    public int updateStuInfo(Student student);

    /**
     * 根据id删除一条学生用户信息
     * @param id
     * @return
     */
    public int delStuById(Integer id);

    /**
     * 批量删除多条学生用户信息
     * @param ids
     * @return
     */
    public int batchDelStu(String[] ids);

    /**
     * 学生修改头像
     * @param stuNo
     * @param photo
     * @return
     */
    public int updatePic(String stuNo,String photo);

    /**
     * 查询学生数量
     * @param faculty
     * @param stuClass
     * @return
     */
    public int countStudent(String faculty, String stuClass);
}

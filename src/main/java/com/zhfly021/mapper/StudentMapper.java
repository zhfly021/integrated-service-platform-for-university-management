package com.zhfly021.mapper;

import com.zhfly021.entity.Student;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-11-27 11:19
 */
@Repository
public interface StudentMapper {
    /**
     * 根据用户名查询用户信息，用于登录判断
     * @param username
     * @return
     */
    public Student queryByUsername(String username);

    /**
     * 修改密码
     * @param stuNo
     */
    public void modifyPassword(String stuNo,String password);

    /**
     * 查找同班同学
     * @param stuClass
     * @return
     */
    public List<Student> queryByStuClass(String stuClass);

    /**
     * 查询各班班委班级职务
     * @param stuClass
     * @return
     */
    public List<Student> queryCommittee(String stuClass);

    /**
     * 查询各班班委班级职务分类tzb、bwh
     * @param stuClass
     * @return
     */
    public List<Student> queryCategory(String stuClass,String category);

    /**
     * 查询各班寝室长
     * @param stuClass
     * @return
     */
    public List<Student> queryQsz(String stuClass);

    /**
     * 管理员查询学生信息
     * @param student
     * @return
     */
    public List<Student> queryStuForAdmin(Student student);


    /**
     * 添加管理员时判断登录用户是否有权限添加
     * @param student
     * @return
     */
    public Student queryStuOfAddAdmin(Student student);


    /**
     * 辅导员查询学生信息
     * @param employers
     * @return
     */
    public List<Student> queryStuForTeacher(String[] employers);

    /**
     * 班委查询学生信息
     * @param student
     * @return
     */
    public List<Student> queryStuForCommittee(Student student);

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



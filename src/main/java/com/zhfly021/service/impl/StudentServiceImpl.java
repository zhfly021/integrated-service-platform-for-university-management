package com.zhfly021.service.impl;

import com.zhfly021.entity.Student;
import com.zhfly021.mapper.StudentMapper;
import com.zhfly021.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-11-27 11:45
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Override
    public Student queryByUsername(String username) {
        return studentMapper.queryByUsername(username);
    }

    @Override
    public void modifyPassword(String stuNo,String password) {
        studentMapper.modifyPassword(stuNo,password);
    }

    @Override
    public List<Student> queryByStuClass(String stuClass) {
        return studentMapper.queryByStuClass(stuClass);
    }

    @Override
    public List<Student> queryCommittee(String stuClass) {
        return studentMapper.queryCommittee(stuClass);
    }

    @Override
    public List<Student> queryCategory(String stuClass, String category) {
        return studentMapper.queryCategory(stuClass,category);
    }

    @Override
    public List<Student> queryQsz(String stuClass) {
        return studentMapper.queryQsz(stuClass);
    }

    @Override
    public List<Student> queryStuForAdmin(Student student) {
        return studentMapper.queryStuForAdmin(student);
    }

    @Override
    public List<Student> queryStuForTeacher(String[] employers){
        return studentMapper.queryStuForTeacher(employers);
    }

    @Override
    public List<Student> queryStuForCommittee(Student student) {
        return studentMapper.queryStuForCommittee(student);
    }

    @Override
    public Student queryStuOfAddAdmin(Student student) {
        return studentMapper.queryStuOfAddAdmin(student);
    }

    @Override
    public int addStuOfAdmin(Student student) {
        return studentMapper.addStuOfAdmin(student);
    }

    @Override
    public Student queryStuById(Integer id) {
        return studentMapper.queryStuById(id);
    }

    @Override
    public int updateStuByIdOfAdmin(Student student) {
        return studentMapper.updateStuByIdOfAdmin(student);
    }

    @Override
    public int updateInfo(Student student) {
        return studentMapper.updateInfo(student);
    }

    @Override
    public int updateStuInfo(Student student) {
        return studentMapper.updateStuInfo(student);
    }

    @Override
    public int delStuById(Integer id) {
        return studentMapper.delStuById(id);
    }

    @Override
    public int batchDelStu(String[] ids) {
        return studentMapper.batchDelStu(ids);
    }

    @Override
    public int updatePic(String stuNo, String photo) {
        return studentMapper.updatePic(stuNo, photo);
    }

    @Override
    public int countStudent(String faculty, String stuClass) {
        return studentMapper.countStudent(faculty, stuClass);
    }
}

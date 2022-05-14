package com.zhfly021.service.impl;

import com.zhfly021.entity.Evidence;
import com.zhfly021.entity.Resume;
import com.zhfly021.mapper.XxwhMapper;
import com.zhfly021.mapper.XxwhMapperZdd;
import com.zhfly021.service.XxwhService;
import com.zhfly021.service.XxwhServiceZdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-19 20:55
 */
@Service
public class XxwhServiceImplZdd implements XxwhServiceZdd {


    @Autowired
    XxwhMapperZdd xxwhMapper;   // 这里的类名不要改，不要加上Zdd

    @Override
    public Integer holdXxwhService(Resume student) {
        return xxwhMapper.holdXxwh(student);
    }

    @Override
    public Resume queryByIdService(String stuNo) {
        Resume resume = xxwhMapper.queryById(stuNo);
        return resume;
    }

    @Override
    public Integer queryByStateService() {
        Integer integer = xxwhMapper.queryByState();
        return integer;
    }

    @Override
    public Integer updateResumeService(Resume resume) {
        Integer integer = xxwhMapper.updateResume(resume);
        return integer;
    }

    @Override
    public Integer updateStatusService(Resume resume) {
        Integer integer = xxwhMapper.updateStatus(resume);
        return integer;
    }


    @Override
    public Integer insertEvidenceXxwh(Evidence evidence) {
        Integer integer = xxwhMapper.insertEvidenceXxwh(evidence);
        return integer;
    }

    @Override
    public Evidence queryByIdEvidenceService(String stuNo) {
        Evidence evidence = xxwhMapper.queryByIdEvidence(stuNo);
        return evidence;
    }

    @Override
    public Integer updateEvidenceStatus(String status, String stuNo) {
        Integer integer = xxwhMapper.updateEvidenceStatus(status, stuNo);
        return integer;
    }

    @Override
    public List<Resume> queryResumeService() {
        List<Resume> resumes = xxwhMapper.queryResume();
        return resumes;
    }


}

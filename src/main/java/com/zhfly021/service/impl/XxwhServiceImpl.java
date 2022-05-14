package com.zhfly021.service.impl;

import com.zhfly021.entity.Evidence;
import com.zhfly021.entity.Resume;
import com.zhfly021.entity.Student;
import com.zhfly021.mapper.XxwhMapper;
import com.zhfly021.service.XxwhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-19 20:55
 */
@Service
public class XxwhServiceImpl implements XxwhService {

    @Autowired
    XxwhMapper xxwhMapper;

    @Override
    public List<Resume> queryResumeList(Resume resume) {
        return xxwhMapper.queryResumeList(resume);
    }

    @Override
    public int deleteResumeList(Integer id) {
        return xxwhMapper.deleteResumeList(id);
    }

    @Override
    public int bacthDeleteResumeList(String[] ids) {
        return xxwhMapper.bacthDeleteResumeList(ids);
    }

    @Override
    public List<Evidence> queryEvidenceList(Evidence evidence) {
        return xxwhMapper.queryEvidenceList(evidence);
    }

    @Override
    public int deleteEvidenceList(Integer id) {
        return xxwhMapper.deleteEvidenceList(id);
    }

    @Override
    public int bacthDeleteEvidenceList(String[] ids) {
        return xxwhMapper.bacthDeleteEvidenceList(ids);
    }

    @Override
    public int addEvidence(Evidence evidence) {
        return xxwhMapper.addEvidence(evidence);
    }

    @Override
    public Evidence queryEviById(int id) {
        return xxwhMapper.queryEviById(id);
    }

    @Override
    public Student queryStuById(String stuNo) {
        return xxwhMapper.queryStuById(stuNo);
    }

    @Override
    public int updateEviByAdmin(Evidence evidence) {
        return xxwhMapper.updateEviByAdmin(evidence);
    }

    @Override
    public Evidence queryEviByStuNo(String stuNo) {
        return xxwhMapper.queryEviByStuNo(stuNo);
    }

    @Override
    public int stuDeleteEvFile(Evidence evidence) {
        return xxwhMapper.stuDeleteEvFile(evidence);
    }

    @Override
    public int countResume(String faculty, String stuClass) {
        return xxwhMapper.countResume(faculty, stuClass);
    }
}

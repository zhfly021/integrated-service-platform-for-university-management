package com.zhfly021.mapper;

import com.sun.org.apache.regexp.internal.RE;
import com.zhfly021.entity.BbtxList;
import com.zhfly021.entity.Evidence;
import com.zhfly021.entity.Resume;
import com.zhfly021.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-19 20:54
 */
@Repository
public interface XxwhMapper {


    /**
     * 管理员端查询学生简历数据列表
     * @param resume
     * @return
     */
    public List<Resume> queryResumeList(Resume resume);

    /**
     * 删除学生简历
     * @param id
     * @return
     */
    public int deleteResumeList(Integer id);

    /**
     * 批量删除学生简历
     * @param ids
     * @return
     */
    public int bacthDeleteResumeList(String[] ids);

    /**
     * 管理员端查询学生简历证明材料数据列表
     * @param evidence
     * @return
     */
    public List<Evidence> queryEvidenceList(Evidence evidence);

    /**
     * 删除学生简历证明材料
     * @param id
     * @return
     */
    public int deleteEvidenceList(Integer id);

    /**
     * 批量删除学生简历材料
     * @param ids
     * @return
     */
    public int bacthDeleteEvidenceList(String[] ids);

    /**
     * 学生添加简历
     * @param evidence
     * @return
     */
    public int addEvidence(Evidence evidence);

    /**
     * 根据id查询某一条证明材料显示于管理员待审核页面
     * @param id
     * @return
     */
    public Evidence queryEviById(int id);

    /**
     * 根据学号查询学生
     * @param stuNo
     * @return
     */
    public Student queryStuById(String stuNo);

    /**
     * 管理员审核证明材料
     * @param evidence
     * @return
     */
    public int updateEviByAdmin(Evidence evidence);

    /**
     * 根据学号查询该学生的证明材料
     * @param stuNo
     * @return
     */
    public Evidence queryEviByStuNo(String stuNo);

    /**
     * 学生证明材料被驳回后重新修改--文件删除
     * @param evidence
     * @return
     */
    public int stuDeleteEvFile(Evidence evidence);

    /**
     * 学生证明材料被驳回后重新提交--整个
     * @param evidence
     * @return
     */
    public int stuUpdateEvFile(Evidence evidence);

    /**
     * 查询简历数量
     * @param faculty
     * @param stuClass
     * @return
     */
    public int countResume(String faculty, String stuClass);

}

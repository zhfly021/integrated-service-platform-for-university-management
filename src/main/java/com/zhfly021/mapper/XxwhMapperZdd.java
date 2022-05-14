package com.zhfly021.mapper;

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
public interface XxwhMapperZdd {

    /**
     * @Description: 简历信息的暂存
     * @Author: 张豆豆
     * @Date: 2021-03-20 22:21:18
     */
    public Integer holdXxwh(Resume resume);

    /**
     * @Description: 查询简历信息
     * @Author: 张豆豆
     * @Date: 2021-03-20 22:21:18
     */
    public Resume queryById(String stuNo);


    /**
     * @Description: 查询用户简历的状态
     * @Author: 张豆豆
     * @Date: 2021-03-21 21:27:54
     */
    public Integer queryByState();

    /**
     * @Description: 修改简历的信息
     * @Author: 张豆豆
     * @Date: 2021-03-22 21:32:22
     */
    public Integer updateResume(Resume resume);

    /**
     * @Description: 修改简历的信息为已提交
     * @Author: 张豆豆
     * @Date: 2021-03-23 21:48:35
     */
    public Integer updateStatus(Resume resume);

    /**
     * @Description: 初始化evidence表
     * @Author: 张豆豆
     * @Date: 2021-03-23 21:57:10
     */
    public Integer insertEvidenceXxwh(Evidence evidence);

    /**
     * @Description: 查找evidence中的信息
     * @Author: 张豆豆
     * @Date: 2021-03-23 22:12:04
     */
    public Evidence queryByIdEvidence(String stuNo);


    /**
     * @Description: 修改信息状态
     * @Author: 张豆豆
     * @Date: 2021-03-23 21:48:35
     */
    public Integer updateEvidenceStatus(String status,String stuNo);

    /**
     * @Description: 查询所有简历的信息
     * @Author: 张豆豆
     * @Date: 2021-03-27 21:49:06
     */
    public List<Resume> queryResume();
}

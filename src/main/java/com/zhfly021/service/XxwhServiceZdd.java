package com.zhfly021.service;

import com.zhfly021.entity.Evidence;
import com.zhfly021.entity.Resume;
import com.zhfly021.entity.Student;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-19 20:55
 */
public interface XxwhServiceZdd {

    /**
     * @Description: 简历信息的暂存Service层
     * @Author: 张豆豆
     * @Date: 2021-03-20 22:23:19
     */
    public Integer holdXxwhService(Resume student);

    /**
     * @Description: 查询简历信息
     * @Author: 张豆豆
     * @Date: 2021-03-21 21:28:55
     */
    public Resume queryByIdService(String stuNo);


    /**
     * @Description: 查询用户简历的状态
     * @Author: 张豆豆
     * @Date: 2021-03-21 21:28:55
     */
    public Integer queryByStateService();

    /**
     * @Description: 修改简历的信息
     * @Author: 张豆豆
     * @Date: 2021-03-22 21:32:22
     */
    public Integer updateResumeService(Resume resume);

    /**
     * @Description: 修改简历的信息为已提交
     * @Author: 张豆豆
     * @Date: 2021-03-23 21:48:35
     */
    public Integer updateStatusService(Resume resume);

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
    public Evidence queryByIdEvidenceService(String stuNo);


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
    public List<Resume> queryResumeService();

}

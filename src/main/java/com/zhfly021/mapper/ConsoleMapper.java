package com.zhfly021.mapper;

import com.zhfly021.entity.Admin;
import com.zhfly021.entity.BwgzCount;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-14 9:59
 */
@Repository
public interface ConsoleMapper {

    /**
     * 查询zhcpDataReview表中各个状态的数据记录条数
     * @param status
     * @param stuClass
     * @param faculty
     * @return
     */
    public int countZhcpOfStatus(String status, String faculty, String stuClass);

    /**
     * 查询bbtxData表中各个状态的数据记录条数
     * @param status
     * @param stuClass
     * @param faculty
     * @return
     */
    public int countBbtxDataOfStatus(String status, String faculty, String stuClass);

    /**
     * 查询bjtz表中的数据记录条数
     * @param stuClass
     * @param faculty
     * @return
     */
    public int countBjtz(String faculty, String stuClass);

    /**
     * 查询Bjjs表中的数据记录条数
     * @param status
     * @param stuClass
     * @param faculty
     * @return
     */
    public int countBjjsOfStatus(String status, String faculty, String stuClass);

    /**
     * 查询Bwgz表中的数据记录条数
     * @param status
     * @param stuClass
     * @param faculty
     * @return
     */
    public int countBwgzOfStatus(String status, String faculty, String stuClass);

    /**
     * 查询BwmydcpData表中各个状态的数据记录条数
     * @param status
     * @param stuClass
     * @param faculty
     * @return
     */
    public int countBwmydcpDataOfStatus(String status, String faculty, String stuClass);

    /**
     * 查询resume表中各个状态的数据记录条数
     * @param status
     * @param stuClass
     * @param faculty
     * @return
     */
    public int countResumeOfStatus(String status, String faculty, String stuClass);

    /**
     * 查询evidence表中各个状态的数据记录条数
     * @param status
     * @param stuClass
     * @param faculty
     * @return
     */
    public int countEvidenceOfStatus(String status, String faculty, String stuClass);


    /**
     * 查询班级建设意见数量
     * @param faculty
     * @param time
     * @return
     */
    public int countBjjsOfDean(String faculty,String sendClass, String time);


    /**
     * 查询班委工作意见数量
     * @param faculty
     * @param time
     * @return
     */
    public int countBwgzOfDean(String faculty, String sendClass, String time);

    /**
     * 班委工作反馈量
     * @param sendClass
     * @return
     */
    public List<BwgzCount> bwgzFeedBack(String sendClass);






}

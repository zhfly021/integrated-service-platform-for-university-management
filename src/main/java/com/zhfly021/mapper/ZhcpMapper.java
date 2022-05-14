package com.zhfly021.mapper;

import com.zhfly021.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-22 9:01
 */
@Repository
public interface ZhcpMapper {
    //    ----------  前台   ---------
    /*
        1. 综合测评任务
            后台发布任务，前台接收到任务，查询任务列表，同时查询该同学是否完成该任务及相关状态
            操作：填报/查看
                未逾期且未填写（s:未完成）跳转至 填报页面
                已逾期且未填写（s:已逾期）跳转至 未及时填报解释页面
                未逾期且未确认（s:已审核）跳转至 填报详情，此时 有 信息无误确认按钮
                其  他（s:待审核、已完成）跳转至 填报详情，此时 无 信息无误确认按钮
            逾期自动确认
        2. 综合测评汇总
            填报数据经学生确认后，写入zhcpSummary表，学生可点击查看。
            未确认的数据全部显示 0，逾期自动确认
            操作：查看（页面含确认按钮）、
     */

    // ************ 1. 综合测评任务  *************************
    /**
     * 学生端查询综合测评项目
     * @param stuNo
     * @param project
     * @param status
     * @return
     */
    public List<ZhcpListAndData> queryByStu(String stuNo, String project, String status);

    /**
     * 学生端查询综测汇总成绩
     * @param zhcpSummary
     * @return
     */
    public List<ZhcpSummary> querySummaryByStu(ZhcpSummary zhcpSummary);

    /**
     * 查询每个班的综合测评总成绩
     * @param evaProjectID
     * @param stuClass
     * @return
     */
    public List<ZhcpSummary> querySummaryByStuClass(String evaProjectID, String stuClass);

    /**
     * 根据id查询Summary
     * @param id
     * @return
     */
    public ZhcpSummary querySummaryById(Integer id);

    /**
     * 学生端填报综合测评任务
     * @param zhcpData
     * @return
     */
    public int addZhcpData(ZhcpData zhcpData);

    /**
     * 学生端填写完综合测评任务后，对审核数据表的部分数据进行刷新
     * @param zhcpDataReview
     * @return
     */
    public int updateZhcpDataReviewByInit(ZhcpDataReview zhcpDataReview);

    /**
     * 根据id查询综合测评数据
     * @param id
     * @return
     */
    public ZhcpData queryZhcpDataById(Integer id);


    /**
     * 根据id查询综合测评审核后的数据
     * @param id
     * @return
     */
    public ZhcpDataReview queryZhcpDataReviewById(Integer id);

    /**
     * 前台综测测评数据表状态更新
     * @param evaProjectID
     * @return
     */
    public int updateDataStatusOfStu(String evaProjectID, String evaStatus, String stuNo);

    /**
     * 前台综合测评复核数据表状态更新为已逾期
     * @param evaProjectID
     * @return
     */
    public int updateDataReviewStatusOfStu(String evaProjectID, String evaStatus, String stuNo);

    /**
     * 综合测评汇总表状态更新为
     * @param evaProjectID
     * @return
     */
    public int updateSummaryStatusOfStu(String evaProjectID, String evaStatus, String stuNo);

    /**
     * 根据List表中的id查询projectID
     * @param id
     * @return
     */
    public String queryProjectIdByID(Integer id);

    /**
     * 根据项目ID、发布班级查询一条测评任务
     * @param projectID
     * @return
     */
    public ZhcpList queryZhcpListByProjectID(String projectID, String releaseClass);

    /**
     * 前台综合测评数据表确认时间更新
     * @param evaProjectID
     * @return
     */
    public int upadZhcpDataConfirmTime(String confirmTime, String evaProjectID, String stuNo);

    /**
     * 前台综合测评复核数据表确认时间更新
     * @param evaProjectID
     * @return
     */
    public int upadZhcpDataReviewConfirmTime(String confirmTime, String evaProjectID, String stuNo);






    //    ----------  后台   ---------
    /*
        1. 综合测评任务
            发布任务
            操作：发布、批量删除、删除、修改（这里需要可以修改截止日期）
        2. 综合测评数据
            操作：批量删除、删除、查看/审核数据
        3. 综合测评汇总
            操作：批量删除、删除、查看数据
        4. 综测数据分析
            echarts
     */


    // 1. 综合测评任务
    /**
     * 管理员端查询综合测评项目列表
     * @param zhcpList
     * @return
     */
    public List<ZhcpList> queryZhcpList(ZhcpList zhcpList);

    /**
     * 管理员端增加测评项目
     * @param zhcpList
     * @return
     */
    public int addZhcpList(ZhcpList zhcpList);

    /**
     * 初始化数据表，并标记状态为“未完成、已逾期”
     * @param zhcpData
     * @return
     */
    public int addZhcpDataByInit(ZhcpData zhcpData);

    /**
     * 初始化复核数据表，并标记状态为“未完成、已逾期”
     * @param zhcpDataReview
     * @return
     */
    public int addZhcpDataReviewByInit(ZhcpDataReview zhcpDataReview);

    /**
     * 初始化汇总数据表，并标记状态为“未完成、已逾期”
     * @param zhcpSummary
     * @return
     */
    public int addZhcpSummaryByInit(ZhcpSummary zhcpSummary);

    /**
     * 综测测评任务表状态更新
     * @param projectID
     * @return
     */
    public int updateListStatus(String projectID, String status, String releaseClass);

    /**
     * 综测测评数据表状态更新
     * @param evaProjectID
     * @return
     */
    public int updateDataStatus(String evaProjectID, String evaStatus, String stuClass);

    /**
     * 综合测评复核数据表状态更新为已逾期
     * @param evaProjectID
     * @return
     */
    public int updateDataReviewStatus(String evaProjectID, String evaStatus, String stuClass);

    /**
     * 综合测评汇总表数据表状态更新为已逾期
     * @param evaProjectID
     * @return
     */
    public int updateSummaryStatus(String evaProjectID, String evaStatus, String stuClass);

    /**
     * 综测测评数据表项目名称更新
     * @param evaProject
     * @param evaProjectID
     * @param stuClass
     * @return
     */
    public int updateDataProject(String evaProject, String evaProjectID, String stuClass);

    /**
     * 综测测评数据表项目名称更新
     * @param evaProject
     * @param evaProjectID
     * @param stuClass
     * @return
     */
    public int updateDataReviewProject(String evaProject, String evaProjectID, String stuClass);

    /**
     * 汇总表数据表项目名称更新
     * @param evaProject
     * @param evaProjectID
     * @param stuClass
     * @return
     */
    public int updateSummaryProject(String evaProject, String evaProjectID, String stuClass);


    /**
     * 根据项目ID、测评人学号查询一条测评数据
     * @param evaProjectID
     * @param stuNo
     * @return
     */
    public ZhcpData queryZhcpDataByProjectIDAndStuNo(String evaProjectID,String stuNo);

    /**
     * 根据项目ID、测评人学号查询一条过审测评数据
     * @param evaProjectID
     * @param stuNo
     * @return
     */
    public ZhcpDataReview queryZhcpDataReviewByProjectIDAndStuNo(String evaProjectID,String stuNo);

    /**
     * 根据项目ID、测评人学号查询一条测评汇总数据
     * @param evaProjectID
     * @param stuNo
     * @return
     */
    public ZhcpSummary queryZhcpSummaryByProjectIDAndStuNo(String evaProjectID,String stuNo);

    /**
     * 查询一条综合测评任务数据
     * @param id
     * @return
     */
    public ZhcpList queryZhcpListById(Integer id);

    /**
     * 更新一条综合测评任务数据
     * @param zhcpList
     * @return
     */
    public int updateZhcpList(ZhcpList zhcpList);

    /**
     * 删除一条综合测评任务
     * @param id
     * @return
     */
    public int deleteZhcpList(Integer id);

    /**
     * 批量删除多条综合测评任务
     * @param ids
     * @return
     */
    public int batchDelZhcpList(String[] ids);

    /**
     * 根据测评项目ID、班级删除综合测评数据
     * @param evaProjectID
     * @param stuClass
     * @return
     */
    public int delZhcpData(String evaProjectID,String stuClass);

    /**
     * 根据测评项目ID、班级删除综合测评复核数据
     * @param evaProjectID
     * @param stuClass
     * @return
     */
    public int delZhcpDataReview(String evaProjectID,String stuClass);

    /**
     * 根据测评项目ID、班级删除综合测评汇总数据
     * @param evaProjectID
     * @param stuClass
     * @return
     */
    public int delZhcpSummary(String evaProjectID,String stuClass);

    /**
     * 查询每个班的测评项目
     * @param releaseClass
     * @return
     */
    public List<String> queryProjectByClass(String releaseClass);



    /**
     * 后台查询本班所有测评数据及根据搜索结果查询
     *
     * @return
     */
    public List<ZhcpData> queryZhcpData(ZhcpData zhcpData);

    /**
     * 班级测评--更新综合测评复核表数据
     * @param zhcpDataReview
     * @return
     */
    public int addZhcpDataReview(ZhcpDataReview zhcpDataReview);

    /**
     * 班级测评--更新综合测评复核表数据
     * @param zhcpSummary
     * @return
     */
    public int addZhcpSummary(ZhcpSummary zhcpSummary);

    /**
     * 根据id删除班委满意度测评测评数据
     * @param id
     * @return
     */
    public int delZhcpDataById(Integer id);

    /**
     * 根据id删除班委满意度测评测评数据
     * @param ids
     * @return
     */
    public int batchDelZhcpDataById(String[] ids);

    /**
     * 根据项目ID、学号删除审核数据
     * @param evaProjectID
     * @param stuNo
     * @return
     */
    public int delZhcpDataReviewByStu(String evaProjectID, String stuNo);

    /**
     * 根据项目ID、学号删除汇总数据
     * @param evaProjectID
     * @param stuNo
     * @return
     */
    public int delZhcpSummaryByStu(String evaProjectID, String stuNo);

    /**
     * 根据项目id、学号更新汇总表数据--更新排名，用于测评项目结束后写入数据库
     * @param zhcpSummary
     * @return
     */
    public int updateSummaryOfSummary(ZhcpSummary zhcpSummary);

    /**
     * 更新综合测评任务表中的终审数据操作人、操作时间
     * @param zhcpList
     * @return
     */
    public int updateZhcpListOfFinal(ZhcpList zhcpList);

    /**
     * 复核时更新学生端提交的信息：复核原因、复核申请说明
     * @param zhcpDataReview
     * @return
     */
    public int upadteZhcpDataReviewOfStu(ZhcpDataReview zhcpDataReview);

    /**
     * 管理员进行复核操作--通过
     * @param zhcpDataReview
     * @return
     */
    public int upadteZhcpDataReviewOfAdminOfAccess(ZhcpDataReview zhcpDataReview);

    /**
     * 管理员进行复核操作——驳回
     * @param zhcpDataReview
     * @return
     */
    public int upadteZhcpDataReviewOfAdminOfReject(ZhcpDataReview zhcpDataReview);
}

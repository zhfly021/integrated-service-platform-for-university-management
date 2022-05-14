package com.zhfly021.mapper;

import com.zhfly021.entity.BwmydcpData;
import com.zhfly021.entity.BwmydcpList;
import com.zhfly021.entity.BwmydcpListAndData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-10 8:06
 */
@Repository
public interface BwmydcpMapper {

    /**
     * 管理员端增加测评项目
     * @param bwmydcpList
     * @return
     */
    public int addBwmydcpList(BwmydcpList bwmydcpList);

    /**
     * 管理员端增加班委满意度测试初始数据，并标记状态为“未完成”
     * @param bwmydcpData
     * @return
     */
    public int updateBwmydcpDataByInit(BwmydcpData bwmydcpData);

    /**
     * 学生端查询班委测评项目
     * @param stuNo
     * @param project
     * @param status
     * @return
     */
//    public List<BwmydcpList> queryByStuClass(BwmydcpList bwmydcpList,String status);
    public List<BwmydcpListAndData> queryByStuClass(String stuNo, String project, String status);

    /**
     * 学生端增加班委满意度测评数据
     * @param bwmydcpData
     * @return
     */
    public int addBwmydcpData(BwmydcpData bwmydcpData);


    /**
     * 根据测评项目ID、学生学号查询该学生本测评项目的填写状态（未完成、已完成）
     * @param projectID
     * @param stuNo
     * @return
     */
    public String queryStatus(String projectID,String stuNo);

    /**
     * 根据测评项目ID、学生学号查询该学生本测评项目
     * @param projectID
     * @param stuNo
     * @return
     */
    public BwmydcpData queryOne(String projectID,String stuNo);


    /**
     * 根据List表中的id查询projectID
     * @param id
     * @return
     */
    public String queryProjectIdByID(Integer id);

    /**
     * 更改测评数据的填写状态为已逾期
     * @param id
     * @return
     */
    public int updateStatusOfYYQ(Integer id);


    /**
     * 管理员端查询班委满意度测评项目列表
     * @param bwmydcpList
     * @return
     */
    public List<BwmydcpList> queryBwmydcpList(BwmydcpList bwmydcpList);

    public int updateListStatusOfYYQ(String projectID);
    public int updateDataStatusOfYYQ(String evaProjectID);


    public BwmydcpList queryBwmydcpListOfOne(Integer id);
    public int updateBwmydcpList(BwmydcpList bwmydcpList);

    /**
     * 删除班委满意度测评列表
     * @param id
     * @return
     */
    public int deleteBwmydcpList(Integer id);

    /**
     * 批量删除班委满意度测评列表
     * @param ids
     * @return
     */
    public int batchDelBwmydcpList(String[] ids);

    /**
     * 根据测评项目ID、班级删除班委满意度测评数据
     * @param evaProjectID
     * @param stuClass
     * @return
     */
    public int delBwmydcpData(String evaProjectID,String stuClass);


    /**
     * 后台查询本班所有测评数据及根据搜索结果查询
     *
     * @return
     */
    public List<BwmydcpData> queryBwmydcpData(BwmydcpData bwmydcpData);

    /**
     * 查询每个班的测评项目
     * @param stuClass
     * @return
     */
    public List<String> queryEvaProjectByStuClass(String stuClass);

    /**
     * 根据id查询学生某项测评项目的数据
     * @param idd
     * @return
     */
    public BwmydcpData queryBwmydcpDataById(Integer idd);
    /**
     * 根据id删除班委满意度测评测评数据
     * @param idd
     * @return
     */
    public int delBwmydcpDataById(Integer idd);

    /**
     * 根据id删除班委满意度测评测评数据
     * @param idds
     * @return
     */
    public int batchDelBwmydcpDataById(String[] idds);


}




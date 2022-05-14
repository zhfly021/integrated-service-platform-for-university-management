package com.zhfly021.service;

import com.zhfly021.entity.BbtxData;
import com.zhfly021.entity.BbtxList;
import com.zhfly021.entity.BbtxListAndData;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-08 22:12
 */
public interface BbtxSevice {


    //    ----------  后台   ---------


    /**
     * 查询报表填写数据信息
     * @return
     */
    public List<BbtxListAndData> queryBbtxData(String stuNo, String project, String status);

    //    ----------  后台   ---------

    // 1. 报表填写任务列表
    /**
     * 管理员端查询综合测评项目列表
     * @param bbtxList
     * @return
     */
    public List<BbtxList> queryBbtxList(BbtxList bbtxList);

    /**
     * 管理员端增加测评项目
     * @param bbtxList
     * @return
     */
    public int addBbtxList(BbtxList bbtxList);

    /**
     * 初始化数据表，并标记状态为“未完成、已逾期”
     * @param bbtxData
     * @return
     */
    public int addBbtxDataByInit(BbtxData bbtxData);

   
    /**
     * 查询一条综合测评任务数据
     * @param id
     * @return
     */
    public BbtxList queryBbtxListById(Integer id);

    /**
     * 根据项目ID、测评人学号查询一条报表填写数据的状态
     * @param evaProjectID
     * @param stuNo
     * @return
     */
    public String queryBbtxDataStatusByProjectIDAndStuNo(String evaProjectID,String stuNo);

    /**
     * 根据项目ID、班级查询报表填写数据
     * @param evaProjectID
     * @return
     */
    public int queryBbtxDataByProjectID(String evaProjectID, String stuClass);

    /**
     * 前台综测测评数据表状态更新
     * @param evaProjectID
     * @return
     */
    public int updateDataStatusOfStu(String evaProjectID, String evaStatus, String stuNo);

    /**
     * 更新一条综合测评任务数据
     * @param bbtxList
     * @return
     */
    public int updateBbtxList(BbtxList bbtxList);

    /**
     * 报表填写数据表项目名称更新
     * @param evaProject
     * @param evaProjectID
     * @param stuClass
     * @return
     */
    public int updateDataProject(String evaProject, String evaProjectID, String stuClass);

    /**
     * 报表填写项目状态更新
     * @param status
     * @param projectID
     * @param releaseClass
     * @return
     */
    public int updateListStatus(String status, String projectID, String releaseClass);

    /**
     * 报表填写数据项目状态更新
     * @param evaStatus
     * @param evaProjectID
     * @param stuClass
     * @return
     */
    public int updateDataStatus(String evaStatus, String evaProjectID, String stuClass);

    /**
     * 根据测评项目ID、班级删除综合测评数据
     * @param evaProjectID
     * @return
     */
    public int delBbtxData(String evaProjectID);

    /**
     * 删除一条综合测评任务
     * @param id
     * @return
     */
    public int deleteBbtxList(Integer id);

    /**
     * 批量删除多条综合测评任务
     * @param ids
     * @return
     */
    public int batchDelBbtxList(String[] ids);

    /**
     * 根据项目id查找文件下载路经以及名称
     * @param id
     * @return
     */
    public BbtxList selectBbtxFile(int id);


    /**
     * 管理员端查询报表填写项目数据列表
     * @param bbtxData
     * @return
     */
    public List<BbtxData> queryBbtxDataOfAdmin(BbtxData bbtxData);

    /**
     * 查询bbxtData表中是否有该学生记录，即判断该报表任务是否为全员任务
     *          如果是全员任务，则执行addBbtxDataOfExist，否则执行addBbtxDataOfNotExist
     * @return
     */
    public int queryBbtxDataIsExist(String stuNo, String evaProjectID);

    /**
     * 学生端添加报表填写数据 ———— bbtxData中 存在初始记录（全员填写项目）
     * @param bbtxData
     * @return
     */
    public int addBbtxDataOfExist(BbtxData bbtxData);

    /**
     * 学生端添加报表填写数据 ———— bbtxData中不存在初始记录（非全员填写项目）
     * @param bbtxData
     * @return
     */
    public int addBbtxDataOfNotExist(BbtxData bbtxData);

    /**
     * 查询一条报表填写数据
     * @param id
     * @return
     */
    public BbtxData queryBbtxDataById(Integer id);


    /**
     * 报表填写数据表项目名称更新
     * @return
     */
    public int checkBbtxDataById(BbtxData bbtxData);

    /**
     * 根据项目id查询一条报表填写任务记录
     * @param projectID
     * @param releaseNo
     * @return
     */
    public BbtxList queryOneBbtxListByProjectID(String projectID, String releaseNo);

    /**
     * 根据项目id查询一条报表填写任务记录
     * @param projectID
     * @param releaseClass
     * @return
     */
    public BbtxList queryOneBbtxListByProjectIDForStu(String projectID, String releaseClass);

    /**
     * 查询一条data
     * @param evaProjectID
     * @param stuNo
     * @return
     */
    public BbtxData queryBbtxDataByProjectIDAndStuNo(String evaProjectID, String stuNo);

    /**
     * 更新data
     * @param bbtxData
     * @return
     */
    public int updateDataOfStu(BbtxData bbtxData);

    /**
     * 删除报表数据
     * @param id
     * @return
     */
    public int deleteBbtxData(Integer id);

    /**
     * 批量删除报表数据
     * @param ids
     * @return
     */
    public int bacthDeleteBbtxData(String[] ids);


    /**
     * 单一删除已上传的文件
     * @param projectID
     * @param fileName
     * @param download
     * @return
     */
    public int editBbtxFile(String projectID, String fileName, String download);

    /**
     * 学生端修改报表文件时的文件删除
     * @param projectID
     * @param upload
     * @param upFilename
     * @return
     */
    public int stuDeleteBbFile(String projectID, String upload, String upFilename, String stuNo);



    /**
     * @Description:
     * @Author: 张豆豆
     * @Date: 2021-03-30 21:33:48
     */
    public BbtxData queryBbtxByStuNo(String stuNo);

    /**
     * 根据项目id获取所有学生本项目id的所有文件
     * @param projectID
     * @return
     */
    public List<BbtxData> selectBbtxFileByProjectId(String projectID);

}

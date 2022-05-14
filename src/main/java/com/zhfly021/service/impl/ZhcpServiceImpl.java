package com.zhfly021.service.impl;

import com.zhfly021.entity.*;
import com.zhfly021.mapper.ZhcpMapper;
import com.zhfly021.service.ZhcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-22 9:02
 */
@Service
public class ZhcpServiceImpl implements ZhcpService {
    @Autowired
    ZhcpMapper zhcpMapper;

    // --------------  前台业务  ----------------
    @Override
    public List<ZhcpListAndData> queryByStu(String stuNo, String project, String status) {
        return zhcpMapper.queryByStu(stuNo, project, status);
    }

    @Override
    public List<ZhcpSummary> querySummaryByStu(ZhcpSummary zhcpSummary) {
        return zhcpMapper.querySummaryByStu(zhcpSummary);
    }

    @Override
    public List<ZhcpSummary> querySummaryByStuClass(String evaProjectID, String stuClass) {
        return zhcpMapper.querySummaryByStuClass(evaProjectID, stuClass);
    }

    @Override
    public ZhcpSummary querySummaryById(Integer id) {
        return zhcpMapper.querySummaryById(id);
    }

    @Override
    public int addZhcpData(ZhcpData zhcpData) {
        return zhcpMapper.addZhcpData(zhcpData);
    }

    @Override
    public int updateZhcpDataReviewByInit(ZhcpDataReview zhcpDataReview) {
        return zhcpMapper.updateZhcpDataReviewByInit(zhcpDataReview);
    }

    @Override
    public int updateDataStatusOfStu(String evaProjectID, String evaStatus, String stuNo) {
        return zhcpMapper.updateDataStatusOfStu(evaProjectID, evaStatus, stuNo);
    }

    @Override
    public int updateDataReviewStatusOfStu(String evaProjectID, String evaStatus, String stuNo) {
        return zhcpMapper.updateDataReviewStatusOfStu(evaProjectID, evaStatus, stuNo);
    }

    @Override
    public int updateSummaryStatusOfStu(String evaProjectID, String evaStatus, String stuNo) {
        return zhcpMapper.updateSummaryStatusOfStu(evaProjectID, evaStatus, stuNo);
    }

    @Override
    public ZhcpData queryZhcpDataById(Integer id) {
        return zhcpMapper.queryZhcpDataById(id);
    }

    @Override
    public ZhcpDataReview queryZhcpDataReviewById(Integer id) {
        return zhcpMapper.queryZhcpDataReviewById(id);
    }


    @Override
    public String queryProjectIdByID(Integer id) {
        return zhcpMapper.queryProjectIdByID(id);
    }

    @Override
    public ZhcpList queryZhcpListByProjectID(String projectID, String releaseClass) {
        return zhcpMapper.queryZhcpListByProjectID(projectID, releaseClass);
    }

    @Override
    public int upadZhcpDataConfirmTime(String confirmTime, String evaProjectID, String stuNo) {
        return zhcpMapper.upadZhcpDataConfirmTime(confirmTime, evaProjectID, stuNo);
    }

    @Override
    public int upadZhcpDataReviewConfirmTime(String confirmTime, String evaProjectID, String stuNo) {
        return zhcpMapper.upadZhcpDataReviewConfirmTime(confirmTime, evaProjectID, stuNo);
    }


    // --------------  后台业务  ----------------
    @Override
    public List<ZhcpList> queryZhcpList(ZhcpList zhcpList) {
        return zhcpMapper.queryZhcpList(zhcpList);
    }

    @Override
    public int addZhcpList(ZhcpList zhcpList) {
        return zhcpMapper.addZhcpList(zhcpList);
    }

    @Override
    public int addZhcpDataByInit(ZhcpData zhcpData) {
        return zhcpMapper.addZhcpDataByInit(zhcpData);
    }

    @Override
    public int addZhcpDataReviewByInit(ZhcpDataReview zhcpDataReview) {
        return zhcpMapper.addZhcpDataReviewByInit(zhcpDataReview);
    }

    @Override
    public int addZhcpSummaryByInit(ZhcpSummary zhcpSummary) {
        return zhcpMapper.addZhcpSummaryByInit(zhcpSummary);
    }

    @Override
    public int updateListStatus(String projectID, String status, String releaseClass) {
        return zhcpMapper.updateListStatus(projectID, status, releaseClass);
    }

    @Override
    public int updateDataStatus(String evaProjectID, String status, String stuClass) {
        return zhcpMapper.updateDataStatus(evaProjectID, status, stuClass);
    }

    @Override
    public int updateDataReviewStatus(String evaProjectID, String status, String stuClass) {
        return zhcpMapper.updateDataReviewStatus(evaProjectID, status, stuClass);
    }

    @Override
    public int updateSummaryStatus(String evaProjectID, String evaStatus, String stuClass) {
        return zhcpMapper.updateSummaryStatus(evaProjectID, evaStatus, stuClass);
    }

    @Override
    public int updateDataProject(String evaProject, String evaProjectID, String stuClass) {
        return zhcpMapper.updateDataProject(evaProject, evaProjectID, stuClass);
    }

    @Override
    public int updateDataReviewProject(String evaProject, String evaProjectID, String stuClass) {
        return zhcpMapper.updateDataReviewProject(evaProject, evaProjectID, stuClass);
    }

    @Override
    public int updateSummaryProject(String evaProject, String evaProjectID, String stuClass) {
        return zhcpMapper.updateSummaryProject(evaProject, evaProjectID, stuClass);
    }

    @Override
    public ZhcpData queryZhcpDataByProjectIDAndStuNo(String evaProjectID, String stuNo) {
        return zhcpMapper.queryZhcpDataByProjectIDAndStuNo(evaProjectID, stuNo);
    }

    @Override
    public ZhcpDataReview queryZhcpDataReviewByProjectIDAndStuNo(String evaProjectID, String stuNo) {
        return zhcpMapper.queryZhcpDataReviewByProjectIDAndStuNo(evaProjectID, stuNo);
    }

    @Override
    public ZhcpSummary queryZhcpSummaryByProjectIDAndStuNo(String evaProjectID, String stuNo) {
        return zhcpMapper.queryZhcpSummaryByProjectIDAndStuNo(evaProjectID, stuNo);
    }

    @Override
    public ZhcpList queryZhcpListById(Integer id) {
        return zhcpMapper.queryZhcpListById(id);
    }

    @Override
    public int updateZhcpList(ZhcpList zhcpList) {
        return zhcpMapper.updateZhcpList(zhcpList);
    }

    @Override
    public int deleteZhcpList(Integer id) {
        return zhcpMapper.deleteZhcpList(id);
    }

    @Override
    public int batchDelZhcpList(String[] ids) {
        return zhcpMapper.batchDelZhcpList(ids);
    }

    @Override
    public int delZhcpData(String evaProjectID, String stuClass) {
        return zhcpMapper.delZhcpData(evaProjectID, stuClass);
    }

    @Override
    public int delZhcpDataReview(String evaProjectID, String stuClass) {
        return zhcpMapper.delZhcpDataReview(evaProjectID, stuClass);
    }

    @Override
    public int delZhcpSummary(String evaProjectID, String stuClass) {
        return zhcpMapper.delZhcpSummary(evaProjectID, stuClass);
    }

    @Override
    public List<String> queryProjectByClass(String releaseClass) {
        return zhcpMapper.queryProjectByClass(releaseClass);
    }

    @Override
    public List<ZhcpData> queryZhcpData(ZhcpData zhcpData) {
        return zhcpMapper.queryZhcpData(zhcpData);
    }

    @Override
    public int addZhcpDataReview(ZhcpDataReview zhcpDataReview) {
        return zhcpMapper.addZhcpDataReview(zhcpDataReview);
    }

    @Override
    public int addZhcpSummary(ZhcpSummary zhcpSummary) {
        return zhcpMapper.addZhcpSummary(zhcpSummary);
    }

    @Override
    public int delZhcpDataById(Integer id) {
        return zhcpMapper.delZhcpDataById(id);
    }

    @Override
    public int batchDelZhcpDataById(String[] ids) {
        return zhcpMapper.batchDelZhcpDataById(ids);
    }

    @Override
    public int delZhcpDataReviewByStu(String evaProjectID, String stuNo) {
        return zhcpMapper.delZhcpDataReviewByStu(evaProjectID, stuNo);
    }

    @Override
    public int delZhcpSummaryByStu(String evaProjectID, String stuNo) {
        return zhcpMapper.delZhcpSummaryByStu(evaProjectID, stuNo);
    }

    @Override
    public int updateSummaryOfSummary(ZhcpSummary zhcpSummary) {
        return zhcpMapper.updateSummaryOfSummary(zhcpSummary);
    }

    @Override
    public int updateZhcpListOfFinal(ZhcpList zhcpList) {
        return zhcpMapper.updateZhcpListOfFinal(zhcpList);
    }

    @Override
    public int upadteZhcpDataReviewOfStu(ZhcpDataReview zhcpDataReview) {
        return zhcpMapper.upadteZhcpDataReviewOfStu(zhcpDataReview);
    }

    @Override
    public int upadteZhcpDataReviewOfAdminOfAccess(ZhcpDataReview zhcpDataReview) {
        return zhcpMapper.upadteZhcpDataReviewOfAdminOfAccess(zhcpDataReview);
    }

    @Override
    public int upadteZhcpDataReviewOfAdminOfReject(ZhcpDataReview zhcpDataReview) {
        return zhcpMapper.upadteZhcpDataReviewOfAdminOfReject(zhcpDataReview);
    }
}

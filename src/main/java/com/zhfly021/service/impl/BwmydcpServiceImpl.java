package com.zhfly021.service.impl;

import com.zhfly021.entity.BwmydcpData;
import com.zhfly021.entity.BwmydcpList;
import com.zhfly021.entity.BwmydcpListAndData;
import com.zhfly021.mapper.BwmydcpMapper;
import com.zhfly021.service.BwmydcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-10 8:07
 */
@Service
public class BwmydcpServiceImpl implements BwmydcpService {

    @Autowired
    BwmydcpMapper bwmydcpMapper;

    @Override
    public int addBwmydcpList(BwmydcpList bwmydcpList) {
        return bwmydcpMapper.addBwmydcpList(bwmydcpList);
    }


    @Override
    public int updateBwmydcpDataByInit(BwmydcpData bwmydcpData) {
        return bwmydcpMapper.updateBwmydcpDataByInit(bwmydcpData);
    }

    @Override
    public List<BwmydcpListAndData> queryByStuClass(String stuNo, String project, String status) {
        return bwmydcpMapper.queryByStuClass(stuNo,project,status);
    }

    @Override
    public String queryProjectIdByID(Integer id) {
        return bwmydcpMapper.queryProjectIdByID(id);
    }

/*    @Override
    public List<BwmydcpList> queryByStuClass(BwmydcpList bwmydcpList, String status) {
        return bwmydcpMapper.queryByStuClass(bwmydcpList, status);
    }*/

    @Override
    public int addBwmydcpData(BwmydcpData bwmydcpData) {
        return bwmydcpMapper.addBwmydcpData(bwmydcpData);
    }

    @Override
    public String queryStatus(String projectID, String stuNo) {
        return bwmydcpMapper.queryStatus(projectID, stuNo);
    }

    @Override
    public BwmydcpData queryOne(String projectID, String stuNo) {
        return bwmydcpMapper.queryOne(projectID, stuNo);
    }

    @Override
    public int updateStatusOfYYQ(Integer id) {
        return bwmydcpMapper.updateStatusOfYYQ(id);
    }

    @Override
    public List<BwmydcpList> queryBwmydcpList(BwmydcpList bwmydcpList) {
        return bwmydcpMapper.queryBwmydcpList(bwmydcpList);
    }

    @Override
    public int updateListStatusOfYYQ(String projectID) {
        return bwmydcpMapper.updateListStatusOfYYQ(projectID);
    }

    @Override
    public int updateDataStatusOfYYQ(String evaProjectID) {
        return bwmydcpMapper.updateDataStatusOfYYQ(evaProjectID);
    }

    @Override
    public BwmydcpList queryBwmydcpListOfOne(Integer id) {
        return bwmydcpMapper.queryBwmydcpListOfOne(id);
    }

    @Override
    public int updateBwmydcpList(BwmydcpList bwmydcpList) {
        return bwmydcpMapper.updateBwmydcpList(bwmydcpList);
    }

    @Override
    public int deleteBwmydcpList(Integer id) {
        return bwmydcpMapper.deleteBwmydcpList(id);
    }

    @Override
    public int batchDelBwmydcpList(String[] ids) {
        return bwmydcpMapper.batchDelBwmydcpList(ids);
    }

    @Override
    public int delBwmydcpData(String evaProjectID, String stuClass) {
        return bwmydcpMapper.delBwmydcpData(evaProjectID, stuClass);
    }

    @Override
    public List<BwmydcpData> queryBwmydcpData(BwmydcpData bwmydcpData) {
        return bwmydcpMapper.queryBwmydcpData(bwmydcpData);
    }

    @Override
    public List<String> queryEvaProjectByStuClass(String stuClass) {
        return bwmydcpMapper.queryEvaProjectByStuClass(stuClass);
    }

    @Override
    public BwmydcpData queryBwmydcpDataById(Integer idd) {
        return bwmydcpMapper.queryBwmydcpDataById(idd);
    }

    @Override
    public int delBwmydcpDataById(Integer idd) {
        return bwmydcpMapper.delBwmydcpDataById(idd);
    }

    @Override
    public int batchDelBwmydcpDataById(String[] idds) {
        return bwmydcpMapper.batchDelBwmydcpDataById(idds);
    }
}

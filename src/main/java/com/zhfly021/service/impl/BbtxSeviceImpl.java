package com.zhfly021.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.zhfly021.entity.BbtxData;
import com.zhfly021.entity.BbtxList;
import com.zhfly021.entity.BbtxListAndData;
import com.zhfly021.mapper.BbtxMapper;
import com.zhfly021.mapper.ZhcpMapper;
import com.zhfly021.service.BbtxSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-08 22:13
 */
@Service
public class BbtxSeviceImpl implements BbtxSevice {

    @Autowired
    BbtxMapper bbtxMapper;


    @Override
    public List<BbtxListAndData> queryBbtxData(String stuNo, String project, String status) {
        return bbtxMapper.queryBbtxData(stuNo, project, status);
    }

    @Override
    public List<BbtxList> queryBbtxList(BbtxList bbtxList) {
        return bbtxMapper.queryBbtxList(bbtxList);
    }

    @Override
    public int addBbtxList(BbtxList bbtxList) {
        return bbtxMapper.addBbtxList(bbtxList);
    }

    @Override
    public int addBbtxDataByInit(BbtxData bbtxData) {
        return bbtxMapper.addBbtxDataByInit(bbtxData);
    }

    @Override
    public BbtxList queryBbtxListById(Integer id) {
        return bbtxMapper.queryBbtxListById(id);
    }

    @Override
    public String queryBbtxDataStatusByProjectIDAndStuNo(String evaProjectID, String stuNo) {
        return bbtxMapper.queryBbtxDataStatusByProjectIDAndStuNo(evaProjectID, stuNo);
    }

    @Override
    public int queryBbtxDataByProjectID(String evaProjectID, String stuClass) {
        return bbtxMapper.queryBbtxDataByProjectID(evaProjectID, stuClass);
    }

    @Override
    public int updateDataStatusOfStu(String evaProjectID, String evaStatus, String stuNo) {
        return bbtxMapper.updateDataStatusOfStu(evaProjectID, evaStatus, stuNo);
    }

    @Override
    public int updateBbtxList(BbtxList bbtxList) {
        return bbtxMapper.updateBbtxList(bbtxList);
    }

    @Override
    public int updateDataProject(String evaProject, String evaProjectID, String stuClass) {
        return bbtxMapper.updateDataProject(evaProject, evaProjectID, stuClass);
    }

    @Override
    public int updateListStatus(String status, String projectID, String releaseClass) {
        return bbtxMapper.updateListStatus(status, projectID, releaseClass);
    }

    @Override
    public int updateDataStatus(String evaStatus, String evaProjectID, String stuClass) {
        return bbtxMapper.updateDataStatus(evaStatus, evaProjectID, stuClass);
    }


    @Override
    public int delBbtxData(String evaProjectID) {
        return bbtxMapper.delBbtxData(evaProjectID);
    }

    @Override
    public int deleteBbtxList(Integer id) {
        return bbtxMapper.deleteBbtxList(id);
    }

    @Override
    public int batchDelBbtxList(String[] ids) {
        return bbtxMapper.batchDelBbtxList(ids);
    }

    @Override
    public BbtxList selectBbtxFile(int id) {
        return bbtxMapper.selectBbtxFile(id);
    }

    @Override
    public List<BbtxData> queryBbtxDataOfAdmin(BbtxData bbtxData) {
        return bbtxMapper.queryBbtxDataOfAdmin(bbtxData);
    }

    @Override
    public int queryBbtxDataIsExist(String stuNo, String evaProjectID) {
        return bbtxMapper.queryBbtxDataIsExist(stuNo, evaProjectID);
    }

    @Override
    public int addBbtxDataOfExist(BbtxData bbtxData) {
        return bbtxMapper.addBbtxDataOfExist(bbtxData);
    }

    @Override
    public int addBbtxDataOfNotExist(BbtxData bbtxData) {
        return bbtxMapper.addBbtxDataOfNotExist(bbtxData);
    }

    @Override
    public BbtxData queryBbtxDataById(Integer id) {
        return bbtxMapper.queryBbtxDataById(id);
    }

    @Override
    public int checkBbtxDataById(BbtxData bbtxData) {
        return bbtxMapper.checkBbtxDataById(bbtxData);
    }

    @Override
    public BbtxList queryOneBbtxListByProjectID(String projectID, String releaseNo) {
        return bbtxMapper.queryOneBbtxListByProjectID(projectID, releaseNo);
    }

    @Override
    public BbtxList queryOneBbtxListByProjectIDForStu(String projectID, String releaseClass) {
        return bbtxMapper.queryOneBbtxListByProjectIDForStu(projectID, releaseClass);
    }

    @Override
    public BbtxData queryBbtxDataByProjectIDAndStuNo(String evaProjectID, String stuNo) {
        return bbtxMapper.queryBbtxDataByProjectIDAndStuNo(evaProjectID, stuNo);
    }

    @Override
    public int updateDataOfStu(BbtxData bbtxData) {
        return bbtxMapper.updateDataOfStu(bbtxData);
    }

    @Override
    public int deleteBbtxData(Integer id) {
        return bbtxMapper.deleteBbtxData(id);
    }

    @Override
    public int bacthDeleteBbtxData(String[] ids) {
        return bbtxMapper.bacthDeleteBbtxData(ids);
    }

    @Override
    public int editBbtxFile(String projectID, String fileName, String download) {
        return bbtxMapper.editBbtxFile(projectID, fileName, download);
    }

    @Override
    public int stuDeleteBbFile(String projectID, String upload, String upFilename, String stuNo) {
        return bbtxMapper.stuDeleteBbFile(projectID, upload, upFilename, stuNo);
    }

    @Override
    public BbtxData queryBbtxByStuNo(String stuNo) {
        return bbtxMapper.queryBbtxByStuNo(stuNo);
    }

    @Override
    public List<BbtxData> selectBbtxFileByProjectId(String projectID) {
        return bbtxMapper.selectBbtxFileByProjectId(projectID);
    }
}

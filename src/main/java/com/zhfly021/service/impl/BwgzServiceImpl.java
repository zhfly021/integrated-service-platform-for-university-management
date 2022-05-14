package com.zhfly021.service.impl;

import com.zhfly021.entity.Bwgz;
import com.zhfly021.mapper.BwgzMapper;
import com.zhfly021.service.BwgzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-09 11:30
 */
@Service
public class BwgzServiceImpl implements BwgzService {

    @Autowired
    BwgzMapper bwgzMapper;

    @Override
    public List<Bwgz> queryBystuNo(Bwgz bwgz) {
        return bwgzMapper.queryBystuNo(bwgz);
    }

    @Override
    public int addBwgz(Bwgz bwgz) {
        return bwgzMapper.addBwgz(bwgz);
    }

    @Override
    public int deleteBwgz(Integer id) {
        return bwgzMapper.deleteBwgz(id);
    }

    @Override
    public int batchDeleteBwgz(String[] ids) {
        return bwgzMapper.batchDeleteBwgz(ids);
    }

    @Override
    public Bwgz queryById(Integer id) {
        return bwgzMapper.queryById(id);
    }

    @Override
    public int updateBwgz(Bwgz bwgz) {
        return bwgzMapper.updateBwgz(bwgz);
    }

    @Override
    public int evaluateBwgz(Bwgz bwgz) {
        return bwgzMapper.evaluateBwgz(bwgz);
    }

    @Override
    public List<Bwgz> queryBwgzfk(Bwgz bwgz) {
        return bwgzMapper.queryBwgzfk(bwgz);
    }

    @Override
    public int responseBwgzfk(Bwgz bwgz) {
        return bwgzMapper.responseBwgzfk(bwgz);
    }
}

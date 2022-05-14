package com.zhfly021.service.impl;

import com.zhfly021.entity.Bjjs;
import com.zhfly021.mapper.BjjsMapper;
import com.zhfly021.service.BjjsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-07 22:53
 */
@Service
public class BjjsServiceImpl implements BjjsService {
    @Autowired
    BjjsMapper bjjsMapper;

    @Override
    public List<Bjjs> queryBystuNo(Bjjs bjjs) {
        return bjjsMapper.queryBystuNo(bjjs);
    }

    @Override
    public int addBjjs(Bjjs bjjs) {
        return bjjsMapper.addBjjs(bjjs);
    }

    @Override
    public int deleteBjjs(Integer id) {
        return bjjsMapper.deleteBjjs(id);
    }

    @Override
    public int batchDeleteBjjs(String[] ids) {
        return bjjsMapper.batchDeleteBjjs(ids);
    }

    @Override
    public Bjjs queryById(Integer id) {
        return bjjsMapper.queryById(id);
    }

    @Override
    public int updateBjjs(Bjjs bjjs) {
        return bjjsMapper.updateBjjs(bjjs);
    }

    @Override
    public int evaluateBjjs(Bjjs bjjs) {
        return bjjsMapper.evaluateBjjs(bjjs);
    }

    @Override
    public List<Bjjs> queryBjjs(Bjjs bjjs) {
        return bjjsMapper.queryBjjs(bjjs);
    }

    @Override
    public int responseBjjs(Bjjs bjjs) {
        return bjjsMapper.responseBjjs(bjjs);
    }

}

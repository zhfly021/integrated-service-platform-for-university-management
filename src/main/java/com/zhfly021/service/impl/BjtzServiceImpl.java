package com.zhfly021.service.impl;

import com.zhfly021.entity.Bjtz;
import com.zhfly021.mapper.BjtzMapper;
import com.zhfly021.service.BjtzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-03 13:12
 */
@Service
public class BjtzServiceImpl implements BjtzService {
    @Autowired
    BjtzMapper bjtzMapper;


    @Override
    public List<Bjtz> query() {
        return bjtzMapper.query();
    }

    @Override
    public List<Bjtz> queryByClass(String stuClass) {
        return bjtzMapper.queryByClass(stuClass);
    }

    @Override
    public int addBjtz(Bjtz bjtz) {
        return bjtzMapper.addBjtz(bjtz);
    }

    @Override
    public List<Bjtz> queryBjtz(Bjtz bjtz) {
        return bjtzMapper.queryBjtz(bjtz);
    }

    @Override
    public Bjtz queryBjtzById(Integer id) {
        return bjtzMapper.queryBjtzById(id);
    }

    @Override
    public int updateBjtzById(Bjtz bjtz) {
        return bjtzMapper.updateBjtzById(bjtz);
    }

    @Override
    public int deleteBjtz(Integer id) {
        return bjtzMapper.deleteBjtz(id);
    }

    @Override
    public int batchDeleteBjtz(String[] ids) {
        return bjtzMapper.batchDeleteBjtz(ids);
    }
}

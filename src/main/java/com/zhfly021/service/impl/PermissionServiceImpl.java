package com.zhfly021.service.impl;

import com.zhfly021.mapper.PermissionMapper;
import com.zhfly021.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-26 17:54
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<String> queryResource(String[] ids) {
        return permissionMapper.queryResource(ids);
    }
}

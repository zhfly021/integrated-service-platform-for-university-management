package com.zhfly021.mapper;


import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-26 17:41
 */
@Repository
public interface PermissionMapper {

    /**
     * 根据id查询资源
     * @param ids
     * @return
     */
    public List<String> queryResource(String[] ids);

}

package com.zhfly021.utils;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author : 动感小⑦
 * @DATE : 2019/8/19 0019 9:10
 */
public class PageUtil {

    /**
     * 分页
     *
     * @param content  当前页数据
     * @param pageNo   当前页码
     * @param pageSize 分页尺寸
     * @param total    数据总条数
     * @return
     */
    public static <T> PageImpl<T> page(List<T> content, int pageNo, int pageSize, Integer total) {
        return new PageImpl<T>(content, PageRequest.of(pageNo - 1, pageSize), total);
    }

    /**
     * 对list进行分页，注意传过来的content一定要是排序好的（desc）
     *
     * @param content
     * @param pageNo
     * @param pageSize
     * @param total
     * @return
     */
    public static <T> HashMap PageByList(List<T> content, int pageNo, int pageSize, Integer total) {
        List<T> ContentList = new ArrayList<T>();
        Integer StartIndex = (pageNo - 1) * pageSize;
        Integer EndIndex = pageNo * pageSize - 1;
        //如果list里的数量小于一页的数量 那么就把列表数量-1赋给endindex
        if (content.size() <= EndIndex) {
            EndIndex = content.size() - 1;
        }
        for (Integer i = StartIndex; i <= EndIndex; i++) {
            ContentList.add(content.get(i));
        }
        HashMap<String, Object> pageContent = new HashMap<String, Object>();
        pageContent.put("content", ContentList);
        pageContent.put("page", pageNo);
        pageContent.put("limit", pageSize);
        pageContent.put("total", total);
        return pageContent;
    }
}
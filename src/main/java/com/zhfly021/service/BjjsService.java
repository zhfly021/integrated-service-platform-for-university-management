package com.zhfly021.service;

import com.zhfly021.entity.Bjjs;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-07 22:52
 */
public interface BjjsService {
    /**
     * 查询班级建设所有意见
     * @return
     */
    public List<Bjjs> queryBystuNo(Bjjs bjjs);

    /**
     * 增加班级建设意见
     * @param bjjs
     * @return
     */
    public int addBjjs(Bjjs bjjs);

    /**
     * 删除班级建设意见
     * @param id
     * @return
     */
    public int deleteBjjs(Integer id);

    /**
     * 批量删除班级建设意见
     * @param ids
     * @return
     */
    public int batchDeleteBjjs(String[] ids);

    /**
     * 查询班级建设意见
     * @param id
     * @return
     */
    public Bjjs queryById(Integer id);

    /**
     * 修改班级建设意见
     * @param bjjs
     * @return
     */
    public int updateBjjs(Bjjs bjjs);

    /**
     * 班级建设意见回复--学生端评价
     * @param bjjs
     * @return
     */
    public int evaluateBjjs(Bjjs bjjs);

    /**
     * 根据班级查询
     * @param bjjs
     * @return
     */
    public List<Bjjs> queryBjjs(Bjjs bjjs);

    /**
     * 管理员回复班级建设意见
     * @param bjjs
     * @return
     */
    public int responseBjjs(Bjjs bjjs);
}

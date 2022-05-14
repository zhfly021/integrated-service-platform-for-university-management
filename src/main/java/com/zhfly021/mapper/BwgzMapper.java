package com.zhfly021.mapper;

import com.zhfly021.entity.Bwgz;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-09 11:23
 */
@Repository
public interface BwgzMapper {
    /**
     * 查询所有班委满意度反馈
     * @return
     */
    public List<Bwgz> queryBystuNo(Bwgz bwgz);

    /**
     * 增加班委工作反馈
     * @param bwgz
     * @return
     */
    public int addBwgz(Bwgz bwgz);

    /**
     * 删除班委工作反馈
     * @param id
     * @return
     */
    public int deleteBwgz(Integer id);

    /**
     * 批量删除班委工作反馈
     * @param ids
     * @return
     */
    public int batchDeleteBwgz(String[] ids);

    /**
     * 查询班委工作反馈
     * @param id
     * @return
     */
    public Bwgz queryById(Integer id);

    /**
     * 修改班委工作反馈
     * @param bwgz
     * @return
     */
    public int updateBwgz(Bwgz bwgz);


    /**
     * 班会工作反馈回复--学生端评价
     * @param bwgz
     * @return
     */
    public int evaluateBwgz(Bwgz bwgz);


    /**
     * 后台查询所有班委满意度反馈
     * @return
     */
    public List<Bwgz> queryBwgzfk(Bwgz bwgz);

    /**
     * 后台回复班委工作反馈
     * @param bwgz
     * @return
     */
    public int responseBwgzfk(Bwgz bwgz);
}

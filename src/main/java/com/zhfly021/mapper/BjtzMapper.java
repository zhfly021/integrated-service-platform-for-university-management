package com.zhfly021.mapper;

import com.zhfly021.entity.Bjtz;
import com.zhfly021.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-03 13:09
 */
@Repository
public interface BjtzMapper {
    public List<Bjtz> query();

    /**
     * 根据登录学生的班级、联合发布人的班级查询班级通知信息
     * @param stuClass
     * @return
     */
    public List<Bjtz> queryByClass(String stuClass);


    /**
     * 后台发布班级通知
     * @param bjtz
     * @return
     */
    public int addBjtz(Bjtz bjtz);

    /**
     * 后台查询班级通知
     * @param bjtz
     * @return
     */
    public List<Bjtz> queryBjtz(Bjtz bjtz);

    /**
     * 根据id查询一条班级通知信息
     * @param id
     * @return
     */
    public Bjtz queryBjtzById(Integer id);

    /**
     * 修改班级通知
     * @param bjtz
     * @return
     */
    public int updateBjtzById(Bjtz bjtz);

    /**
     * 删除班级通知
     * @param id
     * @return
     */
    public int deleteBjtz(Integer id);

    /**
     * 批量删除班级通知
     * @param ids
     * @return
     */
    public int batchDeleteBjtz(String[] ids);

}

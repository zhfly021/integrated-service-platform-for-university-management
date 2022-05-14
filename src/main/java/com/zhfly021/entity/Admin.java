package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-14 9:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private Integer id;
    private String username;
    private String password;
    private String employer;    // 工作或学习单位
    private String faculty;    // 工作或学习单位
    private Integer roleId;     // 角色ID


    private String roleCategory;     // 角色名-用于封装数据

}

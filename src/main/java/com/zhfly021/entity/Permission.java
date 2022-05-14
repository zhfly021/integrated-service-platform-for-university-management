package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-26 17:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private Integer id;
    private String perName;
    private String resource;
}

package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-26 17:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Integer id;
    private String roleName;
    private String sn;
    private String permissionId; // 资源id
    private String description;

    private String[] permissionIds;

    /*private String permissionId1;
    private String permissionId2;
    private String permissionId3;
    private String permissionId4;
    private String permissionId5;
    private String permissionId6;
    private String permissionId7;
    private String permissionId8;
    private String permissionId9;
    private String permissionId10;
    private String permissionId12;
    private String permissionId13;
    private String permissionId14;
    private String permissionId15;
    private String permissionId16;
    private String permissionId17;
    private String permissionId18;
    private String permissionId19;
    private String permissionId20;
    private String permissionId21;
    private String permissionId22;
    private String permissionId23;*/

}

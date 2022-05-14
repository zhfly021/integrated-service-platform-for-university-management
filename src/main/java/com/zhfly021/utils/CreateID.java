package com.zhfly021.utils;

import java.util.Date;
import java.util.UUID;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-03-19 21:37
 */
public class CreateID {
//    时间戳 + 用户id + 业务含义编码。
    public static String create() {
        Date date = new Date();
        String projectID;
        String stamp = String.valueOf(date.getTime());
        int uuid = UUID.randomUUID().toString().hashCode();
        if (uuid < 0) {
            uuid = -uuid;
        }
        String uid = Integer.toString(uuid);
        projectID = "NO.10" + stamp.substring(stamp.length() - 6) + uid.substring(uid.length() - 4);
        return projectID;
    }
}

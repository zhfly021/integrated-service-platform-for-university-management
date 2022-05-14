package com.zhfly021.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-12-03 13:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bjtz {
    private int id;
    private Date releaseTime;
    private String releaseTimestamp;
    private String stamp1;
    private String stamp2;
    private String publisherNo;
    private String publisher;
    private String publisherCommittee;
    private String publisherClass;
    private String content;
    private String images;

    @Transient
    private String[] imageList;
//    private String receiver;
//    private String remarks;

}

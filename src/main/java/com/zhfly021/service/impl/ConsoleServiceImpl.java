package com.zhfly021.service.impl;

import com.zhfly021.entity.BwgzCount;
import com.zhfly021.mapper.ConsoleMapper;
import com.zhfly021.service.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-04-04 21:29
 */
@Service
public class ConsoleServiceImpl implements ConsoleService {

    @Autowired
    ConsoleMapper consoleMapper;

    @Override
    public int countZhcpOfStatus(String status, String faculty, String stuClass) {
        return consoleMapper.countZhcpOfStatus(status, faculty, stuClass);
    }

    @Override
    public int countBbtxDataOfStatus(String status, String faculty, String stuClass) {
        return consoleMapper.countBbtxDataOfStatus(status, faculty, stuClass);
    }

    @Override
    public int countBjtz(String faculty, String stuClass) {
        return consoleMapper.countBjtz(faculty, stuClass);
    }

    @Override
    public int countBjjsOfStatus(String status, String faculty, String stuClass) {
        return consoleMapper.countBjjsOfStatus(status, faculty, stuClass);
    }

    @Override
    public int countBwgzOfStatus(String status, String faculty, String stuClass) {
        return consoleMapper.countBwgzOfStatus(status, faculty, stuClass);
    }


    @Override
    public int countBwmydcpDataOfStatus(String status, String faculty, String stuClass) {
        return consoleMapper.countBwmydcpDataOfStatus(status, faculty, stuClass);
    }

    @Override
    public int countResumeOfStatus(String status, String faculty, String stuClass) {
        return consoleMapper.countResumeOfStatus(status, faculty, stuClass);
    }

    @Override
    public int countEvidenceOfStatus(String status, String faculty, String stuClass) {
        return consoleMapper.countEvidenceOfStatus(status, faculty, stuClass);
    }

    @Override
    public int countBjjsOfDean(String faculty, String sendClass, String time) {
        return consoleMapper.countBjjsOfDean(faculty, sendClass, time);
    }

    @Override
    public int countBwgzOfDean(String faculty, String sendClass, String time) {
        return consoleMapper.countBwgzOfDean(faculty, sendClass, time);
    }

    @Override
    public List<BwgzCount> bwgzFeedBack(String sendClass) {
        return consoleMapper.bwgzFeedBack(sendClass);
    }
}

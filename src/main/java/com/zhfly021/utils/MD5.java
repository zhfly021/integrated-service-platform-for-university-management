package com.zhfly021.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Sun
 * @version 1.0
 * @date 2020/11/14 12:13
 */
//对数据库密码进行加密
public class MD5 {
    /**利用MD5进行加密**/
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newStr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }

    /**判断用户密码是否正确
     *newpasswd  用户输入的密码  表单提交的密码
     *oldpasswd  正确密码  数据库中的密码
     */
    public static boolean checkPassword(String newPassword,String oldPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        if(EncoderByMd5(newPassword).equals(oldPassword)){
            return true;
        }
        else{
            return false;
        }
    }
}

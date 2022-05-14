package com.zhfly021.utils.File;


import org.springframework.context.annotation.Configuration;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.*;

/**
 * @author： wzz
 * @date： 2021-03-26 19:55
 */
@Configuration
public class CopyFileUtil {

    public static void copyFile(File source,String dest,String fileName)throws IOException{

        //创建目的地文件夹
        File destfile = new File(dest);
        if(!destfile.exists()){
            destfile.mkdir();
        }
        //如果source是文件夹，则在目的地址中创建新的文件夹
        if(source.isDirectory()){
            File file = new File(dest+"\\"+source.getName());//用目的地址加上source的文件夹名称，创建新的文件夹
            file.mkdir();
            //得到source文件夹的所有文件及目录
            File[] files = source.listFiles();
            if(files.length==0){
                return;
            }else{
                for(int i = 0 ;i<files.length;i++){
                    copyFile(files[i],file.getPath(),source.getName());   //使用文件的原名
                }
            }

        }
        //source是文件，则用字节输入输出流复制文件
        else if(source.isFile()){
            FileInputStream fis = new FileInputStream(source);
            //创建新的文件，保存复制内容，文件名称与源文件名称一致
//            File dfile = new File(dest+"\\"+source.getName());
            File dfile = new File(dest+"\\"+fileName);
            if(!dfile.exists()){
                dfile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(dfile);
            // 读写数据
            // 定义数组
            byte[] b = new byte[1024];
            // 定义长度
            int len;
            // 循环读取
            while ((len = fis.read(b))!=-1) {
                // 写出数据
                fos.write(b, 0 , len);
            }
            //关闭资源
            fos.close();
            fis.close();
        }
    }

}

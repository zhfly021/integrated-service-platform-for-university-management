package com.zhfly021.utils.File;

import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author： wzz
 * 将字符串分割成map或者数组
 * @date： 2021-03-24 22:10
 */

public class SplitStringUtil {

    /**
     * 将字符串分割为map集合
     * @param paths
     * @param names
     * @param mark
     * @return
     */
    public Map<String,Object> split(String paths, String names, String mark){
        List<String> pathsList = new ArrayList<>();
        List<String> namesList = new ArrayList<>();

        if (paths != null && paths != mark){
            pathsList = Arrays.asList(paths.split(","));
            namesList = Arrays.asList(names.split(","));
        }

        String path = "";
        String name = "";
        Map<String, Object> map = new HashMap<>();

        for (int i=0; i<pathsList.size(); i++){
            path =  pathsList.get(i);
            name = namesList.get(i);
//            map.put(name,path);
            map.put(namesList.get(i),pathsList.get(i));
        }
        return map;

    }

    /**
     * 将字符串分割为集合，并组合为FileBean类型
     * @param paths
     * @param names
     * @param mark
     * @return
     */
    public String splitZip(String paths, String names, String mark, String zipName, String zipSname) throws IOException {

//        String TrialUploadPath = System.getProperty("user.dir") + "/static/zip/";
//        String visionUploadPath = System.getProperty("user.dir") + "/static";

        String TrialUploadPath = "static/zip/";
        String visionUploadPath = "static";

        List<String> pathsList = new ArrayList<>();
        List<String> namesList = new ArrayList<>();

        if (paths != null && paths != mark){
            pathsList = Arrays.asList(paths.split(","));
            namesList = Arrays.asList(names.split(","));
        }

        String path = "";
        String name = "";

        for (int i=0; i<pathsList.size(); i++){
            //复制文件的位置
            path =   TrialUploadPath+zipName +'/'+zipSname;
            //源文件路径
            name = visionUploadPath + pathsList.get(i);
            File source = new File(name);
            CopyFileUtil.copyFile(source,path,namesList.get(i));

        }

        return TrialUploadPath+zipName;

    }


    /**
     * 将字符串分割为集合，并组合为FileBean类型
     * @param paths
     * @param names
     * @param mark
     * @return
     */
    public String adminSplitZip(String paths, String names, String mark, String zipName) throws IOException {


        String TrialUploadPath = "static/zip/";
        String visionUploadPath = "static";

        List<String> pathsList = new ArrayList<>();
        List<String> namesList = new ArrayList<>();

        if (paths != null && paths != mark){
            pathsList = Arrays.asList(paths.split(","));
            namesList = Arrays.asList(names.split(","));
        }

        String path = "";
        String name = "";

        for (int i=0; i<pathsList.size(); i++){
            //复制文件的位置
            path =   TrialUploadPath+zipName ;
            //源文件路径
            name = visionUploadPath + pathsList.get(i);
            File source = new File(name);

            CopyFileUtil.copyFile(source,path,namesList.get(i));

        }

        return TrialUploadPath+zipName;

    }

    /**
     * 迭代删除文件夹
     * @param dirPath 文件夹路径
     */
    public static void deleteDir(String dirPath)
    {
        File file = new File(dirPath);
        if(file.isFile())
        {
            file.delete();
        }else
        {
            File[] files = file.listFiles();
            if(files == null)
            {
                file.delete();
            }else
            {
                for (int i = 0; i < files.length; i++)
                {
                    deleteDir(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }
    }

}

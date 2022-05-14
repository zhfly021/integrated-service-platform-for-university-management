package com.zhfly021.handler;

import com.zhfly021.utils.File.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class TransactionHandler {
    /**
     * 导入
     *
     * @param file
     * @return
     */
    public List importExcel(MultipartFile file, String excelHeader) {
        List list=null;
        try {
            InputStream inputStream = file.getInputStream();
//            log.info("============fileName=============="+file.getOriginalFilename());
            list = ExcelUtils.readExcel(inputStream, file.getOriginalFilename());
            inputStream.close();
            //判断列名是否对的上
            if (list.get(0).toString().equals(excelHeader)){
                for (int i = 0; i < list.size(); i++) {
                    Object object = list.get(i);
//                    System.out.println(object);
                }
            }else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}


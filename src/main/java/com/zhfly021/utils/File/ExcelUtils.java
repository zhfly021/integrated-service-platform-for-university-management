package com.zhfly021.utils.File;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Slf4j
public class ExcelUtils {


    /**
     * 处理上传的文件
     *
     * @param in
     * @param fileName
     * @return
     * @throws Exception
     */
    public static List readExcel(InputStream in, String fileName) throws Exception {
        List list = new ArrayList<>();
        //创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Row row = null;
        Cell cell = null;

        Sheet sheet = work.getSheetAt(0);


        // 获取第一个实际行的下标
//        log.info("======sheet.getFirstRowNum()：获取第一个实际行的下标========="+sheet.getFirstRowNum());
        // 获取最后一个实际行的下标
//        log.info("======sheet.getLastRowNum()：获取最后一个实际行的下标========="+sheet.getLastRowNum());

        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);

            // 获取在某行第一个单元格的下标
//            log.info("======row.getFirstCellNum()：获取在某行第一个单元格的下标========="+row.getFirstCellNum());
            // 获取在某行的列数
//            log.info("======row.getLastCellNum()：获取在某行的列数========="+row.getLastCellNum());

            if (row == null || row.getFirstCellNum() == i) {
                continue;
            }
            List<Object> li = new ArrayList<>();
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                li.add(cell);
            }
            list.add(li);
        }


        work.close();//这行报错的话  看下导入jar包的版本
        return list;
    }

    /**
     * 判断文件格式
     *
     * @param inStr
     * @param fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        log.info("==========fileType========"+fileType);
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inStr);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("请上传excel文件！");
        }
        return workbook;
    }

}

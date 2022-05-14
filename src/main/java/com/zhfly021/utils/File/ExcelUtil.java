package com.zhfly021.utils.File;

import com.alibaba.druid.util.StringUtils;
import com.zhfly021.entity.Student;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author： wzz
 * @date： 2021-04-01 17:06
 */

public class ExcelUtil {


    //是否符合2003Excel
    public static boolean isExcel2003(String filePath){
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //是否符合2007Excel
    public static boolean isExcel2007(String filePath){
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public static List<Student> readExcel(MultipartFile file){
        Workbook workbook = null;
        List<Student> list = new ArrayList<>();
        if (ExcelUtil.isExcel2007(file.getOriginalFilename())){
            try {
                workbook = new XSSFWorkbook(file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (ExcelUtil.isExcel2003(file.getOriginalFilename())){
            try {
                workbook = new HSSFWorkbook(file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Sheet sheet = workbook.getSheetAt(0);
//        从第一个数据开始读
        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++){
            Row row = sheet.getRow(i);

            Cell uniCell = row.getCell(0);
            uniCell.setCellType(1);

            //获取学号
            String stuNo = uniCell.getStringCellValue();
            //获取姓名
            String stuName = getCellValue(row.getCell(1));
            //获取性别
            String sex = getCellValue(row.getCell(2));
            //获取院系
            String faculty = getCellValue(row.getCell(3));
            //获取级数
            String series = getCellValue(row.getCell(4));
            // 获取专业名称
            String profession = getCellValue(row.getCell(5));
            //获取班级
            String stuClass = getCellValue(row.getCell(6));
            // 获取身份证件号
            String stuID = getCellValue(row.getCell(7));

            Student student = new Student(stuNo,stuName,sex,faculty,series,profession,stuClass,stuID);

            list.add(student);

        }

        //最后要关闭流
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void writeExcel(){
        HSSFWorkbook workbook = new HSSFWorkbook();
        DocumentSummaryInformation info = workbook.getDocumentSummaryInformation();
        info.setCategory("育人贡献奖投票统计");
        HSSFSheet sheet = workbook.createSheet("投票统计");
        HSSFRow header = sheet.createRow(0);
        HSSFCell cell0 = header.createCell(0);
        HSSFCell cell1 = header.createCell(1);
        HSSFCell cell2 = header.createCell(2);
        HSSFCell cell3 = header.createCell(3);
        HSSFCell cell4 = header.createCell(4);
        cell0.setCellValue("工号");
        cell1.setCellValue("姓名");
        cell2.setCellValue("学院");
        cell3.setCellValue("学生投票数");
        cell4.setCellValue("教师投票数");
    }

    /**
     * 拿到不同类型单元格中的值
     * 1. 字符串: 字符串
     * 2. 布尔: toString
     * 3. 数值(double): 格式化后的字符串
     * @param cell 获取的单元格
     * @return 单元格中的值
     */
    private static String getCellValue(Cell cell) {
        String resultValue = "";
        // 判空
        if (Objects.isNull(cell)) {
            return resultValue;
        }

        // 拿到单元格类型
        int cellType = cell.getCellType();
        switch (cellType) {
            // 字符串类型
            case Cell.CELL_TYPE_STRING:
                resultValue = StringUtils.isEmpty(cell.getStringCellValue()) ? "" : cell.getStringCellValue().trim();
                break;
            // 布尔类型
            case Cell.CELL_TYPE_BOOLEAN:
                resultValue = String.valueOf(cell.getBooleanCellValue());
                break;
            // 数值类型
            case Cell.CELL_TYPE_NUMERIC:
                resultValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                break;
            // 取空串
            default:
                break;
        }
        return resultValue;
    }

}

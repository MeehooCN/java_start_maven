package com.meehoo.biz.common.io;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author zc
 * @date 2019-04-29
 */
public class ExcelUtil {

    public static HSSFWorkbook getHSSFWorkbook(String description,String sheetName,String []title,String [][]values){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
//        if(wb == null){
        HSSFWorkbook wb = new HSSFWorkbook();
//        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        HSSFRow des = sheet.createRow(0);
        HSSFCell cell1 = des.createCell(0);
        cell1.setCellValue(description);
        CellRangeAddress region = new CellRangeAddress(0,0,0,title.length);
        sheet.addMergedRegion(region);


        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(1);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFDataFormat format= wb.createDataFormat();
        style.setDataFormat(format.getFormat("yyyy-MM-dd"));

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
//            cell.setCellStyle(style);
        }


        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 2);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                HSSFCell c = row.createCell(j);
//                if (j==9){
//                    c.setCellStyle(style);
//                }
                c.setCellValue(values[i][j]);
            }
        }

//        for (int i = 0; i < title.length; i++) {
//            sheet.autoSizeColumn(i);
//            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
//        }
        return wb;
    }

    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
//        if(wb == null){
            HSSFWorkbook wb = new HSSFWorkbook();
//        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
//        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
//            cell.setCellStyle(style);
        }


        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                HSSFCell c = row.createCell(j);
                c.setCellValue(values[i][j]);
//                c.setCellStyle(style);
            }
        }

//        for (int i = 0; i < title.length; i++) {
//            sheet.autoSizeColumn(i);
//            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
//        }
        return wb;
    }



    /**
     * 自定义表头
     * @param sheetName
     * @param headers 表头
     * @param title
     * @param values
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String[][] headers,String []title,String [][]values){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
//        if(wb == null){
        HSSFWorkbook wb = new HSSFWorkbook();
//        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
//        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        int r = 0;
        //创建表头
        for(int i=0;i<headers.length;i++){
            row = sheet.createRow(r++);
            for(int j=0;j<headers[i].length;j++){
                //将内容按顺序赋给对应的列对象
                HSSFCell c = row.createCell(j);
                c.setCellValue(headers[i][j]);
//                c.setCellStyle(style);
            }
        }

        //创建标题
        row = sheet.createRow(r++);
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
//            cell.setCellStyle(style);
        }


        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + r);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                HSSFCell c = row.createCell(j);
                c.setCellValue(values[i][j]);
//                c.setCellStyle(style);
            }
        }

//        for (int i = 0; i < title.length; i++) {
//            sheet.autoSizeColumn(i);
//            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
//        }
        return wb;
    }
}

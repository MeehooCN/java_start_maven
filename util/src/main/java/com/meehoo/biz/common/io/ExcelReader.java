package com.meehoo.biz.common.io;

import com.meehoo.biz.common.exception.ExcelPatternException;
import com.meehoo.biz.common.util.BigDecimalUtil;
import com.meehoo.biz.common.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * poi 引用
 * Created by Wanghan on 2015/12/31.
 */
public class ExcelReader {
    private POIFSFileSystem fs;
    private Workbook wb;
    private Sheet sheet;
    private Row row;

    public List<String[]> readExcelContent(InputStream inputStream,String fileName, String sheetName) throws Exception{
        if (fileName.endsWith(".xls")){
            return readXlsExcelContent(inputStream,sheetName);
        }else if (fileName.endsWith(".xlsx")){
            return readXlsxExcelContent(inputStream,sheetName);
        }else{
            throw new ExcelPatternException();
        }
    }

    /**
     * 读取Excel表格表头的内容
     * @param is
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
        try {
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        //System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            //title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getCellFormatValue(row.getCell((short) i));
        }
        return title;
    }

    /**
     * 读取Excel数据内容
     * @param is
     * @return Map 包含单元格数据内容的Map对象
     */
    public List<String[]> readExcelContent(InputStream is) {
        return readExcelContent(is,0);
    }

    /**
     * 读取Excel数据内容
     * @param is
     * @param sheetName
     * @return Map 包含单元格数据内容的Map对象
     */
    public List<String[]> readXlsxExcelContent(InputStream is, String sheetName) {
        try {
            // fs = new POIFSFileSystem(is);
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int sheetIndex = wb.getSheetIndex(sheetName);
        if (sheetIndex==-1){
            sheetIndex=0;
        }
        sheet = wb.getSheetAt(sheetIndex);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(1);
        //int colNum = row.getPhysicalNumberOfCells();
        int colNum = row.getLastCellNum();

        return read(rowNum,colNum);
    }

    public List<String[]> readXlsExcelContent(InputStream is, String sheetName) {
        try {
            wb = new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int sheetIndex = wb.getSheetIndex(sheetName);
        if (sheetIndex==-1){
            sheetIndex=0;
        }
        sheet = wb.getSheetAt(sheetIndex);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(1);
        //int colNum = row.getPhysicalNumberOfCells();
        int colNum = row.getLastCellNum();

        return read(rowNum, colNum);
    }

    private List<String[]> read( int rowNum, int colNum) {
        List<String[]> returnList = new ArrayList<>(rowNum);
        // 从第二行开始
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            String[] strs = new String[colNum];
            while (j < colNum) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";

                //str += getCellFormatValue(row.getCell((short) j)).trim() + "  ,  ";
                strs[j] = getCellFormatValue(row.getCell((short) j)).trim();
                j++;
            }
            //content.put(i, str);
            returnList.add(strs);
//            str = "";
        }
        return returnList;
    }


    /**
     * 读取Excel数据内容
     * @param is
     * @param index
     * @return Map 包含单元格数据内容的Map对象
     */
    public List<String[]> readExcelContent(InputStream is, int index) {
        try {
            // fs = new POIFSFileSystem(is);
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sheet = wb.getSheetAt(index);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(2);
        //int colNum = row.getPhysicalNumberOfCells();
        int colNum = row.getLastCellNum();
        // 从第二行开始
        return read(rowNum, colNum);
    }

    /**
     * 读取Excel数据内容
     * @param is
     * @return Map 包含单元格数据内容的Map对象
     */
    public int getExcelNumberOfSheet(InputStream is) {
        try {
            // fs = new POIFSFileSystem(is);
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb.getNumberOfSheets();
    }

    /**
     * 读取Excel的sheet名称
     * @param is
     * @param index
     * @return Map 包含单元格数据内容的Map对象
     */
    public String getExcelNameOfSheet(InputStream is, int index) {
        try {
            // fs = new POIFSFileSystem(is);
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb.getSheetName(index);
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(XSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case XSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     *
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(XSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == XSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == XSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == XSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据XSSFCell类型设置数据
     * @param cell
     * @return
     */
    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case XSSFCell.CELL_TYPE_NUMERIC:
                case XSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        //cellvalue = cell.getDateCellValue().toLocaleString();

                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        cellvalue = DateUtil.dateToString(date);

                    }
                    // 如果是纯数字
                    else {
                        //避免科学计算   保留两位小数
                        cellvalue = String.valueOf(BigDecimalUtil.round(cell.getNumericCellValue(),2));
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case XSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    public static void main(String[] args) {
        try {
            // 对读取Excel表格标题测试
            InputStream is = new FileInputStream("d:\\1.xlsx");
            ExcelReader excelReader = new ExcelReader();
            String[] title = excelReader.readExcelTitle(is);
            System.out.println("获得Excel表格的标题:");
            for (String s : title) {
                System.out.print(s + " ");
            }

            // 对读取Excel表格内容测试
            InputStream is2 = new FileInputStream("d:\\1.xlsx");

            //Map<Integer, String> map = excelReader.readExcelContent(is2);
            System.out.println("获得Excel表格的内容:");
//            for (int i = 1; i <= map.size(); i++) {
//                System.out.println(map.get(i));
//            }
            List<String[]> list = excelReader.readExcelContent(is2);
            for(int i = 0; i < list.size(); i++){
                String[] strs = list.get(i);
                for(String s : strs){
                    System.out.print(s);
                }
                System.out.println(" ");
            }
            System.out.println(list.size());
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
    }
}

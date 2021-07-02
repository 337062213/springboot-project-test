 package com.springboot.test.util.excel;

 import org.apache.poi.hssf.usermodel.HSSFCell;
 import org.apache.poi.hssf.usermodel.HSSFFont;
 import org.apache.poi.hssf.usermodel.HSSFRow;
 import org.apache.poi.hssf.usermodel.HSSFSheet;
 import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
 import org.apache.poi.ss.usermodel.*;
 import org.apache.poi.ss.util.CellRangeAddress;
 import org.apache.poi.xssf.usermodel.XSSFFont;
 import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 import java.io.*;
 import java.lang.reflect.Field;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.logging.Level;
 import java.util.logging.Logger;

 public class ExcelUtilX {

     /**
      * @description  生成Excel 2007+
      */
     public static byte[] exportX(String sheetName,String title,String[] headers) {
         XSSFWorkbook wb = new XSSFWorkbook();
         Sheet sheet = wb.createSheet("0");
         for (int i = 0; i < 9; i++) {
             sheet.setColumnWidth(i, 4300);
         }

         /**
          *  @description 单元格样式
          */
         CellStyle cellStyle = wb.createCellStyle();
         cellStyle = ExcelUtil.setBorder(cellStyle, BorderStyle.THIN);
         cellStyle = ExcelUtil.setBorderColor(cellStyle, HSSFColorPredefined.BLACK.getIndex());
         cellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
         cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 上下居中

         /**
          *  @description 标题样式
          */
         XSSFFont titleFont = wb.createFont();
         titleFont.setFontHeight(24);
         titleFont.setBold(true);
         CellStyle titleCellStyle = wb.createCellStyle();
         titleCellStyle = ExcelUtil.setBorder(titleCellStyle, BorderStyle.THIN);
         titleCellStyle = ExcelUtil.setBorderColor(titleCellStyle, HSSFColorPredefined.BLACK.getIndex());
         titleCellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
         titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 上下居中
         titleCellStyle.setFont(titleFont);

         /**
          * @description  主标题 
          */
         Row titleRow;
         Cell titleCell;
         sheet.addMergedRegion(new CellRangeAddress((short) 0, (short) 2, (short) 0, (short) 8));
         for (int i = 0; i <= 2; i++) {
             titleRow = sheet.createRow(i);
             for (int j = 0; j < 9; j++) {
                 titleCell = titleRow.createCell(j);
                 titleCell.setCellType(CellType.STRING);
                 titleCell.setCellStyle(titleCellStyle);
                 titleCell.setCellValue(title);
             }
         }

         /**
          * @description 列 标题 
          */
         Row rowLabel;
         Cell cellLabel;
         for (int i = 3; i < 4; i++) {
             rowLabel = sheet.createRow(i);
             for (int j = 0; j < 9; j++) {
                 cellLabel = rowLabel.createCell(j);
                 cellLabel.setCellType(CellType.STRING);
                 cellLabel.setCellStyle(cellStyle);
                 cellLabel.setCellValue(headers[j]);
             }
         }

         /**
          * @description 列数据 
          */
         Row rowCheck;
         Cell cellCheck;
         for (int i = 3; i < 2000; i++) {
             rowCheck = sheet.createRow((i + 1));
             for (int j = 0; j < 9; j++) {
                 cellCheck = rowCheck.createCell(j);
                 cellCheck.setCellType(CellType.STRING);
                 cellCheck.setCellStyle(cellStyle);
                 cellCheck.setCellValue("测试 - 0" + (i - 2));
             }
         }

         /**
          * @description  页脚
          */
         ExcelUtil.setExcelSheetName(sheetName, 0, wb);
         byte result[] = null;
         ByteArrayOutputStream out = null;
         out = new ByteArrayOutputStream();
         try {
            result =  out.toByteArray();
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
         /**
          *  @description 进行导出
          */
         ExcelUtil.exportOutPutExcel(wb);
         return result;
     }
     
     /**
      * @description  生成Excel 2003-
      */
     public static byte[] export(String sheetName, String title,String[] headers, List<Object> list) {

         HSSFWorkbook wb = new HSSFWorkbook();//创建excel表
         HSSFSheet sheet = wb.createSheet(sheetName);
         sheet.setDefaultColumnWidth(20);//设置默认行宽

         //设置表头样式（加粗，水平居中，垂直居中）
         CellStyle cellStyleHeader = wb.createCellStyle();
         cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);//水平居中
         cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
         //设置表头边框样式
         cellStyleHeader = ExcelUtil.setBorder(cellStyleHeader, BorderStyle.THIN);
         cellStyleHeader = ExcelUtil.setBorderColor(cellStyleHeader, HSSFColorPredefined.BLACK.getIndex());
         //设置表头字体样式
         HSSFFont fontStyle = wb.createFont();
         fontStyle.setFontHeightInPoints((short)12);
         fontStyle.setBold(true);
         cellStyleHeader.setFont(fontStyle);

         //设置标题样式（加粗，垂直居中）
         CellStyle cellStyleTitle = wb.createCellStyle();
         cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);
         cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
         HSSFFont titleFont = wb.createFont();
         titleFont.setFontHeightInPoints((short)16);
         titleFont.setBold(true);
         cellStyleTitle.setFont(titleFont);
         //设置标题边框样式
         cellStyleTitle = ExcelUtil.setBorder(cellStyleTitle, BorderStyle.THIN);
         cellStyleTitle = ExcelUtil.setBorderColor(cellStyleHeader, HSSFColorPredefined.BLACK.getIndex());

         //设置字段样式（垂直居中）
         CellStyle cellStyle3 = wb.createCellStyle();
         cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
         //设置字段边框样式
         cellStyle3 = ExcelUtil.setBorder(cellStyle3, BorderStyle.THIN);
         cellStyle3 = ExcelUtil.setBorderColor(cellStyle3, HSSFColorPredefined.BLACK.getIndex());

         //创建表头
         HSSFRow row = sheet.createRow(0);
         row.setHeightInPoints(20);//行高
         
         HSSFCell cell = row.createCell(0);
         cell.setCellValue(title);
         cell.setCellStyle(cellStyleTitle);

         sheet.addMergedRegion(new CellRangeAddress(0,0,0,(headers.length-1)));
         
         //创建标题
         HSSFRow rowTitle = sheet.createRow(1);
         rowTitle.setHeightInPoints(20);

         HSSFCell hc;
         for (int i = 0; i < headers.length; i++) {
             hc = rowTitle.createCell(i);
             hc.setCellValue(headers[i]);
             hc.setCellStyle(cellStyleHeader);
         }
         byte result[] = null;
         ByteArrayOutputStream out = null;         
         try {
             //创建表格数据
             Field[] fields;
             int i = 2;
             for (Object obj : list) {
                 fields = obj.getClass().getDeclaredFields();
                 HSSFRow rowBody = sheet.createRow(i);
                 rowBody.setHeightInPoints(20);
                 int j = 0;
                 for (Field f : fields) {
                     f.setAccessible(true);
                     Object va = f.get(obj);
                     if (null == va) {
                         va = "";
                     }
                     hc = rowBody.createCell(j);
                     hc.setCellValue(va.toString());
                     hc.setCellStyle(cellStyle3);                    
                     j++;
                 }
                 i++;
             }
             out = new ByteArrayOutputStream();
             wb.write(out);
             result =  out.toByteArray();
             ExcelUtil.exportOutPutExcel(wb);
         } catch (Exception ex) {
             Logger.getLogger(ExcelUtil.class.getName()).log(Level.SEVERE, null, ex);
         } finally{
             try {
                 if(null != out){
                     out.close();
                 }
             } catch (IOException ex) {
                 Logger.getLogger(ExcelUtil.class.getName()).log(Level.SEVERE, null, ex);
             } finally{
                 try {
                     wb.close();
                 } catch (IOException ex) {
                     Logger.getLogger(ExcelUtil.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         }
         
         return result;
     }
     
     /**
      *  @description main test
      */
     public static void main(String[] args) {
         String  sheetName = "test";
         String  title = "2018年度能源科技进步奖";
         String[]  headers = {"测试标题列1","测试标题列2","测试标题列3","测试标题列4","测试标题列5","测试标题列6","测试标题列7","测试标题列8","测试标题列9"};
         List<Object> list= new ArrayList<>();
         export(sheetName,title,headers, list);
         exportX(sheetName,title,headers);
     }

 }

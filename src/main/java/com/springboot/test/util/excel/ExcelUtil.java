 package com.springboot.test.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    /**
     * @description  设置Excel cell border
     */
    public static CellStyle setBorder(CellStyle cellStyle, BorderStyle borderStyle) {
        cellStyle.setBorderTop(borderStyle);
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBorderLeft(borderStyle);
        cellStyle.setBorderRight(borderStyle);
        return cellStyle;
    }
    
    /**
     * @description  设置Excel border color
     */
    public static CellStyle setBorderColor(CellStyle cellStyle, short color) {
        cellStyle.setTopBorderColor(color);
        cellStyle.setBottomBorderColor(color);
        cellStyle.setLeftBorderColor(color);
        cellStyle.setRightBorderColor(color);
        return cellStyle;
    }
    
    /**
     * @description  设置Excel sheet name
     */
    public static void setExcelSheetName(String customExcelFooterName, int setExcelFooterNumber, Workbook wb) {
        wb.setSheetName(setExcelFooterNumber, customExcelFooterName);
    }
    
    /**
     *  @description 导出Excel到桌面
     */
    public static void exportOutPutExcel(Workbook wb) {
        String exportPositionPath = "C:\\Users\\EDZ\\Desktop\\test\\excel\\ExportExcel-xls.xls";
        try {
            if(wb instanceof HSSFWorkbook) {
                exportPositionPath = "C:\\Users\\EDZ\\Desktop\\test\\excel\\ExportExcel-xls.xls";
            }else if(wb instanceof XSSFWorkbook) {
                exportPositionPath = "C:\\Users\\EDZ\\Desktop\\test\\excel\\ExportExcel-xlsx.xlsx";
            }
            File file = new File(exportPositionPath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
 }

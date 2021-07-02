 package com.springboot.test.util.pdf;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

 /**
  * Created on 2017/8/15
  * Author: Joe.
  */
 public class A14Object {
     public static String split = "<brb>";
     public static void main(String[] args) {
         Document document = new Document(PageSize.A4, 48, 48, 60, 65);

         try {
             PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\EDZ\\Desktop\\test\\pdf\\可以重复合并行A14Object.pdf"));
             document.open();
             // 设置字体
             BaseFont bfCN = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
             // 正文的字体
             Font headFont = new Font(bfCN, 12f, Font.BOLD, BaseColor.ORANGE);
             Font textFont = new Font(bfCN, 12f, Font.NORMAL, BaseColor.BLUE);
             
             String json = "[{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"f374ee1923c3e2e3b157fb10e6004e23\",\"changesort\":\"2021/4/23 17:03:34.003\",\"id\":\"da827b04a6b331529f0947fe1f166fcc\",\"ordersort\":\"2021/4/23 17:03:42.001\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容一\",\"changebasis\":\"eng_mark-变更令-006\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"f374ee1923c3e2e3b157fb10e6004e23\",\"changesort\":\"2021/4/23 17:03:34.003\",\"id\":\"098c02857d8e3134827c7341549f3fff\",\"ordersort\":\"2021/4/23 17:04:16.285\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容一\",\"changebasis\":\"eng_mark-变更令-005\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"f374ee1923c3e2e3b157fb10e6004e23\",\"changesort\":\"2021/4/23 17:03:34.003\",\"id\":\"22e65c966bd29467901ef8e7f07c0a13\",\"ordersort\":\"2021/4/23 17:04:16.288\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容一\",\"changebasis\":\"eng_mark-变更令-002\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"f374ee1923c3e2e3b157fb10e6004e23\",\"changesort\":\"2021/4/23 17:03:34.003\",\"id\":\"9256fceb9a2c0ffaae00d48ea52a2bde\",\"ordersort\":\"2021/4/23 17:04:16.289\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容一\",\"changebasis\":\"eng_mark-变更令-004\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"f374ee1923c3e2e3b157fb10e6004e23\",\"changesort\":\"2021/4/23 17:03:34.003\",\"id\":\"d8de86a4924dac9f952825cd5ff14308\",\"ordersort\":\"2021/4/23 17:04:16.292\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容一\",\"changebasis\":\"eng_mark-变更令-001\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"f374ee1923c3e2e3b157fb10e6004e23\",\"changesort\":\"2021/4/23 17:03:34.003\",\"id\":\"f162a58976c8a8c88b79cb320f79ba5b\",\"ordersort\":\"2021/4/23 17:04:16.293\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容一\",\"changebasis\":\"eng_mark-变更令-003\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"709260e54436d2aa8d0a995b0f9ef975\",\"changesort\":\"2021/4/23 17:03:44.011\",\"id\":\"d779fec1f23c4119bcd15e5384dbb20f\",\"ordersort\":\"2021/4/23 17:04:47.753\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容二\",\"changebasis\":\"test\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"a4e24a24de1f42e3bdfa80aa80781c16\",\"changesort\":\"2021/4/23 17:05:27.535\",\"id\":\"f62a5680ec87c35fa9749122ef3d4c9b\",\"ordersort\":\"2021/4/23 17:05:55.412\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-005\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"a4e24a24de1f42e3bdfa80aa80781c16\",\"changesort\":\"2021/4/23 17:05:27.535\",\"id\":\"e31bbad423d5447e8b259624baef9650\",\"ordersort\":\"2021/4/23 17:05:55.415\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-002\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"a4e24a24de1f42e3bdfa80aa80781c16\",\"changesort\":\"2021/4/23 17:05:27.535\",\"id\":\"141bb51542ec7cfcb054f65084e17339\",\"ordersort\":\"2021/4/23 17:05:55.417\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-004\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"a4e24a24de1f42e3bdfa80aa80781c16\",\"changesort\":\"2021/4/23 17:05:27.535\",\"id\":\"0079f0de2c47eeca91b7ccda3efdb3aa\",\"ordersort\":\"2021/4/23 17:05:55.417\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-001\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"a4e24a24de1f42e3bdfa80aa80781c16\",\"changesort\":\"2021/4/23 17:05:27.535\",\"id\":\"35d45ca3f348f28db98983aaf9fd4950\",\"ordersort\":\"2021/4/23 17:05:55.418\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-006\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"a4e24a24de1f42e3bdfa80aa80781c16\",\"changesort\":\"2021/4/23 17:05:27.535\",\"id\":\"f413f89e8ec238b39e43919d5508a861\",\"ordersort\":\"2021/4/23 17:05:55.419\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-003\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"1912a872a6574c03ac974daea826b5b5\",\"pageno\":1,\"changegroupid\":\"5f3001b0ec7641a8a08d36fc7072e693\",\"changesort\":\"2021/4/23 17:05:38.846\",\"id\":\"9abbd650ba3f42d883805e4fab6c40c3\",\"ordersort\":\"2021/4/23 17:06:21.549\",\"sgdrawingno\":\"图表一\",\"jgdrawingno\":\"图表一\",\"drawingname\":\"图表一\",\"changecontent\":\"testName\",\"changebasis\":\"案卷PDF.pdf\",\"pagenumber\":1,\"comment\":\"图表一\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"888b9dc0389a7d8896f11ff54ff6f1f2\",\"changesort\":\"2021/4/23 17:06:33.188\",\"id\":\"74ad2449db42499bb92cd847d792f70f\",\"ordersort\":\"2021/4/23 17:07:50.602\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容一\",\"changebasis\":\"一套房和规范化股份.pdf\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"3c2d805b8ecf12a18c6d0e630cc604b6\",\"changesort\":\"2021/4/23 17:06:38.262\",\"id\":\"3bff2f3bc5cd4bfeb4820ccfd3d46057\",\"ordersort\":\"2021/4/23 17:08:10.247\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容二\",\"changebasis\":\"案卷PDF.pdf\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"e3ea34667dd828b48639e982f8184360\",\"changesort\":\"2021/4/23 17:06:42.489\",\"id\":\"1937473a0ca46e53b22a3396ac72ace9\",\"ordersort\":\"2021/4/23 17:08:17.139\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-005\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"e3ea34667dd828b48639e982f8184360\",\"changesort\":\"2021/4/23 17:06:42.489\",\"id\":\"dd95ea929f413ea49dee9e93e2fca71b\",\"ordersort\":\"2021/4/23 17:08:17.143\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-002\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"e3ea34667dd828b48639e982f8184360\",\"changesort\":\"2021/4/23 17:06:42.489\",\"id\":\"06308419a380307b864ae2b50bca26d6\",\"ordersort\":\"2021/4/23 17:08:17.144\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-004\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"e3ea34667dd828b48639e982f8184360\",\"changesort\":\"2021/4/23 17:06:42.489\",\"id\":\"6893a1b6e61a9494a5d1104104b0d3b7\",\"ordersort\":\"2021/4/23 17:08:17.145\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-001\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"e3ea34667dd828b48639e982f8184360\",\"changesort\":\"2021/4/23 17:06:42.489\",\"id\":\"92e961706552dd9c938e77a7be5b34ec\",\"ordersort\":\"2021/4/23 17:08:17.147\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-006\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"e3ea34667dd828b48639e982f8184360\",\"changesort\":\"2021/4/23 17:06:42.489\",\"id\":\"b75fa24ecdf94eaf985b5c5d1b3b8cf4\",\"ordersort\":\"2021/4/23 17:08:17.148\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容三\",\"changebasis\":\"eng_mark-变更令-003\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"ddf2fad8023983aa8b5070e41d45fdd8\",\"changesort\":\"2021/4/23 17:06:48.742\",\"id\":\"f85b483d515c11119953432bcaf26035\",\"ordersort\":\"2021/4/23 17:08:24.001\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容四\",\"changebasis\":\"eng_mark-变更令-005\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"ddf2fad8023983aa8b5070e41d45fdd8\",\"changesort\":\"2021/4/23 17:06:48.742\",\"id\":\"a59603939cfc318bacabee1d1b2aea7b\",\"ordersort\":\"2021/4/23 17:08:24.005\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容四\",\"changebasis\":\"eng_mark-变更令-002\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"ddf2fad8023983aa8b5070e41d45fdd8\",\"changesort\":\"2021/4/23 17:06:48.742\",\"id\":\"90a34b5d89512e25b16c1977c8464ef1\",\"ordersort\":\"2021/4/23 17:08:24.006\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容四\",\"changebasis\":\"eng_mark-变更令-004\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"ddf2fad8023983aa8b5070e41d45fdd8\",\"changesort\":\"2021/4/23 17:06:48.742\",\"id\":\"85024e81462c63d99cbac3e211c86b3c\",\"ordersort\":\"2021/4/23 17:08:24.007\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容四\",\"changebasis\":\"eng_mark-变更令-006\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"ddf2fad8023983aa8b5070e41d45fdd8\",\"changesort\":\"2021/4/23 17:06:48.742\",\"id\":\"b488541d814ebb829ac96e55611a1f33\",\"ordersort\":\"2021/4/23 17:08:24.007\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容四\",\"changebasis\":\"eng_mark-变更令-001\",\"pagenumber\":6,\"comment\":\"图表二\"},{\"pagegroupid\":\"0880d4fa2f164300a0999bffd47f633b\",\"pageno\":6,\"changegroupid\":\"ddf2fad8023983aa8b5070e41d45fdd8\",\"changesort\":\"2021/4/23 17:06:48.742\",\"id\":\"e30dedd8d3f63dffb806159325ce0500\",\"ordersort\":\"2021/4/23 17:08:24.008\",\"sgdrawingno\":\"图表二\",\"jgdrawingno\":\"图表二\",\"drawingname\":\"图表二\",\"changecontent\":\"修改内容四\",\"changebasis\":\"eng_mark-变更令-003\",\"pagenumber\":6,\"comment\":\"图表二\"}]";
//             json = "[]";
             JSONArray jsonArray = com.alibaba.fastjson.JSON.parseArray(json);
             JSONObject jsonObject = new JSONObject();
             if(!jsonArray.isEmpty()) {
                 jsonObject = (JSONObject)jsonArray.get(0);
             }{
                 jsonObject = (JSONObject)jsonArray.get(0);
             }
             Set<String> columns = jsonObject.keySet();
             String[] columnsKey = columns.toArray(new String[columns.size()]);;
             List<String> columnsList = new ArrayList<>();
             columnsList.addAll(columns);             

             //添加测试数据
             List<String[]> list = new ArrayList<>();
             for (int i = 0; i < jsonArray.size(); i++) {
                 JSONObject Object = (JSONObject)jsonArray.get(i);
                 String[] arr = new String[columnsList.size()];
                 for(int j = 0; j < columnsList.size(); j++) {
                     arr[j] = Object.getString(columnsList.get(j));
                 }
                 list.add(arr);
             }

             //复制要合并的列
             List<String> ListIn = new ArrayList<>();
             for (String[] aList : list) {
                 ListIn.add(aList[0]);
             }
             //根据算法改变合并列的值
             List<String> changeList = changeList(ListIn);

             //改变该列后把它复制到原来的list
             for (int i = 0; i < changeList.size(); i++) {
                 String[] arr = list.get(i);
                 arr[1] = changeList.get(i);
                 list.set(i, arr);
             }

             document.newPage();
             //建立一个4列的表格
             PdfPTable table = new PdfPTable(columnsList.size());
             String[] titleArr = columnsKey;
             //加表格头部
             addTitle(table, titleArr, headFont);
             //加表格内容
             addContent(table, list, textFont);
             document.add(table);
             document.close();
         } catch (DocumentException | IOException e){
             e.printStackTrace();
         }
     }

     private static void addContent(PdfPTable table, List<String[]> list, Font textFont) {
         //表格数据内容
         for (String[] str : list) {
             for (int j = 0; j < str.length; j++) {
                 String value = str[j];
                 if (value != null) {
                     Paragraph paragraph01;
                     int spanNum = 1;
                     if (j == 1 && value.contains(split)) {
                         spanNum = Integer.parseInt(value.split(split)[1]);
                         paragraph01 = new Paragraph(value.split(split)[0], textFont);
                     } else {
                         paragraph01 = new Paragraph(value, textFont);
                     }
                     paragraph01.setAlignment(1);
                     PdfPCell cell = new PdfPCell();
                     cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                     cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);//然并卵
                     cell.addElement(paragraph01);
                     cell.setRowspan(spanNum);
                     cell.setFixedHeight(60f);
                     table.addCell(cell);
                 }
             }
         }
     }

     private static void addTitle(PdfPTable table, String[] titleArr, Font headFont) {
         for (String aTitleArr : titleArr) {
             Paragraph p = new Paragraph(aTitleArr, headFont);
             PdfPCell cell = new PdfPCell();
             p.setAlignment(1);
             cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
             cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);//然并卵
             cell.setPaddingTop(-2f);//把字垂直居中
             cell.setPaddingBottom(8f);//把字垂直居中
             cell.addElement(p);
             table.addCell(cell);
         }
     }

     /**
      * @see 改变合并列的值
      * @param drugList 内容支持 'A,A,B,C,D,D,D,E' 支持 'A,A,B,C,A,D,D,D,E'
      * @return
      */
     private static List<String> changeList(List<String> drugList) {
         List<String> drugListCopy = new ArrayList<String>();
         drugListCopy.addAll(drugList);

         int nullNum = 0;
         for (int i = 0; i < drugList.size(); i++) {
             if (i > 0) {
                 if (drugList.get(i).equals(drugList.get(i - 1))) {
                     drugListCopy.set(i, null);
                     nullNum++;
                 } else {
                     if (nullNum > 0) {
                         int start = i - nullNum - 1;
                         drugListCopy.set(start, drugList.get(start) + split + (nullNum + 1));
                         nullNum = 0;
                     }
                 }

                 // 处理某一列值都相同的情况
                 if (nullNum > 0) {
                     int start = i - nullNum;
                     drugListCopy.set(start, drugList.get(start) + split + (nullNum + 1));
                 }
             }
         }
         return drugListCopy;
     }
 }
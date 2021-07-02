 package com.springboot.test.model.consistant;

import java.util.HashMap;
import java.util.Map;

public class DocumentConst {
     
     public static Map<String,String> DOCUMENT_TABLE_HEADER = new HashMap<>();
     
     public static Map<String,String> DOCUMENT_TABLE_HEADER_UNDEFINED = new HashMap<>();
     
     static {
         DOCUMENT_TABLE_HEADER.put("1", "序号");
         DOCUMENT_TABLE_HEADER.put("2", "设计图号");
         DOCUMENT_TABLE_HEADER.put("3", "施工图号");
         DOCUMENT_TABLE_HEADER.put("4", "竣工图好");
         DOCUMENT_TABLE_HEADER.put("5", "图表名称");
         DOCUMENT_TABLE_HEADER.put("6", "图表类型");
         DOCUMENT_TABLE_HEADER.put("7", "图表页数");
         DOCUMENT_TABLE_HEADER.put("8", "修改内容");
         DOCUMENT_TABLE_HEADER.put("9", "变更依据");
         DOCUMENT_TABLE_HEADER.put("10", "备注");
         DOCUMENT_TABLE_HEADER.put("11", "项目名称");
         DOCUMENT_TABLE_HEADER.put("12", "合同段");
         DOCUMENT_TABLE_HEADER.put("13", "施工单位");
         DOCUMENT_TABLE_HEADER.put("14", "设计单位");
         DOCUMENT_TABLE_HEADER.put("15", "勘察单位");
         DOCUMENT_TABLE_HEADER.put("16", "监理单位");
         
         DOCUMENT_TABLE_HEADER_UNDEFINED.put("1", "第一列");
         DOCUMENT_TABLE_HEADER_UNDEFINED.put("2", "第二列");
         DOCUMENT_TABLE_HEADER_UNDEFINED.put("3", "第三列");
         DOCUMENT_TABLE_HEADER_UNDEFINED.put("4", "第四列");
         DOCUMENT_TABLE_HEADER_UNDEFINED.put("5", "第五列");
         DOCUMENT_TABLE_HEADER_UNDEFINED.put("6", "第六列");
         DOCUMENT_TABLE_HEADER_UNDEFINED.put("7", "第七列");
         DOCUMENT_TABLE_HEADER_UNDEFINED.put("8", "第八列");
     }

}

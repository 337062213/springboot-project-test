package com.springboot.test.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import net.sf.json.JSONObject;

public class Test {
     
     public static void main(String[] args) throws Exception{  
         URL serverUrl = new URL("http://open.iciba.com/dsapi/");  
         HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();  
         conn.setRequestMethod("GET");  
         conn.setRequestProperty("Content-type", "application/json");  
         //必须设置false，否则会自动redirect到重定向后的地址  
         conn.setInstanceFollowRedirects(false);  
         conn.connect();  
         String result = getReturn(conn);
         System.out.println(result);
         Map<String,Charset> availableCharsets = Charset.availableCharsets();
         JSONObject object = JSONObject.fromObject(availableCharsets);
         System.out.println(object.toString());
     }  
   
     /*请求url获取返回的内容*/  
     public static String getReturn(HttpURLConnection connection) throws IOException{  
         StringBuffer buffer = new StringBuffer();
         //将返回的输入流转换成字符串  
         try(InputStream inputStream = connection.getInputStream(); 
             InputStreamReader inputStreamReader1 = new InputStreamReader(inputStream, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader1);){  
             String str = null;  
             while ((str = bufferedReader.readLine()) != null) {  
                 buffer.append(str);  
             }  
             String result = buffer.toString(); 
             return result;  
         }  
     }  

}

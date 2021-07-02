 package com.springboot.test.util.file;

 import java.io.File;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.RandomAccessFile;
 import java.net.HttpURLConnection;
 import java.net.URL;
  
 /***
  * 请求远程http地址，指定文件地址下载文件
  */
 public class DownloadWithRangeByHttpUrl implements Runnable {
  
     /**远程调用地址*/
     private String urlLocation;
  
     /**文件保存地址*/
     private String filePath;
  
     /**下载起始位置*/
     private long start;
  
     /**下载结束位置*/
     private long end;
  
     DownloadWithRangeByHttpUrl(String urlLocation, String filePath, long start, long end) {
         this.urlLocation = urlLocation;
         this.filePath = filePath;
         this.start = start;
         this.end = end;
     }
  
     @Override
     public void run() {
         try {
             HttpURLConnection conn = setHttpArributes();  
             File file = new File(filePath);
             RandomAccessFile out = null;
             if (file != null) {
                 out = new RandomAccessFile(file, "rw");
             }
             out.seek(start);
             InputStream in = conn.getInputStream();
             byte[] b = new byte[1024];
             int len = 0;
             while ((len = in.read(b)) >= 0) {
                 out.write(b, 0, len);
             }
             in.close();
             out.close();
         } catch (Exception e) {
             e.getMessage();
         }
  
     }
  
     private HttpURLConnection setHttpArributes() throws IOException {
         URL url = null;
         if (urlLocation != null) {
             url = new URL(urlLocation);
         }
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         //设置请求超时时间
         conn.setReadTimeout(30000);
         conn.setConnectTimeout(30000);
         //允许写出
         conn.setDoOutput(true);
         //允许写入
         conn.setDoInput(true);
         //不使用缓存
         conn.setUseCaches(false);
         conn.setRequestMethod("GET");
         conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
         return conn;
     } 
 }

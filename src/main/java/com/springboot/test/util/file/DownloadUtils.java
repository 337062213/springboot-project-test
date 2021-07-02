 package com.springboot.test.util.file;

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.util.StringUtils;
  
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServletResponse;
 import java.io.*;
 import java.net.*;
 import java.util.HashMap;
 import java.util.Map;
 
 /**
  * @description 较为简单的下载是单线程的
  * @author EDZ
  * @date 2021/06/30
  */
 public class DownloadUtils {
    private static Logger logger = LoggerFactory.getLogger(DownloadUtils.class);
  
    public static void errorFilePath(HttpServletResponse response, String msg){
       //没有该消息记录直接返回无此文件
       try {
          ServletOutputStream out=response.getOutputStream();
          OutputStreamWriter ow=new OutputStreamWriter(out,"UTF-8");
          ow.write(msg);
          ow.flush();
          ow.close();
       } catch (IOException e) {
  
       }catch (Exception e) {
          e.printStackTrace();
       }  
    }
    /**
     * @param filePath 要下载的文件路径
     * @param returnName 返回的文件名
     * @param response HttpServletResponse
     * @param delFlag 是否删除文件
     */
    protected void download(String filePath,String returnName,HttpServletResponse response,boolean delFlag){
       prototypeDownload(new File(filePath), returnName, response, delFlag);
    }
  
  
    /**
     * @param file 要下载的文件
     * @param returnName 返回的文件名
     * @param response HttpServletResponse
     * @param delFlag 是否删除文件
     */
    public static void download(File file, String returnName, HttpServletResponse response, boolean delFlag){
       prototypeDownload(file, returnName, response, delFlag);
    }
    
    /**
     * @param file 要下载的文件
     * @param returnName 返回的文件名
     * @param response HttpServletResponse
     * @param delFlag 是否删除文件
     */
    private static void prototypeDownload(File file, String returnName, HttpServletResponse response, boolean delFlag){
       // 下载文件
       FileInputStream inputStream = null;
       ServletOutputStream outputStream = null;
       try {
          if(!file.exists()) return;
          response.reset();
          //设置响应类型   PDF文件为"application/pdf"，WORD文件为："application/msword"， EXCEL文件为："application/vnd.ms-excel"。
          response.setContentType("application/octet-stream;charset=utf-8");
          //设置响应的文件名称,并转换成中文编码
          returnName = URLEncoder.encode(returnName,"UTF-8");
          returnName = response.encodeURL(new String(returnName.getBytes(),"iso8859-1"));    //保存的文件名,必须和页面编码一致,否则乱码
  
          //attachment作为附件下载；inline客户端机器有安装匹配程序，则直接打开；注意改变配置，清除缓存，否则可能不能看到效果
          response.addHeader("Content-Disposition",   "attachment;filename="+returnName);  
          //将文件读入响应流
          inputStream = new FileInputStream(file);
          outputStream = response.getOutputStream();
          int length = 1024;
          int readLength=0;
          byte buf[] = new byte[1024];
          readLength = inputStream.read(buf, 0, length);
          while (readLength != -1) {
             outputStream.write(buf, 0, readLength);
             readLength = inputStream.read(buf, 0, length);
          }
       }catch (IOException e) {
          errorFilePath(response,"无此文件");
       }catch (Exception e) {
          e.printStackTrace();
       }finally {
          try {
             outputStream.flush();
          } catch (IOException e) {
  
          }
          try {
             outputStream.close();
          } catch (IOException e) {
  
          }
          try {
             inputStream.close();
          } catch (IOException e) {
  
          }
          //删除原文件
  
          if(delFlag) {
             file.delete();
          }
       }
    }
  
    /**
     * by tony 2013-10-17
     * @param byteArrayOutputStream 将文件内容写入ByteArrayOutputStream
     * @param response HttpServletResponse 写入response
     * @param returnName 返回的文件名
     */
    public void download(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response, String returnName) throws IOException{
       response.setContentType("application/octet-stream;charset=utf-8");
       returnName = response.encodeURL(new String(returnName.getBytes(),"iso8859-1"));          //保存的文件名,必须和页面编码一致,否则乱码
       response.addHeader("Content-Disposition",   "attachment;filename=" + returnName);  
       response.setContentLength(byteArrayOutputStream.size());
       
       ServletOutputStream outputstream = response.getOutputStream(); //取得输出流
       byteArrayOutputStream.writeTo(outputstream);               //写到输出流
       byteArrayOutputStream.close();                         //关闭
       outputstream.flush();                                //刷数据
    }
  
    /***
     * 浏览器远程下载文件
     */
    public static void downloadFileByHttpUrl(HttpServletResponse response,String urlStr,String fileName){
       OutputStream out = null;
       InputStream ips = null;
       try {
          logger.info("第一次请求url==>" + urlStr);
          URL url = new URL(urlStr);
          HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
          httpUrlConn.addRequestProperty("Content-type", "application/json");
          //设置请求超时时间
          httpUrlConn.setReadTimeout(15000);
          httpUrlConn.setConnectTimeout(15000);
          httpUrlConn.setDoOutput(true); //允许写出
          httpUrlConn.setDoInput(true);//允许写入
          httpUrlConn.setUseCaches(false);//不使用缓存
          // 设置请求方式（GET/POST）
          httpUrlConn.setRequestMethod("GET");
          httpUrlConn.connect();
          int responseCode = httpUrlConn.getResponseCode();
          if(responseCode!=200){
             errorFilePath(response,"无此文件");
          }else {
             ips = httpUrlConn.getInputStream();//字节流 输入流
             response.reset();
             response.setContentType("application/x-download");
             response.addHeader("Content-Disposition","attachment;filename="+ new String(fileName.getBytes(),"iso-8859-1"));
             response.setContentType("application/octet-stream");
             out = new BufferedOutputStream(response.getOutputStream());
             int length = 1024;
             int readLength=0;
             byte buf[] = new byte[1024];
             readLength = ips.read(buf, 0, length);
             while (readLength != -1) {
                out.write(buf, 0, readLength);
                readLength = ips.read(buf, 0, length);
             }
          }
       }catch (IOException e) {
          errorFilePath(response,"无此文件");
       }catch (Exception e) {
          e.printStackTrace();
       }finally {
          try {
             out.flush();
          } catch (IOException e) {
  
          }
          try {
             out.close();
          } catch (IOException e) {
  
          }
          try {
             ips.close();
          } catch (IOException e) {
  
          }
       }
    }
  
    /***
     * http 请求获取下载文件保存到本地
     * urlStr 远程地址
     * filePath 指定本地保存路径
     */
    public static Map<String,Object> downloadFileByHttpUrl(String urlStr, String filePath){
       Map<String,Object> map = new HashMap<>();
       map.put("success",false);
       OutputStream out = null;
       InputStream ips = null;
       String requestMethod="GET";
       try {
          File file = new File(filePath);
          logger.info("===================："+urlStr);
          logger.info("===================获取流媒体服务中的文件："+filePath);
          URL url = new URL(urlStr);
          HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
          httpUrlConn.addRequestProperty("Content-type", "application/json");
          //httpUrlConn.addRequestProperty("accessToken",accessToken);
          //设置请求超时时间
          httpUrlConn.setReadTimeout(30000);
          httpUrlConn.setConnectTimeout(30000);
          httpUrlConn.setDoOutput(true); //允许写出
          httpUrlConn.setDoInput(true);//允许写入
          httpUrlConn.setUseCaches(false);//不使用缓存
  
          // 设置请求方式（GET/POST）
          httpUrlConn.setRequestMethod(requestMethod);
          if ("GET".equalsIgnoreCase(requestMethod)) {
             httpUrlConn.connect();
          }
          // 将返回的输入流转换成字符串
          ips = httpUrlConn.getInputStream();
          out = new FileOutputStream(file,false);
          int length = 1024;
          int readLength=0;
          byte buf[] = new byte[1024];
          readLength = ips.read(buf, 0, length);
          while (readLength != -1) {
             String s = new String(buf, "UTF-8");
             if (s.contains("未找到")) {
                logger.info("==================putIOToResponseByHttp错误信息："+s);
                file.delete();
                map.put("msg",s);
                return map;
             }else{
                out.write(buf, 0, readLength);
                readLength = ips.read(buf, 0, length);
             }
          }
          map.put("success",true);
       }catch (IOException e) {
          logger.info("无此文件");
       }catch (Exception e) {
          e.printStackTrace();
       }finally {
          try {
             out.flush();
          } catch (IOException e) {
  
          }
          try {
             out.close();
          } catch (IOException e) {
  
          }
          try {
             ips.close();
          } catch (IOException e) {
  
          }
       }
       return map;
    }
  
    /***
     * 根据远程http地址将流存到respons 返回失败或者成功
     * @param response 浏览器response
     * @param urlStr 远程http地址
     * @return 返回失败或者成功
     */
    public static boolean putIOToResponseByHttp(HttpServletResponse response, String urlStr) {
       OutputStream out = null;
       InputStream ips = null;
       String requestMethod="GET";
       try {
          if (StringUtils.isEmpty(urlStr)) {
             return false;
          }else{
                URL url = new URL(urlStr);
                HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
                httpUrlConn.addRequestProperty("Content-type", "application/json");
                //httpUrlConn.addRequestProperty("accessToken",accessToken);
                //设置请求超时时间
                httpUrlConn.setReadTimeout(300000);
                httpUrlConn.setConnectTimeout(300000);
                httpUrlConn.setDoOutput(true); //允许写出
                httpUrlConn.setDoInput(true);//允许写入
                httpUrlConn.setUseCaches(false);//不使用缓存
  
                // 设置请求方式（GET/POST）
                httpUrlConn.setRequestMethod(requestMethod);
                if ("GET".equalsIgnoreCase(requestMethod)) {
                   httpUrlConn.connect();
                }
                // 将返回的输入流转换成字符串
                ips = httpUrlConn.getInputStream();
                out = new BufferedOutputStream(response.getOutputStream());
                int length = 1024;
                int readLength=0;
                byte buf[] = new byte[1024];
                readLength = ips.read(buf, 0, length);
                boolean bool = true;
                while (readLength != -1) {
                   if (bool && new String(buf, "UTF-8").contains("未找到")) {
                      logger.info("==================putIOToResponseByHttp错误信息："+new String(buf, "UTF-8"));
                      return false;
                   }else{
                      bool = false;
                      out.write(buf, 0, readLength);
                      readLength = ips.read(buf, 0, length);
                   }
                }
             }
             return true;
       }catch (IOException e) {
          errorFilePath(response,"无此文件");
       }catch (Exception e) {
          e.printStackTrace();
       }finally {
          try {
             out.flush();
          } catch (IOException e) {
  
          }
          try {
             out.close();
          } catch (IOException e) {
  
          }
          try {
             ips.close();
          } catch (IOException e) {
  
          }
       }
       return false;
    }
    public static InputStream returnIoByHttUrl(String requestMethod,String urlStr){
       try {
          URL url = new URL(urlStr);
          HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
          httpUrlConn.addRequestProperty("Content-type", "application/json");
          //设置请求超时时间
          httpUrlConn.setReadTimeout(30000);
          httpUrlConn.setConnectTimeout(30000);
          httpUrlConn.setDoOutput(true); //允许写出
          httpUrlConn.setDoInput(true);//允许写入
          httpUrlConn.setUseCaches(false);//不使用缓存
  
          // 设置请求方式（GET/POST）
          httpUrlConn.setRequestMethod(requestMethod);
          if ("GET".equalsIgnoreCase(requestMethod)) {
             httpUrlConn.connect();
          }
          // 将返回的输入流转换成字符串
          return httpUrlConn.getInputStream();
       }catch (IOException e) {
          logger.info("无此文件");
       }catch (Exception e) {
          e.printStackTrace();
       }
       return null;
    }
  
  
    /***
     * 校验远程地址是否可用
     * @param requestUrl 请求地址
     * @return 是否可用
     */
    public static Boolean checkIP(String requestUrl){
       try {
          String hostAddress = InetAddress.getLocalHost().getHostAddress();
          logger.info("java获取到的当前页面地址"+hostAddress);
          URL url = new URL(requestUrl);
          HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
          httpUrlConn.addRequestProperty("Content-type", "application/json");
          //设置请求超时时间
          httpUrlConn.setReadTimeout(5000);
          httpUrlConn.setConnectTimeout(5000);
          httpUrlConn.setDoOutput(true); //允许写出
          httpUrlConn.setDoInput(true);//允许写入
          httpUrlConn.setUseCaches(false);//不使用缓存
          // 设置请求方式（GET/POST）
          httpUrlConn.setRequestMethod("GET");
          httpUrlConn.connect();
          int responseCode = httpUrlConn.getResponseCode();
          if(responseCode==200){
             logger.info("=============="+requestUrl+":地址有效");
             return true;
          }
       }catch (Exception e){
          return false;
       }
       return false;
    }
  
  
    /***
     * 校验远程文件是否可用
     * @return 是否可用
     */
    public static Boolean checkFile(String urlStr){
       InputStream ips = null;
       String requestMethod="GET";
       try {
          URL url = new URL(urlStr);
          HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
          httpUrlConn.addRequestProperty("Content-type", "application/json");
          //httpUrlConn.addRequestProperty("accessToken",accessToken);
          //设置请求超时时间
          httpUrlConn.setReadTimeout(30000);
          httpUrlConn.setConnectTimeout(30000);
          httpUrlConn.setDoOutput(true); //允许写出
          httpUrlConn.setDoInput(true);//允许写入
          httpUrlConn.setUseCaches(false);//不使用缓存
  
          // 设置请求方式（GET/POST）
          httpUrlConn.setRequestMethod(requestMethod);
          if ("GET".equalsIgnoreCase(requestMethod)) {
             httpUrlConn.connect();
          }
          // 将返回的输入流转换成字符串
          ips = httpUrlConn.getInputStream();
          int length = 1024;
          int readLength=0;
          byte buf[] = new byte[1024];
          readLength = ips.read(buf, 0, length);
          while (readLength != -1) {
             String s = new String(buf, "UTF-8");
             if (s.contains("未找到")) {
                return false;
             }else{
                return true;
             }
          }
       }catch (IOException e) {
          logger.info("无此文件");
       }catch (Exception e) {
          e.printStackTrace();
       }finally {
          try {
             ips.close();
          } catch (IOException e) {
  
          }
       }
       return false;
    }   
 }

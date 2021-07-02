 package com.springboot.test.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.springboot.test.util.IOtools;
import com.springboot.test.util.PathUtils;
import com.springboot.test.util.ZipUtils;

@Controller
@RequestMapping("/servlet")
@CrossOrigin
public class DownLoadServletController extends HttpServlet {

     private static final long serialVersionUID = -8652981711630952290L;
     
     private static Logger logger = LoggerFactory.getLogger(DownLoadServletController.class);
     
     @Value("${file.upload.fileDirectory}")
     private String uploadFilePath;
     
     @Value("${file.dowmload.fileDirectory}")
     private String downloadFilePath;
     
     @Value("${file.tempDirectory}")
     private String tempDirectory;
     
     @RequestMapping("/download1")
     public void download1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         //得到要下载的文件名
         String fileName = request.getParameter("filename");
         fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
         //上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
         String fileSaveRootPath=this.getServletContext().getRealPath(this.uploadFilePath);
         //        处理文件名
          String realname = fileName.substring(fileName.indexOf("_")+1);
         //通过文件名找出文件的所在目录
         String path = findFileSavePathByFileName(fileName,fileSaveRootPath);
         //得到要下载的文件
         File file = new File(path+File.separator+fileName);
         //如果文件不存在
         if(!file.exists()){
             request.setAttribute("message", "您要下载的资源已被删除！！");
             return;
         }
         
          //设置响应头，控制浏览器下载该文件
          response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
          //读取要下载的文件，保存到文件输入流
          FileInputStream fis = new FileInputStream(path + File.separator + fileName);
          //创建输出流
          OutputStream fos = response.getOutputStream();
          //设置缓存区
          ByteBuffer buffer = ByteBuffer.allocate(1024);
          //输入通道
          FileChannel readChannel = fis.getChannel();
          //输出通道
          FileChannel writeChannel = ((FileOutputStream)fos).getChannel();
          while(true){
              buffer.clear();
              int len = readChannel.read(buffer);//读入数据
              if(len < 0){
                  break;//传输结束
              }
              buffer.flip();
              writeChannel.write(buffer);//写入数据
          }
          //关闭输入流
          fis.close();
          //关闭输出流
          fos.close();
     }

     @RequestMapping("/download2")
     public void download2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         // 得到要下载的文件名
         String fileName = request.getParameter("filename");
         fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
         // 上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
         String fileSaveRootPath=PathUtils.getPathByProperty() + this.downloadFilePath;
         // 处理文件名
         String realname = fileName.substring(fileName.indexOf("_")+1);
         // 得到要下载的文件
         File file = new File(fileSaveRootPath+fileName);
         // 如果文件不存在
         if(!file.exists()){
             return;
         } 
//         response.setContentType("application/force-download;charset=utf-8");
         // 设置响应头，控制浏览器下载该文件
         response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
         // 读取要下载的文件，保存到文件输入流
         FileInputStream in = new FileInputStream(file);
         response.addHeader("Content-Length", "" + file.length());
         // 创建输出流
         OutputStream os = response.getOutputStream();
         // 设置缓存区
         byte[] bytes = new byte[1024];
         int length = 0;
         while((length = in.read(bytes))>0){
            os.write(bytes,0,length);
         }
         //关闭输入流
         in.close();
         //关闭输出流
         os.close();
     }
     
     @RequestMapping("download3")
     public void download3(@RequestParam("filename") String filename, HttpServletResponse response) throws Exception {
         // 文件地址，真实环境是存放在数据库中的
         String filePath = this.downloadFilePath;
         String fileName = PathUtils.getPathByProperty() + filePath + filename;
         File file = new File(fileName);
         // 穿件输入对象
         FileInputStream fis = new FileInputStream(file);
         // 设置相关格式
         response.setContentType("application/force-download");
         // 设置下载后的文件名以及header
         response.addHeader("Content-disposition", "attachment;fileName=" + filename);
         // 创建输出对象
         OutputStream os = response.getOutputStream();
         // 常规操作
         byte[] buf = new byte[1024];
         int len = 0;
         while((len = fis.read(buf)) != -1) {
             os.write(buf, 0, len);
         }
         fis.close();
     }
     
     @RequestMapping("download4")
     public void download4(@RequestParam(required=false,value="filenames") String filenames, @RequestParam(required=false,value="proName") String proName, HttpServletResponse response) throws Exception {
             logger.info("下载的项目为:" + proName);
             //创建临时文件夹             
             String temporaryPath = (this.tempDirectory + proName);
             logger.info(temporaryPath+ proName);
             File temDir = new File(temporaryPath+ proName);
             if(!temDir.exists()) {
                 temDir.mkdirs();
             }
             /**
              * 1.项目文件存放地址
              */
             String fileUrl = (this.downloadFilePath+proName );

             /**
              * 2.生成需要下载的文件，存放在临时文件夹内
              */
             ZipUtils.copyDir(fileUrl+proName, temporaryPath+proName);
             try {
                 ZipUtils.copyDir(fileUrl+proName, temporaryPath+proName);
             } catch (IOException e) {
                 e.printStackTrace();
             }

             /**
              * 3.设置response的header
              */
             response.setContentType("application/zip");
             response.setHeader("Content-Disposition", "attachment; filename=uchainfile.zip");

             /**
              * 4.调用工具类，下载zip压缩包
              */
             try {
                 ZipUtils.toZip(temDir.getPath(), response.getOutputStream(), true);
             } catch (IOException e) {
                 e.printStackTrace();
             }
             /**
              * 5.删除临时文件和文件夹
              */
             File[] listFiles = temDir.listFiles();
             for (int i = 0; i < listFiles.length; i++) {
                 listFiles[i].delete();
                 logger.info("正在删除第"+i+"个文件");
             }
             temDir.delete();
     }
     
     @RequestMapping("download5")
     public void download5(HttpServletResponse response) {
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
         String zipName = UUID.randomUUID().toString()+".zip";
         String outFilePath = PathUtils.getPathByProperty() + this.uploadFilePath;
         File fileZip = new File(outFilePath+zipName);
         try {
             // 文件保存路径
             String filePath = PathUtils.getPathByProperty() + this.uploadFilePath;
             List<File> fileList = IOtools.getFiles(filePath);
             FileOutputStream outStream = new FileOutputStream(fileZip);
             ZipOutputStream toClient = new ZipOutputStream(outStream);
             IOtools.zipFile(fileList,toClient);
             toClient.close();
             outStream.close();
             IOtools.downloadFile(fileZip,response,true);
         }catch(Exception e){
             logger.info("系统异常,请从新录入!");
             e.printStackTrace();
         }
     }
   
     @RequestMapping("download6")
     public ResponseEntity<byte[]> download6(HttpServletResponse response) {
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
         ArrayList<String> iconNameList = new ArrayList<String>();//返回文件名数组
         String zipName = UUID.randomUUID().toString()+".zip";
         String outFilePath = PathUtils.getPathByProperty() + this.uploadFilePath;
         File fileZip = new File(outFilePath+zipName);
         try {
             // 文件保存路径
             String filePath = PathUtils.getPathByProperty() + this.uploadFilePath;
             List<File> fileList = IOtools.getFiles(filePath);
             FileOutputStream outStream = new FileOutputStream(fileZip);
             ZipOutputStream toClient = new ZipOutputStream(outStream);
             IOtools.zipFile(fileList,toClient);
             toClient.close();
             outStream.close();
             IOtools.downloadFile(fileZip,response,true);
             //单个文件下载
             String curpath = fileList.get(0).getPath();//获取文件路径
             iconNameList.add(curpath.substring(curpath.lastIndexOf("\\") + 1));//将文件名加入数组
             String fileName = new String(filePath.getBytes("UTF-8"),"iso8859-1");
             headers.setContentDispositionFormData("attachment", fileName);
             return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath)),
                     headers, HttpStatus.OK);
         }catch(Exception e){
             logger.info("系统异常,请从新录入!");
             e.printStackTrace();
         }
        return null;
     }
   
     @GetMapping("/download/file")
     public void downloadPattern(HttpServletResponse response){
         System.out.println("开始下载文件.....");
         ClassPathResource resource = new ClassPathResource("\\html\\fileupload.html");
         try(OutputStream os = response.getOutputStream()){
             //获取文件输入流
             InputStream in = resource.getInputStream();
             String fileName = "fileupload.html";
             response.reset();
             response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
             response.setContentType("application/octet-stream; charset=UTF-8");
             byte[] b = new byte[in.available()];
             in.read(b);
             os.write(b);
             os.flush();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
   
     /**通过文件名和存储上传文件根目录找出要下载的文件的所在路径*/
     public String findFileSavePathByFileName(String fileName,String fileSaveRootPath){
         int hashcode = fileName.hashCode();
         int dir1 = hashcode&0xf;
         int dir2 = (hashcode&0xf0)>>4;
         String dir = fileSaveRootPath + "\\" + dir1 + "\\" + dir2;
         File file = new File(dir);
         if(!file.exists()){
             file.mkdirs();
         }
         return dir;
     }
     
     @RequestMapping("/upload1")
     public void upload1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
         String savePath = this.getServletContext().getRealPath(this.uploadFilePath);
         //上传时生成的临时文件保存目录
         String tempPath = this.getServletContext().getRealPath(this.tempDirectory);
         File file = new File(tempPath);
         if(!file.exists()&&!file.isDirectory()){
             System.out.println("目录或文件不存在！");
             file.mkdir();
         }
         //消息提示
         String message = "";
         try {
             //使用Apache文件上传组件处理文件上传步骤：
             //1、创建一个DiskFileItemFactory工厂
             DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
             //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
             diskFileItemFactory.setSizeThreshold(1024*100);
             //设置上传时生成的临时文件的保存目录
             diskFileItemFactory.setRepository(file);
             //2、创建一个文件上传解析器
             ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
             //解决上传文件名的中文乱码
             fileUpload.setHeaderEncoding("UTF-8");
             //监听文件上传进度
             fileUpload.setProgressListener(new ProgressListener(){
                 public void update(long pBytesRead, long pContentLength, int arg2) {
                     System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
                 }
             });
             //3、判断提交上来的数据是否是上传表单的数据
             if(!ServletFileUpload.isMultipartContent(request)){
                 //按照传统方式获取数据
                 return;
             }
             //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
             fileUpload.setFileSizeMax(1024*1024);
             //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
             fileUpload.setSizeMax(1024*1024*10);
             //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
             List<FileItem> list = fileUpload.parseRequest(request);
             for (FileItem item : list) {
                 //如果fileitem中封装的是普通输入项的数据
                 if(item.isFormField()){
                     String name = item.getFieldName();
                     //解决普通输入项的数据的中文乱码问题
                     String value = item.getString("UTF-8");
                     String value1 = new String(name.getBytes("iso8859-1"),"UTF-8");
                     System.out.println(name+"  "+value);
                     System.out.println(name+"  "+value1);
                 }else{
                     //如果fileitem中封装的是上传文件，得到上传的文件名称，
                     String fileName = item.getName();
                     System.out.println(fileName);
                     if(fileName==null||fileName.trim().equals("")){
                         continue;
                     }
                     //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                     //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                     fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
                     //得到上传文件的扩展名
                     String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);
                     if("zip".equals(fileExtName)||"rar".equals(fileExtName)||"tar".equals(fileExtName)||"jar".equals(fileExtName)){
                         request.setAttribute("message", "上传文件的类型不符合！！！");
                         return;
                     }
                     //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                     System.out.println("上传文件的扩展名为:"+fileExtName);
                     //获取item中的上传文件的输入流
                     InputStream fis = item.getInputStream();
                     //得到文件保存的名称
                     fileName = mkFileName(fileName);
                     //得到文件保存的路径
                     String savePathStr = mkFilePath(savePath, fileName);
                     System.out.println("保存路径为:"+savePathStr);
                     //创建一个文件输出流
                     FileOutputStream fos = new FileOutputStream(savePathStr+File.separator+fileName);
                     //获取读通道
                     FileChannel readChannel = ((FileInputStream)fis).getChannel();
                     //获取读通道
                     FileChannel writeChannel = fos.getChannel();
                     //创建一个缓冲区
                     ByteBuffer buffer = ByteBuffer.allocate(1024);
                     //判断输入流中的数据是否已经读完的标识
                     //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                     while(true){
                         buffer.clear();
                         int len = readChannel.read(buffer);//读入数据
                         if(len < 0){
                             break;//读取完毕 
                         }
                         buffer.flip();
                         writeChannel.write(buffer);//写入数据
                     }
                     //关闭输入流
                     fis.close();
                     //关闭输出流
                     fos.close();
                     //删除处理文件上传时生成的临时文件
                     item.delete();
                     message = "文件上传成功";
                 }
             }
         } catch (FileUploadBase.FileSizeLimitExceededException e) {
             e.printStackTrace();
             request.setAttribute("message", "单个文件超出最大值！！！");
             return;
         }catch (FileUploadBase.SizeLimitExceededException e) {
             e.printStackTrace();
             request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
             return;
         }catch (FileUploadException e) {
             e.printStackTrace();
             message = "文件上传失败";
         }
         request.setAttribute("message",message);
     }

     @RequestMapping("upload2")
     public void upload2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
         String savePath = PathUtils.getPathByProperty() +this.uploadFilePath;
         File file = new File(savePath);
         if(!file.exists()&&!file.isDirectory()){
             System.out.println("目录或文件不存在！");
             file.mkdir();
         }
         //消息提示
         String message = "";
         try {
             //使用Apache文件上传组件处理文件上传步骤：
             //1、创建一个DiskFileItemFactory工厂
             DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
             //2、创建一个文件上传解析器
             ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
             //解决上传文件名的中文乱码
             fileUpload.setHeaderEncoding("UTF-8");
             //3、判断提交上来的数据是否是上传表单的数据
             if(!ServletFileUpload.isMultipartContent(request)){
                 //按照传统方式获取数据
                 return;
             }
             //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
             List<FileItem> list = fileUpload.parseRequest(request);
             for (FileItem item : list) {
                 //如果fileitem中封装的是普通输入项的数据
                 if(item.isFormField()){
                     String name = item.getFieldName();
                     //解决普通输入项的数据的中文乱码问题
                     String value = item.getString("UTF-8");
                     String value1 = new String(name.getBytes("iso8859-1"),"UTF-8");
                     System.out.println(name+"  "+value);
                     System.out.println(name+"  "+value1);
                 }else{
                     //如果fileitem中封装的是上传文件，得到上传的文件名称，
                     String fileName = item.getName();
                     System.out.println(fileName);
                     if(fileName==null||fileName.trim().equals("")){
                         continue;
                     }
                     //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                     //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                     fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
                     //获取item中的上传文件的输入流
                     InputStream is = item.getInputStream();
                     //创建一个文件输出流
                     FileOutputStream fos = new FileOutputStream(savePath+File.separator+fileName);
                     //创建一个缓冲区
                     byte buffer[] = new byte[1024];
                     //判断输入流中的数据是否已经读完的标识
                     int length = 0;
                     //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                     while((length = is.read(buffer))>0){
                         //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                         fos.write(buffer, 0, length);
                     }
                     //关闭输入流
                     is.close();
                     //关闭输出流
                     fos.close();
                     //删除处理文件上传时生成的临时文件
                     item.delete();
                     message = "文件上传成功";
                 }
             }
         } catch (FileUploadException e) {
             e.printStackTrace();
             message = "文件上传失败";
         }
         request.setAttribute("message",message);
     }
     
     @RequestMapping("upload3")
     public void upload3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
         String savePath = this.getServletContext().getRealPath(this.uploadFilePath);
         //上传时生成的临时文件保存目录
         String tempPath = this.getServletContext().getRealPath(this.tempDirectory);
         File file = new File(tempPath);
         if(!file.exists()&&!file.isDirectory()){
             System.out.println("目录或文件不存在！");
             file.mkdir();
         }
         //消息提示
         String message = "";
         try {
             //使用Apache文件上传组件处理文件上传步骤：
             //1、创建一个DiskFileItemFactory工厂
             DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
             //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
             diskFileItemFactory.setSizeThreshold(1024*100);
             //设置上传时生成的临时文件的保存目录
             diskFileItemFactory.setRepository(file);
             //2、创建一个文件上传解析器
             ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
             //解决上传文件名的中文乱码
             fileUpload.setHeaderEncoding("UTF-8");
             //监听文件上传进度
             fileUpload.setProgressListener(new ProgressListener(){
                 public void update(long pBytesRead, long pContentLength, int arg2) {
                     System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
                 }
             });
             //3、判断提交上来的数据是否是上传表单的数据
             if(!ServletFileUpload.isMultipartContent(request)){
                 //按照传统方式获取数据
                 return;
             }
             //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
             fileUpload.setFileSizeMax(1024*1024);
             //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
             fileUpload.setSizeMax(1024*1024*10);
             //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
             List<FileItem> list = fileUpload.parseRequest(request);
             for (FileItem item : list) {
                 //如果fileitem中封装的是普通输入项的数据
                 if(item.isFormField()){
                     String name = item.getFieldName();
                     //解决普通输入项的数据的中文乱码问题
                     String value = item.getString("UTF-8");
                     String value1 = new String(name.getBytes("iso8859-1"),"UTF-8");
                     System.out.println(name+"  "+value);
                     System.out.println(name+"  "+value1);
                 }else{
                     //如果fileitem中封装的是上传文件，得到上传的文件名称，
                     String fileName = item.getName();
                     System.out.println(fileName);
                     if(fileName==null||fileName.trim().equals("")){
                         continue;
                     }
                     //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                     //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                     fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
                     //得到上传文件的扩展名
                     String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);
                     if("zip".equals(fileExtName)||"rar".equals(fileExtName)||"tar".equals(fileExtName)||"jar".equals(fileExtName)){
                         request.setAttribute("message", "上传文件的类型不符合！！！");
                         return;
                     }
                     //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                     System.out.println("上传文件的扩展名为:"+fileExtName);
                     //获取item中的上传文件的输入流
                     InputStream is = item.getInputStream();
                     //得到文件保存的名称
                     fileName = mkFileName(fileName);
                     //得到文件保存的路径
                     String savePathStr = mkFilePath(savePath, fileName);
                     System.out.println("保存路径为:"+savePathStr);
                     //创建一个文件输出流
                     FileOutputStream fos = new FileOutputStream(savePathStr+File.separator+fileName);
                     //创建一个缓冲区
                     byte buffer[] = new byte[1024];
                     //判断输入流中的数据是否已经读完的标识
                     int length = 0;
                     //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                     while((length = is.read(buffer))>0){
                         //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                         fos.write(buffer, 0, length);
                     }
                     //关闭输入流
                     is.close();
                     //关闭输出流
                     fos.close();
                     //删除处理文件上传时生成的临时文件
                     item.delete();
                     message = "文件上传成功";
                 }
             }
         } catch (FileUploadBase.FileSizeLimitExceededException e) {
             e.printStackTrace();
             request.setAttribute("message", "单个文件超出最大值！！！");
             return;
         }catch (FileUploadBase.SizeLimitExceededException e) {
             e.printStackTrace();
             request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
             return;
         }catch (FileUploadException e) {
             e.printStackTrace();
             message = "文件上传失败";
         }
         request.setAttribute("message",message);
     }

     @RequestMapping("upload4")
     public void upload4(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles( "file" ); 
         String filePath = this.uploadFilePath;
         // 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
         String savePath = PathUtils.getPathByProperty()+filePath;
         // 文件重命名，防止重复
         String tempPath = PathUtils.getPathByProperty()+filePath;
         File file = new File(tempPath);
         if(!file.exists()&&!file.isDirectory()){
             System.out.println("目录或文件不存在！");
             file.mkdir();
         }
         //使用Apache文件上传组件处理文件上传步骤：
         //1、创建一个DiskFileItemFactory工厂
         DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
         //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
         diskFileItemFactory.setSizeThreshold(1024*100);
         //设置上传时生成的临时文件的保存目录
         diskFileItemFactory.setRepository(file);
         for (MultipartFile item : files) {
             //如果fileitem中封装的是上传文件，得到上传的文件名称，
             String fileName = item.getOriginalFilename();
             System.out.println(fileName);
             if(fileName==null||fileName.trim().equals("")){
                 continue;
             }
             //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
             //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
             fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
             //得到上传文件的扩展名
             String fileExtName = fileName.substring(0,fileName.lastIndexOf("."));
             //得到上传文件的扩展名
             String fileExtType = fileName.substring(fileName.lastIndexOf(".")+1);
             if("zip".equals(fileExtName)||"rar".equals(fileExtName)||"tar".equals(fileExtName)||"jar".equals(fileExtName)){
                 request.setAttribute("message", "上传文件的类型不符合！！！");
                 return;
             }
             //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
             System.out.println("上传文件的扩展名为:"+fileExtType);
             //获取item中的上传文件的输入流
             InputStream fis = item.getInputStream();
             //得到文件保存的名称
             fileName = mkFileName(fileExtName, fileExtType, 1);
             //得到文件保存的路径
             String savePathStr = mkFilePath(savePath, fileName, fileExtName);
             System.out.println("保存路径为:"+savePathStr);
             //创建一个文件输出流
             FileOutputStream fos = new FileOutputStream(savePathStr+File.separator+fileName);
             //获取读通道
             FileChannel readChannel = ((FileInputStream)fis).getChannel();
             //获取读通道
             FileChannel writeChannel = fos.getChannel();
             //创建一个缓冲区
             ByteBuffer buffer = ByteBuffer.allocate(1024);
             //判断输入流中的数据是否已经读完的标识
             //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
             while(true){
                 buffer.clear();
                 int len = readChannel.read(buffer);//读入数据
                 if(len < 0){
                     break;//读取完毕 
                 }
                 buffer.flip();
                 writeChannel.write(buffer);//写入数据
             }
             //关闭输入流
             fis.close();
             //关闭输出流
             fos.close();
         }
     }
     
     @RequestMapping(value = "upload5",method = RequestMethod.POST)
     public String upload5(@RequestParam("file") MultipartFile file) {
         
         // 获取原始名字
         String fileName = file.getOriginalFilename();
         // 文件保存路径
         String filePath = this.uploadFilePath;
         // 文件重命名，防止重复
         fileName = PathUtils.getPathByProperty()+filePath + fileName;
         // 文件对象
         File dest = new File(fileName);
         // 判断路径是否存在，如果不存在则创建
         if(!dest.getParentFile().exists()) {
             dest.getParentFile().mkdirs();
         }
         try {
             // 保存到服务器中
             file.transferTo(dest);
             return "上传成功";
         } catch (Exception e) {
             e.printStackTrace();
         }
         return "上传失败";
     }
     
     @RequestMapping (value= "/upload/batch" , method= RequestMethod.POST) 
     public String batchFileUpload(HttpServletRequest request) throws IOException{ 
         String filePath = PathUtils.getPathByProperty() + this.uploadFilePath;
         List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles( "file" ); 
         MultipartFile file =  null ;
         BufferedOutputStream stream =  null ;
         int number = 0;
         for  (int i = 0 ; i< files.size(); ++i) { 
             file = files.get(i); 
             if(!file.isEmpty()) { 
                 try  { 
                     byte [] bytes = file.getBytes(); 
                     stream = new  BufferedOutputStream( new  FileOutputStream( new  File(filePath + file.getOriginalFilename()))); 
                     stream.write(bytes);  
                 } catch (Exception e) { 
                     return "You failed to upload "  + i +  " => "  + e.getMessage(); 
                 } finally {
                     stream.close();
                 }
             } else {
                 number++; 
             }             
         }
         if(number>0) {
             String value1 = "";
             String value2 = "";
             if(number==1) {
                 value1 = " is ";
                 value2 = " file ";
             }else {
                 value1 = " are ";
                 value2 = " files ";
             }
             return " warning: upload successful！  There " + value1 + number +  value2 + "which " + value1 + "uploaded failed because the file was empty." ; 
         }else {
             return "upload successful" ;
         } 
     }
  
     /** mkFileName 生成上传文件的文件名，文件名以：UUID+"_"+文件的原始名称 */    
     public String mkFileName(String fileName){
         return UUID.randomUUID().toString()+"_"+fileName;
     }
     
     public String mkFilePath(String savePath,String fileName){
         //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
         int hashcode = fileName.hashCode();
         int dir1 = hashcode&0xf;
         int dir2 = (hashcode&0xf0)>>4;
         //构造新的保存目录
         String dir = savePath + "\\" + dir1 + "\\" + dir2;
         //File既可以代表文件也可以代表目录
         File file = new File(dir);
         if(!file.exists()){
             file.mkdirs();
         }
         return dir;
     }
     //生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称    
     private String mkFileName(String fileExtName, String fileExtType, int type){
         String file = "";
         switch(type) {
             case 1: file = fileExtName + "(" + UUID.randomUUID().toString() + ")." + fileExtType; break;
             case 2: file = "(" + fileExtName + ")" + UUID.randomUUID().toString() + "." + fileExtType; break;
             case 3: file = fileExtName + fileExtType; break;
             default : file = fileExtName + "_" + UUID.randomUUID().toString() + "." + fileExtType; break;
         }
         return file;
     }
     
     private String mkFilePath(String savePath,String fileName, String fileExtName){
         //构造新的保存目录
         String dir = savePath;
         //File既可以代表文件也可以代表目录
         File file = new File(dir);
         if(!file.exists()){
             file.mkdirs();
         }
         return dir;
     }
 }

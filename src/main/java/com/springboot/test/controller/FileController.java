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
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.fileupload.disk.DiskFileItemFactory;
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
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.multipart.MultipartFile;
 import org.springframework.web.multipart.MultipartHttpServletRequest;
 import com.springboot.test.util.IOtools;
 import com.springboot.test.util.PathUtils;
 import com.springboot.test.util.ZipUtils;

 @Controller(value="FileController")
 @RequestMapping("/file/")
 public class FileController {
     
     @Value("${file.upload.fileDirectory}")
     private String uploadFilePath;
     
     @Value("${file.dowmload.fileDirectory}")
     private String downloadFilePath;
     
     @Value("${file.tempDirectory}")
     private String tempDirectory;
     
     private static Logger logger = LoggerFactory.getLogger(FileController.class);
     
     @RequestMapping(value = "upload",method = RequestMethod.POST)
     public String upload (@RequestParam("file") MultipartFile file) {
         
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
     
     @RequestMapping("upload1")
     public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
         //消息提示
         String message = "";
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
             System.out.println("上传文件的扩展名为:"+fileExtName);
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
             message = "文件上传成功";
         }
         request.setAttribute("message",message);
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
     
     @RequestMapping("download")
     public void download(@RequestParam("filename") String filename, HttpServletResponse response) throws Exception {
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
     
     @RequestMapping("download1")
     public void download1(@RequestParam(required=false,value="filenames") String filenames, @RequestParam(required=false,value="proName") String proName, HttpServletResponse response) throws Exception {
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
     
     @RequestMapping("download2")
     public void download2(HttpServletResponse response) {
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
     
     @RequestMapping("download3")
     public ResponseEntity<byte[]> download3(HttpServletResponse response) {
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

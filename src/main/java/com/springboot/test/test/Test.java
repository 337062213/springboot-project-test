package com.springboot.test.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.swing.filechooser.FileSystemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.springboot.test.model.DefaultTree;
import com.springboot.test.model.consistant.DirectoryConst;
import com.springboot.test.util.DPIHandleHelper;
import com.springboot.test.util.DateUtils;
import com.springboot.test.util.GeneratorSqlmap;
import com.springboot.test.util.ImageUtils;
import com.springboot.test.util.PathUtils;
import com.springboot.test.util.RegularUtils;
import com.springboot.test.util.ThreadsCopyFile;
import com.springboot.test.util.XMLUtils;
import com.springboot.test.util.ZipUtils;
import com.springboot.test.util.bigfile.BigFileReader;
import com.springboot.test.util.bigfile.IHandle;
import com.springboot.test.util.encrypt.AESUtils;
import com.springboot.test.util.encrypt.JasyptUtils;
import com.springboot.test.util.excel.ExcelUtilX;
import com.springboot.test.util.file.FileUtils;
import com.springboot.test.util.http.IgnoreX509TrustManager;
import com.springboot.test.util.http.MyX509TrustManager;
import com.springboot.test.util.pdf.PdfUtils;
import com.springboot.test.util.qr.QRBarCodeUtil;
import com.springboot.test.util.tree.TreeUtil;

import net.sf.json.JSONObject;

public class Test {
     
     private static Logger logger = LoggerFactory.getLogger(Test.class);
    
     public static void main(String[] args) throws Exception{  
//         testRestTemplate();
//         getAvailableCharsets();
//         newFile();
//         x509TrustManager();
//         ignoreX509TrustManager();
//         getPath();
//         encrpt();
//         jasyptEncrypt();
//         regularUtils();
//         XMLParser();
//         imageUtils();
//         zipUtils();
//         threadsCopyFile();
         pathUtils();
//         GeneratorSqlmap();
//         handleDpi();
//         PdfUtils();
//         excelUtilX();
//         treeUtil();
//         myX509TrustManager();
//         httpURLConnection();
//         bigFileReader();
     }  
   
     /** 请求获取系统字符集 */  
     public static String getAvailableCharsets(){  
         Map<String,Charset> availableCharsets = Charset.availableCharsets();
         JSONObject object = JSONObject.fromObject(availableCharsets);
         logger.info(object.toString());
         return object.toString();
     }
     /** RestTemplate 方法 */
     public static String testRestTemplate() {
         
         RestTemplate template = new RestTemplate();
         String url = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071";
         // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
         MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
         paramMap.add("commentId", "13026194071");
         // 1、使用postForObject请求接口
         String result = template.postForObject(url, paramMap, String.class);
         logger.info("result1==================" + result);
         // 2、使用postForEntity请求接口
         HttpHeaders headers = new HttpHeaders();
         HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap,headers);
         ResponseEntity<String> response2 = template.postForEntity(url, httpEntity, String.class);
         logger.info("result2====================" + response2.getBody());
         // 3、使用exchange请求接口
         ResponseEntity<String> response3 = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
         logger.info("result3====================" + response3.getBody());
         return response3.getBody();
    }
    
    public static void newFile() {
         String path = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071";
         File  file = new File(path);
         logger.info("createNewFile start");  
         FileUtils.newFile(file);
         logger.info("createNewFile end");
    }
    
    public static void ignoreX509TrustManager() throws Exception{
        SSLContext sslcontext = SSLContext.getInstance("SSL","SunJSSE");
        sslcontext.init(null, new TrustManager[]{new IgnoreX509TrustManager()}, new java.security.SecureRandom());
        URL serverUrl = new URL("https://www.baidu.com");
        HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
            public boolean verify(String s, SSLSession sslsession) {
                System.out.println("WARNING: Hostname is not matched for cert.");
                return true;
            }
        };
        //Https协议网站皆能正常访问，同第一种情况
        HttpsURLConnection conn = (HttpsURLConnection) serverUrl.openConnection();
        conn.setHostnameVerifier(ignoreHostnameVerifier);
        conn.setSSLSocketFactory(sslcontext.getSocketFactory());
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(1500);
        conn.setReadTimeout(1000);
        conn.setRequestProperty("Content-type", "application/json");
        //必须设置false，否则会自动redirect到重定向后的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();
        String result = getReturn(conn);
        System.out.println(result);
    }
    
    public static void getPath() {
        logger.info(PathUtils.getPathByClass());
        logger.info(PathUtils.getPathByDefaultClassLoader());
        logger.info(PathUtils.getPathByProperty());
    }
    
    public static void encrpt() {
        String content = "张三";
        String password = "B49117B4A4964EFF";
        String iv = "E8CF95FF1D2A4A74";
        testAESEncryptByCBC(content, password, iv);
        testAESEncryptByECB(content, password);
    }
    private static void testAESEncryptByCBC(String content, String password, String iv) {
        String encryptContent = AESUtils.encrypt(content, password, iv);
        System.out.println("原字符串: " + content);
        System.out.println("加密后字符串: " + encryptContent);
        String decryptContent = AESUtils.decrypt(encryptContent, password, iv);
        System.out.println("解密后字符串: " + decryptContent);
    }
    private static void testAESEncryptByECB(String content, String password) {
        String encryptContent = AESUtils.encrypt(content, password);
        System.out.println("原字符串: " + content);
        System.out.println("加密后字符串: " + encryptContent);
        String decryptContent = AESUtils.decrypt(encryptContent, password);
        System.out.println("解密后字符串: " + decryptContent);
    }
    
    public static void jasyptEncrypt() {
        // 加密
        System.out.println(JasyptUtils.encryptPwd("joe-test-demo", "root"));
        System.out.println(JasyptUtils.encryptPwd("joe-test-demo", "postgres"));
        // 解密
        System.out.println(JasyptUtils.decyptPwd("joe-test-demo", "9XNfyAmJJD6IfFnDg3nrsA=="));
        System.out.println(JasyptUtils.decyptPwd("joe-test-demo", "bgycBQxsrtvJcyqyUmOeUixA2hZdCOVX"));
    }
    
    public static void regularUtils() {
        System.out.println("17630800244 isMoblie----" + RegularUtils.isMoblie("13430800244"));
        System.out.println("17630800244 isQQ----" + RegularUtils.isQQ("17730800244"));
        System.out.println("17630800244 isIP----" + RegularUtils.isIP("17630800244"));
        System.out.println("17630800244 isPhoneValidateCode----" + RegularUtils.isPhoneValidateCode("14730800244"));
        System.out.println("17630800244 isEmail----" + RegularUtils.isEmail("18330800244"));
        System.out.println("17630800244 isDate----" + RegularUtils.isDate("19330800244"));
        System.out.println("17630800244 isDateSimple----" + RegularUtils.isDateSimple("1333000244"));
    }
    
    public static void x509TrustManager() throws Exception{
        SSLContext sslcontext = SSLContext.getInstance("SSL","SunJSSE");
        sslcontext.init(null, new TrustManager[]{new MyX509TrustManager()}, new java.security.SecureRandom());
        URL serverUrl = new URL("https://www.baidu.com");
        HttpsURLConnection conn = (HttpsURLConnection) serverUrl.openConnection();
        conn.setSSLSocketFactory(sslcontext.getSocketFactory());
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //必须设置false，否则会自动redirect到重定向后的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();
        String result = getReturn(conn);
        System.out.println(result);
    }
 
    /** getReturn 请求 url 获取返回的内容  */
    public static String getReturn(HttpsURLConnection connection) throws IOException{
        StringBuffer buffer = new StringBuffer();
        //将返回的输入流转换成字符串
        try(InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);){
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            String result = buffer.toString();
            return result;
        }
    }
    public static void XMLParser() {
        XMLUtils.DomParser();
        XMLUtils.SAXParser();
        XMLUtils.JDOMParser();
        XMLUtils.DOM4jParser();
    }
    public static void imageUtils() {
        ImageUtils.changeImageFormat("C:\\Users\\EDZ\\Desktop\\test\\jpg\\jpg_test1.jpg","C:\\Users\\EDZ\\Desktop\\test\\tiff\\","sample-convertor-bpm.tiff");
        ImageUtils.changeImageFormatFromFile("C:\\Users\\EDZ\\Desktop\\test\\jpg\\jpg_test1.jpg","C:\\Users\\EDZ\\Desktop\\test\\tiff\\","sample-convertor-tiff-0.5-insert.jpg");
        ImageUtils.getReaderFormatNames();
    }
    public static void zipUtils() throws Exception {
        /** 测试压缩方法1 */
        FileOutputStream fos1 = new FileOutputStream(new File(DirectoryConst.FILE_TEST_DIRECTORY_ABSOLUTE_1));
        ZipUtils.toZip(DirectoryConst.FILE_TEST_DIRECTORY_ABSOLUTE_SAVE, fos1, true);
        /** 测试压缩方法2 */
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(DirectoryConst.FILE_TEST_DIRECTORY_ABSOLUTE_1));
        fileList.add(new File(DirectoryConst.FILE_TEST_DIRECTORY_ABSOLUTE_2));
        FileOutputStream fos2 = new FileOutputStream(new File(DirectoryConst.FILE_TEST_DIRECTORY_ABSOLUTE_SAVE + "/mytest02.zip"));
        ZipUtils.toZip(fileList, fos2);
    }
    public static void threadsCopyFile() throws IOException {
        ThreadsCopyFile tcf = new ThreadsCopyFile();
        System.out.println(DateUtils.getCurrentTime1());
        String sourcePath = "C:\\Users\\EDZ\\Desktop\\test file\\抖音.mp4";
        String targetPath = "C:\\Users\\EDZ\\Desktop\\tttt32452.mp4";
        tcf.startCopy(sourcePath, targetPath,10);
        System.out.println(DateUtils.getCurrentTime1());
    }
    public static void pathUtils() {
        logger.info(PathUtils.getPathByClass());
        logger.info(PathUtils.getPathByDefaultClassLoader());
        logger.info(PathUtils.getPathByProperty());
    }
    // 执行main方法以生成代码
    public static void GeneratorSqlmap() {
        try {
            GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void handleDpi() {  
        String path = "C:/Users/Administrator/Desktop/test/smaple-tiff.tiff"; 
        String target = "C:/Users/Administrator/Desktop/test/test-5.tiff";
        File file1 = new File(path); 
        File file2 = new File(target);
        DPIHandleHelper.handleDpi(file1,file2); 
        System.out.println("success");
    }
    // main测试
    public static void PdfUtils() throws Exception {
            logger.info("生成PDF开始！");
            String storePath = "C:\\Users\\EDZ\\Desktop\\test\\pdf\\PDFDemo1.pdf";
            // 1.生成 PDF文件
            PdfUtils.createPdf(storePath);
            logger.info("生成PDF结束！");
            // 2.合并 PDF文件
            String[] files = {"C:\\Users\\EDZ\\Desktop\\test\\pdf\\PDFDemo1.pdf", "C:\\Users\\EDZ\\Desktop\\test\\pdf\\PDFDemo.pdf"};
            String savepath = "C:\\Users\\EDZ\\Desktop\\test\\pdf\\combinePDFDemo.pdf";
            PdfUtils.mergePdfFiles(Arrays.asList(files), savepath);
    }
    public static void excelUtilX() {
        String  sheetName = "test";
        String  title = "2018年度能源科技进步奖";
        String[]  headers = {"测试标题列1","测试标题列2","测试标题列3","测试标题列4","测试标题列5","测试标题列6","测试标题列7","测试标题列8","测试标题列9"};
        List<Object> list= new ArrayList<>();
        ExcelUtilX.export(sheetName,title,headers, list);
        ExcelUtilX.exportX(sheetName,title,headers);
    }
    public static void treeUtil() {
        List<DefaultTree> treeTempList = new ArrayList<>();

        DefaultTree temp;

        for(int i=0; i<3; i++) {
            temp = new DefaultTree();
            temp.setId(i + "");
            temp.setLabel("第一级");
            treeTempList.add(temp);
        }

        for(int i=0; i<3; i++) {
            temp = new DefaultTree();
            temp.setId(0 + "" + i);
            temp.setLabel("第二级");
            temp.setPid(treeTempList.get(0).getId());
            treeTempList.add(temp);
        }

        for(int i=0; i<3; i++) {
            temp = new DefaultTree();
            temp.setId(0 + "" + 0 + "" + i);
            temp.setLabel("第三级");
            temp.setPid(treeTempList.get(3).getId());
            treeTempList.add(temp);
        }

        for(int i=0; i<3; i++) {
            temp = new DefaultTree();
            temp.setId(1 + "" + i);
            temp.setLabel("第二级");
            temp.setPid(treeTempList.get(1).getId());
            treeTempList.add(temp);
        }

        System.out.println(TreeUtil.build(treeTempList, 0+""));
    }
    public static void myX509TrustManager() throws Exception{
        SSLContext sslcontext = SSLContext.getInstance("SSL","SunJSSE");
        sslcontext.init(null, new TrustManager[]{new MyX509TrustManager()}, new java.security.SecureRandom());
        URL serverUrl = new URL("https://www.baidu.com");
        HttpsURLConnection conn = (HttpsURLConnection) serverUrl.openConnection();
        conn.setSSLSocketFactory(sslcontext.getSocketFactory());
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //必须设置false，否则会自动redirect到重定向后的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();
        String result = getReturn(conn);
        System.out.println(result);
    }
    public static void httpURLConnection() throws Exception{  
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
    public static void QRBarCodeUtil() throws MalformedURLException{
        
        //生成二维码
        //  要生成二维码的链接
        String urlSource = "https://www.baidu.com";
        //  指定路径：D:\User\Desktop\testQrcode
        String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
        File file = new File(path);
        //  指定二维码图片名字
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        QRBarCodeUtil.createCodeToFile(urlSource, file, fileName);        
        //解析二维码
        File localFile = new File("C:\\Users\\EDZ\\Desktop\\testQrcode\\20210421144951.jpg");
        String localQRcodeContent = QRBarCodeUtil.parseQRCodeByFile(localFile);
        System.out.println(localFile + " 二维码内容：" + localQRcodeContent);
        URL url = new URL("https://login.weixin.qq.com/qrcode/gfguvlNiDA==");
        String netQRcodeContent = QRBarCodeUtil.parseQRCodeByUrl(url);
        System.out.println(url + " 二维码内容：" + netQRcodeContent);
    }
    public static void bigFileReader() {
        BigFileReader.Builder builder = new BigFileReader.Builder("C:\\Users\\EDZ\\Desktop\\test\\json\\bigfile.json",new IHandle() {
            
            @Override
            public void handle(String line) {
                System.out.println(line);
            }
        });
        builder.withTreahdSize(10)
               .withCharset("GBK")
               .withBufferSize(1024*1024);
        BigFileReader bigFileReader = builder.build();
        bigFileReader.start();
    }
}

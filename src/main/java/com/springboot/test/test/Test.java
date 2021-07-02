package com.springboot.test.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.springboot.test.util.ImageUtils;
import com.springboot.test.util.PathUtils;
import com.springboot.test.util.RegularUtils;
import com.springboot.test.util.XMLUtils;
import com.springboot.test.util.encrypt.AESUtils;
import com.springboot.test.util.encrypt.JasyptUtils;
import com.springboot.test.util.file.FileUtils;
import com.springboot.test.util.http.IgnoreX509TrustManager;
import com.springboot.test.util.http.MyX509TrustManager;

import net.sf.json.JSONObject;

public class Test {
     
     private static Logger logger = LoggerFactory.getLogger(Test.class);
    
     public static void main(String[] args) throws Exception{  
         testRestTemplate();
         getAvailableCharsets();
         newFile();
         x509TrustManager();
         ignoreX509TrustManager();
         getPath();
         encrpt();
         jasyptEncrypt();
         regularUtils();
         XMLParser();
         imageUtils();
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
}

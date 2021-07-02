 package com.springboot.test.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
  
public class IgnoreX509TrustManager implements X509TrustManager {  
  
    @Override  
    public void checkClientTrusted(X509Certificate certificates[],String authType) throws CertificateException {  
    }  
  
    @Override  
    public void checkServerTrusted(X509Certificate[] ax509certificate,String s) throws CertificateException {  
    }  
  
    @Override  
    public X509Certificate[] getAcceptedIssuers() {  
        return null;  
    }
    
    public static void main(String[] args) throws Exception{
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
//        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
//        HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
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
    
    /*请求url获取返回的内容*/
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
}  

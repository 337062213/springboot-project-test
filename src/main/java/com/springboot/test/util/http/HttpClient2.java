 package com.springboot.test.util.http;

 import java.io.IOException;
 import java.io.UnsupportedEncodingException;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.http.HttpEntity;
 import org.apache.http.HttpResponse;
 import org.apache.http.NameValuePair;
 import org.apache.http.client.ClientProtocolException;
 import org.apache.http.client.config.RequestConfig;
 import org.apache.http.client.entity.UrlEncodedFormEntity;
 import org.apache.http.client.methods.CloseableHttpResponse;
 import org.apache.http.client.methods.HttpGet;
 import org.apache.http.client.methods.HttpPost;
 import org.apache.http.entity.StringEntity;
 import org.apache.http.impl.client.CloseableHttpClient;
 import org.apache.http.impl.client.HttpClientBuilder;
 import org.apache.http.impl.client.HttpClients;
 import org.apache.http.message.BasicNameValuePair;
 import org.apache.http.util.EntityUtils;
 import com.alibaba.fastjson.JSONObject;
/**
 * httpClient 4.5.3
 * @author EDZ
 * @date 2021/05/06
 */
 public class HttpClient2 {
     
     private static CloseableHttpClient httpClient = null;
     
     public static String doGet(String url) {
         CloseableHttpClient httpClient = null;
         CloseableHttpResponse response = null;
         String result = "";
         try {
             // 通过址默认配置创建一个httpClient实例
             httpClient = HttpClients.createDefault();
             // 创建httpGet远程连接实例
             HttpGet httpGet = new HttpGet(url);
             // 设置请求头信息，鉴权
             httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
             // 设置配置请求参数
             RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                     .setConnectionRequestTimeout(35000)// 请求超时时间
                     .setSocketTimeout(60000)// 数据读取超时时间
                     .build();
             // 为httpGet实例设置配置
             httpGet.setConfig(requestConfig);
             // 执行get请求得到返回对象
             response = httpClient.execute(httpGet);
             // 通过返回对象获取返回数据
             HttpEntity entity = response.getEntity();
             // 通过EntityUtils中的toString方法将结果转换为字符串
             result = EntityUtils.toString(entity);
         } catch (ClientProtocolException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             // 关闭资源
             if (null != response) {
                 try {
                     response.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
             if (null != httpClient) {
                 try {
                     httpClient.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }
         return result;
     }

     public static String doPost(String url, Map<String, Object> paramMap) {
         CloseableHttpClient httpClient = null;
         CloseableHttpResponse httpResponse = null;
         String result = "";
         // 创建httpClient实例
         httpClient = HttpClients.createDefault();
         // 创建httpPost远程连接实例
         HttpPost httpPost = new HttpPost(url);
         // 配置请求参数实例
         RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                 .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                 .setSocketTimeout(60000)// 设置读取数据连接超时时间
                 .build();
         // 为httpPost实例设置配置
         httpPost.setConfig(requestConfig);
         // 设置请求头
         httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
         // 封装post请求参数
         if (null != paramMap && paramMap.size() > 0) {
             List<NameValuePair> nvps = new ArrayList<NameValuePair>();
             // 通过map集成entrySet方法获取entity
             Set<Entry<String, Object>> entrySet = paramMap.entrySet();
             // 循环遍历，获取迭代器
             Iterator<Entry<String, Object>> iterator = entrySet.iterator();
             while (iterator.hasNext()) {
                 Entry<String, Object> mapEntry = iterator.next();
                 nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
             }

             // 为httpPost设置封装好的请求参数
             try {
                 httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
             } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
             }
         }
         try {
             // httpClient对象执行post请求,并返回响应参数对象
             httpResponse = httpClient.execute(httpPost);
             // 从响应对象中获取响应内容
             HttpEntity entity = httpResponse.getEntity();
             result = EntityUtils.toString(entity);
         } catch (ClientProtocolException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             // 关闭资源
             if (null != httpResponse) {
                 try {
                     httpResponse.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
             if (null != httpClient) {
                 try {
                     httpClient.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }
         return result;
     }
     /**
      * 获取第三方接口的token
      */
     public static String getToken() {
         String token = "";
         JSONObject object = new JSONObject();
         object.put("appid", "appid");
         object.put("secretkey", "secretkey");
         if (null == httpClient) {
             httpClient = HttpClientBuilder.create().build();
         }
         HttpPost httpPost = new HttpPost("https://tcc.taobao.com");
         httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
         try {
             StringEntity se = new StringEntity(object.toString());
             se.setContentEncoding("UTF-8");
             //发送json数据需要设置contentType
             se.setContentType("application/x-www-form-urlencoded");
             //设置请求参数
             httpPost.setEntity(se);
             HttpResponse response = httpClient.execute(httpPost);
             //这里可以把返回的结果按照自定义的返回数据结果，把string转换成自定义类
             //ResultTokenBO result = JSONObject.parseObject(response, ResultTokenBO.class);
             //把response转为jsonObject
             JSONObject result = (JSONObject) JSONObject.parseObject(String.valueOf(response));
             if (result.containsKey("token")) {
                 token = result.getString("token");
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
         return token;
     }
     /**
      * 测试
      */
     public static void test(String telephone) {

         JSONObject object = new JSONObject();
         object.put("telephone", telephone);

         //首先获取token
//         String tokenString = getToken();
         String response = doPost("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071", object);
         //如果返回的结果是list形式的，需要使用JSONObject.parseArray转换
         //List<Result> list = JSONObject.parseArray(response, Result.class);
         System.out.println(response);
     }

     public static void main(String[] args) {
         test("12345678910");
     }
 }

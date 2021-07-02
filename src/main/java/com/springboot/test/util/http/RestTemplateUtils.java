 package com.springboot.test.util.http;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtils {
     
     public static void main(String[] args) {
         RestTemplate template = new RestTemplate();
         String url = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071";
         // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
         MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
         paramMap.add("commentId", "13026194071");
         // 1、使用postForObject请求接口
         String result = template.postForObject(url, paramMap, String.class);
         System.out.println("result1==================" + result);
  
         // 2、使用postForEntity请求接口
         HttpHeaders headers = new HttpHeaders();
         HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap,headers);
         ResponseEntity<String> response2 = template.postForEntity(url, httpEntity, String.class);
         System.out.println("result2====================" + response2.getBody());
  
         // 3、使用exchange请求接口
         ResponseEntity<String> response3 = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
         System.out.println("result3====================" + response3.getBody());
    }
     public static String test() {
         RestTemplate template = new RestTemplate();
         String url = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071";
         // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
         MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
         paramMap.add("commentId", "13026194071");
  
         // 1、使用postForObject请求接口
         String result = template.postForObject(url, paramMap, String.class);
         System.out.println("result1==================" + result);
  
         // 2、使用postForEntity请求接口
         HttpHeaders headers = new HttpHeaders();
         HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap,headers);
         ResponseEntity<String> response2 = template.postForEntity(url, httpEntity, String.class);
         System.out.println("result2====================" + response2.getBody());
  
         // 3、使用exchange请求接口
         ResponseEntity<String> response3 = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
         System.out.println("result3====================" + response3.getBody());
         return response3.getBody();
    } 
    

}

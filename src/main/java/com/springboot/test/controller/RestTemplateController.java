 package com.springboot.test.controller;

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RestController;
 import org.springframework.web.client.RestTemplate;

 @RestController
 public class RestTemplateController {
     
     private static Logger logger = LoggerFactory.getLogger(RestTemplateController.class);
     
     @GetMapping("/msg")
     public String msg(){
        return "this is product' msg";    
     }
     
     @GetMapping("/getProductMsg")
     public String getProductMsg(){
         // 1、第一种方式(直接使用restTemplate，url写死)    
         RestTemplate restTemplate = new RestTemplate();   
         String response = restTemplate.getForObject("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071",String.class);  
         logger.info("response={}",response);   
         return response;
     }
}

 package com.springboot.test.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.springboot.test.model.po.User;
import io.swagger.annotations.ApiOperation;

 @Controller   
 @RequestMapping(value = "/server")    
 public class ServerInfoController extends BaseController {  
      
     @ApiOperation(value = "sayHelloWorld")
     @RequestMapping(path= {"/sayHelloWorld"},method=RequestMethod.GET,produces= {"text/plain"})
     public String sayHelloWorld() {
         return "HELLO JACK,I HAVE A SECRET TO TELL YOU THAT I LOVE YOU FOREVER";
     }
     @ApiOperation(value = "sayHello")
     @RequestMapping(path= {"/sayHello"},method=RequestMethod.POST,produces= {"application/json; charset=UTF-8"})
     @ResponseBody
     public HashMap<String, String> sayHello( @RequestBody @Validated User user) {
         HashMap<String, String> map = new HashMap<>();
         String name= user.getName();
         map.put("name",name);
         map.put("value", "Hello! "+name);
         map.put("secret", "I love you");
         return map;
     }
     @ApiOperation(value = "getUser")
     @RequestMapping(path= {"/getUser"},method=RequestMethod.POST,produces= {"application/json; charset=UTF-8"})
     @ResponseBody
     public CompletableFuture<String> getUser( @RequestBody @Validated List<User> user) {    
         CompletableFuture<String> future=CompletableFuture.supplyAsync(()->{            
               return "ok";
         });     
         future.whenComplete((result,exception)->{
             if(exception==null) {
                 future.complete(result);
                 return;
             }
             future.completeExceptionally(exception);
         });
         List<String> list = new ArrayList<>();
         for(int i = 0 ; i<user.size();i++) {
             String name = user.get(i).getName();
             list.add(name);
         }       
         return future;
     }
     
     @GetMapping("/getProductMsg")
     public String getProductMsg(){
         // 1、第一种方式(直接使用restTemplate，url写死)    
         RestTemplate restTemplate = new RestTemplate();   
         String response = restTemplate.getForObject("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071",String.class);  
         logger.info("response={}",response);   
         return response;
     }
  
     /** * 服务器信息 
      */   
     @RequestMapping(value = "")  
  
     public JSONObject serverInfo(HttpServletResponse response) {  
  
         Properties props = System.getProperties();            
  
         Map<String, String> map = System.getenv();            
  
         JSONObject jsonObject = new JSONObject();            
  
         jsonObject.put("server.user.name", map.get("USERNAME")); //用户名  
  
         jsonObject.put("server.computer.name", map.get("COMPUTERNAME")); //计算机名  
  
         jsonObject.put("server.computer.domain", map.get("USERDOMAIN")); //计算机域名            
  
         InetAddress addr = null;  
  
         try {  
  
             addr = InetAddress.getLocalHost();  
  
             jsonObject.put("server.ip", addr.getHostAddress()); //本机ip  
  
             jsonObject.put("server.host.name", addr.getHostName()); //本机主机名  
               
             jsonObject.put("server.user.home", props.getProperty("user.home")); //用户的主目录  
  
             jsonObject.put("server.user.dir", props.getProperty("user.dir")); //用户的当前工作目录  
  
         } catch (Exception e) {    
             logger.error(e.getMessage());    
         }              
         super.response2Client(response, jsonObject.toString());
         return jsonObject;
     }         
  
       
  
     /** 
      * JVM信息 
      * @throws UnknownHostException 
      */  
  
     @RequestMapping(value = "/jvm")  
  
     public JSONObject jvmInfo(HttpServletResponse response) throws UnknownHostException {  
           
         Runtime r = Runtime.getRuntime();  
         
         Properties props = System.getProperties(); 
         
         JSONObject jsonObject = new JSONObject();  
         
         jsonObject.put("jvm.memory.total", r.totalMemory()); //JVM可以使用的总内存  
  
         jsonObject.put("jvm.memory.free", r.freeMemory()); //JVM可以使用的剩余内存  
  
         jsonObject.put("jvm.processor.avaliable", r.availableProcessors()); //JVM可以使用的处理器个数  
  
         jsonObject.put("jvm.java.version", props.getProperty("java.version")); //Java的运行环境版本  
  
         jsonObject.put("jvm.java.vendor", props.getProperty("java.vendor")); //Java的运行环境供应商  
  
         jsonObject.put("jvm.java.home", props.getProperty("java.home")); //Java的安装路径  
  
         jsonObject.put("jvm.java.specification.version", props.getProperty("java.specification.version")); //Java运行时环境规范版本  
  
         jsonObject.put("jvm.java.class.path", props.getProperty("java.class.path")); //Java的类路径  
  
         jsonObject.put("jvm.java.library.path", props.getProperty("java.library.path")); //Java加载库时搜索的路径列表  
  
         jsonObject.put("jvm.java.io.tmpdir", props.getProperty("java.io.tmpdir")); //Java默认的临时文件路径  
  
         jsonObject.put("jvm.java.ext.dirs", props.getProperty("java.ext.dirs")); //Java扩展目录的路径
         String json = jsonObject.toJSONString();
         JSONObject.parseObject(json).forEach((key, value)->System.out.println(key+":"+value));
         return jsonObject;
     }            
 } 

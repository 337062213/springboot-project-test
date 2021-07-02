 package com.springboot.test.controller;
 import java.io.IOException;
 import java.io.PrintWriter;
 import javax.servlet.http.HttpServletResponse;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import com.springboot.test.model.HttpResult;
 import com.springboot.test.model.ResultCode;

 /**
  * @Author: Sawyer
  * @Description: 基础controller，用来包装http返回对象
  * @Date: Created in 上午10:43 17/8/11
  */
 public abstract class BaseController {
     
     protected Logger logger = LoggerFactory.getLogger(BaseController.class);

     /**
      * responseOK 默认成功返回
      * @param data
      * @return HttpResult<T>
      */
     protected <T> HttpResult<T> responseOK(T data) {
         HttpResult<T> restResult = new HttpResult<>();
         restResult.setSuccess(true);
         restResult.setData(data);
         restResult.setCode(ResultCode.SUCCESS.getCode());
         restResult.setMessage(ResultCode.SUCCESS.getMessage());
         return restResult;
     }
    
     /**
      * responseOK 默认成功返回带消息
      * @param data
      * @param msg
      * @return HttpResult<T>
      */
     protected <T> HttpResult<T> responseOK(T data, String msg) {
         HttpResult<T> restResult = new HttpResult<>();
         restResult.setSuccess(true);
         restResult.setData(data);
         restResult.setCode(ResultCode.SUCCESS.getCode());
         restResult.setMessage(msg);
         return restResult;
     }
    
     /**
      * responseFail 默认失败返回, 不带参数
      * @return HttpResult<T>
      */
     protected <T> HttpResult<T> responseFail() {
         return responseFail(ResultCode.SYSTEM_ERROR);
     }
    
     /**
      * responseFail 默认失败返回, 带信息
      * @param message 
      * @return HttpResult<T>
      */
     protected <T> HttpResult<T> responseFail(String message) {
         return responseFail(ResultCode.SYSTEM_ERROR, message);
     }
    
     /**
      * responseFail默认失败返回，带code
      * @param code
      * @return HttpResult<T>
      */
     protected <T> HttpResult<T> responseFail(ResultCode code) {
         return responseFail(code, code.getMessage());
     }
    
     /**
      * responseFail 失败返回 
      * @param code 错误Code
      * @param message 若为null，则使用Code对应的默认信息
      * @return HttpResult<T>
      */
     protected <T> HttpResult<T> responseFail(ResultCode code, String message) {
         HttpResult<T> restResult = new HttpResult<>();
         restResult.setSuccess(false);
         restResult.setCode(code.getCode());
         message = message == null ? code.getMessage() : message;
         restResult.setMessage(message);
         return restResult;
     }  
     protected void response2Client (HttpServletResponse response, String content) {                
         response.setContentType("text/html;charset=UTF-8");      
         PrintWriter out = null;             
         logger.info("response content -->" + content);                
         try {     
             out = response.getWriter();      
             out.print(content);     
             out.close();      
         } catch (IOException e) {      
             e.printStackTrace();      
         }            
     }    
 }

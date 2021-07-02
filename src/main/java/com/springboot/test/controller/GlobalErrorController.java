package com.springboot.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController  
public class GlobalErrorController implements ErrorController {  
  
  @Value("${server.error.path:${error.path:/error}}")  
  private String errorPath;   
  
  @Override  
  public String getErrorPath() {  
    return errorPath;  
  }   
  
  @RequestMapping("/admin/error")  
  public String error(HttpRequest request, Model model) {  
    return "screen/error";  
  }
  
  @RequestMapping("/404")  
  @ResponseStatus(value=HttpStatus.NOT_FOUND)  
  public String notFoundError() {  
      return "error/404";  
  }  
    
  @RequestMapping("/500")  
  @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)  
  public String interdError() {  
      return "error/500";  
  } 
  
}  

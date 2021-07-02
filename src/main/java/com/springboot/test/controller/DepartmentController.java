 package com.springboot.test.controller;
 
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;
 import com.springboot.test.annotation.Secret;
 import com.springboot.test.model.vo.DeptVO;
 import com.springboot.test.model.vo.ResultVO;

 @RestController
 @RequestMapping("dept")
 public class DepartmentController {
  
     @GetMapping("getDeptName/{id}")
     public ResultVO getDeptName(@PathVariable("id") String id){
         return new ResultVO(0,"查询成功","财务部" + id);
     }
  
     /** 注解在方法上，并传递了encryptStrName自己定义的加密字符串名称encryptJson */
     @Secret(value = DeptVO.class,encryptStrName = "encryptJson")
     @PostMapping("addDept")
     public ResultVO addDept(@RequestBody DeptVO dept){
         return new ResultVO(0,"新增成功",dept);
     }
     
 }

 package com.springboot.test.service;

 import javax.jws.WebMethod;
 import javax.jws.WebService;

 @WebService(targetNamespace = "http://service.test.springboot.com/wsdl")
 public interface IbillService {
     @WebMethod
     String getName(String name);
 }

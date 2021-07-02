 package com.springboot.test.service;

import javax.jws.WebService;

 @WebService(targetNamespace = "http://service.test.springboot.com/wsdl")
 public interface BillService {

     String getName(String name);
 }

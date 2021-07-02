 package com.springboot.test.service.impl;

import javax.jws.WebService;
import org.springframework.stereotype.Component;
import com.springboot.test.service.IbillService;

@WebService(endpointInterface = "com.springboot.test.service.IbillService",
targetNamespace = "http://service.test.springboot.com/wsdl",serviceName = "BillServiceImpl" ,portName = "IbillService")
@Component
public class BillServiceImpl implements IbillService {

     @Override
     public String getName(String name) {
         return "hello:" + name;
     }
 }

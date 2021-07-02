 package com.springboot.test.test;

import java.time.Instant;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.springboot.test.model.po.User;
import com.springboot.test.model.vo.UserIdRequest;
import com.springboot.test.model.vo.UserResponse;

public class WebServiceClient {

     public static void main1(String[] args) {
         // 创建动态客户端
         JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
         Client client = dcf.createClient("http://localhost:8084/webservice/BillServiceImpl?wsdl");
         Object[] objects = new Object[0];
         try {
             objects = client.invoke("getName", "jack");
             System.out.println("返回数据:" + objects[0]);
         } catch (java.lang.Exception e) {
             e.printStackTrace();
         }
     }
     
     public static void main(String[] args) {
         WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
  
         Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
         jaxb2Marshaller.setClassesToBeBound(UserIdRequest.class, UserResponse.class, User.class);
  
         webServiceTemplate.setMarshaller(jaxb2Marshaller);
         webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
  
         //构造 SOAP 请求
         UserIdRequest userIdRequest = new UserIdRequest();
         userIdRequest.setUserId("1");
         userIdRequest.setTimeStamp(Instant.now().toEpochMilli());
  
         /**
          * http://localhost:8080/test/ws/my：访问请求地址
          * http://localhost:8080/test/ws/my.wsdl：这里出现的是xml文件内容
          */
         UserResponse userResponse = (UserResponse) webServiceTemplate.marshalSendAndReceive(
                 "http://localhost:8080/test/ws/my", userIdRequest);
  
         System.out.println(userResponse);
     }
 }

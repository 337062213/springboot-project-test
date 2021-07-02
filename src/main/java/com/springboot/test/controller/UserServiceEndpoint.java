 package com.springboot.test.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.springboot.test.model.po.User;
import com.springboot.test.model.po.UserRepository;
import com.springboot.test.model.vo.UserIdRequest;
import com.springboot.test.model.vo.UserResponse;

@Endpoint
public class UserServiceEndpoint {
    private UserRepository userRepository;
    @Autowired
    public UserServiceEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
 
    /**
     * namespace = "http://segmentfault.com/schemas"：同user.xsd文件中的targetNamespace属性
     * @ResponsePayload类似于@ResponseBody注解
     * @RequestPayload类似于@RequestBody注解
     */
    @PayloadRoot(namespace = "http://segmentfault.com/schemas", localPart = "UserIdRequest")
    @ResponsePayload
    public UserResponse getUser(@RequestPayload UserIdRequest userIdRequest){
        String userId = userIdRequest.getUserId();
        long timeStamp = userIdRequest.getTimeStamp();
        /**
         * JDK1.8出品：线程安全
         */
        Instant instant = Instant.ofEpochMilli(timeStamp);
        //转换成本地时区
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
 
        System.out.println("web service 用户ID：" + userId + "，请求的时间：" + zonedDateTime);
        User user = userRepository.findById(userId);
        UserResponse userResponse = new UserResponse();
        userResponse.setUser(user);
        userResponse.setTimeStamp(Instant.now().toEpochMilli());
        return userResponse;
    }
}

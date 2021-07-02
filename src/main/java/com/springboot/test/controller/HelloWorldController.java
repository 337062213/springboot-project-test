package com.springboot.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.test.model.po.User;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "hello")
public class HelloWorldController {
    
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
}

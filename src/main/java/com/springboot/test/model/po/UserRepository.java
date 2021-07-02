 package com.springboot.test.model.po;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private Map<String, User> cachedUsers = new HashMap<>();
    @PostConstruct
    public void init(){
        User user1 = createUser("1", "潘畅", 25);
        User user2 = createUser("2", "刘德华", 26);
        User user3 = createUser("3", "黄晓明", 27);
        /**
         * 知识点：
         */
        cachedUsers.put("1", user1);
        cachedUsers.put("2", user2);
        cachedUsers.put("3", user3);
    }
    private User createUser(String id, String name, Integer age){
        User user = new User();
        user.setFid(id);
        user.setName(name);
        user.setAge(age);
 
        return user;
    }
 
    public User findById(String id){
        return cachedUsers.get(id);
    }
}

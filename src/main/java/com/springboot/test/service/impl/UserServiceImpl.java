package com.springboot.test.service.impl;

import com.springboot.test.model.Page;
import com.springboot.test.model.po.User;
import com.springboot.test.model.vo.UserGroupVo;
import com.springboot.test.service.IUserService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    
    @Value("${spring.datasource.active}")
    private String datasource;

    @Autowired
    private com.springboot.test.mapper.one.UserMapper1 userMapper1;
    
    @Autowired
    private com.springboot.test.mapper.two.UserMapper2 userMapper2;

    @Override
    public User insertUser(User user) {
        user.setFid(UUID.randomUUID().toString().replaceAll("-",""));
        Date date = new Date();
        user.setCreatTime(date);
        user.setUpdateTime(date);
        if(StringUtils.isNotEmpty(datasource) && "one".equals(datasource)) {
            userMapper1.insertUser(user);
            return userMapper1.findUserById(user.getFid());
        }else {
            userMapper2.insertUser(user);
            return userMapper2.findUserById(user.getFid());
        }
    }

    @Override
    public Integer deleteUser(String uId) {
        if(StringUtils.isNotEmpty(datasource) && "one".equals(datasource)) {
            return userMapper1.deleteUser(uId);
        }else {
            return userMapper2.deleteUser(uId);
        }
    }

    @Override
    public User updateUser(User user) {
        user.setUpdateTime(new Date());
        if(StringUtils.isNotEmpty(datasource) && "one".equals(datasource)) {
            userMapper1.updateUser(user);
            return userMapper1.findUserById(user.getFid());
        }else {
            userMapper2.updateUser(user);
            return userMapper2.findUserById(user.getFid());
        }
    }

    @Override
    public User findUserById(String uId) {
        if(StringUtils.isNotEmpty(datasource) && "one".equals(datasource)) {
            return userMapper1.findUserById(uId);
        }else {
            return userMapper2.findUserById(uId);
        }
    }

    @Override
    public List<User> findAllUser(Page page) {
        if(StringUtils.isNotEmpty(datasource) && "one".equals(datasource)) {
            return userMapper1.findAllUser(page);
        }else {
            return userMapper2.findAllUser(page);
        }
    }

    @Override
    public UserGroupVo findUserGroupVo(String uId) {
        if(StringUtils.isNotEmpty(datasource) && "one".equals(datasource)) {
            return userMapper1.findUserGroupVo(uId);
        }else {
            return userMapper2.findUserGroupVo(uId);
        }
    }

    @Override
    public List<User> findUserByCondition(User user) {
        if(StringUtils.isNotEmpty(datasource) && "one".equals(datasource)) {
            List<User> userList = userMapper1.findUserByCondition(user);
            return userList;
        }else {
            List<User> userList = userMapper2.findUserByCondition(user);
            return userList;
        }
    }

    @Override
    public List<User> findAllUser(String name, String gid) {
        if(StringUtils.isNotEmpty(datasource) && "one".equals(datasource)) {
            return userMapper1.findAllUserTotal(name, gid);
        }else {
            return userMapper2.findAllUserTotal(name, gid);
        }
    }
}

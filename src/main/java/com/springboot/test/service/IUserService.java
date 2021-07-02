package com.springboot.test.service;

import com.springboot.test.model.Page;
import com.springboot.test.model.po.User;
import com.springboot.test.model.vo.UserGroupVo;
import java.util.List;

public interface IUserService {

    User insertUser(User user);

    Integer deleteUser(String uId);

    User updateUser(User user);

    User findUserById(String uId);

    List<User> findAllUser(Page page);

    UserGroupVo findUserGroupVo(String uId);

    List<User> findUserByCondition(User user);

    List<User> findAllUser(String name, String gid);
}

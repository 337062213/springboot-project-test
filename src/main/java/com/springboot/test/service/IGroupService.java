package com.springboot.test.service;

import java.util.List;

import com.springboot.test.model.po.Group;
import com.springboot.test.model.vo.GroupUserVo;

public interface IGroupService {
    
    Group insertGroup(Group group);
    
    Integer deleteGroup(String gId);
    
    Group updateGroup(Group group);
    
    Group findGroupById(String gId);
    
    List<Group> findAllGroup();

    GroupUserVo findGroupUsersByGid(String gid);
    
}

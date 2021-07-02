package com.springboot.test.service.impl;

import com.springboot.test.mapper.one.GroupMapper1;
import com.springboot.test.model.po.Group;
import com.springboot.test.model.vo.GroupUserVo;
import com.springboot.test.service.IGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GroupServiceImpl implements IGroupService {

    @Autowired
    private GroupMapper1 groupMapper;

    @Override
    public Group insertGroup(Group group) {
        Date date = new Date();
        group.setCreatTime(date);
        group.setUpdateTime(date);
        group.setId(UUID.randomUUID().toString().replaceAll("-",""));
        groupMapper.insertGroup(group);
        return groupMapper.findGroupById(group.getId());
    }

    @Override
    public Integer deleteGroup(String uId) {
        return groupMapper.deleteGroup(uId);
    }

    @Override
    public Group updateGroup(Group group) {
        group.setUpdateTime(new Date());
        group.setId(UUID.randomUUID().toString().replaceAll("-",""));
        groupMapper.updateGroup(group);
        return groupMapper.findGroupById(group.getId());
    }

    @Override
    public Group findGroupById(String uId) {
        return groupMapper.findGroupById(uId);
    }

    @Override
    public List<Group> findAllGroup() {
        return groupMapper.findAllGroup();
    }

    @Override
    public GroupUserVo findGroupUsersByGid(String gid) {

        GroupUserVo groupUserVo = groupMapper.findGroupUsersByGid(gid);
        return groupUserVo;

    }
}

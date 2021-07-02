package com.springboot.test.model.vo;

import java.util.List;

import com.springboot.test.model.po.User;

public class GroupUserVo {

    private String gid;

    private String groupName;

    private List<User> users;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "GroupUserVo{" +
                "gid=" + gid +
                ", groupName='" + groupName + '\'' +
                ", users=" + users +
                '}';
    }
}

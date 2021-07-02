package com.springboot.test.model.vo;

public class UserGroupVo {

    private String gid;

    private String groupName;

    private String uid;

    private String name;

    private String age;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserGroupVo{" +
                "gid=" + gid +
                ", groupName='" + groupName + '\'' +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

package com.springboot.test.model.po;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;

@Repository
public class Group {

    private String id;

    private String groupName;
    
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date creatTime; 
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "Group [id=" + id + ", groupName=" + groupName + ", creatTime=" + creatTime + ", updateTime="
            + updateTime + "]";
    }
}

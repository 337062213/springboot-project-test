package com.springboot.test.model.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="User",description="用户model")
@Repository
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(name = "fid", value = "用户id", example="18f36b94852186b4ae998581a9140b51")
	private String fid;

	@ApiModelProperty(name = "name", value = "用户姓名", example="张三")
	private String name;
	
	@ApiModelProperty(name = "password", value = "用户密码", example="admin")
	private String password;
	
    private Integer age;
    
    private String sex;
    
    private String address;

    private String gid;
    
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date creatTime; 
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    private transient String unicode;
    	
	
	public String getUnicode() {
        return unicode;
    }


    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }


    public User() {
        super();
    }
	

    public User(String fid) {
        super();
        this.fid = fid;
    }


    public User(String fid, String name) {
        super();
        this.fid = fid;
        this.name = name;
    }

    public User(String fid, String name, String password) {
        super();
        this.fid = fid;
        this.name = name;
        this.password = password;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
        return age;
    }


    public void setAge(Integer age) {
        this.age = age;
    }


    public String getSex() {
        return sex;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getGid() {
        return gid;
    }


    public void setGid(String gid) {
        this.gid = gid;
    }


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


    @Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + "]";
	}
	

}

 package com.springboot.test.model.vo;

 public class UserVO extends BaseVO {
     
    private static final long serialVersionUID = 2697548991142125603L;
    private Integer id;
    private String name;
    
    public UserVO() {
        super();
    }
    
    public UserVO(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
 }

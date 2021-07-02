 package com.springboot.test.model.vo;

import java.io.Serializable;

public class BaseVO implements Serializable {

    private static final long serialVersionUID = 2621582425315950312L;
    // 加密密文
    private String encryptStr;

    public String getEncryptStr() {
        return encryptStr;
    }

    public void setEncryptStr(String encryptStr) {
        this.encryptStr = encryptStr;
    }     
     
 }

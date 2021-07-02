 package com.springboot.test.model;

 public enum ResultCode {
    SUCCESS("",""),
    SYSTEM_ERROR("","");
    private String code;
    private String message;
    
    private ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
     
}

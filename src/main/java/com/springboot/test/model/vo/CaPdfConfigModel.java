package com.springboot.test.model.vo;

public class CaPdfConfigModel {
    private Integer type;//域类型（1=文本域，2=复选框，3=单选框，4=签名域)
    private String fieldName;//字段名称
    private Float fieldX;
    private Float fieldY;
    private Float fieldW;//宽
    private Float fieldH;//高//开始页码
    private Integer startPage;
    private Integer endPage;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Float getFieldH() {
        return fieldH;
    }

    public void setFieldH(Float fieldH) {
        this.fieldH = fieldH;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getFieldX() {
        return fieldX;
    }

    public void setFieldX(Float fieldX) {
        this.fieldX = fieldX;
    }

    public Float getFieldY() {
        return fieldY;
    }

    public void setFieldY(Float fieldY) {
        this.fieldY = fieldY;
    }

    public Float getFieldW() {
        return fieldW;
    }

    public void setFieldW(Float fieldW) {
        this.fieldW = fieldW;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }
}
package com.springboot.test.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Tree",description="树形结构数据" )
public class Tree  extends BaseTree implements Serializable {
     
     private static final long serialVersionUID = 6995928021657013792L;

     @ApiModelProperty(name = "iconCls", value = "节点样式，默认即可", example="folder")
     private String iconCls = "folder";

     @ApiModelProperty(name = "isLeaf", value = "是否为叶子节点，true表示是叶子节点，false表示不是叶子节点", example="false")
     private Boolean isLeaf = true;

     @ApiModelProperty(name = "expanded", value = "是否展开，默认true,展开", example="true")
     private Boolean expanded = true;
 
     @ApiModelProperty(name = "sort", value = "序号",example="1")
     private int sort;

     /**
      * @取得iconCls的值
      * @return the iconCls
      */
     public String getIconCls() {
         return iconCls;
     }

     /**
      * @设置iconCls的值
      * @param iconCls the iconCls to set
      */
     public void setIconCls(String iconCls) {
         this.iconCls = iconCls;
     }

     /**
      * @取得isLeaf的值
      * @return the isLeaf
      */
     public Boolean getIsLeaf() {
         return isLeaf;
     }

     /**
      * @设置isLeaf的值
      * @param isLeaf the isLeaf to set
      */
     public void setIsLeaf(Boolean isLeaf) {
         this.isLeaf = isLeaf;
     }

     /**
      * @取得expanded的值
      * @return the expanded
      */
     public Boolean getExpanded() {
         return expanded;
     }

     /**
      * @设置expanded的值
      * @param expanded the expanded to set
      */
     public void setExpanded(Boolean expanded) {
         this.expanded = expanded;
     }

}

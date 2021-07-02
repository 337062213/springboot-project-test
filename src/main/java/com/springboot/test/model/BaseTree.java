package com.springboot.test.model;

import java.io.Serializable;
import java.util.List;

public class BaseTree  implements Serializable {
     
     /**
     *
     */
     private static final long serialVersionUID = 6995928021657013792L;

     private String id;//节点id

     private String parentId;//节点父id

     private List<BaseTree> children;//孩子节点

     private String title;//节点名称

     /**
      * @取得id的值
      * @return the id
      */
     public String getId() {
         return id;
     }

     /**
      * @设置id的值
      * @param id the id to set
      */
     public void setId(String id) {
         this.id = id;
     }

     /**
      * 取得title的值
      *
      * @return  title值
      */

     public String getTitle() {
         return title;
     }

     /**
      * 设定title的值
      *
      * @param  title 设定值
      */
     public void setTitle(String title) {
         this.title = title;
     }

     /**
      * @取得parentId的值
      * @return the parentId
      */
     public String getparentId() {
         return parentId;
     }

     /**
      * @设置parentId的值
      * @param parentId the parentId to set
      */
     public void setparentId(String parentId) {
         this.parentId = parentId;
     }


     /**
      * @取得children的值
      * @return the children
      */
     public List<BaseTree> getChildren() {
         return children;
     }

     /**
      * @设置children的值
      * @param children the children to set
      */
     public void setChildren(List<BaseTree> children) {
         this.children = children;
     }

}

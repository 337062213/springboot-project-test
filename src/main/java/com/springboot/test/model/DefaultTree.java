package com.springboot.test.model;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwj
 * @version V1.0
 * @Description: 默认树
 * @date 2018/1/27.
 */
public class DefaultTree {


    private String id;

    private String pid;

    private String label;

    private List<DefaultTree> children =new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<DefaultTree> getChildren() {
        return children;
    }

    public void setChildren(List<DefaultTree> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "DefaultTree{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}

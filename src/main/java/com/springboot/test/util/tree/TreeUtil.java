package com.springboot.test.util.tree;

import org.springframework.util.CollectionUtils;
import com.springboot.test.model.BaseTree;
import com.springboot.test.model.DefaultTree;
import com.springboot.test.model.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtil {
    
    /** transfer List<T> to  Tree
     * @param list 需要格式化的list
     * @param flag true 表示全部展开，其他 表示不展开
     * @param <T> 格式化的实体对象
     * @return Tree 格式化之后的list
     */
    public static <T extends Tree> List<T> formatTree(List<T> list, Boolean flag) {

        List<T> nodeList = new ArrayList<T>();  
        for(T node1 : list){  
            boolean mark = false;  
            for(T node2 : list){  
                if(node1.getparentId()!=null && node1.getparentId().equals(node2.getId())){ 
                    node2.setIsLeaf(false);
                    mark = true;  
                    if(node2.getChildren() == null) {
                        node2.setChildren(new ArrayList<BaseTree>());  
                    }
                    node2.getChildren().add(node1); 
                    if (flag) {
                        //默认已经全部展开
                    } else{
                        node2.setExpanded(false);
                    }
                    break;  
                }  
            }  
            if(!mark){  
                nodeList.add(node1);   
                if (flag) {
                    //默认已经全部展开
                } else{
                    node1.setExpanded(false);
                }
            }  
        }
        return nodeList;
    }

    public static List<DefaultTree> build(List<DefaultTree> trees , String pid) {
        Map<String, List<DefaultTree>> map = new HashMap<>();
        for (DefaultTree t : trees) {
            String parentId = t.getPid();
            List<DefaultTree> childrens = map.get(parentId);
            if (childrens == null) {
                childrens = new ArrayList<>();
                map.put(parentId, childrens);
            }
            childrens.add(t);
        }
        List<DefaultTree> treeList = new ArrayList<>();
        childBuild(map,pid,treeList);
        return  treeList ;
    }

    private  static <T extends DefaultTree> void  childBuild(Map<String, List<T>> map, String pid , List<DefaultTree> treeList){
        List<T> tList = map.get(pid);
        if(!CollectionUtils.isEmpty(tList)){
            for(T tree : tList){
                treeList.add(tree);
                childBuild(map ,tree.getId(), tree.getChildren());
            }
        }

    }

    public static void main(String[] args) {
        List<DefaultTree> treeTempList = new ArrayList<>();

        DefaultTree temp;

        for(int i=0; i<3; i++) {
            temp = new DefaultTree();
            temp.setId(i + "");
            temp.setLabel("第一级");
            treeTempList.add(temp);
        }

        for(int i=0; i<3; i++) {
            temp = new DefaultTree();
            temp.setId(0 + "" + i);
            temp.setLabel("第二级");
            temp.setPid(treeTempList.get(0).getId());
            treeTempList.add(temp);
        }

        for(int i=0; i<3; i++) {
            temp = new DefaultTree();
            temp.setId(0 + "" + 0 + "" + i);
            temp.setLabel("第三级");
            temp.setPid(treeTempList.get(3).getId());
            treeTempList.add(temp);
        }

        for(int i=0; i<3; i++) {
            temp = new DefaultTree();
            temp.setId(1 + "" + i);
            temp.setLabel("第二级");
            temp.setPid(treeTempList.get(1).getId());
            treeTempList.add(temp);
        }

        System.out.println(TreeUtil.build(treeTempList, 0+""));
    }

}

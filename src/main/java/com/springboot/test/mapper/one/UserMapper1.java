package com.springboot.test.mapper.one;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;
import com.springboot.test.model.Page;
import com.springboot.test.model.po.User;
import com.springboot.test.model.vo.UserGroupVo;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper1 {

    @Insert("insert into p_user(fid, name, age, gid, sex, address, creatTime, updateTime) values(#{fid}, #{name}, #{age}, #{gid}, #{sex}, #{address}, #{creatTime}, #{updateTime})")
    void insertUser(User user);

    @Delete("delete from p_user where fid = #{uId}")
    Integer deleteUser(String uId);

    @Update("update p_user set name=#{name}, age=#{age}, gid=#{gid}, sex=#{sex}, address=#{address}, updateTime=#{updateTime} where fid=#{fid}")
    int updateUser(User user);

    @Select("select * from p_user where fid = #{uId}")
    User findUserById(String uId);

    @Select("select * from p_user limit #{pageSize} offset #{pageSize} * #{pageNo}")
    List<User> findAllUser(Page page);

    @Select("select u.fid, u.name, u.age, g.name as groupName, g.id as gid from p_user u left join t_group g on u.gid=g.id where u.fid=#{id}")
    @Results({
            @Result(property = "uid",  column = "fid", javaType = String.class),
            @Result(property = "groupName",  column = "groupName", javaType = String.class),
    })
    UserGroupVo findUserGroupVo(String id);

    @Select("select * from p_user where gid=#{gid}")
    List<User> findUserByGid(String gid);


    /**
     *  @description 根据id age gid 查找用户
     */
    @SelectProvider(type = mybatisSql.class, method = "findUserByCondition")
    List<User> findUserByCondition(User user);

    @SelectProvider(type = mybatisSql.class, method = "findAllUserTotal")
    List<User> findAllUserTotal(@Param(value = "name") String name, @Param(value = "gid")String gid);


    class mybatisSql {
        public String findUserByCondition(User user) {

            return new SQL(){{
                SELECT("*");
                FROM("p_user");
                if(user.getFid() != null) {
                    WHERE("fid = #{fid}");
                }
                if(user.getAge() != null) {
                    WHERE("age = #{age}");
                }
                if(user.getGid() != null) {
                    WHERE("gid = #{gid}");
                }

            }}.toString();
        };

        public String findAllUserTotal(Map<String, Object> para) {

            return new SQL(){{
                SELECT("*");
                FROM("p_user");
                if(!ObjectUtils.isEmpty(para.get("name"))) {
                    WHERE("name like #{name}");
                }
                if(!ObjectUtils.isEmpty(para.get("gid"))) {
                    WHERE("gid = #{gid}");
                }
            }}.toString();
        }
    }

}

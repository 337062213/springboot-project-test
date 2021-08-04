 package com.springboot.test.util;
 
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
 import com.springboot.test.model.po.User;

 public class DBUtil {

     private static final String URL="jdbc:sqlite:C:/Users/EDZ/sqlite3/database/user.db?date_string_format=yyyy-MM-dd HH:mm:ss";
     private static final String NAME="root";
     private static final String PASSWORD="root";

     private static Connection conn=null;
     //静态代码块（将加载驱动、连接数据库放入静态块中）
     static{
         try {
             //1.加载驱动程序
             Class.forName("org.sqlite.JDBC");
             //2.获得数据库的连接
             conn = DriverManager.getConnection(URL, NAME, PASSWORD);
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
     //对外提供一个方法来获取数据库连接
     public static Connection getConnection(){
         return conn;
     }

     public static void main(String[] args) throws Exception{
         
         User user = new User();
         user.setFid(UUID.randomUUID().toString().replace("-", ""));
         user.setName("地狱男爵");
         user.setAge(24);
         user.setGid("2");
         user.setSex("F");
         user.setAddress("来自地狱深处的阿尔法神殿");
         user.setCreatTime(new Date());
         user.setUpdateTime(new Date());
//         add(user);
//         user.setFid("c3cd0feb-2cba-4f15-b16c-c817f7cfd9f5");
//         update(user);
//         delete("c3cd0feb-2cba-4f15-b16c-c817f7cfd9f5");
         System.out.println(((User)get("066daa8265c84930b190c2681323e948")).toString());
         List<Object> list = getAll();
         Iterator<Object> iterator = list.iterator();
         while(iterator.hasNext()){//如果对象中有数据，就会循环打印出来
             User object = (User)iterator.next();
             System.out.println(object.toString());
         }
     }
     
    /**
      * prepareStatement这个方法会将SQL语句加载到驱动程序conn集成程序中，但是并不直接执行
      * 而是当它调用execute()方法的时候才真正执行；
      * 上面SQL中的参数用?表示，相当于占位符，然后在对参数进行赋值。
      * 当真正执行时，这些参数会加载在SQL语句中，把SQL语句拼接完整才去执行。
      * 这样就会减少对数据库的操作
     */
     public static void add(Object object) throws Exception{
         Connection con=DBUtil.getConnection();//首先拿到数据库的连接
         String sql="" +
                 "insert into p_user"+
                 "(fid, name, age, gid, sex, address, creatTime, updateTime)"+
                 "values(?, ?, ?, ?, ?, ?, ?, ?)";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
         //预编译sql语句
         PreparedStatement psmt = con.prepareStatement(sql);
         User user = (User)object;
         //先对应SQL语句，给SQL语句传递参数
         psmt.setString(1, user.getFid());
         psmt.setString(2, user.getName());
         psmt.setInt(3, user.getAge());
         psmt.setString(4, user.getGid());
         psmt.setString(5, user.getSex());
         psmt.setString(6, user.getAddress());
         psmt.setDate(7, new java.sql.Date(user.getCreatTime().getTime()));
         psmt.setDate(8, null);
         //执行SQL语句
         psmt.execute();
     }
     
     //更新
     public static void update(Object object) throws SQLException{
         Connection con=DBUtil.getConnection();//首先拿到数据库的连接
         String sql="update p_user set name=?, age=?, gid=?, sex=?, address=?, updateTime=? where fid=?";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
         //预编译sql语句
         PreparedStatement psmt = con.prepareStatement(sql);
         User user = (User)object;
         //先对应SQL语句，给SQL语句传递参数
         psmt.setString(1, user.getName());
         psmt.setInt(2, user.getAge());
         psmt.setString(3, user.getGid());
         psmt.setString(4, user.getSex());
         psmt.setString(5, user.getAddress());
         psmt.setDate(6, new java.sql.Date(user.getUpdateTime().getTime()));
         psmt.setString(7, user.getFid());
         //执行SQL语句
         psmt.execute();
     }

     //删除
     public static void delete(String id) throws SQLException{
         Connection con=DBUtil.getConnection();
         String sql="delete from p_user where fid=?";
         //预编译sql语句
         PreparedStatement psmt = con.prepareStatement(sql);
         //先对应SQL语句，给SQL语句传递参数
         psmt.setString(1, id);
         //执行SQL语句
         psmt.execute();
     }

     //查询单个
     public static Object get(String id) throws SQLException{
         User user =new User();
         Connection con=DBUtil.getConnection();//首先拿到数据库的连接
         String sql="select * from p_user " + "where fid=?";
         //预编译sql语句
         PreparedStatement psmt = con.prepareStatement(sql);
         //先对应SQL语句，给SQL语句传递参数
         psmt.setString(1, id);
         //执行SQL语句
         /*psmt.execute();*///execute()方法是执行更改数据库操作（包括新增、修改、删除）;executeQuery()是执行查询操作
         ResultSet rs = psmt.executeQuery();//返回一个结果集
         //遍历结果集
         while(rs.next()){
             user.setFid(rs.getString("fid"));
             user.setName(rs.getString("name"));
             user.setAge(rs.getInt("age"));
             user.setGid(rs.getString("gid"));
             user.setSex(rs.getString("sex"));
             user.setAddress(rs.getString("address"));
             user.setCreatTime(rs.getDate("creatTime"));
             user.setUpdateTime(rs.getDate("updateTime"));
         }
         return user;
     }
     //查询所有
     public static List<Object> getAll() throws SQLException{
         List<Object> userList =new ArrayList<>();
         Connection con=DBUtil.getConnection();//首先拿到数据库的连接
         String sql="select * from p_user ";
         //预编译sql语句
         PreparedStatement psmt = con.prepareStatement(sql);
         //执行SQL语句
         /*psmt.execute();*///execute()方法是执行更改数据库操作（包括新增、修改、删除）;executeQuery()是执行查询操作
         ResultSet rs = psmt.executeQuery();//返回一个结果集
         //遍历结果集
         while(rs.next()){
             User user =new User();
             user.setFid(rs.getString("fid"));
             user.setName(rs.getString("name"));
             user.setAge(rs.getInt("age"));
             user.setGid(rs.getString("gid"));
             user.setSex(rs.getString("sex"));
             user.setAddress(rs.getString("address"));
             user.setCreatTime(rs.getDate("creatTime"));
             user.setUpdateTime(rs.getDate("updateTime"));
             userList.add(user);
         }
         return userList;
     }
 }

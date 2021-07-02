 package com.springboot.test.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.springboot.test.model.consistant.DirectoryConst;
import com.springboot.test.model.po.User;

public class TransientTest {
         
         public static void main(String[] args) {
             
             User user = new User();
             user.setName("Alexia");
             user.setUnicode("123456");
             
             System.out.println("read before Serializable: ");
             System.out.println("username: " + user.getName());
             System.err.println("unicode: " + user.getUnicode());
             
             try {
                 ObjectOutputStream os = new ObjectOutputStream(
                         new FileOutputStream(new File(DirectoryConst.FILE_TEST_DIRECTORY_ABSOLUTE_SAVE+"//user.txt")));
                 os.writeObject(user); // 将User对象写进文件
                 os.flush();
                 os.close();
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             try {
                 ObjectInputStream is = new ObjectInputStream(new FileInputStream(
                     new File(DirectoryConst.FILE_TEST_DIRECTORY_ABSOLUTE_SAVE+"//user.txt")));
                 user = (User) is.readObject(); // 从流中读取User的数据
                 is.close();
                 
                 System.out.println("\nread after Serializable: ");
                 System.out.println("username: " + user.getName());
                 System.err.println("unicode: " + user.getUnicode());
                 
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             } catch (ClassNotFoundException e) {
                 e.printStackTrace();
             }
         }
}

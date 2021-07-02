 package com.springboot.test.util.pdf;

import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.List;

public class SystemProperties {
     public static void main(String[] args) {
         getLocalGraphicsEnvironment(String[].class);
     }
    @SuppressWarnings("rawtypes")
    public static Object getLocalGraphicsEnvironment(Class clazz) {
         System.out.println(0);
         if(clazz.getName().equals("java.lang.String")) {
             System.out.println(1);
             return getLocalGraphicsEnvironmentByString();
         }else if(clazz.getName().equals("java.util.List")) {
             System.out.println(2);
             return getLocalGraphicsEnvironmentByList();
         }else if(clazz.getName().equals("[Ljava.lang.String;")) {
             System.out.println(3);
             return getLocalGraphicsEnvironmentByArray();
         }
         return null;
     }
     public static String getLocalGraphicsEnvironmentByString() {
         GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment(); //返回本地 GraphicsEnvironment 。
         String[] forName = e.getAvailableFontFamilyNames();
         System.out.println(Arrays.toString(forName));//返回包含在此所有字体系列名称的数组， GraphicsEnvironment本地化为默认的语言环境，如返回 Locale.getDefault() 。 
         for (int i = 0; i < forName.length; i++) {
             System.out.println(forName[i]);
         }
         return Arrays.toString(forName);
     }
     
     public static String[] getLocalGraphicsEnvironmentByArray() {
         GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment(); //返回本地 GraphicsEnvironment 。
         String[] forName = e.getAvailableFontFamilyNames();
         System.out.println(Arrays.toString(forName));//返回包含在此所有字体系列名称的数组， GraphicsEnvironment本地化为默认的语言环境，如返回 Locale.getDefault() 。 
         for (int i = 0; i < forName.length; i++) {
             System.out.println(forName[i]);
         }
         return forName;
     }
     
     public static List<String> getLocalGraphicsEnvironmentByList() {
         GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment(); //返回本地 GraphicsEnvironment 。
         String[] forName = e.getAvailableFontFamilyNames();
         System.out.println(Arrays.toString(forName));//返回包含在此所有字体系列名称的数组， GraphicsEnvironment本地化为默认的语言环境，如返回 Locale.getDefault() 。 
         for (int i = 0; i < forName.length; i++) {
             System.out.println(forName[i]);
         }
         return Arrays.asList(forName);
     }
}

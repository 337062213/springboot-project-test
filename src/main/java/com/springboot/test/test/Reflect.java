 package com.springboot.test.test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.springboot.test.model.po.User;

import groovy.lang.GroovyClassLoader;

public class Reflect {
    
     public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException, IOException, NoSuchFieldException{
         new User("1","a","1111");
         new User("1","b","2222");
         new User("2","c","3333");
         new User("3","d","4444");         
         GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
         //里面的文本是Java代码,但是我们可以看到这是一个字符串我们可以直接生成对应的Class<?>对象,而不需要我们写一个.java文件
         Class<?> clazz = groovyClassLoader.parseClass("package com.xxl.job.core.glue;\n" +
                 "\n" +
                 "public class Main {\n" +
                 "\n" +
                 "    public int age = 22;\n" +
                 "    \n" +
                 "    public void sayHello() {\n" +
                 "        System.out.println(\"年龄是:\" + age);\n" +
                 "    }\n" +
                 "}\n");
         Object obj = clazz.newInstance();
         Method method = clazz.getDeclaredMethod("sayHello");
         method.invoke(obj);
         groovyClassLoader.close();
         
         
         GroovyClassLoader groovyClassLoader1 = new GroovyClassLoader();
         //里面的文本是Java代码,但是我们可以看到这是一个字符串我们可以直接生成对应的Class<?>对象,而不需要我们写一个.java文件
         Class<?> clazz1 = groovyClassLoader1.parseClass("package com.springboot.test.model;\r\n" + 
             "\r\n" + 
             "import java.io.Serializable;\r\n" + 
             "\r\n" + 
             "import io.swagger.annotations.ApiModel;\r\n" + 
             "import io.swagger.annotations.ApiModelProperty;\r\n" + 
             "\r\n" + 
             "@ApiModel(value=\"User\",description=\"用户model\")\r\n" + 
             "public class User implements Serializable {\r\n" + 
             "    \r\n" + 
             "    /**\r\n" + 
             "     * \r\n" + 
             "     */\r\n" + 
             "    private static final long serialVersionUID = 1L;\r\n" + 
             "    \r\n" + 
             "    @ApiModelProperty(name = \"id\", value = \"用户id\", example=\"18f36b94852186b4ae998581a9140b51\")\r\n" + 
             "    private String id;\r\n" + 
             "\r\n" + 
             "    @ApiModelProperty(name = \"name\", value = \"用户姓名\", example=\"张三\")\r\n" + 
             "    private String name;\r\n" + 
             "    \r\n" + 
             "    @ApiModelProperty(name = \"password\", value = \"用户密码\", example=\"admin\")\r\n" + 
             "    private String password;\r\n" + 
             "    \r\n" + 
             "    \r\n" + 
             "    public User() {\r\n" + 
             "        super();\r\n" + 
             "    }\r\n" + 
             "    \r\n" + 
             "\r\n" + 
             "    public User(String id) {\r\n" + 
             "        super();\r\n" + 
             "        this.id = id;\r\n" + 
             "    }\r\n" + 
             "\r\n" + 
             "\r\n" + 
             "    public User(String id, String name) {\r\n" + 
             "        super();\r\n" + 
             "        this.id = id;\r\n" + 
             "        this.name = name;\r\n" + 
             "    }\r\n" + 
             "\r\n" + 
             "    public User(String id, String name, String password) {\r\n" + 
             "        super();\r\n" + 
             "        this.id = id;\r\n" + 
             "        this.name = name;\r\n" + 
             "        this.password = password;\r\n" + 
             "    }\r\n" + 
             "\r\n" + 
             "    public String getId() {\r\n" + 
             "        return id;\r\n" + 
             "    }\r\n" + 
             "\r\n" + 
             "    public void setId(String id) {\r\n" + 
             "        this.id = id;\r\n" + 
             "    }\r\n" + 
             "\r\n" + 
             "    public String getName() {\r\n" + 
             "        return name;\r\n" + 
             "    }\r\n" + 
             "\r\n" + 
             "    public void setName(String name) {\r\n" + 
             "        this.name = name;\r\n" + 
             "    }\r\n" + 
             "\r\n" + 
             "    public String getPassword() {\r\n" + 
             "        return password;\r\n" + 
             "    }\r\n" + 
             "\r\n" + 
             "    public void setPassword(String password) {\r\n" + 
             "        this.password = password;\r\n" + 
             "    }\r\n" + 
             "\r\n" + 
             "    @Override\r\n" + 
             "    public String toString() {\r\n" + 
             "        return \"User [name=\" + name + \", password=\" + password + \"]\";\r\n" + 
             "    }\r\n" + 
             "    \r\n" + 
             "\r\n" + 
             "}\r\n" + 
             "");
         Object obj1 = clazz1.newInstance();
         Method method10 = clazz1.getDeclaredMethod("setName", String.class);
         method10.invoke(obj1,"ZHANGSAN");
         Method method1 = clazz1.getDeclaredMethod("getName");
         String name = (String)method1.invoke(obj1);
         System.out.println(name);
         
         Field[] fields = clazz1.getFields();
         fields = clazz1.getDeclaredFields();
         System.out.println(Arrays.toString(fields));
         List<Field> list = Arrays.asList(fields);
         List<String> list1 = list.stream().
             map(item->item.getName()).collect(Collectors.toList());
         System.out.println(Arrays.toString(list1.toArray()));
         List<String> list2 = list.stream().
             filter(item->!item.getName().contains("$") && !item.getName().contains("serialVersionUID")&& !item.getName().contains("metaClass")).
             map(item->item.getName()).collect(Collectors.toList());
         System.out.println(Arrays.toString(list2.toArray()));
         System.out.println("对象含有城边变量有" + fields.length + "个");        
         
         Field field = clazz1.getDeclaredField("password");  
         field.setAccessible(true);//设置允许访问  
         field.set(obj1, "asdfasdf");  
         System.out.println(field.get(obj1));
         groovyClassLoader1.close();
     }
}

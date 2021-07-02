 package com.springboot.test.test;
 import java.lang.reflect.Method;
 import java.security.Timestamp;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import org.apache.commons.lang.StringUtils;
 import org.aspectj.lang.JoinPoint;
 import org.aspectj.lang.ProceedingJoinPoint;
 import org.aspectj.lang.annotation.After;
 import org.aspectj.lang.annotation.AfterReturning;
 import org.aspectj.lang.annotation.Around;
 import org.aspectj.lang.annotation.Aspect;
 import org.aspectj.lang.annotation.Before;
 import org.aspectj.lang.annotation.Pointcut;
 import org.aspectj.lang.reflect.MethodSignature;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Component;
 import com.springboot.test.annotation.Secret;
 import com.springboot.test.util.encrypt.AESUtils;
 import groovy.util.logging.Slf4j;

 @Aspect
 @Component
 @Slf4j
 public class SecretAOP {
    
     protected Logger logger = LoggerFactory.getLogger(SecretAOP.class);
     //是否进行加密解密，通过配置文件注入（不配置默认为true）
     @Value("${isSecret:true}")
     boolean isSecret;
     /** 定义切点,使用了@Secret注解的类 或 使用了@Secret注解的方法
      * @Within表示匹配类上的指定注解，
      * @annotation表示匹配方法上的指定注解
      * 
      */
     @Pointcut("@within(com.springboot.test.annotation.Secret) || @annotation(com.springboot.test.annotation.Secret)")
     public void pointcut(){
         
     }
     
     @Pointcut("execution(public * com.springboot.test.*.save*(..))")
     public void save(){
         
     }
     // 环绕切面
     @Before("pointcut()")
     public void before(JoinPoint point){
         String methodName = point.getSignature().getName();
         System.out.println("切入方法名字："+methodName);
         // 获取被代理方法参数
         Object[] args = point.getArgs();
         for(Object arg :args){
             System.out.println("切入方法的参数："+ arg);
         }
         // 获取被代理对象
         Object target = point.getTarget();
         // 获取通知签名
         MethodSignature signature = (MethodSignature )point.getSignature();
         try {
             // 获取被代理方法
             Method pointMethod = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
             // 获取被代理方法上面的注解@Secret
             Secret secret = pointMethod.getAnnotation(Secret.class);
             // 被代理方法上没有，则说明@Secret注解在被代理类上
             if(secret==null){
                 secret = target.getClass().getAnnotation(Secret.class);
             } 
             if(secret!=null){
                 // 获取注解上声明的加解密类
                 Class<?> clazz = secret.value();
                 // 获取注解上声明的加密参数名
                 String encryptStrName = secret.encryptStrName();  
                 for (int i = 0; i < args.length; i++) {
                     // 如果是clazz类型则说明使用了加密字符串encryptStr传递的加密参数
                     if(clazz.isInstance(args[i])){
                         Object cast = clazz.cast(args[i]);      //将args[i]转换为clazz表示的类对象
                         // 通过反射，执行getEncryptStr()方法，获取加密数据
                         Method getMethod = clazz.getMethod(getMethedName(encryptStrName));
                         getMethod.setAccessible(true);
                         // 执行方法，获取加密数据
                         String encryptStr = (String) getMethod.invoke(cast);
                         // 加密字符串是否为空
                         if(StringUtils.isNotBlank(encryptStr)){
                             // 解密
                             String json = AESUtils.decrypt(encryptStr);
                             Class<?> type = clazz.getDeclaredField(encryptStrName).getType();
                             Method setMethod = clazz.getMethod(setMethedName(encryptStrName),type);
                             setMethod.setAccessible(true);
                             if (type.isAssignableFrom(String.class)) {
                                 setMethod.invoke(args[i], json);
                             } else if (type.isAssignableFrom(int.class)|| type.isAssignableFrom(Integer.class)) {
                                 setMethod.invoke(args[i], Integer.parseInt(json));
                             } else if (type.isAssignableFrom(Double.class)|| type.isAssignableFrom(double.class)) {
                                 setMethod.invoke(args[i], Double.parseDouble(json));
                             } else if (type.isAssignableFrom(Boolean.class)|| type.isAssignableFrom(boolean.class)) {
                                  setMethod.invoke(args[i], Boolean.parseBoolean(json));
                             } else if (type.isAssignableFrom(Date.class)) {
                                  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                  setMethod.invoke(args[i], dateFormat.parse(json));
                             } else if (type.isAssignableFrom(Timestamp.class)) {
                                 SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
                                 setMethod.invoke(args[i], dateFormat.parse(json).toString());
                             }
                         }
                     }
                     // 其他类型，比如基本数据类型、包装类型就不使用加密解密了
                 }
             }
  
         } catch (NoSuchMethodException e) {
             logger.error("@Secret注解指定的类没有字段:encryptStr,或encryptStrName参数字段不存在");
             e.printStackTrace();
         } catch (Throwable throwable) {
             throwable.printStackTrace();
         }
     }
     // 后置通知
     @AfterReturning("pointcut()")
     public void afterReturning(JoinPoint point){
         // 获取被代理方法参数
         Object[] args = point.getArgs();
         // 获取被代理对象
         Object target = point.getTarget();
         // 获取通知签名
         MethodSignature signature = (MethodSignature )point.getSignature();
         try {
             // 获取被代理方法
             Method pointMethod = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
             // 获取被代理方法上面的注解@Secret
             Secret secret = pointMethod.getAnnotation(Secret.class);
             // 被代理方法上没有，则说明@Secret注解在被代理类上
             if(secret==null){
                 secret = target.getClass().getAnnotation(Secret.class);
             } 
             if(secret!=null){
                 // 获取注解上声明的加解密类
                 Class<?> clazz = secret.value();
                 // 获取注解上声明的加密参数名
                 String encryptStrName = secret.encryptStrName();
  
                 for (int i = 0; i < args.length; i++) {
                     // 如果是clazz类型则说明使用了加密字符串encryptStr传递的加密参数
                     if(clazz.isInstance(args[i])){
                         Object cast = clazz.cast(args[i]);      //将args[i]转换为clazz表示的类对象
                         // 通过反射，执行getEncryptStr()方法，获取加密数据
                         Method getMethod = clazz.getMethod(getMethedName(encryptStrName));
                         getMethod.setAccessible(true);
                         // 执行方法，获取加密数据
                         String encryptStr = (String) getMethod.invoke(cast);
                         // 加密字符串是否为空
                         if(StringUtils.isNotBlank(encryptStr)){
                             // 解密
                             String json = AESUtils.encrypt(encryptStr);
                             Class<?> type = clazz.getDeclaredField(encryptStrName).getType();
                             Method setMethod = clazz.getMethod(setMethedName(encryptStrName),type);
                             setMethod.setAccessible(true);
                             if (type.isAssignableFrom(String.class)) {
                                 setMethod.invoke(args[i], json);
                             } else if (type.isAssignableFrom(int.class)|| type.isAssignableFrom(Integer.class)) {
                                 setMethod.invoke(args[i], Integer.parseInt(json));
                             } else if (type.isAssignableFrom(Double.class)|| type.isAssignableFrom(double.class)) {
                                 setMethod.invoke(args[i], Double.parseDouble(json));
                             } else if (type.isAssignableFrom(Boolean.class)|| type.isAssignableFrom(boolean.class)) {
                                  setMethod.invoke(args[i], Boolean.parseBoolean(json));
                             } else if (type.isAssignableFrom(Date.class)) {
                                  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                  setMethod.invoke(args[i], dateFormat.parse(json));
                             } else if (type.isAssignableFrom(Timestamp.class)) {
                                 SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
                                 setMethod.invoke(args[i], dateFormat.parse(json).toString());
                             }
                         }
                     }
                     // 其他类型，比如基本数据类型、包装类型就不使用加密解密了
                 }
             }
  
         } catch (NoSuchMethodException e) {
             logger.error("@Secret注解指定的类没有字段:encryptStr,或encryptStrName参数字段不存在");
             e.printStackTrace();
         } catch (Throwable throwable) {
             throwable.printStackTrace();
         }
     }
     
     // 最终通知
     @After("pointcut()")
     public void after(JoinPoint point){
         // 获取被代理对象
         Object target = point.getTarget();
         logger.info(target.toString() + "切面调用结束！");
     }
     
     // 环绕通知
     @Around("save()")
     public void around(ProceedingJoinPoint point){
         // 获取被代理方法参数
         Object[] args = point.getArgs();
         // 获取被代理对象
         Object target = point.getTarget();
         // 获取通知签名
         MethodSignature signature = (MethodSignature )point.getSignature();
         try {
             // 获取被代理方法
             Method pointMethod = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
             // 获取被代理方法上面的注解@Secret
             Secret secret = pointMethod.getAnnotation(Secret.class);
             // 被代理方法上没有，则说明@Secret注解在被代理类上
             if(secret==null){
                 secret = target.getClass().getAnnotation(Secret.class);
             } 
             if(secret!=null){
                 // 获取注解上声明的加解密类
                 Class<?> clazz = secret.value();
                 // 获取注解上声明的加密参数名
                 String encryptStrName = secret.encryptStrName();
  
                 for (int i = 0; i < args.length; i++) {
                     // 如果是clazz类型则说明使用了加密字符串encryptStr传递的加密参数
                     if(clazz.isInstance(args[i])){
                         Object cast = clazz.cast(args[i]);      //将args[i]转换为clazz表示的类对象
                         // 通过反射，执行getEncryptStr()方法，获取加密数据
                         Method getMethod = clazz.getMethod(getMethedName(encryptStrName));
                         getMethod.setAccessible(true);
                         // 执行方法，获取加密数据
                         String encryptStr = (String) getMethod.invoke(cast);
                         // 加密字符串是否为空
                         if(StringUtils.isNotBlank(encryptStr)){
                             // 解密
                             String json = AESUtils.decrypt(encryptStr);
                             Class<?> type = clazz.getDeclaredField(encryptStrName).getType();
                             Method setMethod = clazz.getMethod(setMethedName(encryptStrName),type);
                             setMethod.setAccessible(true);
                             if (type.isAssignableFrom(String.class)) {
                                 setMethod.invoke(args[i], json);
                             } else if (type.isAssignableFrom(int.class)|| type.isAssignableFrom(Integer.class)) {
                                 setMethod.invoke(args[i], Integer.parseInt(json));
                             } else if (type.isAssignableFrom(Double.class)|| type.isAssignableFrom(double.class)) {
                                 setMethod.invoke(args[i], Double.parseDouble(json));
                             } else if (type.isAssignableFrom(Boolean.class)|| type.isAssignableFrom(boolean.class)) {
                                  setMethod.invoke(args[i], Boolean.parseBoolean(json));
                             } else if (type.isAssignableFrom(Date.class)) {
                                  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                  setMethod.invoke(args[i], dateFormat.parse(json));
                             } else if (type.isAssignableFrom(Timestamp.class)) {
                                 SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
                                 setMethod.invoke(args[i], dateFormat.parse(json).toString());
                             }
//                             // 转换vo
//                             args[i] = JSON.parseObject(json, (Type) args[i].getClass());
                         }
                     }
                     // 其他类型，比如基本数据类型、包装类型就不使用加密解密了
                 }
             }
             point.proceed(args);
//             // 执行请求
//             result = (ResultVO) point.proceed(args);
//             // 判断配置是否需要返回加密
//             if(isSecret){
//                 //获取返回值json字符串
//                 String jsonString = JSON.toJSONString(result.getData()); 
//                 // 加密
//                 String s = AESUtils.encrypt(jsonString);
//                 result.setData(s);
//             }
  
         } catch (NoSuchMethodException e) {
             logger.error("@Secret注解指定的类没有字段:encryptStr,或encryptStrName参数字段不存在");
             e.printStackTrace();
         } catch (Throwable throwable) {
             throwable.printStackTrace();
         }
//         return result;
     }
  
     // 转化方法名
     private String getMethedName(String name){
         String first = name.substring(0,1);
         String last = name.substring(1);
         first = StringUtils.upperCase(first);
         return "get" + first + last;
     }
     
  // 转化方法名
     private String setMethedName(String name){
         String first = name.substring(0,1);
         String last = name.substring(1);
         first = StringUtils.upperCase(first);
         return "set" + first + last;
     }
 }

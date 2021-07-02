 package com.springboot.test.util.encrypt;

 import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
 import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
 import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

 /**
  * @Created joe
  * @Author : payne
  * @Date : 2018/5/18 - 10:37
  * @Copyright (C), 2018-2018
  * @Descripition : JasyptUtils 安全框架加密类工具包
  */
 public class JasyptUtils {

     /**
      * encryptPwd 生成加密结果
      * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
      * @param value    待加密值
      * @return String
      */
     public static String encryptPwd(String password, String value) {
         PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
         encryptOr.setConfig(cryptOr(password));
         String result = encryptOr.encrypt(value);
         return result;
     }

     /**
      * decyptPwd 解密
      * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
      * @param value    待解密密文
      * @return String
      */
     public static String decyptPwd(String password, String value) {
         PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
         encryptOr.setConfig(cryptOr(password));
         String result = encryptOr.decrypt(value);
         return result;
     }

     public static SimpleStringPBEConfig cryptOr(String password) {
         SimpleStringPBEConfig config = new SimpleStringPBEConfig();
         config.setPassword(password);
         config.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);
         config.setKeyObtentionIterations("1000");
         config.setPoolSize("1");
         config.setProviderName("SunJCE");
         config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
         config.setStringOutputType("base64");
         return config;
     }
 }

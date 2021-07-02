 package com.springboot.test.util.encrypt;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

 /**
  * AES加解密工具类
  */
 public class AESUtils {
     
     private static final String KEY_ALGORITHM = "AES";
     private static final String DEFAULT_CHARSET_CODE = "UTF-8";
     // 默认的加密算法
     private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
     // ECB加密算法
     private static final String ECB_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
     private static final String DEFAULT_PASSWORD = "B49117B4A4964EFF";
     
     /**
      * @param algorithm 加密算法
      * @param mode      密码器模式
      * @param key       加密秘钥
      * @return Cipher
      */
     public static Cipher initCipher(String algorithm, int mode, Key key) {
         Cipher cipher = null;
         try {
             // 创建密码器
             cipher = Cipher.getInstance(algorithm);
             cipher.init(mode, key);
         } catch (InvalidKeyException e) {
             e.printStackTrace();
         } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
         } catch (NoSuchPaddingException e) {
             e.printStackTrace();
         }
         return cipher;
     }

     public static byte[] doFinal(Cipher cipher, byte[] content) {
         try {
             return cipher.doFinal(content);
         } catch (IllegalBlockSizeException e) {
             e.printStackTrace();
         } catch (BadPaddingException e) {
             e.printStackTrace();
         }
         return null;
     }
     
     /**
      * AES 加密操作
      * @param content  待加密内容
      * @param password 加密密码
      * @return 返回Base64转码后的加密数据
      */
     public static String encrypt(String content) {
         String encyptedContent = null;
         try {
             byte[] byteContent = content.getBytes(DEFAULT_CHARSET_CODE);
             //初始化密码器
             Cipher cipher = initCipher(ECB_CIPHER_ALGORITHM, Cipher.ENCRYPT_MODE, getSecretKey(DEFAULT_PASSWORD));
             // 加密
             byte[] result = cipher.doFinal(byteContent);
             // 7.将加密后的数据转换为字符串
             encyptedContent = new String(Base64.encodeBase64(result));
             //通过Base64转码返回
             return encyptedContent;
         } catch (Exception ex) {
             Logger.getLogger(AESUtils.class.getName()).log(Level.SEVERE, null, ex);
         }
         return encyptedContent;
     }

     /**
      * AES 加密操作
      * @param content  待加密内容
      * @param password 加密密码
      * @return String 返回Base64转码后的加密数据
      */
     public static String encrypt(String content, String password) {
         String encyptedContent = null;
         try {
             byte[] byteContent = content.getBytes(DEFAULT_CHARSET_CODE);
             //初始化密码器
             Cipher cipher = initCipher(ECB_CIPHER_ALGORITHM, Cipher.ENCRYPT_MODE, getSecretKey(password));
             // 加密
             byte[] result = cipher.doFinal(byteContent);
             // 7.将加密后的数据转换为字符串
             encyptedContent = new String(Base64.encodeBase64(result));
             //通过Base64转码返回
             return encyptedContent;
         } catch (Exception ex) {
             Logger.getLogger(AESUtils.class.getName()).log(Level.SEVERE, null, ex);
         }
         return encyptedContent;
     }
     /**
      * AES 解密操作
      * @param content
      * @param password
      * @return String
      */
     public static String decrypt(String content) {
         try {
             //初始化密码器
             Cipher cipher = initCipher(ECB_CIPHER_ALGORITHM, Cipher.DECRYPT_MODE, getSecretKey(DEFAULT_PASSWORD));
             // 加密
             byte[] result = cipher.doFinal(Base64.decodeBase64(content));
             return new String(result, DEFAULT_CHARSET_CODE);
         } catch (Exception ex) {
             Logger.getLogger(AESUtils.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
     }

     /**
      * AES 解密操作
      * @param content
      * @param password
      * @return String
      */
     public static String decrypt(String content, String password) {
         try {
             //初始化密码器
             Cipher cipher = initCipher(ECB_CIPHER_ALGORITHM, Cipher.DECRYPT_MODE, getSecretKey(password));
             // 加密
             byte[] result = cipher.doFinal(Base64.decodeBase64(content));
             return new String(result, DEFAULT_CHARSET_CODE);
         } catch (Exception ex) {
             Logger.getLogger(AESUtils.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
     }

     /**
      * AES 加密操作
      * @param content 待加密内容
      * @param password 加密密码
      * @param iv 使用CBC模式，需要一个向量iv，可增加加密算法的强度
      * @return 加密数据
      */
     public static String encrypt(String content, String password, String iv) {
         String encyptedContent = null;
         try {
             //创建密码器
             Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
             //密码key(超过16字节即128bit的key，需要替换jre中的local_policy.jar和US_export_policy.jar，否则报错：Illegal key size)
             SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(DEFAULT_CHARSET_CODE),KEY_ALGORITHM);
             //向量iv
             IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(DEFAULT_CHARSET_CODE));
             //初始化为加密模式的密码器
             cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivParameterSpec);
             //加密
             byte[] byteContent = content.getBytes(DEFAULT_CHARSET_CODE);
             byte[] result = cipher.doFinal(byteContent);
             // 7.将加密后的数据转换为字符串
             encyptedContent = new String(Base64.encodeBase64(result));
             return encyptedContent;
         } catch (Exception ex) {
             Logger.getLogger(AESUtils.class.getName()).log(Level.SEVERE, null, ex);
         }

         return encyptedContent;
     }

     /**
      * AES 解密操作
      *
      * @param content 密文
      * @param password 密码
      * @param iv 使用CBC模式，需要一个向量iv，可增加加密算法的强度
      * @return 明文
      */
     public static String decrypt(String content, String password,String iv) {
         String decryptContent = null;
         try {
             //创建密码器
             Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
             //密码key
             SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(DEFAULT_CHARSET_CODE),KEY_ALGORITHM);
             //向量iv
             IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(DEFAULT_CHARSET_CODE));
             //初始化为解密模式的密码器
             cipher.init(Cipher.DECRYPT_MODE,keySpec,ivParameterSpec);
             // 5.将加密并编码后的内容解码成字节数组
             byte[] byte_content = Base64.decodeBase64(content);
             // 6.AES解密
             byte_content = cipher.doFinal(byte_content);
             // 7.使用u8生成解密字符串
             decryptContent = new String(byte_content, DEFAULT_CHARSET_CODE);
             return decryptContent;
         } catch (Exception ex) {
             Logger.getLogger(AESUtils.class.getName()).log(Level.SEVERE, null, ex);
         }
         return decryptContent;
     }
     /**
      * 生成加密秘钥
      * @return
      */
     private static SecretKeySpec getSecretKey(final String password) {
         //返回生成指定算法密钥生成器的 KeyGenerator 对象
         KeyGenerator kg = null;
         try {
             kg = KeyGenerator.getInstance(KEY_ALGORITHM);
             //AES 要求密钥长度为 128
             kg.init(128, new SecureRandom(password.getBytes()));
             //生成一个密钥
             SecretKey secretKey = kg.generateKey();
             // 转换为AES专用密钥
             return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
         } catch (NoSuchAlgorithmException ex) {
             Logger.getLogger(AESUtils.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
     }

 }
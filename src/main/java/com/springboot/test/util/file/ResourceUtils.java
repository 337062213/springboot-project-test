 package com.springboot.test.util.file;

 import org.apache.commons.io.IOUtils;
 import java.io.File;
 import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
 import java.nio.charset.StandardCharsets;
 import java.util.*;

 public class ResourceUtils {
     
     private static String userDir = System.getProperty("user.dir");
     
     private static String subDir = "\\src\\main\\resources\\cfg2\\";

     public static String  resourceToString(String path) throws IOException{
         InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
         return IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
     }

     public static String  dirFileToString(String fileName) throws FileNotFoundException, IOException{
         return IOUtils.toString(new FileInputStream(userDir + subDir + fileName), StandardCharsets.UTF_8);
     }

     public static byte[]  fileToByte(String fileName) throws FileNotFoundException, IOException{
         File file = new File(userDir + subDir + fileName);
         if (file.exists()){
             return IOUtils.toByteArray(new FileInputStream(userDir + subDir + fileName));
         }
         return null;
     }

     public static void  byteToFile(byte[] byteArray, String fileName) throws FileNotFoundException, IOException {
         IOUtils.write(byteArray, new FileOutputStream(userDir + subDir + fileName));
     }

     public static void  byteToText(byte[] byteArray, String fileName) throws FileNotFoundException, IOException {
         IOUtils.write(new String(byteArray, StandardCharsets.UTF_8), new FileOutputStream(userDir + subDir + fileName), StandardCharsets.UTF_8);
     }

     public static void  decodeBase64ToFile(String base64, String fileName) throws FileNotFoundException, IOException {
         IOUtils.write(Base64.getDecoder().decode(base64), new FileOutputStream(userDir + subDir + fileName));
     }
}


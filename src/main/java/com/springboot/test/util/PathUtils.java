 package com.springboot.test.util;

 import javax.servlet.http.HttpServletRequest;
 import org.springframework.util.ClassUtils;

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

public class PathUtils {
     private static Logger logger = LoggerFactory.getLogger(PathUtils.class);
     public static String getPathByDefaultClassLoader() {
         return ClassUtils.getDefaultClassLoader().getResource("").getPath().replaceFirst("/", "");
     }
     
     public static String getPathByClass() {
         return Class.class.getClass().getResource("/").getPath().replaceFirst("/", "");
     }
     
     public static String getPathByProperty() {
         return System.getProperty("user.dir").replace('\\', '/');
     }
     
     public static String getPathByRequest(HttpServletRequest request){
         return request.getServletContext().getRealPath("/").replace('\\', '/');
     }
     
     public static void main(String[] args) {
         logger.info(PathUtils.getPathByClass());
         logger.info(PathUtils.getPathByDefaultClassLoader());
         logger.info(PathUtils.getPathByProperty());
     }
}

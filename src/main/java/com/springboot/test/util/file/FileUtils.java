 package com.springboot.test.util.file;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author EDZ
 * @date 2021/04/30
 */
public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    /**
     * file 是文件则删除文件    是文件夹则删除文件夹及以下文件
     * @param file 
     */
    public static void deleteAll(File file) {
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File item : files) {
                if(item.isFile()) {
                    item.delete();
                }else {
                    deleteAll(item);
                }
            }
        }else if(file.isFile()) {
            file.delete();
        }
    }
    
    /**
     * file 文件不存在则生成
     * @param file 
     * @throws IOException 
     */
    public static void newFile(File file) {
        String filePath = file.getAbsolutePath();
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        boolean isFile = fileName.contains(".");
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdirs();
                if(!isFile) {
                    file.mkdir();
                }else {
                    file.createNewFile(); 
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }else {
            if(!file.exists()) {
                if(!isFile) {
                    file.mkdir();
                }else {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    } 
                }
            }
        }
    }
}

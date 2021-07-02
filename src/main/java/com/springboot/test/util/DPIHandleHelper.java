package com.springboot.test.util;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DPIHandleHelper { 
	
	public static final Logger log = LoggerFactory.getLogger(DPIHandleHelper.class);
	
    public static void handleDpi(File file, File taget) {
    	long startTime = System.currentTimeMillis();
        try { 
        	log.info(String.valueOf(startTime));
            RenderedImage image = ImageIO.read(file); 
            ImageIO.write(image, "tiff", new FileOutputStream(taget));
            long endTime = System.currentTimeMillis();
            long costTime = endTime - startTime;
            log.info(String.valueOf(costTime));
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  

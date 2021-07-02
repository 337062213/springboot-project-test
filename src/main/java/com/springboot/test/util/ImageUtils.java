package com.springboot.test.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.IIOParamController;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);
	
	public static void changeImageFormat(String sourcePath, String targetPath,String name) {
		
		File source = new File(sourcePath);
		File target= new File(targetPath + name);
		try {
			BufferedImage bi = ImageIO.read(source);
			ImageIO.write(bi, "tiff", target);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
    public static void changeImageFormatFromFile(String sourcePath, String targetPath,String name) {
		
		File source = new File(sourcePath);
		File target= new File(targetPath + name);
		
		try {
			
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
			ImageReader reader = readers.next();
			ImageInputStream iis = ImageIO.createImageInputStream(source);
			reader.setInput(iis,true);
			int half_width = reader.getWidth(0)/2;
            int half_height = reader.getHeight(0)/2;
            
			ImageReadParam readParam = reader.getDefaultReadParam();			
			Rectangle rect = new Rectangle(0, 0, half_width, half_height); 
			readParam.setSourceRegion(rect);
			
			IIOParamController controller = readParam.getController();
			if (controller != null) {
			        controller.activate(readParam);
			}			
		    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
		    ImageWriter writer = writers.next();

		    ImageOutputStream ios = ImageIO.createImageOutputStream(target);
		    writer.setOutput(ios);

		    BufferedImage first_bi = reader.read(0);
		    IIOImage first_IIOImage = new IIOImage(first_bi, null, null);
		    writer.write(null, first_IIOImage, null);
		    for(int i =0 ; i<5; i++) {
		    	if (writer.canInsertImage(i)) {
		    		BufferedImage second_bi = reader.read(i, readParam);
		    		IIOImage second_IIOImage = new IIOImage(second_bi, null, null);
		            writer.writeInsert(i, second_IIOImage, null);
				    } else {
				        logger.error("Writer can't append a second image!");
				    }
		    }
		    

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
    public static String getReaderFormatNames() {
        String readFormats[] = ImageIO.getReaderFormatNames();
        String writeFormats[] = ImageIO.getWriterFormatNames();
        logger.info("Readers: : " + Arrays.toString(readFormats));
        logger.info("Writers: : " + Arrays.toString(writeFormats));
        return Arrays.toString(readFormats);
    }
    
    public static Image getImageFromLocal(String sourcePath) throws IOException{        
        File file=new File(sourcePath);
        Image image=ImageIO.read(file);
        return image;
    }
    public static void getImageFromLocal2() throws IOException{
        
        File file=new File("zp2.png");
        Image im=ImageIO.read(file);
        int w=im.getWidth(null);
        int h=im.getHeight(null);
        BufferedImage b=new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
         b.getGraphics().drawImage(im.getScaledInstance(w,h, Image.SCALE_SMOOTH), 
                    0, 0, null); 
         BufferedImage b2=new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
         b2.getGraphics().drawImage(im.getScaledInstance(w,h, Image.SCALE_SMOOTH), 
                    0, 0, null); 
         for(int x=0;x<h;x++)
            for(int y=0;y<w;y++)
            {
                int rgb=b.getRGB(y, x);
                b2.setRGB(w-1-y, x, rgb);
            }
        ImageIO.write(b2, "png", new File("4.png"));  
    }
}

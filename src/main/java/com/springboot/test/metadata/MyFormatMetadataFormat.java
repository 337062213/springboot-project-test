package com.springboot.test.metadata;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadataFormatImpl;

public class MyFormatMetadataFormat extends IIOMetadataFormatImpl {

    private static MyFormatMetadataFormat defaultInstance = new MyFormatMetadataFormat();
    
    public static MyFormatMetadataFormat getDefaultInstance() {
        return defaultInstance;
    }

    private MyFormatMetadataFormat() {
    	
            super("com.mycompany.imageio.MyFormatMetadata_1.0", CHILD_POLICY_REPEAT);

            addElement("KeywordValuePair", "com.mycompany.imageio.MyFormatMetadata_1.0", CHILD_POLICY_EMPTY);

            addAttribute("KeywordValuePair", "keyword", DATATYPE_STRING, true, null);
            
            addAttribute("KeywordValuePair", "value", DATATYPE_STRING, true, null);
    }


	@Override
	public boolean canNodeAppear(String elementName, ImageTypeSpecifier imageType) {
		
		return elementName.equals("KeywordValuePair");
	}
}

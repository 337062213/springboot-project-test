package com.springboot.test.listener;

import javax.imageio.ImageWriter;
import javax.imageio.event.IIOWriteProgressListener;

public class AppCommonWriteProgressListener implements IIOWriteProgressListener{
	
	

	public AppCommonWriteProgressListener() {
		super();
	}

	@Override
	public void imageComplete(ImageWriter arg0) {
		
	}

	@Override
	public void imageProgress(ImageWriter arg0, float arg1) {
		
	}

	@Override
	public void imageStarted(ImageWriter arg0, int arg1) {
		
	}

	@Override
	public void thumbnailComplete(ImageWriter arg0) {
		
	}

	@Override
	public void thumbnailProgress(ImageWriter arg0, float arg1) {
		
	}

	@Override
	public void thumbnailStarted(ImageWriter arg0, int arg1, int arg2) {
		
	}

	@Override
	public void writeAborted(ImageWriter arg0) {
		
	}
	
}

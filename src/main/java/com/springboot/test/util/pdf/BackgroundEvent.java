 package com.springboot.test.util.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

public class BackgroundEvent implements PdfPCellEvent {
    public Image image;    
     
    public BackgroundEvent() {
        super();
    }

    public BackgroundEvent(Image image) {
        this.image = image;
    }

    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
         try {
             PdfContentByte cb = canvases[PdfPTable.BACKGROUNDCANVAS];
             this.image.scaleAbsolute(position.getWidth(), position.getHeight());
             this.image.setAbsolutePosition(position.getLeft(), position.getBottom());
             cb.addImage(this.image);
         } catch (DocumentException e) {
             throw new ExceptionConverter(e);
         }
    }
    
    public void cellLayout(Image image, PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
        try {
            PdfContentByte cb = canvases[PdfPTable.BACKGROUNDCANVAS];
            image.scaleAbsolute(position.getWidth(), position.getHeight());
            image.setAbsolutePosition(position.getLeft(), position.getBottom());
            cb.addImage(image);
        } catch (DocumentException e) {
            throw new ExceptionConverter(e);
        }
   }
 }

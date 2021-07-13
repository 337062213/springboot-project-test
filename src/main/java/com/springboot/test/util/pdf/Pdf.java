package com.springboot.test.util.pdf;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.fields.PdfField;
import com.spire.pdf.fields.PdfTextBoxField;
import com.spire.pdf.graphics.PdfTextAlignment;
import com.spire.pdf.graphics.PdfTrueTypeFont;
import com.spire.pdf.widget.PdfFormFieldWidgetCollection;
import com.spire.pdf.widget.PdfFormWidget;
import com.spire.pdf.widget.PdfTextBoxFieldWidget;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pdf {

    static PdfTrueTypeFont font;
    static String fileName = "BG-0022钢筋试验检测报告.pdf";
    static String configName = "竖表config.cfg";

    public static void main(String[] args) throws Exception {
        loadFont();
        String userDir = System.getProperty("user.dir");
        while (true) {
            try {
                String line = IOUtils.toString(new FileInputStream(userDir + "\\src\\main\\resources\\cfg\\" + configName), StandardCharsets.UTF_8);
                String[] split = line.split(",");
                String x = split[0];
                String y = split[1];
                String w = split[2];
                String h = split[3];
                addField(x, y, w, h);
                writeText("测试签名");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadFont() throws Exception {
        Font 宋体 = new Font("新宋体", Font.PLAIN, 9);
        Pdf.font = new PdfTrueTypeFont(宋体, 9);
    }


    static private void writeText(String text) throws Exception {
        String userDir = System.getProperty("user.dir");
        PdfDocument doc = loadPdf(userDir + "\\src\\main\\resources\\cfg\\" + "addField.pdf");
        PdfFormFieldWidgetCollection fieldsWidget = ((PdfFormWidget) doc.getForm()).getFieldsWidget();
        PdfTextBoxFieldWidget find = null;
        for (Object field : fieldsWidget) {
            PdfField pdfField = (PdfField) field;

            String name = pdfField.getName();
            if (StringUtils.equals(name, "意见1")) {
                find = (PdfTextBoxFieldWidget) pdfField;
                Rectangle2D bounds = find.getBounds();
                double x = bounds.getX();
                double y = bounds.getY();
                double w = bounds.getWidth();
                double h = bounds.getHeight();
                String collect = Stream.of(x, y, w, h).map(String::valueOf).collect(Collectors.joining(","));
                System.out.println(collect);
            }
        }

        List<PdfTextBoxField> insertList = new ArrayList<>();
        if (find != null) {
            for (Object page : doc.getPages()) {
                PdfPageBase page1 = (PdfPageBase) page;
                PdfTextBoxField textBox = new PdfTextBoxField(page1, find.getName());
                Rectangle2D bounds = find.getBounds();
                Rectangle2D.Float tbxBounds = new Rectangle2D.Float((float) bounds.getX(), (float) bounds.getY(),
                        (float) bounds.getWidth(), (float) bounds.getHeight()); //创建Rectangle2D对象
                textBox.setTextAlignment(PdfTextAlignment.Left);
                textBox.setBounds(tbxBounds);
                textBox.setBorderWidth(0.1f);
                textBox.setFont(font); //设置文本框的字体
                textBox.setMultiline(true);
                textBox.setText(text);
//                textBox.setReadOnly(true);
                insertList.add(textBox);
            }
            fieldsWidget.remove(find);
        }
        insertList.forEach(i -> {
            doc.getForm().getFields().add(i);
        });
        saveToFile(doc, "fixed.pdf");
    }

    private static void saveToFile(PdfDocument doc, String fileName) throws IOException {
        String userDir = System.getProperty("user.dir");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        doc.saveToStream(byteArrayOutputStream);
        doc.close();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        IOUtils.write(bytes, new FileOutputStream(userDir + "\\src\\main\\resources\\cfg\\" + fileName));
    }

    static private void addField(String x, String y, String w, String h) throws Exception {
        String userDir = System.getProperty("user.dir");
        PdfDocument doc = loadPdf(userDir + "\\src\\main\\resources\\cfg\\" +fileName);
        PdfFormFieldWidgetCollection fieldsWidget = ((PdfFormWidget) doc.getForm()).getFieldsWidget();
        for (Object page : doc.getPages()) {
            PdfPageBase page1 = (PdfPageBase) page;
            PdfTextBoxField textBox = new PdfTextBoxField(page1, "意见1");
            Rectangle2D.Float tbxBounds = new Rectangle2D.Float(Float.parseFloat(x), Float.parseFloat(y), Integer.parseInt(w), Integer.parseInt(h)); //创建Rectangle2D对象
            textBox.setTextAlignment(PdfTextAlignment.Left);
            textBox.setBounds(tbxBounds);
            textBox.setBorderWidth(1);
            textBox.setFont(font); //设置文本框的字体
//            textBox.setMultiline(true);
//            textBox.setText();
//            textBox.setReadOnly(true);
            fieldsWidget.add(textBox);
        }
        saveToFile(doc, "addField.pdf");
    }

    private static PdfDocument loadPdf(String path) throws Exception {
        PdfDocument doc = new PdfDocument();
        doc.loadFromBytes(IOUtils.toByteArray(new FileInputStream(new File(path))));
        return doc;
    }
}


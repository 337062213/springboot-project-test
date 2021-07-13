package com.springboot.test.util.pdf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spire.license.LicenseProvider;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.conversion.PdfStandardsConverter;
import com.spire.pdf.fields.*;
import com.spire.pdf.graphics.PdfRGBColor;
import com.spire.pdf.graphics.PdfTextAlignment;
import com.spire.pdf.graphics.PdfTrueTypeFont;
import com.spire.pdf.widget.PdfFormFieldWidgetCollection;
import com.spire.pdf.widget.PdfFormWidget;
import com.spire.pdf.widget.PdfTextBoxFieldWidget;
import com.springboot.test.model.vo.CaPdfConfigModel;
import com.springboot.test.util.file.ResourceUtils;

import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;
import org.apache.commons.lang3.StringUtils;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.net.URLDecoder;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PdfTest {

    static {
        LicenseProvider.setLicenseKey("7a2ghwEAwRTtxjmfEoZUVe21jMhAC6zS8pMweDmU1sX0XWKEa6y4iBYb1fgvSWwA4nhpNKi2iwpEsKvygue21aMt/HoSESgvlDtk9dYtsqNSYl7tFZv9p3I+rLtW+ypSD/4yAh9XP+aPmioEHg/a+UK9DDTc8D46CPX5ZE5Rc92K9sL73MKbHgTR1ebOy0yXHTbHIEmj3mrjTdmS4vx6p0bujjYvlHa3Qf6Vh7m4VO02QSUifwUb4rdLcZ+RCcj0MUhSfYBYJEzIvnZ3ZbTHpZVnumeQNZKd/GR4PBLXwe9uVuT77leWMNwmRFlxNq8MiVv6+BxOfu6wUt1vDIwYwS+bw96gpWqnr2m+OX2ojwjkb4FD2n3JWZtpM7EtKlpNGchFPAa8vMALCmY0LJHzKt3C6nHACWkI6kAESeKyX8LkF6uctGNDSLDMQ7ZYtD2I4ZPzSHqFsxdgEPEsWoGYXtfN/GM9ASB0VfmB6bZFVtFh/xLMI4Y2Q78H1XsStYsXc60G65uXOu43VVP5qKkedaAd5uexepHu5zxMLOFc/Ejqh4nop4vHmsz6F9ebh9WYJ8F35Rpe9WgZrDnBmUfYLfuxAekvbJ0IsyT5A9gT5UOcWedRjOlYbTm9NZZmCE6fYRmYMzlnwIms8szXOzC5U/zD+Y1a25cyz0TvF1INc+sMsyFApiyRTaKN+0pjbosCPHX0v3OpeLIvDNo97+cFWyyxjtyH8aYgWFrUeeoy9fqiGgxJmQVRJeWbHziSG8lRLXOZ42UgOCNkbOaeztbpADlsgrRtsxpEjX52zoJ9HfLaCcsxQABevbs8F8QnzPcg6vGsFUBHCLXoKw0u6BFljOAEySBdAZMfQqy6Q0JBAcv2t2OLjQ6NzIPan/GfcjYgyYgujDyDwV3DsTzFinrHXIm+5D3j/v+uXpWEXAin0dYXaFUCuiP2iCOf+vqsT28RRHYLPV+SzXqQvqWQr25ADDsYnG/+r7gziRJPBuJhwUt2VxyRjQjIepoOTT15HgfikNBFU/ak3lSdSHX1wgRP3RKkkIczjYFdEXjBHyW8PJn6PHLSB9Ig7yDnX5BFL5SxK//vHs+VQz5Xr2Ijkk1j2N0xvTJPoHdmsPXD4nhahizkw0PwgpFHNkWAS9dXL91bJvRvrh4I4fdbamWPFYqYpjktFVEo3FCfA8QsejkaEzQVIY07CmSbJuSYhGS+3/N8qt07yY6/Zbm7br++OG11RACMfF0u+luW5ifV8U6Il3iWrC6gOuYvCW2pby64h2EEUZKusLCaFvWZtH9ULUorH0slXiZt/bisECrwONOpnGSpbqo5X6TUSO/09iR9lpjS/o3p8LFTSPig5QHwAesU0QR+YzKnCANJ51Bf/qJqzn4blEi6KQKHwziFLKMfnYIgxxuc46b3rJY=");
    }

    public static byte[] downloadPdf(String url) throws FileNotFoundException, IOException {
        String downloadParams = "&format=pdf";
        if (!url.contains(downloadParams)){
            url = url +  downloadParams;
        }
        Pattern compile = Pattern.compile("jspro-reportlets/experiment/report/(.*?)\\.cpt");
        Matcher matcher = compile.matcher(url);
        matcher.find();
        String group = matcher.group(1);
        group = URLDecoder.decode(group, "utf-8");
        System.out.println("当前处理的表是：" + group);
        String fileName = group + ".pdf";
        byte[] bytes = ResourceUtils.fileToByte(fileName);
        if (bytes == null){
            RawResponse res = Requests.get(url).send();
            byte[] resp = Requests.get(url).send().readToBytes();
            if(res.statusCode()==200 || res.statusCode()==201) {
              //保存下载到的PDF文件为
              ResourceUtils.byteToFile(resp,fileName);
            }else {
              //如果报错了，那肯定是报表出错了，查看下载到的PDF.txt是不是输出html了
              ResourceUtils.byteToText(resp,"下载到的PDF.txt");
            }
            return resp;
        }else {
            return bytes;
        }
    }

    public static void main(String[] args) throws Exception {
        //下载PDF文件
        String reportUrl = ResourceUtils.dirFileToString("reportUrl.txt");
        byte[] reportBytes = downloadPdf(reportUrl);

        PdfTest test = new PdfTest();
        Base64.Encoder encoder = Base64.getEncoder();
        String reportBase64 = encoder.encodeToString(reportBytes);
        String reportConfig = ResourceUtils.dirFileToString("reportConfig.json");
        List<CaPdfConfigModel> caPdfConfigModels = JSONObject.parseArray(reportConfig, CaPdfConfigModel.class);
        String createdFormBase64 = test.createForm(reportBase64, caPdfConfigModels);
        ResourceUtils.decodeBase64ToFile(createdFormBase64,"创建了文本域的PDF.pdf");

        createdFormBase64 = test.write(createdFormBase64, "检测人张三", "YJH");
        createdFormBase64 = test.write(createdFormBase64, "记录人李四", "complete");
        createdFormBase64 = test.write(createdFormBase64, "复核人王五", "YTJ");
        createdFormBase64 = test.write(createdFormBase64, "2021年07月07日", "ReviewTime");

        ResourceUtils.decodeBase64ToFile(createdFormBase64,"填写了文本的PDF.pdf");

    }

    public String createForm(String pdfBase64, List<CaPdfConfigModel> configList) throws Exception {
        configList = configList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(CaPdfConfigModel::getFieldName))), ArrayList::new));
        try {
            PdfDocument doc = loadPdfBase64(pdfBase64);
            int pageIndex = 0;
            for (Object page : doc.getPages()) {
                pageIndex++;
                PdfPageBase currentPage = (PdfPageBase) page;
                for (CaPdfConfigModel config : configList) {
                    if (config.getStartPage() != null && pageIndex < config.getStartPage()) {
                        break;
                    }
                    if (config.getEndPage() != null){
                        if (config.getEndPage() != -1 && pageIndex > config.getEndPage()) {
                            break;
                        }
                    }
                    if (Objects.equals(config.getType(), 1) || config.getType() == null) {
                        PdfTextBoxField textBox = new PdfTextBoxField(currentPage, buildNameWithPageIndex(pageIndex,
                                config.getFieldName()));
                        textBox.setBounds(new Rectangle2D.Float(config.getFieldX(), config.getFieldY(),
                                config.getFieldW(), config.getFieldH()));
                        textBox.setRequired(true);
                        textBox.setBorderWidth(0);
                        Color color =
                                new Color(1, 1, 1, 0);
                        textBox.setBackColor(new PdfRGBColor(color));
                        textBox.setMultiline(true);
                        textBox.setTextAlignment(PdfTextAlignment.Center);
                        doc.getForm().getFields().add(textBox);
                    } else if (Objects.equals(config.getType(), 4)) {
                        PdfSignatureField signaturefield = new PdfSignatureField(currentPage,
                                buildNameWithPageIndex(pageIndex, config.getFieldName()));
                        signaturefield.setBorderWidth(1f);
                        signaturefield.setBorderStyle(PdfBorderStyle.Solid);
                        signaturefield.setBorderColor(new PdfRGBColor(Color.BLACK));
                        signaturefield.setHighlightMode(PdfHighlightMode.Outline);
                        signaturefield.setBounds(new Rectangle2D.Float(config.getFieldX(), config.getFieldY(),
                                config.getFieldW(), config.getFieldH()));
                        doc.getForm().getFields().add(signaturefield);
                    }

                }
            }
            return toPdfBase64(doc);
        } catch (Exception e) {
            throw new Exception(JSON.toJSONString(configList), e);
        }
    }

    public byte[] toPdfA(String pdfBase64) {
        byte[] data = Base64.getDecoder().decode(pdfBase64);
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfStandardsConverter converter = new PdfStandardsConverter(input);
        converter.toPdfA1A(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private PdfDocument loadPdfBase64(String pdfBase64) {
        PdfDocument doc = new PdfDocument();
        byte[] decodeBase64 = Base64.getDecoder().decode(pdfBase64);
        doc.loadFromBytes(decodeBase64);
        return doc;
    }

    private String buildNameWithPageIndex(int pageIndex, String fieldName) {
        return fieldName + "-Page" + (pageIndex + "");
    }

    private String toPdfBase64(PdfDocument doc) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        doc.saveToStream(bos);
        doc.close();
        return Base64.getEncoder().encodeToString(bos.toByteArray());
    }

    public String write(String pdfBase64, String comment, String filedName) {
        PdfDocument doc = loadPdfBase64(pdfBase64);
        PdfFormWidget form = (PdfFormWidget) doc.getForm();
        PdfFormFieldWidgetCollection formWidgetcollection = form.getFieldsWidget();
        try {
            PdfTrueTypeFont font = new PdfTrueTypeFont(new Font("仿宋", Font.PLAIN, 10),
                    true);
            //重新创建文本域对象,包含要写入的意见
            for (int pageIndex = 0; pageIndex < doc.getPages().getCount(); pageIndex++) {
                //找到已存在的文本域
                String fixFieldName = buildNameWithPageIndex(pageIndex + 1, filedName);
                for (Object field : formWidgetcollection) {
                    PdfField pdfField = (PdfField) field;
                    String name = pdfField.getName();
                    if (StringUtils.equals(name, fixFieldName)) {
                        if (pdfField instanceof PdfTextBoxFieldWidget) {
                            PdfTextBoxFieldWidget textBoxFieldWidget = (PdfTextBoxFieldWidget) pdfField;
                            textBoxFieldWidget.setFont(font);
                            textBoxFieldWidget.setTextAlignment(PdfTextAlignment.Left);
                            //填充
                            textBoxFieldWidget.setText(comment);
                            textBoxFieldWidget.setReadOnly(true);
                        }
                    }
                }
            }
            return toPdfBase64(doc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

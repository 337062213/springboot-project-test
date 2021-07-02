 package com.springboot.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.springboot.test.helper.SAXParserHandler;
import com.springboot.test.model.Book;
import com.springboot.test.model.consistant.DirectoryConst;

public class XMLUtils {
     
    private static final Logger logger = LoggerFactory.getLogger(XMLUtils.class);
     /**
      * DomParser DOM的全称是Document Object Model，也即文档对象模型。在应用程序中，基于DOM的XML分析器将一个XML文档转换成一个对象模型的集合（通常称DOM树），应用程序正是通过对这个对象模型的操作，来实现对XML文档数据的操作。
      * DOM 接口，应用程序可以在任何时候访问XML文档中的任何一部分数据，因此，这种利用DOM接口的机制也被称作随机访问机制。
      */
     public static void DomParser() {
         //创建一个DocumentBuilderFactory的对象
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         //创建一个DocumentBuilder的对象
         try {
             logger.info("==========DomParser start==========");
             //创建DocumentBuilder对象
             DocumentBuilder db = dbf.newDocumentBuilder();
             //通过DocumentBuilder对象的parser方法加载books.xml文件到当前项目下
             org.w3c.dom.Document document =  db.parse(DirectoryConst.XML_PARSERE_DIRECTORY_RELATIVE);
             //获取所有book节点的集合
             NodeList bookList = document.getElementsByTagName("book");
             //通过node list的getLength()方法可以获取bookList的长度
             logger.info("一共有" + bookList.getLength() + "本书");
             //遍历每一个book节点
             for (int i = 0; i < bookList.getLength(); i++) {
                 logger.info("-----开始遍历第" + (i + 1) + "本书的内容-----");
                 //通过 item(i)方法 获取一个book节点，node list的索引值从0开始
                 Node book = bookList.item(i);
                 //获取book节点的所有属性集合
                 NamedNodeMap attrs = book.getAttributes();
                 logger.info("第 " + (i + 1) + "本书共有" + attrs.getLength() + "个属性");
                 //遍历book的属性
                 for (int j = 0; j < attrs.getLength(); j++) {
                     //通过item(index)方法获取book节点的某一个属性
                     Node attr = attrs.item(j);
                     //获取属性名
                     System.out.print("属性名：" + attr.getNodeName());
                     //获取属性值
                     logger.info("--属性值" + attr.getNodeValue());
                 }
                 //解析book节点的子节点
                 NodeList childNodes = book.getChildNodes();
                 //遍历childNodes获取每个节点的节点名和节点值
                 logger.info("第" + (i+1) + "本书共有" + 
                 childNodes.getLength() + "个子节点");
                 int nodeNumber = 0;
                 for (int k = 0; k < childNodes.getLength(); k++) {
                     //区分出text类型的node以及element类型的node
                     if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                         nodeNumber++;
                         //获取了element类型节点的节点名
                         System.out.print("第" + (nodeNumber) + "个元素节点的节点名：" 
                         + childNodes.item(k).getNodeName());
                         //获取了element类型节点的节点值
                         logger.info("--节点值是：" + childNodes.item(k).getFirstChild().getNodeValue());
                         //logger.info("--节点值是：" + childNodes.item(k).getTextContent());
                     }
                 }
                 logger.info("-----结束遍历第" + (i + 1) + "本书的内容-----");
             }
             logger.info("==========DomParser finish==========");
         } catch (ParserConfigurationException e) {
             e.printStackTrace();
         } catch (SAXException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }        
     }
     
     /**
      * SAXParser SAX的全称是Simple APIs for XML，也即XML简单应用程序接口。与DOM不同，SAX提供的访问模式是一种顺序模式，这是一种快速读写XML数据的方式。当使用
      * SAX分析器对XML文档进行分析时，会触发一系列事件，并激活相应的事件处理函数，应用程序通过这些事件处理函数实现对XML文档的访问，因而SAX接口也被称作事件驱动接口
      */
     public static void SAXParser() {
         //锟斤拷取一锟斤拷SAXParserFactory锟斤拷实锟斤拷
         SAXParserFactory factory = SAXParserFactory.newInstance();
         //通锟斤拷factory锟斤拷取SAXParser实锟斤拷
         try {
             logger.info("==========SAXParser start==========");
             SAXParser parser = factory.newSAXParser();
             //锟斤拷锟斤拷SAXParserHandler锟斤拷锟斤拷
             SAXParserHandler handler = new SAXParserHandler();
             parser.parse(DirectoryConst.XML_PARSERE_DIRECTORY_RELATIVE, handler);
             logger.info("共有" + handler.getBookList().size()
                     + "本书");
             for (Book book : handler.getBookList()) {
                 logger.info(book.getId());
                 logger.info(book.getName());
                 logger.info(book.getAuthor());
                 logger.info(book.getYear());
                 logger.info(book.getPrice());
                 logger.info(book.getLanguage());
             }
             logger.info("==========SAXParser finish==========");
         } catch (ParserConfigurationException e) {
             e.printStackTrace();
         } catch (SAXException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 
     /**
      * JDOMParser 特征：
　　　　　　1、仅使用具体类，而不使用接口。
　　　　　　2、API大量使用了Collections类
      */
     public static void JDOMParser() {
         // 进行对books.xml文件的JDOM解析
         // 准备工作
         // 1.创建一个SAXBuilder的对象
         SAXBuilder saxBuilder = new SAXBuilder();
         InputStream in;
         List<Book> booksList = new ArrayList<Book>();
         try {
             logger.info("==========JDOMParser start==========");
             // 2.创建一个输入流，将xml文件加载到输入流中
             in = new FileInputStream(DirectoryConst.XML_PARSERE_DIRECTORY_RELATIVE);
             InputStreamReader isr = new InputStreamReader(in, "UTF-8");
             // 3.通过saxBuilder的build方法，将输入流加载到saxBuilder中
             org.jdom2.Document document = saxBuilder.build(isr);
             // 4.通过document对象获取xml文件的根节点
             Element rootElement = document.getRootElement();
             // 5.获取根节点下的子节点的List集合
             List<Element> bookList = rootElement.getChildren();
             // 继续进行解析
             for (Element book : bookList) {
                 Book bookEntity = new Book();
                 logger.info("-----开始解析第" + (bookList.indexOf(book) + 1)
                         + "书-----");
                 // 解析book的属性集合
                 List<Attribute> attrList = book.getAttributes();
                 // //知道节点下属性名称时，获取节点值
                 // book.getAttributeValue("id");
                 // 遍历attrList(针对不清楚book节点下属性的名字及数量)
                 for (Attribute attr : attrList) {
                     // 获取属性名
                     String attrName = attr.getName();
                     // 获取属性值
                     String attrValue = attr.getValue();
                     logger.info("属性名：" + attrName + "-----属性值："
                             + attrValue);
                     if (attrName.equals("id")) {
                         bookEntity.setId(attrValue);
                     }
                 }
                 // 对book节点的子节点的节点名以及节点值的遍历
                 List<Element> bookChilds = book.getChildren();
                 for (Element child : bookChilds) {
                     logger.info("节点名：" + child.getName() + "-----节点值："
                             + child.getValue());
                     if (child.getName().equals("name")) {
                         bookEntity.setName(child.getValue());
                     }
                     else if (child.getName().equals("author")) {
                         bookEntity.setAuthor(child.getValue());
                     }
                     else if (child.getName().equals("year")) {
                         bookEntity.setYear(child.getValue());
                     }
                     else if (child.getName().equals("price")) {
                         bookEntity.setPrice(child.getValue());
                     }
                     else if (child.getName().equals("language")) {
                         bookEntity.setLanguage(child.getValue());
                     }
                 }
                 logger.info("-----结束解析第" + (bookList.indexOf(book) + 1)
                         + "书-----");
                 booksList.add(bookEntity);
                 bookEntity = null;
                 logger.info("" + booksList.size());
                 logger.info(booksList.get(0).getId());
                 logger.info(booksList.get(0).getName());
                 
             }
             logger.info("==========JDOMParser finish==========");
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (JDOMException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 
     /**
      * DOM4jParser 特征：
　　　　　　1、JDOM的一种智能分支，它合并了许多超出基本XML文档表示的功能
　　　　　　2、它使用接口和抽象基本类方法。
　　　　　　3、具有性能优异、灵活性好、功能强大和极端易用的特点。
　　　　　　4、是一个开放源码的文件
      */
     @SuppressWarnings("unchecked")
     public static void DOM4jParser() {
         // 解析books.xml文件
         // 创建SAXReader的对象reader
         SAXReader reader = new SAXReader();
         try {
             logger.info("==========DOM4jParser start==========");
             // 通过reader对象的read方法加载books.xml文件,获取document对象。
             org.dom4j.Document document = reader.read(new File(DirectoryConst.XML_PARSERE_DIRECTORY_RELATIVE));
             // 通过document对象获取根节点bookstore
             org.dom4j.Element bookStore = document.getRootElement();
             // 通过element对象的elementIterator方法获取迭代器
             Iterator<org.dom4j.Element> it = bookStore.elementIterator();
             // 遍历迭代器，获取根节点中的信息（书籍）
             while (it.hasNext()) {
                 logger.info("-----开始遍历某一本书-----");
                 org.dom4j.Element book = (org.dom4j.Element) it.next();
                 // 获取book的属性名以及 属性值
                 List<org.dom4j.Attribute> bookAttrs = book.attributes();
                 for (org.dom4j.Attribute attr : bookAttrs) {
                     logger.info("属性名：" + attr.getName() + "--属性值："
                             + attr.getValue());
                 }
                 Iterator<org.dom4j.Element> itt = book.elementIterator();
                 while (itt.hasNext()) {
                     org.dom4j.Element bookChild = (org.dom4j.Element) itt.next();
                     logger.info("节点名：" + bookChild.getName() + "--节点值：" + bookChild.getStringValue());
                 }
                 logger.info("-----结束遍历某一本书-----");
             }
             logger.info("==========DOM4jParser finish==========");
         } catch (DocumentException e) {
             e.printStackTrace();
         }
     }
}

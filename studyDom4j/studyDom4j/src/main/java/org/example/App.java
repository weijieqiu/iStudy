package org.example;



import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        try {
            // 使用 InputStream 从本地读 xml 文件进行解析
            // useInputStreamOperaXML();
            // 通过DocumentHelper创建（解析）一个Document对象,并为Document添加元素
            // useDocumentCreateXML();
            // 解析XML字符串获取Document对象 public static Document parseText(String text) throws DocumentException {...}
            // useDocumentParseText();
            // 模拟SpringIOC 依赖注入机制(通过反射实现)，有如下spring_core.xml配置,主要使用了反射机制
            // simulateIOC();
            //  将Document写出
            outPutDocumentXML();
        }catch (Exception  e){
            e.printStackTrace();
        }

    }

    // 将 document对象 输出为xml文件
    private static void outPutDocumentXML() throws Exception {
        logger.info("----------- org.example.App.outPutDocumentXML start");
        String xmlStr = "<books>"
                + "<book><title>AAA</title><author>aaa</author></book>"
                + "<book><title>BBB</title><author>bbb</author></book>"
                + "<book><title>CCC</title><author>ccc</author></book>"
                + "</books>";
        // 解析字符串获取document对象
        Document document = DocumentHelper.parseText(xmlStr);
        //设置输出样式（整洁）
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 将document写出到output.xml中,并指定输出样式
        XMLWriter writer = new XMLWriter(new FileOutputStream("output.xml"),format);
        writer.write(document);
        // 使用pretty（整洁）样式输出到控制台
        writer = new XMLWriter(System.out, format);
        writer.write(document);
        System.out.println("--------------------------------------------------------------");
        // 使用紧凑样式输出到控制台
        format = OutputFormat.createCompactFormat();
        writer = new XMLWriter(System.out, format);
        writer.write(document);
        logger.info("----------- org.example.App.outPutDocumentXML end");
    }

    // 模拟SpringIOC 依赖注入机制(通过反射实现)，有如下spring_core.xml配置,主要使用了反射机制
    private static void simulateIOC() throws FileNotFoundException, DocumentException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        logger.info("----------- org.example.App.simulateIOC start");
        SAXReader reader = new SAXReader();
        // File file =new File("logback.xml");
        File file = new File("spring-ioc.xml");
        if(file.exists()){
            InputStream in = new FileInputStream(file);
            Document document = reader.read(in);
            logger.info(document.asXML());
            Element root = document.getRootElement();
            Element foo;
            for (Iterator i = root.elementIterator("bean"); i.hasNext();){
                foo = (Element) i.next();
                //针对每一个bean实例获取id和name属性
                Attribute id = foo.attribute("id");
                Attribute clazz = foo.attribute("class");

                //利用反射，通过class名称获取class对象
                Class bean = Class.forName(clazz.getText());
                Object object = bean.newInstance();
                Method method = bean.getMethod("sayHello");
                Object result = method.invoke(object);
                logger.info(result.toString());
                // other 省略无关的反射操作
            }

            logger.info("----------- org.example.App.simulateIOC end");

        }



    }

    /**
     * 解析XML字符串获取Document对象 public static Document parseText(String text) throws DocumentException {...}
     */
    private static void useDocumentParseText() throws DocumentException {
        logger.info("----------- org.example.App.useDocumentParseText start");
        Document document = DocumentHelper.parseText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root><person age=\"18\">张三</person><person age=\"19\">李四</person><person age=\"20\">王五</person></root>");
        logger.info(document.asXML());
        logger.info("----------- org.example.App.useDocumentParseText end");
    }

    /**
     * 通过DocumentHelper创建（解析）一个Document对象,并为Document添加元素
     */
    private static void useDocumentCreateXML() {
        logger.info("----------- org.example.App.useDocumentCreateXML start");
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");
        Element p1 =root.addElement("person")
                    .addAttribute("age", "18")
                .addText("张三");
        Element p2 =root.addElement("person")
                .addAttribute("age", "19")
                .addText("李四");
        Element p3 =root.addElement("person")
                .addAttribute("age", "20")
                .addText("王五");
        logger.info(document.asXML());
        logger.info("----------- org.example.App.useDocumentCreateXML end");

    }

    /**
     * 使用 InputStream 从本地读 xml 文件进行解析
     */
    private static void useInputStreamOperaXML() throws FileNotFoundException, DocumentException {
        logger.info("----------- org.example.App.useInputStreamOperaXML start");
        SAXReader reader = new SAXReader();
        File file = new File("book.xml");
        if(file.exists()){
            InputStream in = new FileInputStream(file);
            Document document = reader.read(in);
            logger.info(document.asXML());
        }
        logger.info("----------- org.example.App.useInputStreamOperaXML end");
    }
}

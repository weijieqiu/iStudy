package org.example;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;
import com.fasterxml.jackson.dataformat.xml.deser.XmlTokenStream;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.dataformat.xml.util.DefaultXmlPrettyPrinter;
import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken;
import org.example.pojo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Hello world!
 */
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        // 创建一个 XML 字符串记录学生的详细信息，并将其反序列化到学生对象中，然后再讲其序列化为 JSON 对象
        // useJacksonXMLStudent();
        // 使用树模型
        // useJacksonXMLNode();

        // 使用 stream 流式 API 操作 xml 文档
        useJacksonXMLStream();
    }

    // 使用 stream 流式 API 操作 xml 文档
    private static void useJacksonXMLStream() throws IOException {
        // 第一步： 创建 ObjectMapper 它提供一些功能将转换成Java对象匹配JSON\XML结构,这里解析 XML 文档所以用子类  XmlMapper 它使用XMLParser和ToXmlGenerator的实例实现XML的读/写。
        ObjectMapper xmlMapper = new XmlMapper();

        // 强制JSON 空字符串("")转换为null对象值:
        // xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // 美化输出
        // xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 第二步： 创建 ToXmlGenerator的实例实现XML写入， 由于ToXmlGenerator使用了工厂模式所以要用工厂中创建
        ToXmlGenerator toXmlGenerator = (ToXmlGenerator) xmlMapper.getFactory().createGenerator(new File("student3.xml"), JsonEncoding.UTF8);




        // ToXmlGenerator toXmlGenerator = (ToXmlGenerator) xmlMapper.getFactory().createGenerator(System.out, JsonEncoding.UTF8);
        // 流式输出美化要用这个 new DefaultXmlPrettyPrinter()
        toXmlGenerator.setPrettyPrinter(new DefaultXmlPrettyPrinter());
        toXmlGenerator.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        /**
         * 最后写入 student3.xml 的内容为
         * <student>
         *   <cdata>中文</cdata>
         *   <age>22</age>
         *   <classroom>
         *     <grade>4</grade>
         *     <id>1</id>
         *     <name>软件工程</name>
         *   </classroom>
         *   <id>101</id>
         *   <name>张三</name>
         *   <item>1</item>
         *   <item>2</item>
         * </student>
         */
        QName student = new QName("student");
        toXmlGenerator.setNextName(student);  //<student>
        toXmlGenerator.writeStartObject();  // 写入对象开始
        toXmlGenerator.writeStringField("cdata", "<![CDATA[]]>");
        toXmlGenerator.writeStringField("age", "22");
        toXmlGenerator.writeStringField("id", "101");
        toXmlGenerator.writeStringField("name", "张三");

        int[] item1 = new int[]{1, 2};
        for (int i = 0; i < item1.length; i++) {
            toXmlGenerator.writeNumberField("item", item1[i]);
        }
        QName classroom = new QName("classroom");
        toXmlGenerator.startWrappedValue(classroom, student);
        toXmlGenerator.writeNumberField("grade", 4);
        toXmlGenerator.writeNumberField("id", 1);
        toXmlGenerator.writeStringField("name", "软件工程");
        toXmlGenerator.finishWrappedValue(classroom, student);

        toXmlGenerator.writeEndObject();    // 写入对象结束
        // 第三步: 输出前需要flush()，再执行 close() 才会输出到应该序列化的地方

        toXmlGenerator.flush();
        toXmlGenerator.close();


        // FromXmlParser 的实例读取XML反序列化
        FromXmlParser fromXmlParser = (FromXmlParser) xmlMapper.getFactory().createParser(new File("student3.xml"));
    }

    // 使用树模型
    private static void useJacksonXMLNode() throws IOException {
        logger.info("useJacksonXMLNode start");
        ObjectMapper xmlMapper = new XmlMapper();
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<student><cdata><![CDATA[中<a>文]]></cdata><age>22</age><classroom><grade>4</grade><id>1</id><name>软件工程</name></classroom><id>101</id><name>张三</name><item>1</item><item>2</item></student>";
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 我看了源码，发现没有 XmlNode, 但是无妨，使用 JsonNode 去操作XML文档也没关系，一样也是操作对象就对了
        JsonNode rootNode = xmlMapper.readTree(xmlString);
        // 直接打印整个节点树
        System.out.println(rootNode);
        // 获取节点树中的 age 节点
        JsonNode ageNode = rootNode.path("age");
        System.out.println("age:" + ageNode.textValue());


        // 遍历文档节点树
        JsonNode classroomNode = rootNode.path("classroom");
        Iterator<JsonNode> iterator = classroomNode.elements();
        while (iterator.hasNext()) {
            JsonNode classroomChirldrensNode = iterator.next();
            System.out.println(classroomChirldrensNode.textValue());
        }


        // ArrayNode marksNode = xmlMapper.createArrayNode();
        // marksNode.add(100);
        // marksNode.add(90);
        // marksNode.add(85);
        //
        //
        // ObjectNode rootNode1 = xmlMapper.createObjectNode();
        //
        //
        //
        //
        // rootNode1.put("name", "Mahesh Kumar");
        // rootNode1.put("age", 21);
        // rootNode1.put("verified", false);
        // rootNode1.set("root",marksNode);
        // // rootNode1.put("marks",marksNode);
        // xmlMapper.writeValue(new File("./student1.xml"), rootNode1);


        logger.info("useJacksonXMLNode end");
    }

    // 创建一个 XML 字符串记录学生的详细信息，并将其反序列化到学生对象中，然后再讲其序列化为 JSON 对象
    private static void useJacksonXMLStudent() {
        logger.info("useJacksonXMLStudent start");
        // 创建 ObjectMapper 对象，这里使用 XmlMapper 表示操作的是 xml 文件
        ObjectMapper xmlMapper = new XmlMapper();
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<student><cdata><![CDATA[中<a>文]]></cdata><age>22</age><classroom><grade>4</grade><id>1</id><name>软件工程</name></classroom><id>101</id><name>张三</name><item>1</item><item>2</item></student>";
        // map xml to student
        try {
            // Student student = xmlMapper.readValue(xmlString, Student.class);
            Student student = xmlMapper.readValue(xmlString, new TypeReference<Student>() {
            });
            System.out.println(student.getItem());
            System.out.println(student);
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlString = xmlMapper.writeValueAsString(student.getItem().get(0));
            System.out.println(xmlString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.info("useJacksonXMLStudent end");
    }
}

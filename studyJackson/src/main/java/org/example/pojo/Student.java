package org.example.pojo;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "student")
public class Student {
    private int id;
    private String name;
    private int age;
    private Classroom classroom;
    //  @JacksonXmlCData 解析 <![CDATA[ ]]>
    @JacksonXmlCData
    // @JacksonXmlProperty xml 名称
    @JacksonXmlProperty(localName = "cdata")
    private String cData;


    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> item;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public String getcData() {
        return cData;
    }

    public void setcData(String cData) {
        this.cData = cData;
    }

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", classroom=" + classroom +
                ", cData='" + cData + '\'' +
                ", item=" + item +
                '}';
    }
}

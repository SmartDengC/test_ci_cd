package com.pojo;

import com.utils.MakeJson;

import java.io.IOException;

/**
 * @ClassName Student
 * @Version 1.0
 * @Author Lenovo
 * @Date 2019/10/24 19:05
 * @Description TODO
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
public class Student {
    private String name;
    private int age;
    public Student(){}
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
    @Override
    public String toString(){
        return "Student [ name: "+name+", age: "+ age+ " ]";
    }

    public static void main(String[] args) throws IOException {
        Student student = new Student();
        student.age=19;
        student.name="吕志伟";
        MakeJson makeJson = new MakeJson();
        makeJson.writeJSON(student);
        System.out.print(makeJson.readJSON());
    }
}


package com.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.Student;

import java.io.File;
import java.io.IOException;

public class MakeJson {
//    public Object toJSON(Student student){
//        MakeJson tester = new MakeJson();
//        try {
//            student.setAge(10);
//            student.setName("Mahesh");
//            tester.writeJSON(student);
//
//            Student student1 = tester.readJSON();
//            System.out.println(student1);
//
//        } catch (JsonParseException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     *
     * @param object
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * parameter 需要一个实体类
     */
    public void writeJSON(Object object) throws JsonGenerationException, JsonMappingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("object.json"), object);
    }

    public String readJSON() throws JsonParseException, JsonMappingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
       Object object = mapper.readValue(new File("object.json"), Object.class);
        return object.toString();
    }
}
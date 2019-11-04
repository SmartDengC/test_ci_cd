package com;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.Student;
import com.service.impl.DataImportServiceImpl;

import java.io.*;

public class test {
    public static void main(String args[]) throws FileNotFoundException {
        File file = new File("C:/Users/Lenovo/Desktop/生源分析系统/生源分析系统原始数据/2015data/四川/11079aa/TD_KLDM.dbf");
        FileInputStream pcdm = new FileInputStream(file);
        DataInputStream data = new DataInputStream(pcdm);
        DataImportServiceImpl a = new DataImportServiceImpl();
        System.out.print(a.insertTD_KLDM(data,2019,1).get(1).getKLMC());
    }
}


package com;

import java.io.*;

public class test {
    public static void main(String args[]) throws FileNotFoundException {
        File file = new File("C:/Users/Lenovo/Desktop/生源分析系统/生源分析系统原始数据/2015data/四川/11079aa/TD_KLDM.dbf");
        FileInputStream pcdm = new FileInputStream(file);
        DataInputStream data = new DataInputStream(pcdm);
    }
}


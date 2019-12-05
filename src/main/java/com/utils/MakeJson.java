package com.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.javassist.bytecode.ByteArray;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MakeJson {
    private static String hexStr =  "0123456789ABCDEF";
    public  byte[] hexToByte(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    public ByteArrayInputStream getStringStream(String sInputString){

    if (sInputString != null && !sInputString.trim().equals("")) {
        try {
            ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
            return tInputStringStream;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    return null;
    }

//    public void writeFile(InputStream input) throws IOException {
////        try {
////            //准备文件lol.txt其中的内容是AB，对应的ASCII分别是65 66
////            File f =new File("d:/lol.txt");
////            //创建基于文件的输入流
////            FileInputStream fis =new FileInputStream(f);
////            //创建字节数组，其长度就是文件的长度
//            byte[] all =new byte[3];
//        FileInputStream fis = (FileInputStream) input;
//            //以字节流的形式读取文件所有内容
//            fis.read(all);
//            for (byte b : all) {
//                //打印出来是65 66
//                System.out.println(b);
//            }
//
//            //每次使用完流，都应该进行关闭
//            fis.close();
//
//
//    }
}
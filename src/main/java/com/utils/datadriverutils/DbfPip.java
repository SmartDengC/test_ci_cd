package com.utils.datadriverutils;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.sun.org.apache.bcel.internal.generic.NEW;
import joinery.DataFrame;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 导入DBF文件的类
 */
public class DbfPip extends AbstractFilePipUtils {

    public final static  String CHARSET = "GB2312";
    /**
     * Default constructor
     */
    public DbfPip() { }

    /**
     * 将DBF格式数据转换成DataFrame对象的数据。具体流程如下：
     * 首先按照指定的编码方式打开文件DBF，将DBF文件中的列名导入到DataFrame中的，将DBF文件中的数据去除空字符后导入到DataFrame中。
     * 异常处理：
     * 1、如果文件名无效：
     * 没有找到file文件
     * @param file DBF文件路径
     * @param encode DataFrame对象中的编码方式
     * @return
     */
    public static DataFrame open(String file, String encode) {
        InputStream dbfFileInputStream = null;
        DataFrame data =new DataFrame();
        try {
            // 读取文件的输入流
            dbfFileInputStream = new FileInputStream(file);
            // 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
            DBFReader reader = new DBFReader(dbfFileInputStream);
            // 设置读取文件的字符集规范，这一步是为了解决中文乱码问题
            reader.setCharactersetName(CHARSET);
            // 调用DBFReader对实例方法得到path文件中字段的个数
            int fieldsCount = reader.getFieldCount();
            // 取出字段信息写入到dataframe的头中
            for (int i = 0; i < fieldsCount; i++) {
                DBFField field = reader.getField(i);
                data.add(field.getName());
            }
            Object[] rowValues;
            // 一条条取出path文件中记录
            while ((rowValues = reader.nextRecord()) != null) {
                data.append(Arrays.asList(rowValues));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbfFileInputStream.close();
            } catch (Exception e) {
            }
        }
        return data;
    }

    /**
     *将被InputSteam类型的Dbf文件转换成DataFrame类型
     * @param file InputStream类型的Dbf文件
     * @return DataFrame对象中的编码方式
     */
    public static DataFrame open(InputStream file){
        DataFrame data =new DataFrame();

        try {
            // 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
            DBFReader reader = new DBFReader(file);
            // 设置读取文件的字符集规范，这一步是为了解决中文乱码问题
            reader.setCharactersetName(CHARSET);
            // 调用DBFReader对实例方法得到path文件中字段的个数
            int fieldsCount = reader.getFieldCount();
            // 取出字段信息写入到dataframe的头中
            Boolean hasDateYtpe=false;
            for (int i = 0; i < fieldsCount; i++) {
                DBFField field = reader.getField(i);
                data.add(field.getName());
                if(field.getName().equals("CSNY")){
                    hasDateYtpe=true;
                }
            }
            Object[] rowValues;
            // 一条条取出path文件中记录遍历所有的数据，如果是Datec处理它
            String strDateFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            while ((rowValues = reader.nextRecord()) != null) {
//                if (hasDateYtpe ){
//                    if(rowValues[4]!=null){
//                        String dataFormat =sdf.format(new Date(rowValues[4].toString()));
//                        rowValues[4]= dataFormat;
//                    }else {
//                        Object unllObject ="";
//                        rowValues[4]=unllObject;
//                    }
//                }
                data.append(Arrays.asList(rowValues));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (Exception e) {
            }
        }
        return  data;
    }

}
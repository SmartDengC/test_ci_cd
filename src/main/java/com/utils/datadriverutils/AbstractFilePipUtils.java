package com.utils.datadriverutils;
import joinery.DataFrame;

/**
 * 数据导入的最高抽象类
 */
public abstract class AbstractFilePipUtils {

    /**
     * Default constructor
     */
    public AbstractFilePipUtils() {
    }

    /**
     * 数据编码方式
     */
    public String Ecode;

    /**
     * 根据参数地址，打开文件，返回Data类型的数据
     * @param file
     * @return
     */
    public static DataFrame open(String file) {
        // TODO implement here
        return null;
    }

}
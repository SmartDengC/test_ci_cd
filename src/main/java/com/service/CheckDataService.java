package com.service;


import java.io.IOException;

/**
 * @program: ssm
 *
 * @description: 根据年份检查数据的service层接口
 *
 * @author: 吕志伟
 *
 * @create: 2019-10-20 20:30
 **/
public interface CheckDataService {
    /**
     * 检查该表中是否有数据
     * @param tableName 表名
     * @return json
     * @throws IOException
     */
    public String checkData(String tableName) throws IOException;

    /**
     *检查该年份是否有数据
     * @param year
     * @return
     * @throws IOException
     */
    public String checkYear(String year) throws IOException;

    /**
     *获取该表中的特点数据
     * @param tableName
     * @return
     */
    public String getData(String tableName);
}

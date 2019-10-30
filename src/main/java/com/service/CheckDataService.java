package com.service;


import com.fasterxml.jackson.core.JsonProcessingException;

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
     * @param year 年份
     * @param province 省份
     * @return json
     * @throws IOException
     */
    public String checkYear(String year,int province) throws IOException;

    /**
     *检查该年份是否有数据
     * @param year
     * @return
     * @throws IOException
     */
    public String checkData(String year) throws IOException;

    /**
     *获取该表中的特点数据
     * @param year 年份
     * @param ems 快递单号
     * @return
     */
    public String getData(String year,String ems) throws JsonProcessingException;
}

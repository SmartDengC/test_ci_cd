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
    public String checkdata(String tablename) throws IOException;
    public String checkyear(String year) throws IOException;
    public String getdata(String tablename);
}

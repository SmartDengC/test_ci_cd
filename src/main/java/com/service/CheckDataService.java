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
    * Modification User： 程序修改时由修改人员编写
    * Modification Date: 程序修改的时间
    *
    *
    * @Author 吕志伟
    * @param: year
    * @param: province
    * @return
    */
    public String checkYear(String year,int province) throws IOException;

   /**
    * Modification User： 程序修改时由修改人员编写
    * Modification Date: 程序修改的时间
    *
    *
    * @Author 吕志伟
    * @param: year
    * @return
    */
    public String checkData(String year) throws IOException;

  /**
   * Modification User： 吕志伟
   * Modification Date: 2019/10/31
   *
   *
   * @Author 吕志伟
   * @param: year 年份
   * @param: ems 快递单号
   * @return
   */
    public String getData(String year,String ems) throws JsonProcessingException;
}

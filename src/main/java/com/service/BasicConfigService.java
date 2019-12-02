package com.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * @program: ssm
 *
 * @description: 根据年份检查数据的service层接口,缺少了一个接口
 *
 * @author: 吕志伟
 *
 * @create: 2019-10-20 20:30
 **/
public interface BasicConfigService {
   /**
    * Modification User： 吕志伟
    * Modification Date: 2019/11/4
    *
    * 检查该年份的T_TDD 表中是否有数据
    * @Author 吕志伟
    * @param year 年份
    * @param province 省份
    * @return 0：说明表中没有符合参数的数据
    * 1：说明表中有符合参数的数据
    * @throws IOException
    */
    public String checkYear(String year,int province) throws IOException;

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     * 检查三张表中是否有数据
     * @Author 吕志伟
     * @param year 年份
     * @return 0：该表中无数据
     * 1：该表中有数据
     * @throws IOException
     */
    public String checkData(String year) throws IOException;

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     * 获取三张表中的所有数据
     * @Author 吕志伟
     * @param year 年份
     * @param fileType 文件类型
     * @return  三张表中的数据
     * @throws JsonProcessingException
     */
    public String getData(String year,String fileType) throws JsonProcessingException;

   /**
    * Modification User: 邓聪
    * Modification Date: 20
    *
    * 上传文件
    * @author 邓聪
    * @param year 年份
    * @param file 文件对象
    * @param fileType 文件类型
    * @return 文件上传的状态
    * @throws Exception
    */

   public String uploadFile(String year, MultipartFile file, String fileType) throws Exception;

   /**
    * 重新上传文件
    * @param year 年份
    * @param file 文件
    * @param fileType 文件类型
    * @return 文件上传结果
    */
   public String reUploadFile(String year, MultipartFile file, String fileType) throws Exception;


   /**
    * Modification User: 邓聪
    * Modification Date: 2019/11/29
    *
    * 返回省份map{"四川":1}
    * @author 邓聪
    * @return map
    */

   public HashMap<String, Integer> provinceMap();


   public Boolean judgeFilePairFileType(MultipartFile file, String fileType) throws Exception;
}

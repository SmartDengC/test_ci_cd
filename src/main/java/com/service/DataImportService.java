package com.service;

import com.pojo.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @ClassName ImportDataService
 * @Author 吕志伟
 * @Time 2019/10/25 11:16
 * @Description 数据导入的接口，将dbf文件里的内容存储到pojo实体类中
 * 再由mapper层接口存入数据库
 */
public interface DataImportService {

   /**
    * Modification User： 吕志伟
    * Modification Date: 2019/10/31
    *
    * 导入TD_CJXDM数据接口函数
    * @Author 吕志伟
    * @param TD_CJXDMFile 导入的dbf数据
    * @param year 年份
    * @param province 省份
    * @return 是否插入成功，1代表成功，0代表失败
    */
    public List<TD_CJXDM> insertTD_CJXDM(InputStream TD_CJXDMFile, int year, int province);

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *导入TD_DQDM数据接口函数
     * @author 吕志伟
     * @param TD_DQDMFile 导入的dbf文件
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public List<TD_DQDM> insertTD_DQDM(InputStream TD_DQDMFile, int year, int province);

/**
 * Modification User： 吕志伟
 * Modification Date: 2019/10/31
 *
 *导入TD_JHXZDM接口函数
 * @author 吕志伟
 * @param TD_JHXZDMFile 导入的dbf文件
 * @param year 年份
 * @param province 省份
 * @return 是否插入成功，1代表成功，0代表失败
 */
    public List<TD_JHXZDM> insertTD_JHXZDM(InputStream TD_JHXZDMFile, int year, int province);

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *导入TD_KLDM数据的接口函数
     * @author 吕志伟
     * @param TD_KLDMFile 导入的dbf文件
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public List<TD_KLDM> insertTD_KLDM(InputStream  TD_KLDMFile, int year, int province);

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *导入TD_KSLBDM数据的接口函数
     * @author 吕志伟
     * @param TD_KSLBDMFile 导入的dbf文件
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public List<TD_KSLBDM> insertTD_KSLBDM(InputStream  TD_KSLBDMFile, int year, int province);

    /**
     * Modification User：吕志伟
     * Modification Date: 2019/10/31
     *
     *导入TD_KSLXDM数据的接口函数
     * @author 吕志伟
     * @param TD_KSLXDMFile 导入的dbf文件
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public List<TD_KSLXDM>  insertTD_KSLXDM(InputStream  TD_KSLXDMFile, int year, int province);

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *导入TD_LQFSDM数据的接口函数
     * @author 吕志伟
     * @param TD_LQFSDMFile 导入的dbf文件
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public List<TD_LQFSDM> insertTD_LQFSDM(InputStream  TD_LQFSDMFile, int year, int province);

   /**
    * Modification User： 吕志伟
    * Modification Date: 2019/10/31
    *
    *导入TD_MZDM数据的接口函数
    * @author 吕志伟
    * @param TD_MZDMFile 导入的dbf文件
    * @param year 年份
    * @param province 省份
    * @return 是否插入成功，1代表成功，0代表失败
    */
    public List<TD_MZDM> insertTD_MZDM(InputStream  TD_MZDMFile, int year, int province);

   /**
    * Modification User： 吕志伟
    * Modification Date: 2019/10/31
    *
    *导入TD_PCDM数据的接口函数
    * @author 吕志伟
    * @param TD_PCDMFile 导入的dbf文件
    * @param year 年份
    * @param province 省份
    * @return 是否插入成功，1代表成功，0代表失败
    */
    public List<TD_PCDM> insertTD_PCDM(InputStream TD_PCDMFile, int year, int province);
}

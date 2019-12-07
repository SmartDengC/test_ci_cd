package com.service;

/**
 * @title: DataImportService
 * @projectName FreshmanInfomationAnalysSystem
 * @description: TODO
 * @author 陈泯全
 * @date 2019/10/2321:30
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pojo.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName DataImportService
 * @Author 陈泯全
 * @Time 2019/10/23 21:30
 * @Description 处理dbf数据业务接口，实现dbf文件到实体list类型的转化
 */
public interface DataImportService {

    /**陈泯全
     * 导入所有的数据
     * @param dbfsMap 一个包含dbf文件名与其InputStream的HashMap
     * @param year 年份
     * @param province 省份代码
     * @return 导入结果，1代表成功；0代表失败
     */
    public int importData(HashMap<String, InputStream> dbfsMap, int year, int province)throws Exception;

    /**陈泯全
     * @param T_QBJHKFile InputStream类型的T_QBJHK.dbf数据
     * @param year 年份
     * @param province 省份
     * @return TD_QBJHK的实体集合，如果出错返回null
     */
    public List<TD_QBJHK> importT_QBJHKTable(InputStream T_QBJHKFile, int year, int province);
    /**陈泯全
     * 导入t_jhk数据接口函数
     * @param TD_BYLBDMFile InputStream类型的BYlbdm.dbf数据
     * @param year 年份
     * @param province 省份代码
     * @return TD_QBJHK的实体集合，如果出错返回null
     */
    public List<TD_BYLBDM> importTD_BYLBDMTable(InputStream TD_BYLBDMFile, int year, int province);
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

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/10/31
     *
     * 导入TD_WYYZDM数据
     * @author 邓聪
     * @param TD_WYYZDMFile InputStream类型的TD_WYYZDM.dbf数据
     * @param year 年份
     * @param province 省份代码
     * @return TD_WYYZDM的实体集合，如果出错返回null
     */

    public List<TD_WYYZDM> importTD_WYYZDMTable(InputStream TD_WYYZDMFile, int year, int province);

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/10/31
     *
     * 导入TD_ZCDM.dbf数据
     * @author 邓聪
     * @param province 省份代码
     * @param TD_ZCDMFile InputStearm 类型的TD_ZCDM.dbf数据
     * @param year 年份
     * @return TD_ZCDM的实体集合，如果出错返回null
     */
    public List<TD_ZCDM> importTD_ZCDMTable(InputStream TD_ZCDMFile, int year, int province);

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/10/31
     *
     * 导入ZYTZDM.dbf数据
     * @author 邓聪
     * @param TD_ZYTZDMFile 数据文件
     * @param year 年份 默认本年
     * @param province 省份代码
     * @return TD_ZYTZDM的实体集合，如果出错返回null
     */

    public List<TD_ZYTZDM> importTD_ZYTZDMTable(InputStream TD_ZYTZDMFile, int year, int province);

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/10/31
     *
     * 导入TD_ZZMMDMFile数据
     * @param TD_ZZMMDMFile InputStream数据
     * @param year 年份,默认本年
     * @param province 省份
     * @return TD_ZZMMDMFile的实体集合，如果出错返回null
     */
    public List<TD_ZZMMDM> importTD_ZZMMDMTable(InputStream TD_ZZMMDMFile, int year, int province);
    /**邓聪
     * 获取数据库表是否导入的状态
     * @param year 年份
     * @param province 省份
     * @return 0 or 1，1表示有数据，0则相反
     */
    public String getTableStatus(String year, String province) throws JsonProcessingException;

    /**邓聪
     * 获取所有省信息，包括代码
     * @return json 对象list
     */
    public List<TD_SFDM> getProvincesStatus();

    /*吕志伟*/

    /**
     * 吕志伟
     * 检查有没有省份未完成整个导入流程
     * @param year
     * @return 0//无未完成导入省份；1//未完成省份进行到数据清洗；2//未完成省份进行到分数整合
     */
    public String checkData(String year) throws JsonProcessingException;

    /**
     * 吕志伟
     * 清空该省数据库中的数据
     * @param province
     * @return 0//清空失败；1//清空成功
     */
    public  String clearData(String year,String province) throws JsonProcessingException;

    /**
     * 吕志伟
     * 检测已导入省份和未导入省份
     * @param year
     * @return json {[ "imported":list,
     * "unimport":list]}
     * @throws JsonProcessingException
     */
    public String checkProvince(String year) throws JsonProcessingException;

//    /**
//     * 吕志伟
//     * 修改规范值
//     * @param dm 规范值代码
//     * @param value 规范值
//     * @return 0//修改失败；1//修改成功
//     * @throws JsonProcessingException
//     */
//    public  String updateGaugeValue(String dm,String value) throws JsonProcessingException;
//
//    public String addGaugeValue(String data) throws JsonProcessingException;
}
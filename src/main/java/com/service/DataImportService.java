package com.service;

import java.io.InputStream;

/**
 * @ClassName ImportDataService
 * @Author 吕志伟
 * @Time 2019/10/25 11:16
 * @Description 数据导入的接口，将dbf文件里的内容存储到pojo实体类中
 * 再由mapper层接口存入数据库
 */
public interface DataImportService {

    /**
     * 导入TD_CJXDM数据接口函数
     * @param TD_CJXDMFile InputStream类型的T_KSHKCJ.dbf数据
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public int insertTD_CJXDM(InputStream TD_CJXDMFile, int year, int province);

    /**
     * 导入TD_DQDM数据接口函数
     * @param TD_DQDMFile InputStream类型的T_KSHKCJ.dbf数据
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public int insertTD_DQDM(InputStream TD_DQDMFile, int year, int province);

    /**
     * 导入TD_JHXZDM数据接口函数
     * @param TD_JHXZDMFile InputStream类型的T_KSHKCJ.dbf数据
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public int insertTD_JHXZDM(InputStream TD_JHXZDMFile, int year, int province);

    /**
     * 导入TD_KLDM数据接口函数
     * @param  TD_KLDMFile InputStream类型的T_KSHKCJ.dbf数据
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public int insertTD_KLDM(InputStream  TD_KLDMFile, int year, int province);

    /**
     * 导入TD_KSLBDM数据接口函数
     * @param  TD_KSLBDMFile InputStream类型的T_KSHKCJ.dbf数据
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public int insertTD_KSLBDM(InputStream  TD_KSLBDMFile, int year, int province);

    /**
     * 导入TD_KSLXDM数据接口函数
     * @param  TD_KSLXDMFile InputStream类型的T_KSHKCJ.dbf数据
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public int insertTD_KSLXDM(InputStream  TD_KSLXDMFile, int year, int province);

    /**
     * 导入TD_LQFSDM数据接口函数
     * @param  TD_LQFSDMFile InputStream类型的T_KSHKCJ.dbf数据
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public int insertTD_LQFSDM(InputStream  TD_LQFSDMFile, int year, int province);

    /**
     * 导入TD_MZDM数据接口函数
     * @param  TD_MZDMFile InputStream类型的T_KSHKCJ.dbf数据
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public int insertTD_MZDM(InputStream  TD_MZDMFile, int year, int province);

    /**
     * 导入TD_PCDM数据接口函数
     * @param  TD_PCDMFile InputStream类型的T_KSHKCJ.dbf数据
     * @param year 年份
     * @param province 省份
     * @return 是否插入成功，1代表成功，0代表失败
     */
    public int insertTD_PCDM(InputStream TD_PCDMFile, int year, int province);
}

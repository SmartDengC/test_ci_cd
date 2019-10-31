package com.service.impl;

import com.dao.DataImportDao;
import com.pojo.*;
import com.service.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import joinery.DataFrame;
import com.utils.datautils.DataFrameUtils;
import com.utils.datadriverutils.DbfPip;

/**
 * @ClassName ImportDataServiceImpl
 * @Version 1.0
 * @Author 吕志伟
 * @Date 2019/10/25 17:01
 * @Description 处理dbf数据业务的实现类，实现dbf文件到实体list类型的转化
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */

@Service
public class DataImportServiceImpl implements DataImportService {

    @Autowired
    DataImportDao dataImportDao;

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_CJXDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_CJXDM> insertTD_CJXDM(InputStream TD_CJXDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_CJXDMFile);
        if(dataFrame.isEmpty())
        {
            return null;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_CJXDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_CJXDM> td_cjxdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_cjxdms==null){
            return null;
        }
        else {
            return td_cjxdms;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_DQDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_DQDM> insertTD_DQDM(InputStream TD_DQDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_DQDMFile);
        if(dataFrame.isEmpty())
        {
            return null;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_DQDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_DQDM> td_dqdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_dqdms==null){
            return null;
        }
        else {
            return td_dqdms;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_JHXZDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_JHXZDM> insertTD_JHXZDM(InputStream TD_JHXZDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_JHXZDMFile);
        if(dataFrame.isEmpty())
        {
            return null;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_JHXZDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_JHXZDM> td_jhxzdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_jhxzdms==null){
            return null;
        }
        else {
            return td_jhxzdms;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_KLDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_KLDM> insertTD_KLDM(InputStream TD_KLDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_KLDMFile);
        if(dataFrame.isEmpty())
        {
            return null;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_KLDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_KLDM> td_kldms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_kldms==null){
            return null;
        }
        else {
            return td_kldms;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_KSLBDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_KSLBDM> insertTD_KSLBDM(InputStream TD_KSLBDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_KSLBDMFile);
        if(dataFrame.isEmpty())
        {
            return null;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_KSLBDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_KSLBDM> td_kslbdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_kslbdms==null){
            return null;
        }
        else {
            return td_kslbdms;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_KSLXDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_KSLXDM> insertTD_KSLXDM(InputStream TD_KSLXDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_KSLXDMFile);
        if(dataFrame.isEmpty())
        {
            return null;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_KSLXDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_KSLXDM> td_kslxdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_kslxdms==null){
            return null;
        }
        else {
            return td_kslxdms;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_LQFSDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_LQFSDM> insertTD_LQFSDM(InputStream TD_LQFSDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_LQFSDMFile);
        if(dataFrame.isEmpty())
        {
            return null;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_LQFSDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_LQFSDM> td_lqfsdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_lqfsdms==null){
            return null;
        }
        else {
            return td_lqfsdms;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_MZDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_MZDM> insertTD_MZDM(InputStream TD_MZDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_MZDMFile);
        if(dataFrame.isEmpty())
        {
            return null;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_MZDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_MZDM> td_mzdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_mzdms==null){
            return null;
        }
        else {
            return td_mzdms;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_PCDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_PCDM> insertTD_PCDM(InputStream TD_PCDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_PCDMFile);
        if(dataFrame.isEmpty())
        {
            return null;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_PCDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_PCDM> td_pcdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_pcdms==null){
            return null;
        }
        else {
            return td_pcdms;
        }
    }
}
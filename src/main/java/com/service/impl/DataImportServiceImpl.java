package com.service.impl;

import com.dao.DataImportDao;
import com.pojo.*;
import com.service.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public int insertTD_CJXDM(InputStream TD_CJXDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_CJXDMFile);
        if(dataFrame.isEmpty())
        {
            return 1;
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
        System.out.println(td_cjxdms.size());
        return 0;
    }

    @Override
    public int insertTD_DQDM(InputStream TD_DQDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_DQDMFile);
        if(dataFrame.isEmpty())
        {
            return 1;
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
        System.out.println(td_dqdms.size());
        return 0;
    }

    @Override
    public int insertTD_JHXZDM(InputStream TD_JHXZDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_JHXZDMFile);
        if(dataFrame.isEmpty())
        {
            return 1;
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
        System.out.println(td_jhxzdms.size());
        return 0;
    }

    @Override
    public int insertTD_KLDM(InputStream TD_KLDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_KLDMFile);
        if(dataFrame.isEmpty())
        {
            return 1;
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
        System.out.println(td_kldms.size());
        return 0;
    }

    @Override
    public int insertTD_KSLBDM(InputStream TD_KSLBDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_KSLBDMFile);
        if(dataFrame.isEmpty())
        {
            return 1;
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
        System.out.println(td_kslbdms.size());
        return 0;
    }

    @Override
    public int insertTD_KSLXDM(InputStream TD_KSLXDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_KSLXDMFile);
        if(dataFrame.isEmpty())
        {
            return 1;
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
        System.out.println(td_kslxdms.size());
        return 0;
    }

    @Override
    public int insertTD_LQFSDM(InputStream TD_LQFSDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_LQFSDMFile);
        if(dataFrame.isEmpty())
        {
            return 1;
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
        System.out.println(td_lqfsdms.size());
        return 0;
    }

    @Override
    public int insertTD_MZDM(InputStream TD_MZDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_MZDMFile);
        if(dataFrame.isEmpty())
        {
            return 1;
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
        System.out.println(td_mzdms.size());
        return 0;
    }

    @Override
    public int insertTD_PCDM(InputStream TD_PCDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_PCDMFile);
        if(dataFrame.isEmpty())
        {
            return 1;
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
        System.out.println(td_pcdms.size());
        return 0;
    }
}
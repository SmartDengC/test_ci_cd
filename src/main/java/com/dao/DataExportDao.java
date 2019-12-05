package com.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName DataExportDao
 * @Version 1.0
 * @Author Lenovo
 * @Date 2019/12/1 14:37
 * @Description TODO
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
public interface DataExportDao {
    public List<Object> requestNotice(@Param("NF") String year);

    public List<Object> requestBankCard(@Param("NF") String year);

    public  List<Object> requestExpressSingle(@Param("YEAR") String year);
}
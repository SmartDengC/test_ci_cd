package com.dao;

import com.pojo.BankCard;
import com.pojo.KDD;
import com.pojo.Notice;
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
    public List<Notice> requestNotice(@Param("NF") String year);

    public List<BankCard> requestBankCard(@Param("NF") String year);

    public  List<KDD> requestExpressSingle(@Param("YEAR") String year);
}
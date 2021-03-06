package com.service.impl;

import com.dao.DataExportDao;
import com.pojo.BankCard;
import com.pojo.KDD;
import com.pojo.Notice;
import com.service.DataExportService;
import com.utils.datautils.toExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName DataExportServiceImpl
 * @Version 1.0
 * @Author Lenovo
 * @Date 2019/11/29 19:52
 * @Description TODO
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
@Service
public class DataExportServiceImpl implements DataExportService {

    @Autowired
    DataExportDao dataExportDao;


    @Override
    public HSSFWorkbook exportNotice(String year){
        toExcel toexcel = new toExcel();
        List<Notice> list = dataExportDao.requestNotice(year);
        HashMap titleMap = new HashMap();
        titleMap.put("XM","姓名");
        titleMap.put("ZYMC","专业");
        titleMap.put("CCCC","层次");
        titleMap.put("XUEZHI","学制");
        titleMap.put("KSH","考生号");
        titleMap.put("SFZH","身份证号");
        String sheetName = "通知书";
        HSSFWorkbook wb = toexcel.excelExport(list, titleMap, sheetName);
        return wb;
    }

    @Override
    public HSSFWorkbook exportBankCard(String year){
        toExcel toexcel = new toExcel();
        List<BankCard> list = dataExportDao.requestBankCard(year);
        HashMap titleMap = new HashMap();
        titleMap.put("XM","姓名");
        titleMap.put("JTDZ","通讯地址");
        titleMap.put("LXDH","联系电话");
        titleMap.put("SFZH","身份证号");
        String sheetName = "银行卡";
        HSSFWorkbook wb = toexcel.excelExport(list, titleMap, sheetName);
        return wb;
    }

    @Override
    public HSSFWorkbook exportExpressSingle(String year){
        toExcel toexcel = new toExcel();
        List<KDD> list = dataExportDao.requestExpressSingle(year);
        HashMap titleMap = new HashMap();
        titleMap.put("SJR","收件人");
        titleMap.put("XM","姓名");
        titleMap.put("JTDZ","通讯地址");
        titleMap.put("LXDH","联系电话");
        titleMap.put("KSH","考生号");
        titleMap.put("EMS","EMS编号");
        String sheetName = "快递单";
        HSSFWorkbook wb = toexcel.excelExport(list, titleMap, sheetName);

        return wb;
    }
}
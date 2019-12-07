package com.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.util.List;

/**
 * @ClassName DataExportService
 * @Author Lenovo
 * @Time 2019/11/29 19:51
 * @Description TODO
 */
public interface DataExportService {

    public HSSFWorkbook exportNotice(String year);

    public HSSFWorkbook exportBankCard(String year);

    public HSSFWorkbook exportExpressSingle(String year);
}

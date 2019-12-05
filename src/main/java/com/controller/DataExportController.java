package com.controller;

import com.service.DataExportService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @ClassName DataExportController
 * @Version 1.0
 * @Author 吕志伟
 * @Date 2019/11/29 19:49
 * @Description TODO
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
@RestController
@RequestMapping("/dataExport")
public class DataExportController {
    @Autowired
    DataExportService dataExportService;

    @RequestMapping("/getScore")
    public String getScore(){
        String result ="";

        return result;
    }

    @RequestMapping("/exportNotice")
    public HSSFWorkbook exportNotice(String year){
        HSSFWorkbook file = dataExportService.exportNotice(year);
        return file;
    }

    @RequestMapping("/exportBankCard")
    public HSSFWorkbook exportBankCard(String year){
        HSSFWorkbook file = dataExportService.exportBankCard(year);
        return file;
    }

    @RequestMapping("/exportExpressSingle")
    public HSSFWorkbook exportExpressSingle(String year){
        HSSFWorkbook file  = dataExportService.exportExpressSingle(year);
            return file;
        }

}
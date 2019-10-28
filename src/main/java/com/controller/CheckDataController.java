package com.controller;

import com.service.CheckDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


/**
 * @program: ssm
 *
 * @description: 根据年份检查数据的springmvc层接口
 *
 * @author: 吕志伟
 *
 * @create: 2019-10-20 20:30
 **/
@Controller
@RequestMapping("/files")
public class CheckDataController {
    @Autowired
    private CheckDataService checkdataservice;

    @RequestMapping("/checkData")
    public String checkData(String tableName) throws IOException {
        return checkdataservice.checkData(tableName);
    }

    @RequestMapping("/checkYear")
    public String checkYear(String year) throws IOException {
        return checkdataservice.checkYear(year);
    }

    @RequestMapping("/getData")
    public String getData(String tableName){
        return checkdataservice.getData(tableName);
    }
}
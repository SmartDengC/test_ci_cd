package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @RequestMapping("/checkYear")
    public String checkYear(String year,int province) throws IOException {
        System.out.print("helloword");
        return checkdataservice.checkYear(year,province);
    }

    @RequestMapping("/checkData")
    public String checkData(String year) throws IOException {
        return checkdataservice.checkData(year);
    }

    @RequestMapping("/getData")
    public String getData(String year,String ems) throws JsonProcessingException {
        return checkdataservice.getData(year,ems);
    }
    @RequestMapping("test")
    public String testProvince(String province){
        System.out.print(province);
        return null;
    }
}
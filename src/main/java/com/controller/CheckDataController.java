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
@RequestMapping("/basicConfig")
public class CheckDataController {
    @Autowired
    private CheckDataService checkdataservice;

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     * 检查该年份的T_TDD 表中是否有数据
     * @Author 吕志伟
     * @param year 年份
     * @param province 省份
     * @return 0：说明表中没有符合参数的数据
     * 1：说明表中有符合参数的数据
     */
    @RequestMapping("/checkYear")
    public String checkYear(String year,String  province) throws IOException {
        int pro = Integer.parseInt(province);
        String returnStatus = null;
        try{
            returnStatus = checkdataservice.checkYear(year,pro);
        }
        catch (Exception e){
            System.out.print(e);
        }

        return returnStatus;
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     * 检查三张表中是否有数据
     * @Author 吕志伟
     * @param year 年份
     * @return 0：该表中无数据
     * 1：该表中有数据
     */
    @RequestMapping("/checkData")
    public String checkData(String year) throws IOException {
        String status = checkdataservice.checkData(year);
        return status;
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     * 获取三张表中的所有数据
     * @Author 吕志伟
     * @param year 年份
     * @param fileType 文件类型
     * @return  三张表中的数据
     */
    @RequestMapping("/seeConfigMessage")
    public String getData(String year,String fileType) throws JsonProcessingException {
        String status = checkdataservice.getData(year,fileType);
        return status;
    }
}
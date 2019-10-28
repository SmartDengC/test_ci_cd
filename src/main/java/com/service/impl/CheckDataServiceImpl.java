package com.service.impl;

import com.dao.CheckDataDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.CheckDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;


/**
 * @program: ssm
 *
 * @description: 根据相关表检查数据的service层接口的实现类
 *
 * @author: 吕志伟
 *
 * @create: 2019-10-20 20:30
 **/
@Service
public class CheckDataServiceImpl implements CheckDataService {
    @Autowired
    private CheckDataDao checkDataDao;

    @Override
    public String checkData(String tableName) throws IOException {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        map.put("status",checkDataDao.checkSRT_U(tableName));
        result =  mapper.writeValueAsString(map);
        return  result;
    }

    @Override
    public String checkYear(String year) throws IOException {
        String result ="";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map  = new HashMap();
        map.put("SRT_U",checkDataDao.checkSRT_U(year));
        map.put("SRT_ASS",checkDataDao.checkSRT_ASS(year));
        map.put("CN",checkDataDao.checkCN(year));
        result = mapper.writeValueAsString(map);
        return  result;
    }

    @Override
    public String getData(String tableName) {
        if ("SRT_U".equals(tableName)) {
            return checkDataDao.getSRT_U();
        } else if ("SRT_ASS".equals(tableName)) {
            return checkDataDao.getSRT_ASS();
        } else {
            return checkDataDao.getCN();
        }
    }
}

package com.service.impl;

import com.dao.CheckDataDao;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public String checkYear(String year, int province) throws IOException {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        map.put("status", checkDataDao.selectT_TDD(year, province));
        result = mapper.writeValueAsString(map);
        return result;
    }

    @Override
    public String checkData(String year) throws IOException {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        map.put("SRT_U", checkDataDao.selectTD_PTFSX(year));
        map.put("SRT_ASS", checkDataDao.selectTD_YTFSX(year));
        map.put("CN", checkDataDao.selectKDD(year));
        result = mapper.writeValueAsString(map);
        return result;
    }

    @Override
    public String getData(String year, String ems) throws JsonProcessingException {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        map.put("SRT_U", checkDataDao.requestTD_PTFSX(year));
        map.put("SRT_ASS", checkDataDao.requestTD_YTFSX(year));
        map.put("CN", checkDataDao.requestKDD(ems));
        result = mapper.writeValueAsString(map);
        return result;
    }

}

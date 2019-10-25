package com.service.impl;

import com.dao.CheckDataDao;
import com.service.CheckDataService;


import com.utils.MakeJson;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


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
    public String checkdata(String tablename) throws IOException {
        MakeJson makeJson = new MakeJson();
        makeJson.writeJSON(checkDataDao.checkSRT_U(tablename));
        return  makeJson.readJSON();
    }

    @Override
    public String checkyear(String year) throws IOException {
        MakeJson makeJson = new MakeJson();
        makeJson.writeJSON(checkDataDao.checkSRT_U(year));
        makeJson.writeJSON(checkDataDao.checkSRT_ASS(year));
        makeJson.writeJSON(checkDataDao.checkCN(year));
        return  makeJson.readJSON();
    }

    @Override
    public String getdata(String tablename) {
        if ("SRT_U".equals(tablename)) {
            return checkDataDao.getSRT_U();
        } else if ("SRT_ASS".equals(tablename)) {
            return checkDataDao.getSRT_ASS();
        } else {
            return checkDataDao.getCN();
        }
    }
}

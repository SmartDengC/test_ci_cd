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

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     * 根据参数year来检查T_TDD表中是否有该年份的数据
     * @Author 吕志伟
     * @param year 年份
     * @param province 省份
     * @return json：0//该年份没有导入过数据  1//该年份已经导入过数据
     */
    @Override
    public String checkYear(String year, int province) throws IOException {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        int count =  checkDataDao.selectT_TDD(year,province);
        if(count == 0){
            map.put("status",0);
        }else {
            map.put("status", 1);
        }
        result = mapper.writeValueAsString(map);
        return result;
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *检查三张表中是否已经导入数据
     * @Author 吕志伟
     * @param year 年份
     * @return json
     */
    @Override
    public String checkData(String year) throws IOException {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
            int count = checkDataDao.selectTD_PTFSX(year);
            if(count == 0){
                map.put("SRT_U", 0);
            }
            else {
                map.put("SRT_U", 1);
            }

        int count1 = checkDataDao.selectTD_YTFSX(year);
        if(count1 == 0){
            map.put("SRT_ASS", 0);
        }
        else {
            map.put("SRT_ASS", 1);
        }

        int count2 = checkDataDao.selectKDD(year);
        if(count2 == 0){
            map.put("CN", 0);
        }
        else {
            map.put("CN", 1);
        }
        result = mapper.writeValueAsString(map);
        return result;
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/11/4
     *
     * 查看某一张表的所有数据
     * @Author 吕志伟
     * @param: year 年份
     * @param: fileType 文件类型
     * @return json，三张表中的数据
     */
    @Override
    public String getData(String year, String fileType) throws JsonProcessingException {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        String srt_u = "SRT_U";
        String srt_ass = "SRT_ASS";
        String cn = "CN";


        if(srt_u.equals(fileType)) {
            map.put("SRT_U", checkDataDao.requestTD_PTFSX(year));
        }
        else if(srt_ass.equals(fileType)) {
            map.put("SRT_ASS", checkDataDao.requestTD_YTFSX(year));
        }
        else if(cn.equals(fileType)){
            map.put("CN", checkDataDao.requestKDD(year));
        }
        else
        {
            map.put("status","传入表名错误");
        }
        result = mapper.writeValueAsString(map);
        return result;
    }

}

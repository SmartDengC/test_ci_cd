package com.service.impl;

import com.dao.DataShowDao;
import com.service.DataShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DataShowServiceImpl
 * @Version 1.0
 * @Author 马雪冬
 * @Date 2019/12/6 19:48
 * @Description TODO
 * Modification User:
 * Modification Date:
 */
@Service
public class DataShowServiceImpl implements DataShowService {
    @Autowired
    private DataShowDao dataShowDao;


    /**
     * Modification User:马雪冬
     * Modification Date:2019/12/6
     *从t_tdd表中获取所有学生人数
     *
     * @author 马雪冬
     * @return Map:key=total,value=所有学生人数
     */
    @Override
    public Map<String, Object> getStudentsTotal() {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",null);
        dataShowDao.getStudentsTotal(map);
        return map;
    }

    /**
     * Modification User:马雪冬
     * Modification Date:2019/12/6
     *获取所有男女比列
     *
     * @author 马雪冬
     * @return Map:key=total,value=所有男女比列
     */
    @Override
    public Map<String, Object> getStudentRatio() {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("boy","0");
        map.put("girl","0");
        dataShowDao.getStudentRatio(map);
        return map;
    }

    /**
     * Modification User:马雪冬
     * Modification Date:2019/12/6
     *获取所有每个省份下的学生人数
     *
     * @author 马雪冬
     * @return Map:key=total,value=每个省份下的学生人数
     */
    @Override
    public List<Map<String,Integer>> getStudentsByProvince() {
        List<Map<String,Integer>> list=null;
        list=dataShowDao.getStudentsByProvince();
        return list;
    }
}

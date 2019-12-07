package com.dao;

import java.util.List;
import java.util.Map;

public interface DataShowDao {
    /**
     * 从数据库中获取所有学生人数
     * @Author 马雪冬
     * @param m 将结果保存在传入的Map中
     */
    public void getStudentsTotal(Map<String,Object> m);
    /**
     * 从数据库中获取所有男女比例
     * @Author 马雪冬
     * @param m 将结果保存在传入的Map中
     */
    public void getStudentRatio(Map<String,Object> m);

    /**
     * 从数据库中获取每个省份下的学生人数
     *@Author 马雪冬
     * @return 每个省份是一个Map,所有省份就是List<Map>
     */
    public List<Map<String,Integer>> getStudentsByProvince();

}

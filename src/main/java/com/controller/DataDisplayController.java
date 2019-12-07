package com.controller;

import com.service.DataShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 数据展示Controller
 * @Version 1.0
 * @Author dell
 * @Date 2019/12/6 19:45
 * @Description TODO
 * Modification User:马雪冬
 * Modification Date: 2019/12/6
 */
@Controller
@CrossOrigin
@RequestMapping("/dataDisplay")
public class DataDisplayController {
    @Autowired
    private DataShowService dataShowService;


    /**
    * Modification User:马雪冬
    * Modification Date:2019/12/6
    *从t_tdd表中获取所有学生人数
    *
    * @author 马雪冬
    * @return json:所有学生人数
    */

    @RequestMapping("/allStudentDisplay")
    @ResponseBody
    public String getAllStudent() {
        String result="";
        Map<String, Object> totalMap=null;
        int total=0;
        totalMap=dataShowService.getStudentsTotal();

        total= (int) totalMap.get("total");
        System.out.println("***1total:"+totalMap);
        if(totalMap!=null) {
            result = "{\"status\":\"1\""+",\"total\":" + total +"}";
        }else {
            result="{\"status\":\"0\"}";
        }
        return result;
    }

    /**
     * Modification User:马雪冬
     * Modification Date:2019/12/6
     *获取所有男女比列
     *
     * @author 马雪冬
     * @return json:所有男女比列
     */
    @RequestMapping("/studentRatioDisplay")
    @ResponseBody
    public String getStudentRatio() {
        String result="";
        int boy=0,girl=0;
        Map<String, Object> ratioMap=null;
        List<Map<String,Integer>> listMap=null;

        ratioMap=dataShowService.getStudentRatio();
        listMap=dataShowService.getStudentsByProvince();

        boy= (int) ratioMap.get("boy");
        girl= (int) ratioMap.get("girl");

        System.out.println("***1ratioMap:"+ratioMap);
        System.out.println("***1listMap:"+listMap);
        if(ratioMap!=null) {
            result = "{\"status\":\"1\""+ ",\"ratio\":" + "{\"boy\":" + boy + ",\"girl\":" + girl + "}"+"}";
        }else {
            result="{\"status\":\"0\"}";
        }
        return result;
    }

    /**
     * Modification User:马雪冬
     * Modification Date:2019/12/6
     *获取所有每个省份下的学生人数
     *
     * @author 马雪冬
     * @return json:每个省份下的学生人数
     */
    @RequestMapping("/studentByProvinceDisplay")
    @ResponseBody
    public String getStudentByProvince() {
        String result="";
        List<Map<String,Integer>> listMap=null;

        listMap=dataShowService.getStudentsByProvince();
        System.out.println("***1listMap:"+listMap);
        if(listMap!=null) {
            result = "{\"status\":\"1\""+ ",\"listMap\":" + listMap + "}";
        }else {
            result="{\"status\":\"0\"}";
        }
        return result;
    }

}
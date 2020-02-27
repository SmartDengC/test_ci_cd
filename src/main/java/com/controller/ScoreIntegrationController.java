package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.ScoreIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ScoreIntegrationController
 * @Version 1.0
 * @Author dell
 * @Date 2019/12/2 20:59
 * @Description TODO
 * Modification User:
 * Modification Date:
 */
@Controller
@CrossOrigin
@RequestMapping("/scoreIntegration")
public class ScoreIntegrationController {

    @Autowired
    private ScoreIntegrationService integrationService;

    //最大年份2059
    private static int maxYear=2059;
    //最小年份2002
    private static int minYear=2002;


    /**
     * TDCJ=CJ/ WHX_ZS* WHX_BL + ZYCJ/ ZYX_ZS* ZYX_BL
     * @param map
     * @return
     */
    @RequestMapping("/scoreIntegration")
    @ResponseBody
    public String ScoreIntegration(@RequestBody Map<String,Object> map) throws JsonProcessingException {
        String result="";
        List<Map<String,Object>> students=new ArrayList<>();
        int count=0;
//        String year, int provinceCode
        if(map!=null) {
            String year= (String) map.get("year");
            int provinceCode= (int) map.get("provinceCode");

            if (Integer.parseInt(year) >= minYear && Integer.parseInt(year) <= maxYear && provinceCode >= 0) {
                count = integrationService.studentIsExist(year, provinceCode);
                System.out.println("****1count:"+count);
                if (count > 0) {
                    //从td_xsfs中获取 每个student就是一个map map中有 编号ID 文化成绩CJ 专业成绩ZYCJ 投档成绩TDCJ 折算成绩ZSCJ
                    students = integrationService.getStudents(year, provinceCode);
                    System.out.println("***1students:"+students);
                    //整合students 返回count1 0失败 1成功
                    int count1 = integrationService.integrationStudents(students, year, provinceCode);
                    System.out.println("***1count1:"+count1);
                    if (count1 == 1) {
                        //获取最高分，最低分，平均分,以及结果
                        result = integrationService.getThreeScore(year,provinceCode);
                    } else {
                        result = "{\"status\":\"0\"}";
                    }

                } else {
                    result = "{\"status\":\"0\"}";
                }

            } else {
                result = "{\"status\":\"0\"}";
            }
        }else {
            result = "{\"status\":\"0\"}";
        }

        return result;
    }
}

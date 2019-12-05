package com.service.impl;

import com.dao.ScoreIntegrationDao;
import com.service.ScoreIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ScoreIntegrationServiceImpl
 * @Version 1.0
 * @Author dell
 * @Date 2019/12/2 21:05
 * @Description TODO
 * Modification User:
 * Modification Date:
 */
@Service
public class ScoreIntegrationServiceImpl implements ScoreIntegrationService {
    @Autowired
    private ScoreIntegrationDao scoreIntegrationDao;


    @Override
    public int studentIsExist(String year, int provinceCode) {
        return scoreIntegrationDao.studentIsExist(year, provinceCode);
    }

    @Override
    public List<Map<String, Object>> getStudents(String year, int provinceCode) {
        List<Map<String, Object>> students=scoreIntegrationDao.getStudents(year, provinceCode);
        return students;
    }

    @Override
    public int integrationStudents(List<Map<String, Object>> students, String year, int provinceCode) {
        List<Map<String, Object>> newStudents=new ArrayList<>();
        //整合结果状态 学生被整合的数量
        int result=0;
        int size=students.size();
        System.out.println("***2size:"+size);
        //保存获取的 WHX_ZS WHX_BL ZYX_ZS ZYX_BL
        Map<String, Float> map;
        //用于艺体生 计算ZYCJ=(TDCJ-CJ*WHX_BJ/WHS_ZS)*ZYX_ZS
//        float WHX_ZS,WHX_BL, ZYX_ZS, ZYX_BL;

        for(Map<String,Object> student :students){
            System.out.println("***2student:"+student);
            //每个student就是一个map map中有 编号ID 文化成绩CJ 专业成绩ZYCJ 投档成绩TDCJ 折算成绩ZSCJ
            int ID= (int) student.get("ID");
            int CJ= (int) student.get("CJ");
            int ZYCJ= (int) student.get("ZYCJ");
            int TDCJ= (int) student.get("TDCJ");
            int ZSCJ= (int) student.get("ZSCJ");
            //普通学生 折算成绩就等于CJ ZYCJ原本就等于0 默认所有省份的满分制度都是750不用换算 所以ZSCJ就等于CJ
            if(TDCJ==CJ){
                ZSCJ=CJ;
                student.put("ZSCJ",ZSCJ);
            }else if(TDCJ>CJ){
                ZSCJ=CJ;
                //艺体生  TDCJ=CJ/ WHX_ZS* WHX_BL + ZYCJ/ ZYX_ZS* ZYX_BL 需要WHX_ZS WHX_BL ZYX_ZS ZYX_BL
                map=scoreIntegrationDao.getFourData(year,provinceCode,ID);
                System.out.println("***2map:"+map);
                //计算    ZYCJ=((TDCJ-CJ*WHX_BJ/WHS_ZS)*ZYX_ZS)/ZYX_BL
                ZYCJ= (int) (((TDCJ-CJ*map.get("WHX_BL")/map.get("WHX_ZS"))*map.get("ZYX_ZS"))/map.get("ZYX_BL"));
                System.out.println("***2ZYCJ:"+ZYCJ);

                student.put("ZYCJ",ZYCJ);
                student.put("ZSCJ",ZSCJ);
            }

            //将计算后的student装入newStudent
            newStudents.add(student);
        }
        System.out.println("***2newStudents:"+newStudents);
        //整合完重新写入数据库
        result=scoreIntegrationDao.setStudents(newStudents);
        System.out.println("***2result:"+result);
        //当全部学生整合时
        if (result==size){
            return 1;
        }
        return 0;
    }

    @Override
    public String getThreeScore(String year, int provinceCode){
        String result="";
        String maxScore,minScore,avgScore;

        Map<String, Object> threeScore = scoreIntegrationDao.getThreeScore(year, provinceCode);
        System.out.println("***2threeScore:"+threeScore);
        if(threeScore!=null){
            maxScore= threeScore.get("maxScore").toString();
            minScore= threeScore.get("minScore").toString();
            avgScore= threeScore.get("avgScore").toString();
            result="{\"status\":\"1\""+","
                    +"\"maxScore\":"+maxScore+","
                    +"\"minScore\":"+minScore+","
                    +"\"avgScore\":"+avgScore.substring(0,avgScore.indexOf('.'))+"}";
        }else {
            result="{\"status\":\"0\"}";
        }
        return result;
    }
}

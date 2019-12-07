package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.DataStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
* @ClassName ${NAME}
* @Version 1.0
* @Author 马雪冬
* @Date 2019/11/23
* @Description 处理数据清洗页面请求的Controller
* Modification User: 马雪冬
* Modification Date: 2019/12/1
*/
@Controller
@CrossOrigin
@RequestMapping("/importFormatField")
public class DataStandardController {
    @Autowired
    private DataStandardService dataStandardService;
    //最大年份2059
    private static int maxYear=2059;
    //最小年份2002
    private static int minYear=2002;
    /**
    * Modification User: 马雪冬
    * Modification Date: 2019/11/25
    *
    * @author 马雪冬
     * @param map
    * @return 返回一个json给前台  json中包含四个对象 每个对象都是一个json数组 1.status 请求状态 2.规范值与代码 3.不规范值与代码 4.规范与不规范代码关系
    */

    @RequestMapping(value = "/fetchUnFormatData",method = RequestMethod.POST)
    @ResponseBody
    public String fetchUnFormatData(@RequestBody Map<String,Object> map) throws JsonProcessingException {
//        ,String year,int provinceCode,String field
        String result="";
        ObjectMapper mapper=new ObjectMapper();
        List<Map<String,Object>> fomatList=null;
        List<Map<String,Object>> unFomatList=null;
        List<Map<String,Object>> relationList=null;
        if(map!=null) {
            String year = (String) map.get("year");
            int provinceCode = (int) map.get("provinceCode");
            String field = (String) map.get("field");


            if (Integer.parseInt(year) >= minYear && Integer.parseInt(year) <= maxYear && (field.equals("PCMC") || field.equals("KLMC")) && provinceCode >= 0) {
                //获取规范
                fomatList = dataStandardService.fetchFormatData(field);
                //获取不规范
                unFomatList = dataStandardService.fetchUnFormatData(year, provinceCode, field);
                //获取对应关系
                relationList = dataStandardService.fetchRelationship(year, provinceCode, field);
            }
        }

        //当前年对应关系可能没有 不做硬性要求
        if(unFomatList!=null||fomatList!=null||relationList!=null){
            String formatStr=mapper.writeValueAsString(fomatList);
            String unFormatStr=mapper.writeValueAsString(unFomatList);
            String relationStr=mapper.writeValueAsString(relationList);
            result="{\"status\":\"1\""+","+"\"unFormat\":"+unFormatStr+","+"\"format\":"+formatStr+","+"\"relation\":"+relationStr+"}";
            return result;
        }

        return "{\"status\":\"0\"}";
    }

    /**
     * Modification User: 马雪冬
     * Modification Date: 2019/11/25
     *
     * 在数据清洗页面中 右侧规范值栏中 点击进行规范值修改时,请求后台对指定的规范值进行修改
     * @param map
     * @return 返回一个json给前台  value为0 为请求失败 value为1 为请求成功
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/modifyFormatField",method = RequestMethod.POST)
    @ResponseBody
    public String modifyFormatField(@RequestBody Map<String,Object> map) throws JsonProcessingException {
        String result="";
//        int formatFieldCode, String newFormatFieldData
        if(map!=null) {
            int formatFieldCode= (int) map.get("formatFieldCode");
            String newFormatFieldData= (String) map.get("newFormatFieldData");

            if (formatFieldCode >= 0 && newFormatFieldData != null) {
                result = dataStandardService.modifyFormatField(formatFieldCode, newFormatFieldData);
                return result;
            }
        }
        return "{\"status\":\"0\"}";
    }

    /**
     * Modification User: 马雪冬
     * Modification Date: 2019/11/25
     *
     * 在数据清洗页面中 最右侧添加字段规范值时 请求后台在字段规范值表中添加新的规范值
     * @param map
     * @return 返回一个json给前台  value为0 为请求失败 value为1 为请求成功
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/writeFormatField",method = RequestMethod.POST)
    @ResponseBody
    public String writeFormatField(@RequestBody Map<String,Object> map) throws JsonProcessingException {
        String result="";
//        String field, String newFormatFieldData
        if(map!=null) {
            String field= (String) map.get("field");
            String newFormatFieldData= (String) map.get("newFormatFieldData");

            if ((field.equals("PCMC") || field.equals("KLMC")) && newFormatFieldData != null) {
                result = dataStandardService.writeFormatField(field, newFormatFieldData);
                return result;
            }
        }
        return "{\"status\":\"0\"}";
    }

    /**
     * Modification User:马雪冬
     * Modification Date:2019/11/25
     *
     * 在数据清洗页面的锁定面板取消关联时，请求后台取消映射表中对应的关联
     * @param map
     * @return 返回一个json给前台  value为0 为请求失败 value为1 为请求成功
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/deleteFieldRelation",method = RequestMethod.POST)
    @ResponseBody
    public String deleteFieldRelation(@RequestBody Map<String,Object> map) throws JsonProcessingException {
        String result="";
//        String year,int provinceCode,String field, String unFormatFieldCode,int formatFieldCode
        if(map!=null){
            String year= (String) map.get("year"); //指定年份
            int provinceCode= (int) map.get("provinceCode"); //指定省份代码
            String field= (String) map.get("field"); //指定字段
            String unFormatFieldCode= (String) map.get("unFormatFieldCode"); //不规范值代码
            int formatFieldCode= (int) map.get("formatFieldCode"); //规范值代码

        if(Integer.parseInt(year)>=minYear&&Integer.parseInt(year)<=maxYear&&(field.equals("PCMC")||field.equals("KLMC"))&&unFormatFieldCode!=null&&formatFieldCode>=0){
            result=dataStandardService.deleteFieldRelation(year, provinceCode, field, unFormatFieldCode, formatFieldCode);
            return result;
        }
        }
        return "{\"status\":\"0\"}";
    }
    /**
     * Modification User: 马雪冬
     * Modification Date: 2019/12/1
     *
     * 在数据清洗页面中 当所有不规范值都建立对应关系式 点击确定后请求后台在映射表中建立所有的对应关系
     * @param map Map中有指定年份、省份、字段和(字段规范值代码、字段不规范值代码的json对象数组)
     * @return 返回一个json给前台  value为0 为请求失败 value为1 为请求成功
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/biuldRelationship",method = RequestMethod.POST)
    @ResponseBody
    public String biuldRelationship(@RequestBody Map<String,Object> map) throws JsonProcessingException {
        String result="";
        if(map!=null){
            String year= (String) map.get("year");
            int provinceCode= (int) map.get("provinceCode");
            String field= (String) map.get("field");
            List<Map<String,Object>> mapList= (List<java.util.Map<String, Object>>) map.get("relation");

            result=dataStandardService.biuldRelationship(year,provinceCode,field,mapList);
            return result;
        }
        return "{\"status\":\"0\"}";
    }
}

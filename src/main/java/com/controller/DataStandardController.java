package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.DataStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
* @ClassName ${NAME}
* @Version 1.0
* @Author 马雪冬
* @Date 2019/11/23
* @Description 处理数据清洗页面请求的Controller
* Modification User: 马雪冬
* Modification Date: 2019/11/25
*/
@Controller
@CrossOrigin
@RequestMapping("/importFormatField")
public class DataStandardController {
    @Autowired
    private DataStandardService dataStandardService;


    /**
    * Modification User: 马雪冬
    * Modification Date: 2019/11/25
    *
    *
    * @author 马雪冬
    * @param year 指定年份
    * @param provinceCode 指定省份代码
    * @param field 指定字段名称
    * @return 返回一个json给前台  json中包含四个对象 每个对象都是一个json数组 1.status 请求状态 2.规范值与代码 3.不规范值与代码 4.规范与不规范代码关系
    */

    @RequestMapping("/fetchUnFormatData")
    @ResponseBody
    public String fetchUnFormatData(String year,int provinceCode,String field) throws JsonProcessingException {
        String result="";
        ObjectMapper mapper=new ObjectMapper();

        List<Map<String,Object>> fomatList=null;
        List<Map<String,Object>> unFomatList=null;
        List<Map<String,Object>> relationList=null;

        if(year!=null&&field!=null&&provinceCode>=2002&&provinceCode<=2059){
            //获取规范
            fomatList=dataStandardService.fetchFormatData(field);
            //获取不规范
            unFomatList=dataStandardService.fetchUnFormatData(year, provinceCode, field);
            //获取对应关系
            relationList=dataStandardService.fetchRelationship(year, provinceCode, field);
        }

        //当前年对应关系可能没有 不做硬性要求
        if(unFomatList!=null||fomatList!=null||relationList!=null){
            String formatStr=mapper.writeValueAsString(fomatList);
            String unFormatStr=mapper.writeValueAsString(unFomatList);
            String relationStr=mapper.writeValueAsString(relationList);

            result="{\"status\":+\"1\""+","+"\"unFormat\":"+unFormatStr+","+"\"format\":"+formatStr+","+"\"relation\":"+relationStr+"}";
        }else {
            result="{\"status\":\"0\"}";
        }
        return result;
    }

    /**
     * Modification User: 马雪冬
     * Modification Date: 2019/11/25
     *
     * 在数据清洗页面中 右侧规范值栏中 点击进行规范值修改时,请求后台对指定的规范值进行修改
     * @param formatFieldCode 指定的字段代码
     * @param newFormatFieldData 新的字段规范值
     * @return 返回一个json给前台  value为0 为请求失败 value为1 为请求成功
     * @throws JsonProcessingException
     */
    @RequestMapping("/modifyFormatField")
    @ResponseBody
    public String modifyFormField(String formatFieldCode, String newFormatFieldData) throws JsonProcessingException {
        String result="";
        if(formatFieldCode!=null&&newFormatFieldData!=null){
            result=dataStandardService.modifyFormField(formatFieldCode, newFormatFieldData);
            return result;
        }
        return "{\"status\":\"0\"}";
    }

    /**
     * Modification User: 马雪冬
     * Modification Date: 2019/11/25
     *
     * 在数据清洗页面中 最右侧添加字段规范值时 请求后台在字段规范值表中添加新的规范值
     * @param field 指定字段名称
     * @param newFormatFieldData 新的字段规范值
     * @return 返回一个json给前台  value为0 为请求失败 value为1 为请求成功
     * @throws JsonProcessingException
     */
    @RequestMapping("/writeFormatField")
    @ResponseBody
    public String writeFormField(String field, String newFormatFieldData) throws JsonProcessingException {
        String result="";
        if(field!=null&&newFormatFieldData!=null){
            result=dataStandardService.writeFormField(field, newFormatFieldData);
            return result;
        }
        return "{\"status\":\"0\"}";
    }

    /**
     * Modification User:马雪冬
     * Modification Date:2019/11/25
     *
     * 在数据清洗页面的锁定面板取消关联时，请求后台取消映射表中对应的关联
     * @param year 指定年份
     * @param provinceCode 指定省份代码
     * @param field 指定字段
     * @param unFormatFieldCode 不规范值代码
     * @param formatFieldCode 规范值代码
     * @return 返回一个json给前台  value为0 为请求失败 value为1 为请求成功
     * @throws JsonProcessingException
     */
    @RequestMapping("/deleteFieldRelation")
    @ResponseBody
    public String deleteFieldRelation(String year,int provinceCode,String field, String unFormatFieldCode,String formatFieldCode) throws JsonProcessingException {
        String result="";
        if(year!=null&&field!=null&&unFormatFieldCode!=null&&formatFieldCode!=null){
            result=dataStandardService.deleteFieldRelation(year, provinceCode, field, unFormatFieldCode, formatFieldCode);
            return result;
        }
        return "{\"status\":\"0\"}";
    }
    /**
     * Modification User: 马雪冬
     * Modification Date: 2019/11/25
     *
     * 在数据清洗页面中 当所有不规范值都建立对应关系式 点击确定后请求后台在映射表中建立所有的对应关系
     * @param list 指定年份、省份、字段、字段规范值代码、字段不规范值代码的json对象数组
     * @return 返回一个json给前台  value为0 为请求失败 value为1 为请求成功
     * @throws JsonProcessingException
     */
    @RequestMapping("/biuldRelationship")
    @ResponseBody
    public String biuldRelationship(@RequestBody List<Map<String,Object>> list) throws JsonProcessingException {
        String result="";
        if(list!=null){
            result=dataStandardService.biuldRelationship(list);
            return result;
        }
        return "{\"status\":\"0\"}";
    }
}

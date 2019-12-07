package com.service.impl;

import com.dao.DataStandardDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.DataStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @ClassName DataStandardServiceImpl
* @Version 1.0
* @Author 马雪冬
* @Date 2019/11/22
* @Description TODO
* Modification User: 马雪冬
* Modification Date: 2019/12/1
*/

@Service
public class DataStandardServiceImpl implements DataStandardService {
    @Autowired
    private DataStandardDao dataStandardDao;

    /**
    * Modification User:
    * Modification Date:
    *获取指定字段的所有规范值与代码
    *
    * @author 马雪冬
    * @param field 指定字段名称
    * @return 返回指定字段的规范值与代码的Map集合 List存储所有Map
    */
    @Override
    public List<Map<String, Object>> fetchFormatData(String field) {
        List<Map<String,Object>> list=null;
        list=dataStandardDao.fecthFormatData(field);
        return list;
    }

    /**
     * Modification User:
     * Modification Date:
     *获取指定字段，指定年份，指定省份的所有不规范规范值与代码
     *
     * @author 马雪冬
     * @param year 指定年份
     * @param provinceCode 指定省份代码
     * @param field 指定字段名称
     * @return 返回指定字段的规范值与代码的Map集合 List存储所有Map
     */
    @Override
    public List<Map<String, Object>> fetchUnFormatData(String year, int provinceCode, String field) {
        List<Map<String,Object>> list=null;
        if(field.equals("PCMC")){
            list=dataStandardDao.fecthUnFormatDataPC(year, provinceCode);
        }else if(field.equals("KLMC")){
            list=dataStandardDao.fecthUnFormatDataKL(year, provinceCode);
        }
        return list;
    }

    /**
     * Modification User: 马雪冬
     * Modification Date: 2019/11/25
     *获取指定字段，指定年份，指定省份的所有规范值代码与不规范值代码的关系
     *
     * @author 马雪冬
     * @param year 指定年份
     * @param provinceCode 指定省份代码
     * @param field 指定字段名称
     * @return 返回指定字段的规范值与代码的Map集合 List存储所有Map
     */
    @Override
    public List<Map<String, Object>> fetchRelationship(String year, int provinceCode, String field) {
        List<Map<String,Object>> list=null;
        list=dataStandardDao.fecthRelation(year, provinceCode, field);
        return list;
    }

    /**
     * Modification User: 马雪冬
     * Modification Date: 2019/11/25
     * 修改指定规范值代码下的规范值
     *
     * @author 马雪冬
     * @param formatFieldCode 指定规范值代码
     * @param newFormatFieldData 指定不规范值代码
     * @return 返回一个json给前台  value为0 为请求失败 value为1 为请求成功
     */
    @Override
    public String modifyFormatField(int formatFieldCode, String newFormatFieldData) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        HashMap map=new HashMap<>();
        String status="";

        //判断对应字段代码下的 规范值是否已经存在 0不存在 1存在（新值是否与旧值重复）
        int isExist=dataStandardDao.formatDataIsExistByCode(formatFieldCode, newFormatFieldData);
        System.out.println("***isExist:"+isExist);
        //0默认失败
        int returnValue=0;
        if(isExist==0){
            returnValue=dataStandardDao.modifyFormatData(formatFieldCode, newFormatFieldData);
        }
        if(returnValue==1){
            map.put("status","1");
        }else {
            map.put("status","0");
        }
        status=mapper.writeValueAsString(map);
        return status;
    }


    /**
     * Modification User: 马雪冬
     * Modification Date: 2019/11/25
     * 添加一个指定字段的新的规范值
     *
     * @author 马雪冬
     * @param field 指定字段名称
     * @param newFormatFieldData 指定不规范值代码
     * @return 返回一个json给前台  value为0 为请求失败 value为1 为请求成功
     */
    @Override
    public String writeFormatField(String field, String newFormatFieldData) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        HashMap map=new HashMap<>();
        String status="";
        //判断对应字段名称下的 规范值是否已经存在 0不存在 1存在(该新值是否已经存在)
        int isExist=dataStandardDao.formatDataIsExistByField(field, newFormatFieldData);
        System.out.println("***isExist:"+isExist);
        //0默认失败
        int returnValue=0;
        if(isExist==0){
            returnValue=dataStandardDao.insertFormatData(field, newFormatFieldData);
        }
        if(returnValue==1){
            map.put("status","1");
        }else {
            map.put("status","0");
        }

        status=mapper.writeValueAsString(map);
        return status;
    }

    /**
     * Modification User: 马雪冬
     * Modification Date: 2019/12/1
     *将前端传来的所有规范与不规范对应关系 在映射表中 进行建立关联
     *
     * @author 马雪冬
     * @param year 指定年份
     * @param provinceCode 指定省份代码
     * @param field 指定字段名称
     * @param mapList 指定的规范代码与不规范代码的对象数组
     * @return 返回一个json对象 value为0-->失败 1-->成功
     */

    @Override
    public String biuldRelationship(String year,int provinceCode,String field,List<Map<String,Object>> mapList) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        HashMap map=new HashMap<>();
        String status="";

        List<Map<String ,Object>> newList=new ArrayList<>();
        List<Map<String ,Object>> updateList=new ArrayList<>();
        for(Map<String,Object> item:mapList){
            //如果是新的
            System.out.println("*****mappingID:"+item.get("mappingID"));
            if(item.get("mappingID").equals("")||item.get("mappingID").equals("-1")) {
                Map<String, Object> a = new HashMap<String, Object>();
                a.put("year", year);
                a.put("provinceCode", provinceCode);
                a.put("field", field);
                a.put("unFormatFieldCode", item.get("unFormatFieldCode"));
                //formatFieldCode在zdysdm表中是外键
                a.put("formatFieldCode", item.get("formatFieldCode"));
                newList.add(a);
            }else {
                //不是新的
                Map<String, Object> b = new HashMap<String, Object>();
                b.put("mappingID",item.get("mappingID"));
                b.put("formatFieldCode", item.get("formatFieldCode"));
                updateList.add(b);
            }
        }
        //新的
        int returnValue1=0;
        if(newList.size()>0) {
            returnValue1 = dataStandardDao.biuldNewRelationship(newList);
        }
        System.out.println("******updateList:"+updateList);
        //存在的
        int returnValue2=0;
        if(updateList.size()>0) {
            returnValue2 = dataStandardDao.updateExistRelationship(updateList);
        }
        System.out.println("***SERVICE:returnValue1:"+returnValue1);
        System.out.println("***SERVICE:returnValue2:"+returnValue2);
        if(returnValue2>0||returnValue1>0){
            map.put("status","1");
        }else {
            map.put("status","0");
        }
        status=mapper.writeValueAsString(map);
        return status;
    }


    /**
     * Modification User:马雪冬
     * Modification Date:2019/11/25
     * 在映射表中取消指定的规范与不规范的关联
     *
     * @param year 指定年份
     * @param provinceCode 指定省份代码
     * @param field 指定字段
     * @param unFormatFieldCode 不规范值代码
     * @param formatFieldCode 规范值代码
     * @return 返回一个json对象 value为0-->失败 1-->成功
     * @throws JsonProcessingException
     */
    @Override
    public String deleteFieldRelation(String year, int provinceCode, String field, String unFormatFieldCode, int formatFieldCode) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        HashMap map=new HashMap<>();
        String status="";
        int returnValue=dataStandardDao.deleteFieldRelation(year, provinceCode, field, unFormatFieldCode, formatFieldCode);
        if(returnValue==1){
            map.put("status","1");
        }else {
            map.put("status","0");
        }
        status=mapper.writeValueAsString(map);
        return status;
    }

}

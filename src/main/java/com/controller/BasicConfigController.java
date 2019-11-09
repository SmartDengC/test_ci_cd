package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.BasicConfigservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * @program: ssm
 *
 * @description: 根据年份检查数据的springmvc层接口
 *
 * @author: 吕志伟
 *
 * @create: 2019-10-20 20:30
 **/
@Controller
@RequestMapping("/basicConfig")
public class BasicConfigController {
    @Autowired
    private BasicConfigservice basicConfigservice;

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     * 检查该年份的T_TDD 表中是否有数据
     * @Author 吕志伟
     * @param year 年份
     * @param province 省份
     * @return 0：说明表中没有符合参数的数据
     * 1：说明表中有符合参数的数据
     */
    @RequestMapping("/checkYear")
    public String checkYear(String year,String  province) throws IOException {
        int pro = Integer.parseInt(province);
        String returnStatus = null;
        try{
            returnStatus = basicConfigservice.checkYear(year,pro);
        }
        catch (Exception e){
            System.out.print(e);
        }

        return returnStatus;
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     * 检查三张表中是否有数据
     * @Author 吕志伟
     * @param year 年份
     * @return 0：该表中无数据
     * 1：该表中有数据
     */
    @RequestMapping("/checkData")
    public String checkData(String year) throws IOException {
        String status = basicConfigservice.checkData(year);
        return status;
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     * 获取三张表中的所有数据
     * @Author 吕志伟
     * @param year 年份
     * @param fileType 文件类型
     * @return  三张表中的数据
     */
    @RequestMapping("/seeConfigMessage")
    public String getData(String year,String fileType) throws JsonProcessingException {
        String status = basicConfigservice.getData(year,fileType);
        return status;
    }

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/10/31
     *
     * 上传文件
     * @author 邓聪
     * @param year 年份
     * @param file 文件对象
     * @param fileType 文件类型
     * @return 上传文件是否成功 1表示都成功,00表示其他失败情况，01表示文件上传失败，02表示数据库添加失败，03表示数据库删除失败
     * @throws Exception
     */

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(String year, MultipartFile file, String fileType) throws Exception {
        int beginYear = 2010;
        int endYear = 2019;
        String status = "";
        if(Integer.parseInt(year) >= beginYear && Integer.parseInt(year) <= endYear && !file.isEmpty() && fileType != null){
            status = basicConfigservice.uploadFile(year, file, fileType);
            return status;
        }
        else{
            return "{\"status\":\"0\"}";
        }
    }

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/10/31
     *
     * 重新上传文件
     * @author 邓聪
     * @param year 年份
     * @param file 文件对象
     * @param fileType 文件类型，普通分数线STR_U，艺体分数线STR_ASS，快递单CN
     * @return 重新上传文件是否成功 1表示都成功,00表示其他失败情况，01表示文件上传失败，02表示数据库添加失败，03表示数据库删除失败
     * @throws Exception
     */

    @RequestMapping("/reUploadFile")
    public @ResponseBody
    String reUploadFile(String year, MultipartFile file, String fileType) throws Exception {
        int beginYear = 2010;
        int endYear = 2019;
        String status = "";
        if (Integer.parseInt(year) >= beginYear && Integer.parseInt(year) <= endYear && !file.isEmpty() && fileType!=null) {
            status = basicConfigservice.reUploadFile(year, file, fileType);
            return status;
        }
        return "{\"status\":\"0\"}";
    }

}
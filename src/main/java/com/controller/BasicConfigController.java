package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.BasicConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * @ClassName basicConfigController
 * @Version 1.0
 * @Author 吕志伟
 * @Date 2019/10/20 16:36
 * @Description 根据年份检查数据的springmvc层口
 * Modification User： 邓聪
 * Modification Date： 2019/11/12
 */
@RestController
@CrossOrigin
@RequestMapping("/basicConfig")
public class BasicConfigController {
    @Autowired
    private BasicConfigService basicConfigservice;

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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
     * @return 上传文件是否成功 1表示都成功,0表示失败
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(String year, @RequestParam("file")MultipartFile file, String fileType) throws Exception {
        int beginYear = 2009;
        int endYear = 2029;
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
     * @return 重新上传文件是否成功 1表示都成功,0表示失败
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/reUploadFile")
    public String reUploadFile(String year, MultipartFile file, String fileType) throws Exception {
        int beginYear = 2009;
        int endYear = 2029;
        String status = "";
        if (Integer.parseInt(year) >= beginYear && Integer.parseInt(year) <= endYear && !file.isEmpty() && fileType!=null) {
            status = basicConfigservice.reUploadFile(year, file, fileType);
            return status;
        }
        return "{\"status\":\"0\"}";
    }

}
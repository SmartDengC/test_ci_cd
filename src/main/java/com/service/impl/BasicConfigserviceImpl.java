package com.service.impl;

import com.dao.BasicConfigDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.KDD;
import com.pojo.TD_PTFSX;
import com.pojo.TD_YTFSX;
import com.service.BasicConfigservice;

import com.utils.GetDefaultInfo;
import com.utils.ReadExcelUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @program: ssm
 *
 * @description: 根据相关表检查数据的service层接口的实现类
 *
 * @author: 吕志伟
 *
 * @create: 2019-10-20 20:30
 **/

@Service
public class BasicConfigserviceImpl implements BasicConfigservice {
    @Autowired
    private BasicConfigDao basicConfigDao;

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     * 根据参数year来检查T_TDD表中是否有该年份的数据
     * @Author 吕志伟
     * @param year 年份
     * @param province 省份
     * @return json：0//该年份没有导入过数据  1//该年份已经导入过数据
     */
    @Override
    public String checkYear(String year, int province) throws IOException {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        int count =  basicConfigDao.selectT_TDD(year,province);
        if(count == 0){
            map.put("status",0);
        }else {
            map.put("status", 1);
        }
        result = mapper.writeValueAsString(map);
        return result;
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *检查三张表中是否已经导入数据
     * @Author 吕志伟
     * @param year 年份
     * @return json
     */
    @Override
    public String checkData(String year) throws IOException {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
            int count = basicConfigDao.selectTD_PTFSX(year);
            if(count == 0){
                map.put("SRT_U", 0);
            }
            else {
                map.put("SRT_U", 1);
            }

        int count1 = basicConfigDao.selectTD_YTFSX(year);
        if(count1 == 0){
            map.put("SRT_ASS", 0);
        }
        else {
            map.put("SRT_ASS", 1);
        }

        int count2 = basicConfigDao.selectKDD(year);
        if(count2 == 0){
            map.put("CN", 0);
        }
        else {
            map.put("CN", 1);
        }
        result = mapper.writeValueAsString(map);
        return result;
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/11/4
     *
     * 查看某一张表的所有数据
     * @Author 吕志伟
     * @param: year 年份
     * @param: fileType 文件类型
     * @return json，三张表中的数据
     */
    @Override
    public String getData(String year, String fileType) throws JsonProcessingException {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        String srt_u = "SRT_U";
        String srt_ass = "SRT_ASS";
        String cn = "CN";


        if(srt_u.equals(fileType)) {
            map.put("SRT_U", basicConfigDao.requestTD_PTFSX(year));
        }
        else if(srt_ass.equals(fileType)) {
            map.put("SRT_ASS", basicConfigDao.requestTD_YTFSX(year));
        }
        else if(cn.equals(fileType)){
            map.put("CN", basicConfigDao.requestKDD(year));
        }
        else
        {
            map.put("status","传入表名错误");
        }
        result = mapper.writeValueAsString(map);
        return result;
    }

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/11/8
     * <p>
     * 上传dbf文件到服务器
     *
     * @param year     年份，默认是本年
     * @param file     文件对象
     * @param fileType 文件类型
     * @return 文件上传的状态, 00表示其他情况，01表示文件上传失败，02表示数据库添加失败，03表示数据库删除失败
     * @throws Exception exception
     * @author 邓聪
     */

    @Override
    public String uploadFile(String year, MultipartFile file, String fileType) throws Exception {
        year = GetDefaultInfo.getCurYear(year);
        String fileName = file.getOriginalFilename();
        String PTFSX = "STR_U";
        String YTFSX = "STR_ASS";
        String KDD = "CN";
        String path = "./";
        if (file.isEmpty()) {
            System.out.println("打印日志操作");
            return "none";
        }
        File newFile = new File(path + fileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);
        ReadExcelUtils excelReader = new ReadExcelUtils(path + fileName);
        List<List> paramList = excelReader.readExcelContent_1();

        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        String result = "";
        // 普通分数线
        if (PTFSX.equals(fileType)) {
            List<TD_PTFSX> PT_List = new ArrayList<TD_PTFSX>();
            for (List lists : paramList) {
                String NF = "";
                int SFDM = Integer.parseInt(null);
                int YBWS = Integer.parseInt(lists.get(1).toString());
                int YBLG = Integer.parseInt(lists.get(2).toString());
                int EBWS = Integer.parseInt(lists.get(3).toString());
                int EBLG = Integer.parseInt(lists.get(4).toString());

                TD_PTFSX td_ptfsx = new TD_PTFSX();
                td_ptfsx.setYBWS(SFDM);
                td_ptfsx.setYBLG(YBLG);
                td_ptfsx.setEBWS(EBWS);
                td_ptfsx.setEBLG(EBLG);
                PT_List.add(td_ptfsx);
            }
            // 需要一个对象List
            int status = basicConfigDao.insertTD_PTFSX(PT_List);
            if (status >= 1) {
                map.put("status", 1);
                result = mapper.writeValueAsString(map);
                return result;
            }
            map.put("status", 0);
            result = mapper.writeValueAsString(map);
            return result;
        }
        // 艺体分数线
        else if (YTFSX.equals(fileType)) {
            List<TD_YTFSX> YT_List = new ArrayList<TD_YTFSX>();
            for (List lists : paramList) {
                String NF = "";
                int SFDM = Integer.parseInt(null);
                String KLMC = (String) lists.get(1);
                String KLDM = "";
                String PCMC = (String) lists.get(2);
                String PCDM = "";
                int ZYX_NS = Integer.parseInt(lists.get(3).toString());
                int ZYX_VS = Integer.parseInt(lists.get(4).toString());
                float ZYX_ZS = Float.parseFloat(lists.get(5).toString());
                float ZYX_BL = Float.parseFloat(lists.get(6).toString());
                int WHX_W = Integer.parseInt(lists.get(7).toString());
                int WHX_L = Integer.parseInt(lists.get(8).toString());
                float WHX_ZS = Float.parseFloat(lists.get(9).toString());
                float WHX_BL = Float.parseFloat(lists.get(10).toString());
                int ZF = Integer.parseInt(lists.get(11).toString());
                TD_YTFSX td_ytfsx = new TD_YTFSX();
                td_ytfsx.setNF(NF);
                td_ytfsx.setSFDM(SFDM);
                td_ytfsx.setKLDM(KLDM);
                td_ytfsx.setKLMC(KLMC);
                td_ytfsx.setPCDM(PCDM);
                td_ytfsx.setPCMC(PCMC);
                td_ytfsx.setZYX_NS(ZYX_NS);
                td_ytfsx.setZYX_VS(ZYX_VS);
                td_ytfsx.setZYX_ZS(ZYX_ZS);
                td_ytfsx.setZYX_BL(ZYX_BL);
                td_ytfsx.setWHX_W(WHX_W);
                td_ytfsx.setWHX_L(WHX_L);
                td_ytfsx.setWHX_ZS(WHX_ZS);
                td_ytfsx.setWHX_BL(WHX_BL);
                td_ytfsx.setZF(ZF);
                YT_List.add(td_ytfsx);
            }
            int status = basicConfigDao.insertTD_YTFSX(YT_List);
            if (status >= 1) {
                map.put("status", 1);
                result = mapper.writeValueAsString(map);
                return result;
            }
            map.put("status", 0);
            result = mapper.writeValueAsString(map);
            return result;
        }
        // 快递单号表
        else if (KDD.equals(fileType)) {
            List<com.pojo.KDD> KDD_List = new ArrayList<KDD>();
            for (List list : paramList) {
                String EMS = (String) list.get(0);
                int AVAI = Integer.parseInt(list.get(1).toString());
                String YEAR = (String) list.get(2);
                String SRJ = (String) list.get(3);
                String LXDH = (String) list.get(4);
                String JTDZ = (String) list.get(5);
                String XM = (String) list.get(6);
                String KSH = (String) list.get(7);
                KDD kdd = new KDD();
                kdd.setEMS(EMS);
                kdd.setAVAI(AVAI);
                kdd.setYEAR(YEAR);
                kdd.setSRJ(SRJ);
                kdd.setLXDH(LXDH);
                kdd.setJTDZ(JTDZ);
                kdd.setXM(XM);
                kdd.setKSH(KSH);
                KDD_List.add(kdd);
            }
            int status;
            status = basicConfigDao.insertKDD(KDD_List);
            if (status >= 1) {
                map.put("status", 1);
                result = mapper.writeValueAsString(map);
                return result;
            }
            map.put("status", 0);
            result = mapper.writeValueAsString(map);
            return result;
        }
        // 其他情况
        else {
            map.put("status", -1);
            result = mapper.writeValueAsString(map);
            return result;
        }
    }

    /**
     * Modification User: 程序修改时由修改人员编写
     * Modification Date: 程序修改的时间
     *
     * 重新上传文件
     * @author 邓聪
     * @param year 年份，默认是当年
     * @param file 文件对象
     * @param fileType 文件类型
     * @return 文件上传的状态, 00表示其他情况，01表示文件上传失败，02表示数据库添加失败，03表示数据库删除失败
     * @throws Exception
     */
    @Override
    public String reUploadFile(String year, MultipartFile file, String fileType) throws Exception {
        year = GetDefaultInfo.getCurYear(year);
        String TD_PTFSX = "STR_U";
        String TD_YTFSX = "STR_ASS";
        String KDD = "CN";
        int dbSucceed = 1;
        int dbFault = 0;
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        String result = "";
        String status = "";
        int selectCount = 0;
        if(fileType.equals(TD_PTFSX)){
            // 普通
            selectCount = basicConfigDao.selectTD_PTFSX(year);
            if (selectCount > 0){
                int delStatus = basicConfigDao.deleteTD_PTFSX(year);
                if (delStatus > 0){
                    status = uploadFile(year, file, fileType);
                }
            }
        }
        else if (fileType.equals(TD_YTFSX)){
            selectCount = basicConfigDao.selectTD_YTFSX(year);
            if (selectCount > 0){
                int delStatus = basicConfigDao.deleteTD_YTFSX(year);
                if (delStatus > 0){
                    status = uploadFile(year, file, fileType);
                }
            }
        }
        else if (fileType.equals(KDD)){
            selectCount = basicConfigDao.selectTD_YTFSX(year);
            if (selectCount>0){
                int delStatus = basicConfigDao.deleteKDD(year);
                if (delStatus>0){
                    status = uploadFile(year, file, fileType);
                }
            }
        }
        else{
            map.put("status" , 0);
            status = mapper.writeValueAsString(map);
        }
        return status;

    }


}

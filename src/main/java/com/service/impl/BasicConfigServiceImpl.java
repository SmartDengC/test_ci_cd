package com.service.impl;

import com.dao.BasicConfigDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.KDD;
import com.pojo.TD_PTFSX;
import com.pojo.TD_SFDM;
import com.pojo.TD_YTFSX;
import com.service.BasicConfigService;
import com.utils.GetDefaultInfo;
import com.utils.basicConfig.ReadExcelUtils;
import com.utils.basicConfig.SearchMapByLike;
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
 * @ClassName basicConfig
 * @Version 1.0
 * @Author 吕志伟
 * @Date 2019/10/20 16:36
 * @Description 根据相关表检查数据的service层接口的实现类
 * Modification User： 邓聪
 * Modification Date： 2019/11/12
 */

@Service
public class BasicConfigServiceImpl implements BasicConfigService {
    @Autowired
    private BasicConfigDao basicConfigDao;
    @Autowired
    private BasicConfigServiceImpl basicConfigServiceImpl;

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
     * Modification Date: 2019/11/29
     * 上传excel文件到服务器
     *
     * @param year     年份，默认是本年
     * @param file     文件对象
     * @param fileType 文件类型
     * @return 文件上传的状态,0 表示上传失败，1表示成功
     * @throws Exception exception
     * @author 邓聪
     */

    @Override
    public String uploadFile(String year, MultipartFile file, String fileType) throws Exception {
        year = GetDefaultInfo.getCurYear(year);
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        String result = "";
        // 处理bug：上传的fileType在选项中，file不是想要的
        Boolean judge = basicConfigServiceImpl.judgeFilePairFileType(file, fileType);
        if (judge == false){
            map.put("status", "File does not match file type!");
            result = mapper.writeValueAsString(map);
            return result;
        }
        String fileName = file.getOriginalFilename();
        String PTFSX = "SRT_U";
        String YTFSX = "SRT_ASS";
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
        HashMap<String,Integer> provinceMap = basicConfigServiceImpl.provinceMap();
        SearchMapByLike searchMapByLike = new SearchMapByLike();
        // 普通分数线
        if (PTFSX.equals(fileType)) {
            List<TD_PTFSX> PT_List = new ArrayList<TD_PTFSX>();
            for (List lists : paramList) {
                String NF = year;
                int SFDM = searchMapByLike.getLikeByMap(provinceMap, lists.get(0).toString()).get(0);
                int YBWS = Integer.parseInt("".equals(lists.get(1).toString())?"0":lists.get(1).toString());
                int YBLG = Integer.parseInt("".equals(lists.get(2).toString())?"0":lists.get(2).toString());
                int EBWS = Integer.parseInt("".equals(lists.get(3).toString())?"0":lists.get(3).toString());
                int EBLG = Integer.parseInt("".equals(lists.get(4).toString())?"0":lists.get(4).toString());
                TD_PTFSX td_ptfsx = new TD_PTFSX();
                td_ptfsx.setNF(NF);
                td_ptfsx.setSFDM(SFDM);
                td_ptfsx.setYBWS(YBWS);
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
                String NF = year;
                int SFDM = searchMapByLike.getLikeByMap(provinceMap, lists.get(0).toString()).get(0);
                String KLMC = (String) lists.get(1);
                String KLDM = "";
                String PCMC = (String) lists.get(2);
                String PCDM = "";
                int ZYX_NS = Integer.parseInt("".equals(lists.get(3).toString())?"0":lists.get(3).toString());
                int ZYX_VS = Integer.parseInt("".equals(lists.get(4).toString())?"0":lists.get(4).toString());
                float ZYX_ZS = Float.parseFloat("".equals(lists.get(5).toString())?"0.00":lists.get(5).toString());
                float ZYX_BL = Float.parseFloat("".equals(lists.get(6).toString())?"0.00":lists.get(6).toString());
                int WHX_W = Integer.parseInt("".equals(lists.get(7).toString())?"0":lists.get(7).toString());
                int WHX_L = Integer.parseInt("".equals(lists.get(8).toString())?"0":lists.get(8).toString());
                float WHX_ZS = Float.parseFloat("".equals(lists.get(9).toString())?"0.00":lists.get(9).toString());
                float WHX_BL = Float.parseFloat("".equals(lists.get(10).toString())?"0.00":lists.get(10).toString());
                int ZF = Integer.parseInt("".equals(lists.get(11).toString())?"0":lists.get(11).toString());
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
                // 这里kdd.excel中有年份，有传入了一个年份
                String YEAR = year;
                String SJR = (String) list.get(3);
                String LXDH = (String) list.get(4);
                String JTDZ = (String) list.get(5);
                String XM = (String) list.get(6);
                String KSH = (String) list.get(7);
                KDD kdd = new KDD();
                kdd.setEMS(EMS);
                kdd.setAVAI(AVAI);
                kdd.setYEAR(YEAR);
                kdd.setSJR(SJR);
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
            map.put("status", "fileType not in [SRT_U, SRT_ASS, CN]");
            result = mapper.writeValueAsString(map);
            return result;
        }
    }

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/11/29
     *
     * 重新上传文件
     * @author 邓聪
     * @param year 年份，默认是当年
     * @param file 文件对象
     * @param fileType 文件类型
     * @return 文件上传的状态,0表示失败，1表示成功
     * @throws Exception
     */
    @Override
    public String reUploadFile(String year, MultipartFile file, String fileType) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        String status = "";
        // 处理bug：上传的fileType在选项中，file不是想要的
        Boolean judge = basicConfigServiceImpl.judgeFilePairFileType(file, fileType);
        if (judge == false){
            map.put("status", "File does not match file type!");
            status = mapper.writeValueAsString(map);
            return status;
        }
        year = GetDefaultInfo.getCurYear(year);
        String TD_PTFSX = "SRT_U";
        String TD_YTFSX = "SRT_ASS";
        String KDD = "CN";
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
            selectCount = basicConfigDao.selectKDD(year);
            if (selectCount>0){
                int delStatus = basicConfigDao.deleteKDD(year);
                if (delStatus>0){
                    status = uploadFile(year, file, fileType);
                }
            }
        }
        else{
            map.put("status" ,"fileType not in [SRT_U, SRT_ASS, CN]");
            status = mapper.writeValueAsString(map);
        }
        // 处理所有异常的if
        if (status == ""){
            map.put("status", 0);
            status = mapper.writeValueAsString(map);
        }
        return status;

    }

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/11/29
     * 返回省份map{"四川":1}
     *
     * @return map
     * @author 邓聪
     */
    @Override
    public HashMap<String, Integer> provinceMap() {
        // 数据库返回的数据类似：[TD_SFDM@68{"SFDM":1,"SF":"北京市"}]
        List<TD_SFDM> returnList = basicConfigDao.requestTD_SFDM();
        HashMap<String,Integer> provinceMap = new HashMap<String, Integer>();
        for (TD_SFDM list:returnList){
            provinceMap.put(list.getSF(), list.getSFDM());
        }
        return provinceMap;
    }

    @Override
    public Boolean judgeFilePairFileType(MultipartFile file, String fileType) throws Exception {
        Boolean result = false;
        String path = "./";
        if (file.isEmpty()) {
            System.out.println("打印日志操作");
            return result;
        }
        String fileName = file.getOriginalFilename();
        File newFile = new File(path + fileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);
        ReadExcelUtils excelReader = new ReadExcelUtils(path + fileName);
        List excelTitle = new ArrayList();
        excelTitle = excelReader.readExcelTitle();
        int field = excelTitle.size();
        String srt_u = "SRT_U";
        String srt_ass = "SRT_ASS";
        String cn = "CN";
        if (field == 5 && fileType.equals(srt_u)){
            result = true;
        }
        else if (field == 12 && fileType.equals(srt_ass)){
            result = true;
        }
        else if (field == 8 && fileType.equals(cn)){
            result = true;
        }
        else {
            result = false;
        }
        return result;
    }

}


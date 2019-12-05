package com.controller;/**
 * @title: dataImport
 * @projectName FreshmanInfomationAnalysSystem
 * @description: TODO
 * @author msi
 * @date 2019/10/2321:28
 */
import com.dao.BasicConfigDao;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.Files;
import com.pojo.TD_SFDM;
import com.service.DataImportService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.utils.MakeJson;
import com.utils.datadriverutils.DbfPip;
import joinery.DataFrame;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * @ClassName dataImport
 * @Version 1.0
 * @Author 陈泯全
 * @Date 2019/10/23 21:28
 * @Description 接收数据导入功能的所有请求(缺陷：没有加入日志)
 * Modification User： 陈泯全
 * Modification Date： 2019.10.24
 */
@Controller
@CrossOrigin
@RequestMapping("/dataImport")
public class DataImportController {

    @Autowired
    private DataImportService dataImportService;
    @Autowired
    private BasicConfigDao basicConfigDao;

    private final static  String CHARSET = "GB2312";

    /**
     * 一共有25个dbf文件：
     * "TD_BYLBDM", "TD_CJXDM","TD_DQDM","TD_JHXZDM","TD_KLDM","TD_KSLBDM","TD_KSLXDM","TD_LQFSDM","TD_MZDM",
     * "TD_PCDM","TD_TDYYDM","TD_TJJLDM","TD_WYYZDM","TD_XBDM","TD_XTDWDM","TD_ZCDM","TD_ZYTZDM","TD_ZZMMDM",
     * "t_jhk","T_KSHKCJ","T_KSJL","T_QBJHK","T_TDD","t_tddw","T_TJXX"
     * 但是只有其中16个是需要用户导入的：
     * T_TDD、TD_CJXDM
     * TD_BYLBDM、TD_DQDM、TD_JHXZDM、TD_KLDM、TD_KSLBDM、TD_KSLXDM、TD_LQFSDM、
     * TD_MZDM、TD_PCDM、TD_WYYZDM、TD_ZCDM、TD_ZYTZDM、TD_ZZMMDM、TD_QBJHK
     */
    public static HashSet<String> DBF_NAME_SET = new HashSet<String>(Arrays.asList(
            "TD_BYLBDM", "TD_CJXDM", "TD_DQDM", "TD_JHXZDM", "TD_KLDM", "TD_KSLBDM", "TD_KSLXDM", "TD_LQFSDM", "TD_MZDM",
            "TD_PCDM", "TD_TDYYDM", "TD_TJJLDM", "TD_WYYZDM", "TD_XBDM", "TD_XTDWDM", "TD_ZCDM", "TD_ZYTZDM", "TD_ZZMMDM",
            "t_jhk", "T_KSHKCJ", "T_KSJL", "T_QBJHK", "T_TDD", "t_tddw", "T_TJXX"));
    /**
     * 最大的年份值
     */
    public static int maxYear = 2059;
    /**
     * 最小的年份值
     */
    public static int minYear = 2002;
    /**
     * 用户一次上传的文件数
     */
    public static int dbfNumber = 16;

    /**
     * 接收前端传输的16个dbf文件
     *
     * @param year     年份
     * @param province 省份
     * @param dbfs     dbf文件
     * @return 请状态码json, 值为1代表成功，0代表失败
     */
    @ResponseBody
    @RequestMapping("/read")
    public String dataImport(String year, String province,String status, MultipartFile[] dbfs) throws JsonProcessingException {
        //1，年份检测、省份安全检查，省份代码是要查询数据库吗？
        //新的省份处理方式,是否这里要请数据库检测省份代码存不存在？
        if (year == null || Integer.parseInt(year) < minYear || Integer.parseInt(year) > maxYear ||
                province == null) {
            return "{\"status\":\"0\"}";
        }
        HashMap<String, InputStream> dbfMap = new HashMap<String, InputStream>();
        //2，dbf文件数量与文件名检测  测试是关闭严格数量检测 dbfNumber
        if (dbfs == null || dbfs.length != dbfNumber) {
            return "{\"status\":\"0\"}";
        } else {
            //将dbf文件的名字与其对应的InputStream封装到dbfMap里
            for (MultipartFile dbfFile : dbfs) {
                String originalFilename = dbfFile.getOriginalFilename();
                String subName = originalFilename.substring(0, originalFilename.indexOf("."));
                //判断是否为空，名字是否为指定的名字
                if (dbfFile.isEmpty() || !DBF_NAME_SET.contains(subName)) {
                    return "{\"status\":\"0\"}";
                } else {
                    try {
                        //问题：目前dbfMap之中只存了一个年份和省份的数据；当存入多个省份的时候，不能区分数据的年份和省份？
                        dbfMap.put(subName, dbfFile.getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "{\"status\":\"0\"}";
                    }
                }
            }
        }
        if("1".equals(status)) {
            //3调用service函数，写入数据
            int importResoult = 0;
            try {
                importResoult = dataImportService.importData(dbfMap, Integer.parseInt(year), Integer.parseInt(province));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (importResoult == 1) {
                return "{\"status\":\"1\"}";
            } else {
                return "{\"status\":\"0\"}";
            }
        }
        else {
            dataImportService.clearData(year, province);
            int importResoult = 0;
            try {
                importResoult = dataImportService.importData(dbfMap, Integer.parseInt(year), Integer.parseInt(province));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (importResoult == 1) {
                return "{\"status\":\"1\"}";
            } else {
                return "{\"status\":\"0\"}";
            }
        }
    }

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/11/5
     * <p>
     * 获取数据空中是否有数据
     *
     * @param year     年份 默认为本年
     * @param province 省份代码，默认四川省代码1
     * @return 是否有数据，1表示有数据，0表示没有数据
     * @author 邓聪
     */
    @ResponseBody
    @RequestMapping("/queryInfo")
    public String getTableStatus(String year, String province) throws JsonProcessingException {
        int beginYear = 2002;
        int endYear = 2059;
        String haveMessage = "1";
        String noMessage = "0";
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        String result = "";
        if (year != null && year != null && Integer.parseInt(year) >= beginYear && Integer.parseInt(year) <= endYear) {
            String status = dataImportService.getTableStatus(year, province);
            return status;
        }
        map.put("tableStatus", -1);
        List<String> tableName = new ArrayList<>();
        tableName.add("T_TDD");
        tableName.add("TD_CJXDM");
        tableName.add("TD_BYLBDM");
        tableName.add("TD_DQDM");
        tableName.add("TD_JHXZDM");
        tableName.add("TD_KLDM");
        tableName.add("TD_KSLBDM");
        tableName.add("TD_KSLXDM");
        tableName.add("TD_LQFSDM");
        tableName.add("TD_MZDM");
        tableName.add("TD_PCDM");
        tableName.add("TD_WYYZDM");
        tableName.add("TD_ZCDM");
        tableName.add("TD_ZYTZDM");
        tableName.add("TD_ZZMMDM");
        tableName.add("T_QBJHK");
        map.put("tableName", tableName);
        result = mapper.writeValueAsString(map);
        return result;
    }

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/11/4
     * <p>
     * 获取所有省份的代码和名称，返回前端
     *
     * @return json 对象的list，格式为 [{"SFDM":1,"SF":"北京市"},{...}]
     * @author 邓聪
     */
    @ResponseBody
    @RequestMapping("/queryProvinces")
    public List<TD_SFDM> getProvincesStatus() {
        List<TD_SFDM> returnList = dataImportService.getProvincesStatus();
        return returnList;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public int testJson(@RequestBody Map ob) throws Exception {
        MakeJson m = new MakeJson();
        int year = Integer.parseInt((String) ob.get("year"));
//        int province = Integer.parseInt((String) ob.get("province"));
        ArrayList<HashMap> files = (ArrayList) ob.get("files");
        HashMap<String, InputStream> dbfMap = new HashMap<>();
        for (HashMap dbf : files) {
            String filename = (String) dbf.get("fileName");
            String subName = filename.substring(0, filename.indexOf("."));
            String filedata = (String) dbf.get("fileData");
            byte[] datas = m.hexToByte(filedata);
            String d1 = new String(datas);
            InputStream dbfdata = m.getStringStream(d1);
            dbfMap.put(subName, dbfdata);
        }
       int i = dataImportService.importData(dbfMap, year, 1);


//        FileOutputStream fis = new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\222.dbf");
//
//        //取出前端参数中的files对呀的value：是一个List
//        ArrayList<HashMap> files = (ArrayList) ob.get("files");
//
//        //取出List中的对象：使用map来容纳
//        HashMap dbf = (HashMap) files.get(15);
//
//        //取出第一个对象的fileData属性值
//        String data = (String) dbf.get("fileData");
//        byte[] src = data.getBytes();
//        MakeJson m = new MakeJson();
//        byte[] datas = m.hexToByte(data);
//        String d1 = new String(datas);
//        ByteArrayInputStream d2 = (ByteArrayInputStream) m.getStringStream(d1);
////        List<List<Object>> aaa = Arrays.asList(Arrays.<Object>asList("alpha", "bravo", "charlie"),
////        Arrays.<Object>asList(1, 2, 3));
////        DataFrame<Object> df = new DataFrame<>(aaa);
////        df.show();
////        DataFrame dataFrame = DbfPip.open(d2);
////        dataFrame.show();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int ch = 0;
//        while ((ch = d2.read()) != -1) {
//            baos.write(ch);
//        }
//        fis.write(baos.toByteArray());
//        //dataFrame.show();
////        System.out.print(dataFrame.s);
        return i;
    }
    /*吕志伟*/

    /**
     * Modification User: 吕志伟
     * Modification Date: 2019/11/24
     *
     * 检查上次导入有没有未完成整个流程的省份
     * @param year
     * @return 0//无未完成导入省份；1//未完成省份进行到数据清洗；2//未完成省份进行到分数整合
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="/checkData",produces="text/html;charset=UTF-8;")
    public String checkYear(String year) throws IOException {
        String result = null;
        try{
            result = dataImportService.checkData(year);
        }
        catch (Exception e){
        System.out.print(e);
    }
        return result;
    }

    /**
     * 吕志伟
     * 清空该省数据库中的数据
     * @param province
     * @return 0//清空失败；1//清空成功
     */
    @ResponseBody
    @RequestMapping("/clearData")
    public String clearData(String year,String province) throws JsonProcessingException {
        String result = null;
        result = dataImportService.clearData(year,province);
        return result;
    }

    /**
     * 吕志伟
     * 检测已导入省份和未导入省份
     * @param year
     * @return json {[ "imported":list,
     * "unimport":list]}
     * @throws JsonProcessingException
     */
    @ResponseBody
    @RequestMapping(value = "/checkProvince",produces="text/html;charset=UTF-8;")
    public String checkProvince(String year) throws JsonProcessingException {
        String result = null;
        result = dataImportService.checkProvince(year);
        return  result;
    }

}
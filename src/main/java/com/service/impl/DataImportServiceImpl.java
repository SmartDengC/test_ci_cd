package com.service.impl;/**
 * @title: DataImportServiceImpl
 * @projectName FreshmanInfomationAnalysSystem
 * @description: TODO
 * @author msi
 * @date 2019/10/2321:31
 */

import com.dao.DataImportDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.*;
import com.service.DataImportService;
import com.utils.GetDefaultInfo;
import com.utils.provincescore.*;
import joinery.DataFrame;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.utils.datadriverutils.DbfPip;
import com.utils.datautils.DataFrameUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @ClassName DataImportServiceImpl
 * @Version 1.0
 * @Author 陈泯全
 * @Date 2019/10/23 21:31
 * @Description 处理dbf数据业务的实现类，实现dbf文件到实体list类型的转化并写入数据库。
 * 实现以下dbf数据的导入:
 * T_TDD、TD_CJXDM
 * TD_BYLBDM、TD_DQDM、TD_JHXZDM、TD_KLDM、TD_KSLBDM、TD_KSLXDM、TD_LQFSDM、
 * TD_MZDM、TD_PCDM、TD_WYYZDM、TD_ZCDM、TD_ZYTZDM、TD_ZZMMDM、TD_QBJHK
 * 陈泯全实现的表导入是：
 *TD_QBJHK、TD_BYLBDM、T_TDD、(TD_CJXDM)
 * 吕志伟实现的表导入是：
 * TD_CJXDM、TD_DQDM、TD_JHXZDM、TD_KLDM、TD_KSLBDM、TD_KSLXDM、TD_LQFSDM、TD_MZDM、TD_PCDM
 * 邓聪实现的表导入时：
 *TD_WYYZDM、TD_ZCDM、TD_ZYTZDM、TD_ZZMMDM
 * Modification User： 陈泯全
 * Modification Date： 2019.11.9
 */
@Service
public class DataImportServiceImpl implements DataImportService {

    @Autowired
    DataImportDao dataImportDao;

    int count;

    /**陈泯全
     * 实现以下dbf数据的导入:
     * T_TDD、TD_CJXDM(这个表只是用到了信息，不需要导入数据库)
     * TD_BYLBDM、TD_DQDM、TD_JHXZDM、TD_KLDM、TD_KSLBDM、TD_KSLXDM、TD_LQFSDM、
     * TD_MZDM、TD_PCDM、TD_WYYZDM、TD_ZCDM、TD_ZYTZDM、TD_ZZMMDM、TD_QBJHK
     * 其中，T_TDD、TD_CJXDM必须要有数据，其他可以没有数据
     * @param dbfMap dbf文件的InputStream格式
     * @param year 年份
     * @param province 省份
     * @return 导入结果情况，1代表成功；0代表失败
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int importData (HashMap<String, InputStream> dbfMap, int year, int province) throws Exception{

        heBeiScore hebei = new heBeiScore();
        guangXiScore guangxi = new guangXiScore();
        shanXiScore shanxi = new shanXiScore();
        haiNanScore hainan = new haiNanScore();
        yunNanScore yunnan = new yunNanScore();

            /**
             * 先处理特殊的T_TDD与TD_XSFS表
             */
            DataFrame T_TDDDataFrame = DbfPip.open(dbfMap.get("T_TDD"));
            //找到T_TDD表中表示特定分数的字段，并将这个字段映射到分数表的字段上
       //Double zonhescore = (Double)T_TDDDataFrame.get(0,81);

            DataFrame TD_CJXDMdataFrame = DbfPip.open(dbfMap.get("TD_CJXDM"));
            HashMap<String ,String> colmnMap = new HashMap<String, String>();
            if(TD_CJXDMdataFrame.isEmpty()|| T_TDDDataFrame.isEmpty())
            {
                System.out.println("提取成绩对照表：TD_CJXDMFile，出错！表是空的！");
                return -1;
            }
            else
            {
                for(int i = 0; i <TD_CJXDMdataFrame.length() ; i++)
                {
                    String CJXDM = null;
                    String CJXMC = null;
                    if(TD_CJXDMdataFrame.get(i,"CJXDM")!=null && TD_CJXDMdataFrame.get(i,"CJXMC")!=null){
                        CJXDM="GKCJX"+TD_CJXDMdataFrame.get(i,"CJXDM").toString().replaceAll(" ","");
                        CJXMC=TD_CJXDMdataFrame.get(i,"CJXMC").toString().replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", ""); //去掉首尾空格
                    }
                    //河北省
                    if (province == 3){
                        colmnMap = hebei.heBeiScore(colmnMap,CJXDM,CJXMC);
                    }
                    //广西
                    else if (province == 20){
                        colmnMap = guangxi.guangXiScore(colmnMap,CJXDM,CJXMC);
                    }
                    //陕西
                    else if(province == 27){
                        colmnMap = shanxi.shanXiScore(colmnMap,CJXDM,CJXMC);
                    }
                    //海南省
                    else if(province == 21){
                        colmnMap = hainan.haiNanScore(colmnMap,CJXDM,CJXMC);
                    }
                    //云南省
                    else if(province == 24){
                        colmnMap = yunnan.yunNanScore(colmnMap,CJXDM,CJXMC);
                    }
                    else {
                        switch (CJXMC) {
                            case "语文":
                                colmnMap.put("YWCJ", CJXDM);
                                break;
                            case "数学":
                                colmnMap.put("SXCJ", CJXDM);
                                break;
                            case "外语":
                                colmnMap.put("WYCJ", CJXDM);
                                break;
                            case "综合":
                                colmnMap.put("ZHCJ", CJXDM);
                            default:
                                break;
                        }
                        //广西
                        if(province == 20 && colmnMap.size() == 2){
                            break;
                        }
                        else if(province == 27 && colmnMap.size() == 3){
                            break;
                        }
                        else if (colmnMap.size() == 4) {
                            break;
                        }
                    }
                }
                }
            if(colmnMap.size() != 4 && province!= 20 && province != 27){
                System.out.println("提取成绩对照表：TD_CJXDMFile，出错！配置多了或少了字段映射！");
                return -1;
            }
            /**
             * 提取XSFS的信息并将它变成List<obj>
              * 将学生分数信息保存到数据库,添加年份，省份等代码
              */
            List<TD_XSFS> td_xsfsList =new ArrayList<TD_XSFS>();


            count = 0;
            for(;count < td_xsfsList.size();){
                count++;
            }

            //配置忽略的字段
            Set<String> TD_XSFSTableXxcludeSet =new  HashSet<String>();
            TD_XSFSTableXxcludeSet.add("ID");
            /**
             * 处理T_TDDDataFrame中的数据：
             * 1、将性别代码变成中文
             * 2、添加年份、省份代码
             * 3、将分数全部变成整数
             */
            List<Integer> yearList = new ArrayList<Integer>();
            List<Integer> provinceList = new ArrayList<Integer>();
            List<Integer> ZYCJList = new ArrayList<Integer>();
            List<Integer> ZSCJList = new ArrayList<Integer>();
            T_TDDDataFrame.rename("XBDM","XB");
            for (int i = 0; i <T_TDDDataFrame.length() ; i++) {
                //处理性别数据
                if(T_TDDDataFrame.get(i,"XB")!=null){
                    String gender = T_TDDDataFrame.get(i,"XB").toString();
                    if(gender !=null && gender !=""){
                        if(gender.equals("1"))
                        {
                            T_TDDDataFrame.set(i,"XB","男");
                        }
                        else
                        {
                            T_TDDDataFrame.set(i,"XB","女");
                        }
                }else {
                        T_TDDDataFrame.set(i,"XB"," ");
                    }
                }
                //添加年份、省份
                yearList.add(year);
                provinceList.add(province);
                //处理分数
                Object scoreObject=null;
                int score =0;
                ZYCJList.add(0);
                ZSCJList.add(0);
                /**
                 * 这里对映射集合中的分数去除小数点,并将空值置零
                 */
                for( String item : colmnMap.keySet()){
                    scoreObject = T_TDDDataFrame.get(i,colmnMap.get(item));
                    if(scoreObject!=null && !scoreObject.toString().equals("")){
                        score = Integer.parseInt(scoreObject.toString().substring(0,scoreObject.toString().indexOf(".")));
                    }
                    else{
                        score=0;
                    }
                    T_TDDDataFrame.set(i,colmnMap.get(item),score);
                }
                /**
                 * 这里对通用分数去小数点：
                 * JC TZCJ ZGF TDCJ
                 */
                scoreObject = T_TDDDataFrame.get(i,"CJ");
                if(scoreObject!=null &&!scoreObject.toString().equals("")){
                        score = Integer.parseInt(scoreObject.toString().substring(0,scoreObject.toString().indexOf(".")));
                        T_TDDDataFrame.set(i,"CJ",score);
                    }else{
                        T_TDDDataFrame.set(i,"CJ",0);
                }
                scoreObject = T_TDDDataFrame.get(i,"TZCJ");
                if(scoreObject!=null &&!scoreObject.toString().equals("")){
                    score = Integer.parseInt(scoreObject.toString().substring(0,scoreObject.toString().indexOf(".")));
                    T_TDDDataFrame.set(i,"TZCJ",score);
                }else{
                    T_TDDDataFrame.set(i,"TZCJ",0);
                }
                scoreObject = T_TDDDataFrame.get(i,"ZGF");
                if(scoreObject!=null &&!scoreObject.toString().equals("")){
                    score = Integer.parseInt(scoreObject.toString().substring(0,scoreObject.toString().indexOf(".")));
                    T_TDDDataFrame.set(i,"ZGF",score);
                }else{
                    T_TDDDataFrame.set(i,"ZGF",0);
                }
                scoreObject = T_TDDDataFrame.get(i,"TDCJ");
                if(scoreObject!=null &&!scoreObject.toString().equals("")){
                    score = Integer.parseInt(scoreObject.toString().substring(0,scoreObject.toString().indexOf(".")));
                    T_TDDDataFrame.set(i,"TDCJ",score);
                }else{
                    T_TDDDataFrame.set(i,"TDCJ",0);
                }

            }
            //T_TDDDataFrame中添上数据
            T_TDDDataFrame.add("NF",yearList);
            T_TDDDataFrame.add("SFDM",provinceList);
            T_TDDDataFrame.add("ZSCJ",ZSCJList);
            T_TDDDataFrame.add("ZYCJ",ZYCJList);

            Class clazz = new TD_XSFS().getClass();
            /**
             * 获得TD_XSFX实体集合
             */
            td_xsfsList = DataFrameUtils.fillListByMap(T_TDDDataFrame,clazz,TD_XSFSTableXxcludeSet,colmnMap);
            if (province == 20){
                td_xsfsList = guangxi.score(T_TDDDataFrame,td_xsfsList);
            }
            else if (province == 27){
                td_xsfsList = shanxi.score(T_TDDDataFrame,td_xsfsList);
            }

            int insertTd_xsfsDataResult =0;
            //在这执行插入数据库功能，并获得主键值
            if(td_xsfsList != null){
                insertTd_xsfsDataResult = dataImportDao.insertTD_XSFS(td_xsfsList);
            }
            else {
                System.out.println("将T_TDDDataFrame的数据转换到TD_XSFS实体出错！");
                return -1;
            }
            if (insertTd_xsfsDataResult==0)
            {
                System.out.println("导入TD_XSFS出错！");
                throw new Exception();
            }

        /**
         * 提取td_xsfsList实体集中的主键并写入到T_TDDDataFrame中
         */
        List<String>  TD_XSFSTableId = new ArrayList<String>();
            for(TD_XSFS td_xsfsItem: td_xsfsList){
                TD_XSFSTableId.add(String.valueOf(td_xsfsItem.getID()));
            }
            T_TDDDataFrame.add("FS_ID",TD_XSFSTableId);

            // T_TDD转换成List
            clazz = new T_TDD().getClass();
            Set<String> excludeSet =new  HashSet<String>();
            excludeSet.add("ID");
            /**
             * KSLXDM 原数据float->数据库为int
             * ZYDH5 为空情况，引申出：所有非String类型为空情况怎么处理
             *
             */
            List<T_TDD> t_tdds = DataFrameUtils.fillListByDefault(T_TDDDataFrame,clazz,excludeSet);
            //这里调用Dao,写入数据
            if(dataImportDao.insertT_TDD(t_tdds)==0){
                System.out.println("导入TD_TDD出错！");
                throw new Exception();
            }
            dbfMap.remove("T_TDD");
            dbfMap.remove("TD_CJXDM");
            /**
             * 调用各个mapper.写入数据，如果发生任何错误，全部回滚,回滚后就应该结束函数
             */
            HashMap<String,List<Object>> entityMap =new HashMap<String, List<Object>>();
            for (String key : dbfMap.keySet()){
                switch (key){
                    case "T_QBJHK": {
                        List<TD_QBJHK> td_qbjhkList =  this.importT_QBJHKTable(dbfMap.get("T_QBJHK"),year,province);
                        //这里需要判断null
                        if (td_qbjhkList==null){
                            System.out.println("导入T_QBJHK出错！");
                            throw new Exception();
                        }else if(td_qbjhkList.size()!=0){
                            dataImportDao.insertTD_QBJHK(td_qbjhkList);
                        }
                        break;
                    }
                    case"TD_BYLBDM":{
                         List<TD_BYLBDM> td_bylbdmList =this.importTD_BYLBDMTable(dbfMap.get("TD_BYLBDM"),year,province);
                        if(td_bylbdmList==null){
                            System.out.println("导入TD_BYLBDM出错！");
                            throw new Exception();
                        }else if(td_bylbdmList.size()!=0){
                            dataImportDao.insertTD_BYLBDM(td_bylbdmList);
                        }
                        break;
                    }
                    case "TD_DQDM":{
                        List<TD_DQDM> td_dqdmList =this.insertTD_DQDM(dbfMap.get("TD_DQDM"),year,province);
                        if(td_dqdmList==null){
                            System.out.println("导入TD_DQDM出错！");
                            throw new Exception();
                        }else if(td_dqdmList.size()!=0){
                            dataImportDao.insertTD_DQDM(td_dqdmList);
                        }
                        break;
                    }
                    case "TD_JHXZDM":{
                        List<TD_JHXZDM> td_jhxzdmList =this.insertTD_JHXZDM(dbfMap.get("TD_JHXZDM"),year,province);
                        if(td_jhxzdmList==null){
                            System.out.println("导入TD_JHXZDM出错！");
                            throw new Exception();
                        }else if(td_jhxzdmList.size()!=0){
                            dataImportDao.insertTD_JHXZDM(td_jhxzdmList);
                        }
                        break;
                    }
                    case "TD_KLDM":{
                        List<TD_KLDM> td_kldmList =this.insertTD_KLDM(dbfMap.get("TD_KLDM"),year,province);
                        if(td_kldmList==null){
                            System.out.println("导入TD_KLDM出错！");
                            throw new Exception();
                        }else if(td_kldmList.size()!=0){
                            dataImportDao.insertTD_KLDM(td_kldmList);
                        }
                        break;
                    }
                    case "TD_KSLBDM":{
                        List<TD_KSLBDM> td_kslbdmList =this.insertTD_KSLBDM(dbfMap.get("TD_KSLBDM"),year,province);
                        if(td_kslbdmList==null){
                            System.out.println("导入TD_KSLBDM出错！");
                            throw new Exception();
                        }else if(td_kslbdmList.size()!=0){
                            dataImportDao.insertTD_KSLBDM(td_kslbdmList);
                        }
                        break;
                    }
                    case "TD_KSLXDM":{
                        List<TD_KSLXDM> td_kslxdmList =this.insertTD_KSLXDM(dbfMap.get("TD_KSLXDM"),year,province);
                        if(td_kslxdmList==null){
                            System.out.println("导入TD_KSLXDM出错！");
                            throw new Exception();
                        }else if(td_kslxdmList.size()!=0){
                            dataImportDao.insertTD_KSLXDM(td_kslxdmList);
                        }
                        break;
                    }
                    case "TD_LQFSDM":{
                        List<TD_LQFSDM> td_lqfsdmList =this.insertTD_LQFSDM(dbfMap.get("TD_LQFSDM"),year,province);
                        if(td_lqfsdmList==null){
                            System.out.println("导入TD_LQFSDM出错！");
                            throw new Exception();
                        }else if(td_lqfsdmList.size()!=0){
                            dataImportDao.insertTD_LQFSDM(td_lqfsdmList);
                        }
                        break;
                    }
                    case "TD_MZDM":{
                        List<TD_MZDM> td_mzdmList =this.insertTD_MZDM(dbfMap.get("TD_MZDM"),year,province);
                        if(td_mzdmList==null){
                            System.out.println("导入TD_MZDM出错！");
                            throw new Exception();
                        }else if(td_mzdmList.size()!=0){
                            dataImportDao.insertTD_MZDM(td_mzdmList);
                        }
                        break;
                    }
                    case "TD_PCDM":{
                        List<TD_PCDM> td_pcdmList =this.insertTD_PCDM(dbfMap.get("TD_PCDM"),year,province);
                        if(td_pcdmList==null){
                            System.out.println("导入TD_PCDM出错！");
                            throw new Exception();
                        }else if(td_pcdmList.size()!=0){
                            dataImportDao.insertTD_PCDM(td_pcdmList);
                        }
                        break;
                    }
                    case "TD_WYYZDM":{
                        List<TD_WYYZDM> td_wyyzdmList =this.importTD_WYYZDMTable(dbfMap.get("TD_WYYZDM"),year,province);
                        if(td_wyyzdmList==null){
                            System.out.println("导入TD_WYYZDM出错！");
                            throw new Exception();
                        }else if(td_wyyzdmList.size()!=0){
                            dataImportDao.insertTD_WYYZDM(td_wyyzdmList);
                        }
                        break;
                    }
                    case "TD_ZCDM":{
                        List<TD_ZCDM> td_zcdmList =this.importTD_ZCDMTable(dbfMap.get("TD_ZCDM"),year,province);
                        if(td_zcdmList==null){
                            System.out.println("导入TD_ZCDM出错！");
                            throw new Exception();
                        }else {
                            dataImportDao.insertTD_ZCDM(td_zcdmList);
                        }
                        break;
                    }
                    case "TD_ZYTZDM":{
                        List<TD_ZYTZDM> td_zytzdmList =this.importTD_ZYTZDMTable(dbfMap.get("TD_ZYTZDM"),year,province);
                        if(td_zytzdmList==null){
                            System.out.println("导入TD_ZYTZDM出错！");
                            throw new Exception();
                        }else if(td_zytzdmList.size()!=0){
                            dataImportDao.insertTD_ZYTZDM(td_zytzdmList);
                        }
                        break;
                    }
                    case "TD_ZZMMDM":{
                        List<TD_ZZMMDM> td_zzmmdmList =this.importTD_ZZMMDMTable(dbfMap.get("TD_ZZMMDM"),year,province);
                        if(td_zzmmdmList==null){
                            System.out.println("导入TD_ZZMMDM出错！");
                            throw new Exception();
                        }else if (td_zzmmdmList.size()!=0){
                            dataImportDao.insertTD_ZZMMDM(td_zzmmdmList);
                        }
                        break;
                    }
                    default:{
                        System.out.println("出现未知出错！");
                        throw new Exception();
                    }
                }
            }
            return count;
    }
    /**陈泯全
     * 将InputStream数据类型的TD_QBJHK表变成实体List
     * @param T_QBJHKFile InputStream类型的T_QBJHK.dbf数据
     * @param year 年份
     * @param province 省份代码代码
     * @return TD_QBJHK的实体集合，如果出错返回null
     */
    @Override
    public List<TD_QBJHK> importT_QBJHKTable(InputStream T_QBJHKFile, int year, int province) {
        //1，讲数据变成dataframe类型
        List<TD_QBJHK> td_qbjhksList=new ArrayList<TD_QBJHK>();
        DataFrame dataFrame = DbfPip.open(T_QBJHKFile);
        if(dataFrame.isEmpty())
        {
            return td_qbjhksList;
        }
        //2，添加数据
        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_QBJHK().getClass();
            Set<String> excludeSet =new  HashSet<String>();
            excludeSet.add("ID");
            td_qbjhksList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
            if (td_qbjhksList==null){
            return null;
        }
        else {
            return td_qbjhksList;
        }
    }

    /**陈泯全
     * 将InputStream数据类型的BYLBDM表变成实体List
     * @param TD_BYLBDMFile InputStream类型的BYlbdm.dbf数据
     * @param year 年份
     * @param province 省份代码
     * @return TD_BYLBDM的实体集合，如果出错返回null
     */
    @Override
    public List<TD_BYLBDM> importTD_BYLBDMTable(InputStream TD_BYLBDMFile, int year, int province) {
        //1，将数据变成dataframe类型
        List<TD_BYLBDM> td_bylbdmList=new ArrayList<TD_BYLBDM>();
        DataFrame dataFrame = DbfPip.open(TD_BYLBDMFile);
        if(dataFrame.isEmpty())
        {
            return td_bylbdmList;
        }
        //2，添加数据
        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_BYLBDM().getClass();
        Set<String> excludeSet =new  HashSet<String>();
        excludeSet.add("ID");
        td_bylbdmList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_bylbdmList==null){
            return null;
        }
        else {
            return td_bylbdmList;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_CJXDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_CJXDM> insertTD_CJXDM(InputStream TD_CJXDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        DataFrame dataFrame = DbfPip.open(TD_CJXDMFile);
        if(dataFrame.isEmpty())
        {
            return null;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_CJXDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_CJXDM> td_cjxdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_cjxdms==null){
            return null;
        }
        else {
            return td_cjxdms;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_DQDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_DQDM> insertTD_DQDM(InputStream TD_DQDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        List<TD_DQDM> td_dqdmList = new ArrayList<TD_DQDM>();
        DataFrame dataFrame = DbfPip.open(TD_DQDMFile);
        if(dataFrame.isEmpty())
        {
            return td_dqdmList;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_DQDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        td_dqdmList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_dqdmList==null){
            return null;
        }
        else {
            return td_dqdmList;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_JHXZDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_JHXZDM> insertTD_JHXZDM(InputStream TD_JHXZDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        List<TD_JHXZDM> td_jhxzdmList=new ArrayList<>();
        DataFrame dataFrame = DbfPip.open(TD_JHXZDMFile);
        if(dataFrame.isEmpty())
        {
            return td_jhxzdmList;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_JHXZDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        td_jhxzdmList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_jhxzdmList==null){
            return null;
        }
        else {
            return td_jhxzdmList;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_KLDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_KLDM> insertTD_KLDM(InputStream TD_KLDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        List<TD_KLDM> td_kldmList=new ArrayList<TD_KLDM>();
        DataFrame dataFrame = DbfPip.open(TD_KLDMFile);
        if(dataFrame.isEmpty())
        {
            return td_kldmList;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_KLDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        td_kldmList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_kldmList==null){
            return null;
        }
        else {
            return td_kldmList;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_KSLBDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_KSLBDM> insertTD_KSLBDM(InputStream TD_KSLBDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        List<TD_KSLBDM> td_kslbdmList=new ArrayList<TD_KSLBDM>();
        DataFrame dataFrame = DbfPip.open(TD_KSLBDMFile);
        if(dataFrame.isEmpty())
        {
            return td_kslbdmList;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_KSLBDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        td_kslbdmList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_kslbdmList==null){
            return null;
        }
        else {
            return td_kslbdmList;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_KSLXDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_KSLXDM> insertTD_KSLXDM(InputStream TD_KSLXDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        List<TD_KSLXDM> td_kslxdmList=new ArrayList<>();
        DataFrame dataFrame = DbfPip.open(TD_KSLXDMFile);
        if(dataFrame.isEmpty())
        {
            return td_kslxdmList;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_KSLXDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        td_kslxdmList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_kslxdmList==null){
            return null;
        }
        else {
            return td_kslxdmList;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_LQFSDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_LQFSDM> insertTD_LQFSDM(InputStream TD_LQFSDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        List<TD_LQFSDM> td_lqfsdmList=new ArrayList<>();
        DataFrame dataFrame = DbfPip.open(TD_LQFSDMFile);
        if(dataFrame.isEmpty())
        {
            return td_lqfsdmList;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_LQFSDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        td_lqfsdmList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_lqfsdmList==null){
            return null;
        }
        else {
            return td_lqfsdmList;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_MZDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_MZDM> insertTD_MZDM(InputStream TD_MZDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        List<TD_MZDM> td_mzdmList=new ArrayList<TD_MZDM>();
        DataFrame dataFrame = DbfPip.open(TD_MZDMFile);
        if(dataFrame.isEmpty())
        {
            return td_mzdmList;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_MZDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        td_mzdmList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_mzdmList==null){
            return null;
        }
        else {
            return td_mzdmList;
        }
    }

    /**
     * Modification User： 吕志伟
     * Modification Date: 2019/10/31
     *
     *
     * @Author 吕志伟
     * @param: TD_PCDMFile
     * @param: year
     * @param: province
     * @return
     */
    @Override
    public List<TD_PCDM> insertTD_PCDM(InputStream TD_PCDMFile, int year, int province) {
        //1，讲数据变成dataframe类型
        List<TD_PCDM> td_pcdmList=new ArrayList<TD_PCDM>();
        DataFrame dataFrame = DbfPip.open(TD_PCDMFile);
        if(dataFrame.isEmpty())
        {
            return td_pcdmList;
        }
        //2，添加数据

        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 0; i <dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        //转换成List
        Class clazz = new TD_PCDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        td_pcdmList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_pcdmList==null){
            return null;
        }
        else {
            return td_pcdmList;
        }
    }

    /**
     * Modification User： 邓聪
     * Modification Date: 2019/10/29
     *
     * 导入TD_WYYZDM表数据
     * @Author 邓聪
     * @param: TD_WYYZDMFile InputStream类型的TD_WYYZDM.dbf数据
     * @param: year 年份
     * @param: province 省份
     * @return TD_WYYZDM的实体list集合，如果出错返回null
     */
    @Override
    public List<TD_WYYZDM> importTD_WYYZDMTable(InputStream TD_WYYZDMFile, int year, int province) {
        DataFrame dataFrame = DbfPip.open(TD_WYYZDMFile);
        List<TD_WYYZDM> td_wyyzdmList=new ArrayList<TD_WYYZDM>();
        if (dataFrame.isEmpty()){
            return td_wyyzdmList;
        }
        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 1; i <= dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        Set<String> excludeSet = new HashSet<>();
        excludeSet.add("ID");
        Class pojo = new TD_WYYZDM().getClass();
        td_wyyzdmList = DataFrameUtils.fillListByDefault(dataFrame, pojo, excludeSet);
        if (td_wyyzdmList == null){
            return null;
        }
        else {
            return td_wyyzdmList;
        }
    }

    /**
     * Modification User： 邓聪
     * Modification Date: 2019/10/29
     *
     * 导入TD_ZCDM.dbf数据
     * @Author 邓聪
     * @param province     省份代码
     * @param TD_ZCDMFile InputStearm 类型的TD_ZCDM.dbf数据
     * @param year        年份
     * @return TD_ZCDM的实体list集合，如果出错返回null
     */
    @Override
    public List<TD_ZCDM> importTD_ZCDMTable(InputStream TD_ZCDMFile, int year, int province) {
        DataFrame dataFrame = DbfPip.open(TD_ZCDMFile);
        List<TD_ZCDM> td_zcdmList=new ArrayList<>();
        if (dataFrame.isEmpty()){
            return td_zcdmList;
        }
        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 1; i <= dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        Set<String> excludeSet = new HashSet<>();
        excludeSet.add("ID");
        Class pojo = new TD_ZCDM().getClass();
        td_zcdmList = DataFrameUtils.fillListByDefault(dataFrame, pojo, excludeSet);
        if (td_zcdmList == null){
            return null;
        }
        else {
            return td_zcdmList;
        }
    }

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/10/31
     *
     *
     * @author 邓聪
     * @param TD_ZYTZDMFile 文件
     * @param year 年份默认本年
     * @param province 省份
     * @return  返回实体对象的集合，如果错返回null
     */
    @Override
    public List<TD_ZYTZDM> importTD_ZYTZDMTable(InputStream TD_ZYTZDMFile, int year, int province) {
        DataFrame dataFrame = DbfPip.open(TD_ZYTZDMFile);
        List<TD_ZYTZDM> td_zytzdmList=new ArrayList<TD_ZYTZDM>();
        if (dataFrame.isEmpty()){
            return td_zytzdmList;
        }
        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 1; i <= dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        Set<String> excludeSet = new HashSet<>();
        excludeSet.add("ID");
        Class pojo = new TD_ZYTZDM().getClass();
        td_zytzdmList = DataFrameUtils.fillListByDefault(dataFrame, pojo, excludeSet);
        if (td_zytzdmList == null){
            return null;
        }
        else {
            return td_zytzdmList;
        }
    }

    /**
     * Modification User： 邓聪
     * Modification Date: 2019/10/31
     *
     * 导入TD_ZZMMDM.dbf数据
     * @author 邓聪
     * @param TD_ZZMMDMFile 文件
     * @param year 年份默认本年
     * @param province 省份代码，默认四川1
     * @return 成功为实体对象list，失败null
     */
    @Override
    public List<TD_ZZMMDM> importTD_ZZMMDMTable(InputStream TD_ZZMMDMFile, int year, int province) {
        DataFrame dataFrame = DbfPip.open(TD_ZZMMDMFile);
        List<TD_ZZMMDM> td_zzmmdmList=new ArrayList<TD_ZZMMDM>();
        if (dataFrame.isEmpty()){
            return td_zzmmdmList;
        }
        List<Integer> yearList = new ArrayList<Integer>();
        List<Integer> provinceList = new ArrayList<Integer>();
        for (int i = 1; i <= dataFrame.length() ; i++) {
            yearList.add(year);
            provinceList.add(province);
        }
        dataFrame.add("NF",yearList);
        dataFrame.add("SFDM",provinceList);
        Set<String> excludeSet = new HashSet<>();
        excludeSet.add("ID");
        Class pojo = new TD_ZZMMDM().getClass();
        td_zzmmdmList = DataFrameUtils.fillListByDefault(dataFrame, pojo, excludeSet);
        if (td_zzmmdmList == null){
            return null;
        }
        else {
            return td_zzmmdmList;
        }
    }
    /**
     * Modification User: 邓聪
     * Modification Date: 2019/11/4
     *
     * 获取数据库中是否有数据
     * @author 邓聪
     * @param year 年份， 默认是当年
     * @param province 省份，默认为
     * @return 1表示数据表中有数据，0表示数据表没有数据
     */
    @Override
    public String getTableStatus(String year, String province) throws JsonProcessingException {
        // 先判断年份和省份是否为空，为空使用默认的数据填充
        year = GetDefaultInfo.getCurYear(year);
        province = GetDefaultInfo.getCurProvince(province);
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        List<String> tableName=new ArrayList<>();
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
        map.put("tableName",tableName);
        String result = "";
        int proDm = Integer.parseInt(province);
        // 根据这两个条件进行查表，返回值使用map存储，这里只写了一个，其他的类似。
        int  count = dataImportDao.selectT_TDD(year, proDm);
        if (count >= 1){
            map.put("tableStatus", 1);
            result = mapper.writeValueAsString(map);
            return result;
        }
        map.put("tableStatus", 0);
        result = mapper.writeValueAsString(map);
        return result;
    }

    /**
     * Modification User: 邓聪
     * Modification Date: 2019/11/4
     *
     * 获取所有省信息，包括代码
     * @return json 对象list 形如：[{"SFDM":1,"SF":"北京市"}, {...}]
     */
    @Override
    public List<TD_SFDM> getProvincesStatus() {
        // 数据库返回的数据类似：[TD_SFDM@68{"SFDM":1,"SF":"北京市"}]
        List<TD_SFDM> returnList = dataImportDao.requestTD_SFDM();
        if (returnList == null){
            return null;
        }
        return returnList;
    }

    /*吕志伟*/

    /**
     * Modification User: 吕志伟
     * Modification Date: 2019/11/24
     *
     * 检查有无未完成导入流程的省份
     * @author 吕志伟
     * @param year 年份， 默认是当年
     * @return 0//无未完成导入省份；1//未完成省份进行到数据清洗；2//未完成省份进行到分数整合
     */
    @Override
    public String checkData(String year) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap map = new HashMap();
        List<String> sf = new ArrayList();
        //第一个Integer是省份代码，String是省份名字,第二个Integer是状态码
        List<HashMap<String,Object>> list = dataImportDao.request1(year);
        for(HashMap m: list){
            String name =(String) m.get("SF");
//            int dm = (int) m.get("SFDM");
//            int status =(int) m.get("STATUS");
//            if (status == 0){
                sf.add(name);
//            }
        }
        HashMap map1 = new HashMap();
        map1.put("unfinished",sf);
        //这儿返回值是未完成完整导入流程的省份名
        String result = mapper.writeValueAsString(map1);
        return  result;
    }

    /**
     * Modification User: 吕志伟
     * Modification Date: 2019/11/24
     *
     * @param province
     * @return 0//清空失败；1//清空成功
     * @throws JsonProcessingException
     */
    @Override
    public String clearData(String year,String province) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map map = new HashMap();
        int pro = Integer.parseInt(province);
        int res = dataImportDao.deleteProvince(year,pro);
        if(res == 1) {
            map.put("status","1");
        }
        else{
            map.put("status","0");
        }
        String result = mapper.writeValueAsString(map);
        return result;
    }

    /**
     * Modification User: 吕志伟
     * Modification Date: 2019/11/24
     *
     * @param year
     * @return json {[ "imported":list,
     * "unimport":list]}
     * @throws JsonProcessingException
     */
    @Override
    public String checkProvince(String year) throws JsonProcessingException {
        List<String> provinces = new ArrayList();
        List<String> importedprovince = new ArrayList();
        provinces.add("四川省");
        provinces.add("北京市");
        provinces.add("天津市");
        provinces.add("内蒙古自治区");
        provinces.add("吉林省");
        provinces.add("上海市");
        provinces.add("山东省");
        provinces.add("广东省");
        provinces.add("安徽省");
        provinces.add("福建省");
        provinces.add("甘肃省");
        provinces.add("广西壮族自治区");
        provinces.add("贵州省");
        provinces.add("海南省");
        provinces.add("河北省");
        provinces.add("河南省");
        provinces.add("黑龙江省");
        provinces.add("湖北省");
        provinces.add("湖南省");
        provinces.add("江苏省");
        provinces.add("江西省");
        provinces.add("辽宁省");
        provinces.add("山西省");
        provinces.add("陕西省");
        provinces.add("云南省");
        provinces.add("浙江省");
        provinces.add("西藏自治区");
        provinces.add("青海省");
        provinces.add("宁夏回族自治区");
        provinces.add("新疆维吾尔自治区");
        provinces.add("香港特别行政区");
        provinces.add("澳门特别行政区");
        provinces.add("台湾省");
        provinces.add("重庆市");
        ObjectMapper mapper = new ObjectMapper();
        Map map = new HashMap();
        List<HashMap> imported = (List) dataImportDao.CheckProvince(year);
        String name = "";
        for(HashMap m: imported){
            name= (String) m.get("SF");
            provinces.remove(name);
            importedprovince.add(name);
        }
        map.put("unimport",provinces);
        map.put("imported",importedprovince);
        String result = mapper.writeValueAsString(map);
        return result;
    }


//
//    /**
//     * Modification User: 吕志伟
//     * Modification Date: 2019/11/24
//     * @param dm 规范值代码
//     * @param value 规范值
//     * @return 0//修改失败；1//修改成功
//     * @throws JsonProcessingException
//     */
//    @Override
//    public String updateGaugeValue(String dm,String value) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        Map map = new HashMap();
//        map.put("status",dataImportDao.updateGaugeValue(dm,value));
//        String result = mapper.writeValueAsString(map);
//        return  result;
//    }
//
//    @Override
//    public String addGaugeValue(String data) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        Map map = new HashMap();
//        map.put("status",dataImportDao.addGaugeValue(data));
//        String result = mapper.writeValueAsString(map);
//        return result;
//    }
}
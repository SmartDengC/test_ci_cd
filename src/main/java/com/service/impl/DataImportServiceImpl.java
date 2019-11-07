package com.service.impl;

/**
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
import joinery.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.utils.datadriverutils.DbfPip;
import com.utils.datautils.DataFrameUtils;
import org.springframework.transaction.annotation.Transactional;
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
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
@Service
public class DataImportServiceImpl implements DataImportService {

    @Autowired
    DataImportDao dataImportDao;

    /**陈泯全
     * 实现以下dbf数据的导入:
     * T_TDD、TD_CJXDM(这个表只是用到了信息，不需要导入数据库)
     * TD_BYLBDM、TD_DQDM、TD_JHXZDM、TD_KLDM、TD_KSLBDM、TD_KSLXDM、TD_LQFSDM、
     * TD_MZDM、TD_PCDM、TD_WYYZDM、TD_ZCDM、TD_ZYTZDM、TD_ZZMMDM、TD_QBJHK
     * @param dbfMap dbf文件的InputStream格式
     * @param year 年份
     * @param province 省份
     * @return 导入结果情况，1代表成功；0代表失败或表中没有数据
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int importData (HashMap<String,InputStream> dbfMap, int year, int province) throws Exception{
        /**
         * 先处理特殊的T_TDD与TD_XSFS表
         */
        DataFrame T_TDDDataFrame = DbfPip.open(dbfMap.get("T_TDD"));
        //找到T_TDD表中表示特定分数的字段，并将这个字段映射到分数表的字段上
        DataFrame TD_CJXDMdataFrame = DbfPip.open(dbfMap.get("TD_CJXDM"));
        HashMap<String ,String> colmnMap = new HashMap<String, String>();
        if(TD_CJXDMdataFrame.isEmpty())
        {
            System.out.println("提取成绩对照表：TD_CJXDMFile，出错！表是空的！");
            return 0;
        }
        else
        {
            for(int i = 0; i <TD_CJXDMdataFrame.length() ; i++)
            {
                String CJXDM = null;
                String CJXMC = null;
                if(TD_CJXDMdataFrame.get(i,"CJXDM")!=null && TD_CJXDMdataFrame.get(i,"CJXMC")!=null){
                    CJXDM="GKCJX"+TD_CJXDMdataFrame.get(i,"CJXDM").toString().replaceAll(" ","");
                    CJXMC=TD_CJXDMdataFrame.get(i,"CJXMC").toString().replaceAll(" ","");

                }
                switch (CJXMC){
                    case "语文":
                        colmnMap.put("YWCJ",CJXDM);
                        break;
                    case "数学":
                        colmnMap.put("SXCJ",CJXDM);
                        break;
                    case "外语":
                        colmnMap.put("WYCJ",CJXDM);
                        break;
                    case "综合":
                        colmnMap.put("ZHCJ",CJXDM);
                    default:
                        break;
                }
                if(colmnMap.size()==4){
                    break;
                }
            }
        }
        if(colmnMap.size() != 4){
            System.out.println("提取成绩对照表：TD_CJXDMFile，出错！配置多了或少了字段映射！");
            return 0;
        }
        /**
         * 提取XSFS的信息并将它变成List<obj>
         * 将学生分数信息保存到数据库,添加年份，省份等代码
         */
        List<TD_XSFS> td_xsfsList =new ArrayList<TD_XSFS>();

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
        int insertTd_xsfsDataResult =0;
        //在这执行插入数据库功能，并获得主键值
        if(td_xsfsList != null){
            insertTd_xsfsDataResult = dataImportDao.insertTD_XSFS(td_xsfsList);
        }
        else {
            System.out.println("将T_TDDDataFrame的数据转换到TD_XSFS实体出错！");
            return 0;
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
                    }else {
                        dataImportDao.insertTD_QBJHK(td_qbjhkList);
                    }
                    break;
                }
                case"TD_BYLBDM":{
                    List<TD_BYLBDM> td_bylbdmList =this.importTD_BYLBDMTable(dbfMap.get("TD_BYLBDM"),year,province);
                    if(td_bylbdmList==null){
                        System.out.println("导入TD_BYLBDM出错！");
                        throw new Exception();
                    }else{
                        dataImportDao.insertTD_BYLBDM(td_bylbdmList);
                    }
                    break;
                }
                case "TD_DQDM":{
                    List<TD_DQDM> td_dqdmList =this.insertTD_DQDM(dbfMap.get("TD_DQDM"),year,province);
                    if(td_dqdmList==null){
                        System.out.println("导入TD_DQDM出错！");
                        throw new Exception();
                    }else {
                        dataImportDao.insertTD_DQDM(td_dqdmList);
                    }
                    break;
                }
                case "TD_JHXZDM":{
                    List<TD_JHXZDM> td_jhxzdmList =this.insertTD_JHXZDM(dbfMap.get("TD_JHXZDM"),year,province);
                    if(td_jhxzdmList==null){
                        System.out.println("导入TD_JHXZDM出错！");
                        throw new Exception();
                    }else {
                        dataImportDao.insertTD_JHXZDM(td_jhxzdmList);
                    }
                    break;
                }
                case "TD_KLDM":{
                    List<TD_KLDM> td_kldmList =this.insertTD_KLDM(dbfMap.get("TD_KLDM"),year,province);
                    if(td_kldmList==null){
                        System.out.println("导入TD_KLDM出错！");
                        throw new Exception();
                    }else {
                        dataImportDao.insertTD_KLDM(td_kldmList);
                    }
                    break;
                }
                case "TD_KSLBDM":{
                    List<TD_KSLBDM> td_kslbdmList =this.insertTD_KSLBDM(dbfMap.get("TD_KSLBDM"),year,province);
                    if(td_kslbdmList==null){
                        System.out.println("导入TD_KSLBDM出错！");
                        throw new Exception();
                    }else {
                        dataImportDao.insertTD_KSLBDM(td_kslbdmList);
                    }
                    break;
                }
                case "TD_KSLXDM":{
                    List<TD_KSLXDM> td_kslxdmList =this.insertTD_KSLXDM(dbfMap.get("TD_KSLXDM"),year,province);
                    if(td_kslxdmList==null){
                        System.out.println("导入TD_KSLXDM出错！");
                        throw new Exception();
                    }else {
                        dataImportDao.insertTD_KSLXDM(td_kslxdmList);
                    }
                    break;
                }
                case "TD_LQFSDM":{
                    List<TD_LQFSDM> td_lqfsdmList =this.insertTD_LQFSDM(dbfMap.get("TD_LQFSDM"),year,province);
                    if(td_lqfsdmList==null){
                        System.out.println("导入TD_LQFSDM出错！");
                        throw new Exception();
                    }else {
                        dataImportDao.insertTD_LQFSDM(td_lqfsdmList);
                    }
                    break;
                }
                case "TD_MZDM":{
                    List<TD_MZDM> td_mzdmList =this.insertTD_MZDM(dbfMap.get("TD_MZDM"),year,province);
                    if(td_mzdmList==null){
                        System.out.println("导入TD_MZDM出错！");
                        throw new Exception();
                    }else {
                        dataImportDao.insertTD_MZDM(td_mzdmList);
                    }
                    break;
                }
                case "TD_PCDM":{
                    List<TD_PCDM> td_pcdmList =this.insertTD_PCDM(dbfMap.get("TD_PCDM"),year,province);
                    if(td_pcdmList==null){
                        System.out.println("导入TD_PCDM出错！");
                        throw new Exception();
                    }else {
                        dataImportDao.insertTD_PCDM(td_pcdmList);
                    }
                    break;
                }
                case "TD_WYYZDM":{
                    List<TD_WYYZDM> td_wyyzdmList =this.importTD_WYYZDMTable(dbfMap.get("TD_WYYZDM"),year,province);
                    if(td_wyyzdmList==null){
                        System.out.println("导入TD_WYYZDM出错！");
                        throw new Exception();
                    }else {
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
                    }else {
                        dataImportDao.insertTD_ZYTZDM(td_zytzdmList);
                    }
                    break;
                }
                case "TD_ZZMMDM":{
                    List<TD_ZZMMDM> td_zzmmdmList =this.importTD_ZZMMDMTable(dbfMap.get("TD_ZZMMDM"),year,province);
                    if(td_zzmmdmList==null){
                        System.out.println("导入TD_ZZMMDM出错！");
                        throw new Exception();
                    }else {
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
        return 1;
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
        DataFrame dataFrame = DbfPip.open(T_QBJHKFile);
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
        Class clazz = new TD_QBJHK().getClass();
        Set<String> excludeSet =new  HashSet<String>();
        excludeSet.add("ID");
        List<TD_QBJHK> td_qbjhksList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
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
        DataFrame dataFrame = DbfPip.open(TD_BYLBDMFile);
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
        Class clazz = new TD_BYLBDM().getClass();
        Set<String> excludeSet =new  HashSet<String>();
        excludeSet.add("ID");
        List<TD_BYLBDM> td_bylbdmList = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
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
        DataFrame dataFrame = DbfPip.open(TD_DQDMFile);
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
        Class clazz = new TD_DQDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_DQDM> td_dqdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_dqdms==null){
            return null;
        }
        else {
            return td_dqdms;
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
        DataFrame dataFrame = DbfPip.open(TD_JHXZDMFile);
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
        Class clazz = new TD_JHXZDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_JHXZDM> td_jhxzdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_jhxzdms==null){
            return null;
        }
        else {
            return td_jhxzdms;
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
        DataFrame dataFrame = DbfPip.open(TD_KLDMFile);
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
        Class clazz = new TD_KLDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_KLDM> td_kldms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_kldms==null){
            return null;
        }
        else {
            return td_kldms;
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
        DataFrame dataFrame = DbfPip.open(TD_KSLBDMFile);
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
        Class clazz = new TD_KSLBDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_KSLBDM> td_kslbdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_kslbdms==null){
            return null;
        }
        else {
            return td_kslbdms;
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
        DataFrame dataFrame = DbfPip.open(TD_KSLXDMFile);
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
        Class clazz = new TD_KSLXDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_KSLXDM> td_kslxdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_kslxdms==null){
            return null;
        }
        else {
            return td_kslxdms;
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
        DataFrame dataFrame = DbfPip.open(TD_LQFSDMFile);
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
        Class clazz = new TD_LQFSDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_LQFSDM> td_lqfsdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_lqfsdms==null){
            return null;
        }
        else {
            return td_lqfsdms;
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
        DataFrame dataFrame = DbfPip.open(TD_MZDMFile);
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
        Class clazz = new TD_MZDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_MZDM> td_mzdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_mzdms==null){
            return null;
        }
        else {
            return td_mzdms;
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
        DataFrame dataFrame = DbfPip.open(TD_PCDMFile);
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
        Class clazz = new TD_PCDM().getClass();
        Set<String> excludeSet =new HashSet<String>();
        excludeSet.add("ID");
        List<TD_PCDM> td_pcdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
        if (td_pcdms==null){
            return null;
        }
        else {
            return td_pcdms;
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
        if (dataFrame.isEmpty()){
            return null;
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
        List<TD_WYYZDM> returnList = DataFrameUtils.fillListByDefault(dataFrame, pojo, excludeSet);
        if (returnList == null){
            return null;
        }
        else {
            return returnList;
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
        if (dataFrame.isEmpty()){
            return null;
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
        List<TD_ZCDM> returnList = DataFrameUtils.fillListByDefault(dataFrame, pojo, excludeSet);
        if (returnList == null){
            return null;
        }
        else {
            return returnList;
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
        if (dataFrame.isEmpty()){
            return null;
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
        List<TD_ZYTZDM> returnList = DataFrameUtils.fillListByDefault(dataFrame, pojo, excludeSet);
        if (returnList == null){
            return null;
        }
        else {
            return returnList;
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
        if (dataFrame.isEmpty()){
            return null;
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
        List<TD_ZZMMDM> returnList = DataFrameUtils.fillListByDefault(dataFrame, pojo, excludeSet);
        if (returnList == null){
            return null;
        }
        else {
            return returnList;
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
        String result = "";
        int proDm = Integer.parseInt(province);
        // 根据这两个条件进行查表，返回值使用map存储，这里只写了一个，其他的类似。
        int  count = dataImportDao.selectT_TDD(year, proDm);
        if (count >= 1){
            map.put("T_TDD", 1);
            result = mapper.writeValueAsString(map);
            return result;
        }
        map.put("T_TDD", 0);
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

}

//import com.dao.DataImportDao;
//import com.pojo.*;
//import com.service.DataImportService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import joinery.DataFrame;
//import com.utils.datautils.DataFrameUtils;
//import com.utils.datadriverutils.DbfPip;
//
///**
// * @ClassName ImportDataServiceImpl
// * @Version 1.0
// * @Author 吕志伟
// * @Date 2019/10/25 17:01
// * @Description 处理dbf数据业务的实现类，实现dbf文件到实体list类型的转化
// * Modification User： 程序修改时由修改人员编写
// * Modification Date： 程序修改时间
// */
//
//@Service
//public class DataImportServiceImpl implements DataImportService {
//
//    @Autowired
//    DataImportDao dataImportDao;
//
//    /**
//     * Modification User： 吕志伟
//     * Modification Date: 2019/10/31
//     *
//     *
//     * @Author 吕志伟
//     * @param: TD_CJXDMFile
//     * @param: year
//     * @param: province
//     * @return
//     */
//    @Override
//    public List<TD_CJXDM> insertTD_CJXDM(InputStream TD_CJXDMFile, int year, int province) {
//        //1，讲数据变成dataframe类型
//        DataFrame dataFrame = DbfPip.open(TD_CJXDMFile);
//        if(dataFrame.isEmpty())
//        {
//            return null;
//        }
//        //2，添加数据
//
//        List<Integer> yearList = new ArrayList<Integer>();
//        List<Integer> provinceList = new ArrayList<Integer>();
//        for (int i = 0; i <dataFrame.length() ; i++) {
//            yearList.add(year);
//            provinceList.add(province);
//        }
//        dataFrame.add("NF",yearList);
//        dataFrame.add("SFDM",provinceList);
//        //转换成List
//        Class clazz = new TD_CJXDM().getClass();
//        Set<String> excludeSet =new HashSet<String>();
//        excludeSet.add("ID");
//        List<TD_CJXDM> td_cjxdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
//        if (td_cjxdms==null){
//            return null;
//        }
//        else {
//            return td_cjxdms;
//        }
//    }
//
//    /**
//     * Modification User： 吕志伟
//     * Modification Date: 2019/10/31
//     *
//     *
//     * @Author 吕志伟
//     * @param: TD_DQDMFile
//     * @param: year
//     * @param: province
//     * @return
//     */
//    @Override
//    public List<TD_DQDM> insertTD_DQDM(InputStream TD_DQDMFile, int year, int province) {
//        //1，讲数据变成dataframe类型
//        DataFrame dataFrame = DbfPip.open(TD_DQDMFile);
//        if(dataFrame.isEmpty())
//        {
//            return null;
//        }
//        //2，添加数据
//
//        List<Integer> yearList = new ArrayList<Integer>();
//        List<Integer> provinceList = new ArrayList<Integer>();
//        for (int i = 0; i <dataFrame.length() ; i++) {
//            yearList.add(year);
//            provinceList.add(province);
//        }
//        dataFrame.add("NF",yearList);
//        dataFrame.add("SFDM",provinceList);
//        //转换成List
//        Class clazz = new TD_DQDM().getClass();
//        Set<String> excludeSet =new HashSet<String>();
//        excludeSet.add("ID");
//        List<TD_DQDM> td_dqdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
//        if (td_dqdms==null){
//            return null;
//        }
//        else {
//            return td_dqdms;
//        }
//    }
//
//    /**
//     * Modification User： 吕志伟
//     * Modification Date: 2019/10/31
//     *
//     *
//     * @Author 吕志伟
//     * @param: TD_JHXZDMFile
//     * @param: year
//     * @param: province
//     * @return
//     */
//    @Override
//    public List<TD_JHXZDM> insertTD_JHXZDM(InputStream TD_JHXZDMFile, int year, int province) {
//        //1，讲数据变成dataframe类型
//        DataFrame dataFrame = DbfPip.open(TD_JHXZDMFile);
//        if(dataFrame.isEmpty())
//        {
//            return null;
//        }
//        //2，添加数据
//
//        List<Integer> yearList = new ArrayList<Integer>();
//        List<Integer> provinceList = new ArrayList<Integer>();
//        for (int i = 0; i <dataFrame.length() ; i++) {
//            yearList.add(year);
//            provinceList.add(province);
//        }
//        dataFrame.add("NF",yearList);
//        dataFrame.add("SFDM",provinceList);
//        //转换成List
//        Class clazz = new TD_JHXZDM().getClass();
//        Set<String> excludeSet =new HashSet<String>();
//        excludeSet.add("ID");
//        List<TD_JHXZDM> td_jhxzdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
//        if (td_jhxzdms==null){
//            return null;
//        }
//        else {
//            return td_jhxzdms;
//        }
//    }
//
//    /**
//     * Modification User： 吕志伟
//     * Modification Date: 2019/10/31
//     *
//     *
//     * @Author 吕志伟
//     * @param: TD_KLDMFile
//     * @param: year
//     * @param: province
//     * @return
//     */
//    @Override
//    public List<TD_KLDM> insertTD_KLDM(InputStream TD_KLDMFile, int year, int province) {
//        //1，讲数据变成dataframe类型
//        DataFrame dataFrame = DbfPip.open(TD_KLDMFile);
//        if(dataFrame.isEmpty())
//        {
//            return null;
//        }
//        //2，添加数据
//
//        List<Integer> yearList = new ArrayList<Integer>();
//        List<Integer> provinceList = new ArrayList<Integer>();
//        for (int i = 0; i <dataFrame.length() ; i++) {
//            yearList.add(year);
//            provinceList.add(province);
//        }
//        dataFrame.add("NF",yearList);
//        dataFrame.add("SFDM",provinceList);
//        //转换成List
//        Class clazz = new TD_KLDM().getClass();
//        Set<String> excludeSet =new HashSet<String>();
//        excludeSet.add("ID");
//        List<TD_KLDM> td_kldms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
//        if (td_kldms==null){
//            return null;
//        }
//        else {
//            return td_kldms;
//        }
//    }
//
//    /**
//     * Modification User： 吕志伟
//     * Modification Date: 2019/10/31
//     *
//     *
//     * @Author 吕志伟
//     * @param: TD_KSLBDMFile
//     * @param: year
//     * @param: province
//     * @return
//     */
//    @Override
//    public List<TD_KSLBDM> insertTD_KSLBDM(InputStream TD_KSLBDMFile, int year, int province) {
//        //1，讲数据变成dataframe类型
//        DataFrame dataFrame = DbfPip.open(TD_KSLBDMFile);
//        if(dataFrame.isEmpty())
//        {
//            return null;
//        }
//        //2，添加数据
//
//        List<Integer> yearList = new ArrayList<Integer>();
//        List<Integer> provinceList = new ArrayList<Integer>();
//        for (int i = 0; i <dataFrame.length() ; i++) {
//            yearList.add(year);
//            provinceList.add(province);
//        }
//        dataFrame.add("NF",yearList);
//        dataFrame.add("SFDM",provinceList);
//        //转换成List
//        Class clazz = new TD_KSLBDM().getClass();
//        Set<String> excludeSet =new HashSet<String>();
//        excludeSet.add("ID");
//        List<TD_KSLBDM> td_kslbdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
//        if (td_kslbdms==null){
//            return null;
//        }
//        else {
//            return td_kslbdms;
//        }
//    }
//
//    /**
//     * Modification User： 吕志伟
//     * Modification Date: 2019/10/31
//     *
//     *
//     * @Author 吕志伟
//     * @param: TD_KSLXDMFile
//     * @param: year
//     * @param: province
//     * @return
//     */
//    @Override
//    public List<TD_KSLXDM> insertTD_KSLXDM(InputStream TD_KSLXDMFile, int year, int province) {
//        //1，讲数据变成dataframe类型
//        DataFrame dataFrame = DbfPip.open(TD_KSLXDMFile);
//        if(dataFrame.isEmpty())
//        {
//            return null;
//        }
//        //2，添加数据
//
//        List<Integer> yearList = new ArrayList<Integer>();
//        List<Integer> provinceList = new ArrayList<Integer>();
//        for (int i = 0; i <dataFrame.length() ; i++) {
//            yearList.add(year);
//            provinceList.add(province);
//        }
//        dataFrame.add("NF",yearList);
//        dataFrame.add("SFDM",provinceList);
//        //转换成List
//        Class clazz = new TD_KSLXDM().getClass();
//        Set<String> excludeSet =new HashSet<String>();
//        excludeSet.add("ID");
//        List<TD_KSLXDM> td_kslxdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
//        if (td_kslxdms==null){
//            return null;
//        }
//        else {
//            return td_kslxdms;
//        }
//    }
//
//    /**
//     * Modification User： 吕志伟
//     * Modification Date: 2019/10/31
//     *
//     *
//     * @Author 吕志伟
//     * @param: TD_LQFSDMFile
//     * @param: year
//     * @param: province
//     * @return
//     */
//    @Override
//    public List<TD_LQFSDM> insertTD_LQFSDM(InputStream TD_LQFSDMFile, int year, int province) {
//        //1，讲数据变成dataframe类型
//        DataFrame dataFrame = DbfPip.open(TD_LQFSDMFile);
//        if(dataFrame.isEmpty())
//        {
//            return null;
//        }
//        //2，添加数据
//
//        List<Integer> yearList = new ArrayList<Integer>();
//        List<Integer> provinceList = new ArrayList<Integer>();
//        for (int i = 0; i <dataFrame.length() ; i++) {
//            yearList.add(year);
//            provinceList.add(province);
//        }
//        dataFrame.add("NF",yearList);
//        dataFrame.add("SFDM",provinceList);
//        //转换成List
//        Class clazz = new TD_LQFSDM().getClass();
//        Set<String> excludeSet =new HashSet<String>();
//        excludeSet.add("ID");
//        List<TD_LQFSDM> td_lqfsdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
//        if (td_lqfsdms==null){
//            return null;
//        }
//        else {
//            return td_lqfsdms;
//        }
//    }
//
//    /**
//     * Modification User： 吕志伟
//     * Modification Date: 2019/10/31
//     *
//     *
//     * @Author 吕志伟
//     * @param: TD_MZDMFile
//     * @param: year
//     * @param: province
//     * @return
//     */
//    @Override
//    public List<TD_MZDM> insertTD_MZDM(InputStream TD_MZDMFile, int year, int province) {
//        //1，讲数据变成dataframe类型
//        DataFrame dataFrame = DbfPip.open(TD_MZDMFile);
//        if(dataFrame.isEmpty())
//        {
//            return null;
//        }
//        //2，添加数据
//
//        List<Integer> yearList = new ArrayList<Integer>();
//        List<Integer> provinceList = new ArrayList<Integer>();
//        for (int i = 0; i <dataFrame.length() ; i++) {
//            yearList.add(year);
//            provinceList.add(province);
//        }
//        dataFrame.add("NF",yearList);
//        dataFrame.add("SFDM",provinceList);
//        //转换成List
//        Class clazz = new TD_MZDM().getClass();
//        Set<String> excludeSet =new HashSet<String>();
//        excludeSet.add("ID");
//        List<TD_MZDM> td_mzdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
//        if (td_mzdms==null){
//            return null;
//        }
//        else {
//            return td_mzdms;
//        }
//    }
//
//    /**
//     * Modification User： 吕志伟
//     * Modification Date: 2019/10/31
//     *
//     *
//     * @Author 吕志伟
//     * @param: TD_PCDMFile
//     * @param: year
//     * @param: province
//     * @return
//     */
//    @Override
//    public List<TD_PCDM> insertTD_PCDM(InputStream TD_PCDMFile, int year, int province) {
//        //1，讲数据变成dataframe类型
//        DataFrame dataFrame = DbfPip.open(TD_PCDMFile);
//        if(dataFrame.isEmpty())
//        {
//            return null;
//        }
//        //2，添加数据
//
//        List<Integer> yearList = new ArrayList<Integer>();
//        List<Integer> provinceList = new ArrayList<Integer>();
//        for (int i = 0; i <dataFrame.length() ; i++) {
//            yearList.add(year);
//            provinceList.add(province);
//        }
//        dataFrame.add("NF",yearList);
//        dataFrame.add("SFDM",provinceList);
//        //转换成List
//        Class clazz = new TD_PCDM().getClass();
//        Set<String> excludeSet =new HashSet<String>();
//        excludeSet.add("ID");
//        List<TD_PCDM> td_pcdms = DataFrameUtils.fillListByDefault(dataFrame,clazz,excludeSet);
//        if (td_pcdms==null){
//            return null;
//        }
//        else {
//            return td_pcdms;
//        }
//    }
//}
package com.dao;

import com.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName DataImportDao
 * @Author Rose
 * @Time 2019/10/31 23:17
 * @Description TODO
 */
public interface DataImportDao {
    /**向表T_TDD查询数据
     * @param year 年份
     * @param province 省份代码 （整型）
     * @return 返回条数
     */
    public int selectT_TDD(@Param("NF") String year, @Param("SFDM") int province);

    /**
     * 查询T_TDD表信息
     * @return 返回T_TDD的实体类，用list包装
     */
    public List<T_TDD> requestT_TDD();

    /**
     * 向T_TDD中插入数据   ！！暂时无法成功插入
     * @param list 插入的内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertT_TDD(List<T_TDD> list);

    /**
     * 向表TD-CJXDM中插入数据
     * @param list
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_CJXDM(List<TD_CJXDM> list);

    /**
     * 向TD_XSFS表插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_XSFS(List<TD_XSFS> list);

    /**
     * 向TD_BYLBDM表中插数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_BYLBDM(List<TD_BYLBDM> list);

    /**
     * 向TD_DQDM中插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_DQDM(List<TD_DQDM> list);

    /**
     * 向TD_JHXZDM表插入数据
     * @param list 保存内存
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_JHXZDM(List<TD_JHXZDM> list);

    /**
     * 向TD_KLDM表插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_KLDM(List<TD_KLDM> list);

    /**
     * 向TD_KSLBDM表插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_KSLBDM(List<TD_KSLBDM> list);

    /**
     * 向TD_KSLXDM表插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_KSLXDM(List<TD_KSLXDM> list);

    /**
     * 向TD_LQFSDM表插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_LQFSDM(List<TD_LQFSDM> list);

    /**
     * 向TD_MZDM表插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_MZDM(List<TD_MZDM> list);

    /**
     * 向TD_PCDM表插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int  insertTD_PCDM(List<TD_PCDM> list);

    /**
     * 向表TD_WYYZDM插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_WYYZDM(List<TD_WYYZDM> list);

    /**
     * 向TD_ZCDM表插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_ZCDM(List<TD_ZCDM> list);

    /**
     * 向表TD_ZZMMDM插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_ZZMMDM(List<TD_ZZMMDM> list);

    /**
     * 向TD_QBJHK表插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_QBJHK(List<TD_QBJHK> list);

    /**
     * 向表TD_ZYTZDM插入数据
     * @param list 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_ZYTZDM(List<TD_ZYTZDM> list);



    /**
     *
     * @return 返回list对象
     */
    public List<TD_SFDM> requestTD_SFDM();
}
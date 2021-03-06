package com.dao;

import com.pojo.*;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @ClassName BasicConfigMapper
 * @Author 何政梁
 * @Time 2019.10.29
 * @Description 基础配置、数据导入的接口
 */
public interface BasicConfigDao {

    /**
     * 查询KDD表年份是否有该年份数据
     * @param year 年份
     * @return 1：表示有该快递单号数据 0：表示没上传该快递单号数据
     */
    public int selectKDD(String year);

    /**
     * 查询TD_YTFSX表年份是否有该年份数据
     * @param year 年份
     * @return 1：表示有该年份数据 0：表示没上传该年份数据
     */
    public int selectTD_YTFSX(String year);

    /**
     *查询TD_PTFSX表年份是否有该年份数据
     * @param year 年份
     * @return 1：表示有该年份数据 0：表示没上传该年份数据
     */
    public int selectTD_PTFSX(String year);



    /**
     * 删除原文件，上传调用insertXXX函数
     * @param year 年份
     * @return 0：删除数据失败 1：删除数据成功
     */

    public int deleteKDD(String year);
    /**
     * 删除原文件，上传调用updateXXX函数
     * @param year 年份
     * @return 0：删除数据失败 1：删除数据成功
     */
    public int deleteTD_YTFSX(String year);
    /**
     * 删除原文件，上传调用updateXXX函数
     * @param year 年份
     * @return 0：删除数据失败 1：删除数据成功
     */
    public int deleteTD_PTFSX(String year);

    /**
     * 请求数据库中的表数据，返回一个List,包含表中所有字段
     * @param year 年份
     * @return List<TD_PTFSX>
     */
    public List<TD_PTFSX> requestTD_PTFSX(String year);

    /**
     *请求数据库中的表数据，返回一个List,包含表中所有字段
     * @param year 年份
     * @return List<TD_PTFSX>
     */
    public List<TD_YTFSX> requestTD_YTFSX(String year);

    /**
     * 请求数据库中的表数据，返回一个List,包含表中所有字段
     * @param year 年份
     * @return
     */
    public List<KDD> requestKDD(String year);

    /**
     * 查询T_TDD表信息
     * @return 返回T_TDD的实体类，用list包装
     */
    public List<T_TDD> requestT_TDD();

    /**
     * 向数据库中插入KDD表要保存的内容
     * @param KDDList 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertKDD(List<KDD> KDDList);
    /**
     * 向数据库中插入TD_PTFSX表要保存的内容
     * @param TD_PTFSXList 保存内容
     * @return 0：添加数据库失败 1：添加数据成功
     */
    public int insertTD_PTFSX( List<TD_PTFSX> TD_PTFSXList);
    /**
     * 向数据库中插入TD_YTFSX表要保存的内容
     * @param TD_YTFSXList 保存内容
     * @return 0：添加数据失败 1：添加数据成功
     */
    public int insertTD_YTFSX(List<TD_YTFSX> TD_YTFSXList);

    /**
     * @param year 年份
     * @param province 省份代码 （整型）
     * @return 暂不清楚
     */
    public int selectT_TDD(@Param("NF") String year, @Param("SFDM") int province);

    /**
     * 查询TD_SFDM中的所有数据
     * @return list
     */
    public List<TD_SFDM> requestTD_SFDM();
}

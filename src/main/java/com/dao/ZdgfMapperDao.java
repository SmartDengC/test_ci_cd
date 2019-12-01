package com.dao;

import com.pojo.ZDGFYSDM;
import com.pojo.ZDGFZDM;
import org.apache.ibatis.annotations.Param;
//import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @ClassName ZdgfMapperDao
 * @Author Rose
 * @Time 2019/11/23 21:33
 * @Description TODO
 */
public interface ZdgfMapperDao {

    /**
     * 向ZDGFYSDM插入数据
     * @param list 一个ZDGFYSDM的List封装
    * @return  状态码
     */
    int insertZDGFYSDM(List<ZDGFYSDM> list);

    /**
     * 根据字段来删除该字段
     * @param zd 字段
     * @return 状态码
     */
    int deleteZDGFYSDM(@Param("ZD") String zd);

    /**
     * 修改字段数据
     * @param nzd 修改后的字段名
     * @param ozd 修改前的字段名
     * @return 状态码
     */
    int updateZDGFYSDM(@Param("NZD") String nzd,@Param("OZD") String ozd);

    /**
     * 根据年份和省份查询规范字段
     * @param year 年份
     * @param sfdm 省份代码
     * @return 状态码
     */
    List<ZDGFYSDM> selectZDGFYSDM(@Param("NF")String year,@Param("SFDM")int sfdm);
    /**
     * 向ZDGFYSDM插入数据
     * @param list 一个ZDGFDM 的list封装
     * @return 状态码
     */
    int insertZDGFZDM(List<ZDGFZDM> list);

    /**
     * 根据字段删除字段
     * @param zd 字段
     * @return 状态码
     */
    int deleteZDGFZDM(@Param("ZD") String zd);

    /**
     *根据字段修改规范值
     * @param zd 字段
     * @param gfz 规范值
     * @return 状态码
     */
    int updateZDGFZDMByZd(@Param("ZD") String zd,@Param("GFZ") String gfz);

    /**
     * 根据规范值修改字段
     * @param zd 字段名
     * @param gfz 规范值
     * @return 状态码
     */
    int updateZDGFZDMByGfz(@Param("ZD") String zd,@Param("GFZ") String gfz);

    /**
     * 根据字段查询表ZDGFZDM
     * @param zd 字段
     * @return 状态码
     */
    List<ZDGFZDM> selectZDGFZDM(@Param("ZD")String zd);

}
package com.dao;

import com.pojo.TD_KLDM;
import com.pojo.TD_PCDM;
import com.pojo.ZDGFYSDM;
import com.pojo.ZDGFZDM;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
* @ClassName DataStandardDao
* @Version 1.0
* @Author 马雪冬
* @Date 2019/11/22
* @Description TODO
* Modification User: 马雪冬
* Modification Date: 2019/11/25
*/

public interface DataStandardDao {

    /**
     * TD_PCDM中获取不规范值与代码(要保证PCMC的批次在T_TDD表中有)
     * @param year 指定年份
     * @param provinceCode 指定省份
     * @return 返回一个List存储的Map集合 Map存储了不规范值与不规范值代码
     */
    public List<Map<String,Object>> fecthUnFormatData_PC(@Param("NF") String year, @Param("SFDM") int provinceCode);
    /**
     * TD_KLDM中获取不规范值与代码
     * @param year 指定年份
     * @param provinceCode 指定省份
     * @return 返回一个List存储的Map集合 Map存储了不规范值与不规范值代码
     */
    public List<Map<String,Object>> fecthUnFormatData_KL(@Param("NF") String year, @Param("SFDM") int provinceCode);

    /**
     * ZDGFZDM中获取规范值与代码
     * @param field 指定字段名称
     * @return 返回一个List存储的Map集合 Map存储了规范值与规范值代码
     */
    public List<Map<String,Object>> fecthFormatData(@Param("ZD") String field);

    /**
     * ZDGFZYSDM中获取规范值代码与不规范代码
     * @param year 指定年份
     * @param provinceCode 指定省份代码
     * @param field 指定字段名称
     * @return 返回一个List存储的Map集合 Map存储了规范值代码与不规范代码
     */
    public List<Map<String,Object>> fecthRelation(@Param("NF") String year, @Param("SFDM") int provinceCode,@Param("ZD") String field);

    /**
     * 修改ZDGFZDM中指定的规范值
     * @param formatFieldCode 指定规范值的代码
     * @param newFormatFieldData 新的规范值
     * @return 0:修改失败 1:修改成功
     */
    public int modifyFormatData(@Param("ZDGFZDM")String formatFieldCode,@Param("GFZ") String newFormatFieldData);

    /**
     * 在ZDGFZDM中添加新的规范值
     * @param field 指定的字段名称
     * @param newFormatFieldData 新的规范值
     * @return 0:添加失败 1:添加成功
     */
    public int insertFormatData(@Param("ZD") String field,@Param("GFZ") String newFormatFieldData);

    /**
     *在ZDGFZYSDM中添加所有与不规范对应关系
     * @param list 所有规范值与不规范值的对应关系 集合
     * @return 0:添加失败 1:添加成功
     */
    public int biuldRelationship(List<Map<String,Object>> list);

    /**
     * 在ZDGFZYSDM中删除指定的规范与不规范对应关系
     * @param year 指定年份
     * @param provinceCode 指定省份代码
     * @param field 指定字段名称
     * @param unFormatFieldCode 指定不规范值代码
     * @param formatFieldCode 指定规范值代码
     * @return 0:删除失败 1:删除成功
     */
    public int deleteFieldRelation(@Param("NF") String year, @Param("SFDM") int provinceCode, @Param("ZD") String field, @Param("ZDBGFZDM") String unFormatFieldCode, @Param("ZDGFZDM") String formatFieldCode);

}

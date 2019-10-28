package com.utils.datautils;

import joinery.DataFrame;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 将DataFrame数据变成list<obj>的形式（实现）
 */
public interface toList extends DataFlow {

    /**
     * 将DataFrame对象内的数据转换成一个实体对象list。具体流程：
     * 首先使用反射技术获取Object的所有属性的名字，根据这些名字，在DataFrame中提取相应的数据存入这个实体中，循环以上过程，最后的得到实体list。
     * 异常处理：
     * 1、如果obj中属性名不在DataFrame的列中:
     * DataFrame中没能找到obj需要的数据。
     * 2、如果DataFrame中没有数据：
     * 空白的DataFrame
     * @param dataFrame DataFrame格式的数据
     * @param obj 索要转换到的实体对象
     * @param exclude 实体中不要写入数据到字段
     * @return 实体对象list化的数据
     */
    public static List<Object> fillListByDefault(DataFrame dataFrame, Class<T> obj, Set<String> exclude){return null;};

    /**
     * 将DataFrame对象中的按照特定的字段匹配规则，存入到实体对象的List中。具体流程如下：
     * 首先根据map中的value提取出DataDrame对象中需要的数据。在根据map中的key存入实体对象。循环以上过程，最终将数据导出到实体对象list。
     * 异常处理：
     * 1、如果map中的某个key不存在于实体对象中的属性名：
     * obj对象中不存在key这个属性
     * 2、如果map中的莫格value不存在于DataFrame对象中的列名：
     * dataFrame中不存在values这一列
     * 2、如果dataFrame中没有数据：
     * 空白的DataFrame
     * @param Dataframe DataFrame的原始数据
     * @param obj 需要转换的实体对象
     * @param map 自定义的对应关系，key代表实体对象中的属性名，value代表DataFrame对象中的列名
     * @return 实体对象list化的数据
     */
    public static Map fillListByMap(DataFrame Dataframe, Object obj, Map<String, String> map){return null; };

}
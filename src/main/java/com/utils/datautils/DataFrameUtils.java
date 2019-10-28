package com.utils.datautils;
import joinery.DataFrame;
import org.apache.poi.ss.formula.functions.T;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DataFrame对象数据的工具包
 */
public class DataFrameUtils extends AbstractDataUtils implements toList, toJson, toExcel {

    /**
     * Default constructor
     */
    public DataFrameUtils() {
    }

    /**
     * 将DataFrame对象内的数据转换成一个实体对象list。具体流程：
     * 首先使用反射技术获取Object的所有属性的名字，根据这些名字，在DataFrame中提取相应的数据存入这个实体中，循环以上过程，最后的得到实体list。
     * 异常处理(未处理)：
     * 1、如果obj中属性名不在DataFrame的列中:
     * DataFrame中没能找到obj需要的数据。
     * 2、如果DataFrame中没有数据：
     * 空白的DataFrame
     * @param dataFrame DataFrame格式的数据
     * @param obj 索要转换到的实体对象
     * @param exclude 实体中不要写入数据到字段
     * @return 实体对象list化的数据
     */
    public static List<Object> fillListByDefault(DataFrame dataFrame, Class<T> obj, Set<String> exclude) {
        /**
         * 1、初始化数据：创建实体List、创建一个Object对象、获取DataFrame中所有的列名、创建反射方法
         */
        List<Object> out = new ArrayList();
        Object instance = null;
        Set colname = dataFrame.columns();
        Method mysetMethod = null;
        /**
         * 2、检查obj中需要的属性是否全部存在于colname中,
         * 如果不在，返回null
         */
        Field[] myfields = obj.getDeclaredFields(); //获取类中声明的所有字段
        for (int i = 0; i < myfields.length; i++) {
            //如果是不需要的写入数据的字段就跳过
            if(exclude.contains(myfields[i].getName()))
            {
                continue;
            }
            if(!colname.contains(myfields[i].getName()))
            {
                System.out.println("dataFrame中没有列："+myfields[i].getName());
                return null;
            }
        }
        //全部转换成标准格式
        dataFrame.convert();
        /**
         * 3、遍历DataFrame中的数据，将每一行存储到一个obj的实体中，并添加到List
         */
        for(int i=0 ;i < dataFrame.length();i++)
        {
//            String  n1 = (String) df.get(i,"PCDM");
//            String  n2 = (String) df.get(i,"KLDM");
//            String  n3 = (String) df.get(i,"JHXZ");
//            String  n4 = (String) df.get(i,"TDDWDM");
//            String  n5 = (String) df.get(i,"TDDWMC");
//            String  n6 = (String) df.get(i,"CSMBH");
//            System.out.println(n1+" "+n2+" "+n3+" "+n4+" "+n5+" "+n6);
            /**
             * 创建一个对象
             */
            try {
                instance = obj.newInstance();
            } catch (Exception e) {
                System.out.println("DataFrameUtils.fillListByDefault:构建目标实例出错！");
                e.printStackTrace();
            }
            for (int j = 0; j < myfields.length; j++) {
                //如果是不需要的写入数据的字段就跳过
                if(exclude.contains(myfields[j].getName()))
                {
                    continue;
                }
                /**
                 * 取这个字段的名字、获取这个字段在DataFrame中的数据并全部换成Sting类型
                 */
                String name = myfields[j].getName();
                String thisData = (dataFrame.get(i,name).toString()).replace(" ","");
                /**
                 * /先获取这个属性的类型，在获取set方法，这里要注意，拼接后就真的是set方法了吗？
                 * 最后根据类型转换数据并写入数据
                 */
                Class<?> type = null;
                try {
                    type = obj.getDeclaredField(name).getType();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                try {
                    mysetMethod = obj.getMethod("set" + name, type);
                } catch (NoSuchMethodException e) {
                    System.out.println("DataFrameUtils.fillListByDefault:没有 set"+name+" 这个set方法");
                    e.printStackTrace();
                }
                try {
                    if (type.isAssignableFrom(String.class)) {
                        mysetMethod.invoke(instance, thisData);
                    } else if (type.isAssignableFrom(int.class)
                            || type.isAssignableFrom(Integer.class)) {
                        mysetMethod.invoke(instance, Integer.parseInt(thisData));
                    } else if (type.isAssignableFrom(Float.class)
                            || type.isAssignableFrom(float.class)) {
                        mysetMethod.invoke(instance, Float.parseFloat(thisData));
                    }
                } catch (IllegalAccessException e) {
                    System.out.println("DataFrameUtils.fillListByDefault:invlke方法出错->IllegalAccessException");
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    System.out.println("DataFrameUtils.fillListByDefault:invlke方法出错->InvocationTargetException");
                    e.printStackTrace();
                }catch (NumberFormatException e){
                    System.out.println("DataFrameUtils.fillListByDefault:Lang数据格式转换出错,原始数据为："+thisData+
                            "字段名字是："+name);
                    e.printStackTrace();
                }
            }
            /**
             * 将写入数据的实体存入list
             */
            out.add(instance);
        }

        /**
         *返回实体类对象
         */
        return out;
    }

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
    public static Map fillListByMap(DataFrame Dataframe, Object obj, Map<String,String> map) {
        // TODO implement here
        return null;
    }

    /**
     * 暂时不实现
     * @param Config
     * @return
     */
    public static String fillJsonByDefault(Object Config) {
        // TODO implement here
        return "";
    }

    /**
     * 暂时不实现
     * @param Config
     * @return
     */
    public static Object fillExcelByDefault(Object Config) {
        // TODO implement here
        return null;
    }

}
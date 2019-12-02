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
     * 抛出异常的(未处理，输出在控制台了)：
     * 1、如果obj中属性名不在DataFrame的列中:DataFrame中没能找到obj需要的数据。
     * 2、如果DataFrame中没有数据：空白的DataFrame
     * 对于空值的处理：
     * String 赋值为：""
     * int 赋值为：-1
     * float 赋值为：-1.0
     * 对于类型与dbf文件类型不同的处理:
     * pojo类型int 实际类型带非数字的text：报错，返回null
     * pojo类型int 实际类型float：只提取整数
     * pojo类型float 实际类型带非数字的text:报错，返回null
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
        //dataFrame.convert();
        /**
         * 3、遍历DataFrame中的数据，将每一行存储到一个obj的实体中，并添加到List
         */
        for(int i=0 ;i < dataFrame.length();i++)
        {
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
                String thisData=null;
                Object temp = dataFrame.get(i,name);
                if(temp!=null){
                    if(name.equals("CSNY")){
                        String strDateFormat = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                        thisData=sdf.format(new Date(temp.toString()));
                    }else {
                        thisData = temp.toString().replaceAll(" ","");
                    }
                }
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
                        if(!thisData.equals("")){
                            if(thisData.indexOf(".")!=-1){
                                mysetMethod.invoke(instance, Integer.parseInt(thisData.substring(0,thisData.indexOf("."))));
                            }else {
                                mysetMethod.invoke(instance,Integer.parseInt(thisData));
                            }
                        }else {
                            mysetMethod.invoke(instance, -1);
                        }
                    } else if (type.isAssignableFrom(Float.class)
                            || type.isAssignableFrom(float.class)) {
                        if(!thisData.equals("")){
                            mysetMethod.invoke(instance, Float.parseFloat(thisData));
                        }else {
                            mysetMethod.invoke(instance, -1.0f);
                        }
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
     * 抛出异常的(未处理，输出在控制台了)：
     * 1、如果obj中属性名不在DataFrame的列中:DataFrame中没能找到obj需要的数据。
     * 2、如果DataFrame中没有数据：空白的DataFrame
     * 对于空值的处理：
     * String 赋值为：""
     * int 赋值为：-1
     * float 赋值为：-1.0
     * 对于类型与dbf文件类型不同的处理:
     * pojo类型int 实际类型带非数字的text：报错，返回null
     * pojo类型int 实际类型float：只提取整数
     * pojo类型float 实际类型带非数字的text:报错，返回null
     * @param dataFrame DataFrame的原始数据
     * @param obj 需要转换的实体对象
     * @param exclude 实体中不要写入数据到字段
     * @param map 自定义的对应关系，key代表实体对象中的属性名，value代表DataFrame对象中的列名
     * @return 实体对象list化的数据
     */
    public static List<Object> fillListByMap(DataFrame dataFrame, Class<T> obj,Set<String> exclude,Map<String,String> map) {
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
            if(!(colname.contains(myfields[i].getName()) || map.containsKey(myfields[i].getName())))
            {
                System.out.println("dataFrame中没有列或请检查你的自定义映射关系："+myfields[i].getName());
                return null;
            }
        }
        //全部转换成标准格式
        //dataFrame.convert();
        /**
         * 3、遍历DataFrame中的数据，根据map中定义的对应关系将每一行存储到一个obj的实体中，并添加到List
         */
        for(int i=0 ;i < dataFrame.length();i++)
        {
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
                 * 取这个实体中的字段的名字、根据map中对应关系获取这个字段在DataFrame中的数据
                 */
                String name = myfields[j].getName();
                String thisData=null;
                //首先判断是否需要转换，要才转换，不要就不管
                if(map.containsKey(name)){
                    String transforName = map.get(name);
                    if(colname.contains(transforName)){
                        thisData = (dataFrame.get(i,transforName).toString()).replace(" ","");
                    }
                    else {
                        System.out.println("dataFrame中没有你转换后的列名："+transforName+"！，转换前的值为："+name);
                        return null;
                    }
                }
                else {
                    Object temp = dataFrame.get(i,name);
                    if(temp!=null){
                        thisData = temp.toString().replaceAll(" ","");
                    }
                }


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
                        if(!thisData.equals("")){
                            if(thisData.indexOf(".")!=-1){
                                mysetMethod.invoke(instance, Integer.parseInt(thisData.substring(0,thisData.indexOf("."))));
                            }else {
                                mysetMethod.invoke(instance,Integer.parseInt(thisData));
                            }
                        }else {
                            mysetMethod.invoke(instance, -1);
                        }
                    }else if (type.isAssignableFrom(Float.class)
                            || type.isAssignableFrom(float.class)) {
                        if(!thisData.equals("")){
                            mysetMethod.invoke(instance, Float.parseFloat(thisData));
                        }else {
                            mysetMethod.invoke(instance, -1.0f);
                        }
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
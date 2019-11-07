package com.utils;

import java.util.Calendar;

/**
 * @ClassName GetDefaultInfo
 * @Version 1.0
 * @Author 邓聪
 * @Date 2019/10/29 20:26
 * @Description 获取默认的信息，例如年份和省份
 * Modification User： 邓聪
 * Modification Date： 2019/10/28
 */
public class GetDefaultInfo {
    /**
     * 获取默认年份，默认为本年
     * @param year 年份
     * @return 本年年份
     */
    public static String getCurYear(String year){
        if (year == null){
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            year = Integer.toString(currentYear);
        }
        return year;
    }

    /**
     * 获取默认年份
     * @param province 省份
     * @return 默认省份
     */
    public static String getCurProvince(String province){
        if (province == null){
            province = "四川省";
        }
        return province;
    }
}
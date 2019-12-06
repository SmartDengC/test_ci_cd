package com.utils.datautils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName toExcel
 * @Version 1.0
 * @Author Lenovo
 * @Date 2019/11/30 23:01
 * @Description TODO
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
public class toExcel {

    /***
     * 构造方法
     */
    public toExcel() {

    }

    /***
     * 工作簿
     */
    private static HSSFWorkbook workbook;

    private static HSSFCreationHelper createHelper;

    /***
     * sheet
     */
    private static HSSFSheet sheet;
    /***
     * 标题行开始位置
     */
    private static final int TITLE_START_POSITION = 0;

    /***
     * 时间行开始位置
     */
    private static final int DATEHEAD_START_POSITION = 1;

    /***
     * 表头行开始位置
     */
    private static final int HEAD_START_POSITION = 2;

    /***
     * 文本行开始位置
     */
    private static final int CONTENT_START_POSITION = 3;


    /**
     * @param dataList  对象集合
     * @param titleMap  表头信息（对象属性名称->要显示的标题值)[按顺序添加]
     * @param sheetName sheet名称和表头值
     * @return
     */
    public HSSFWorkbook excelExport(List<?> dataList, Map<String, String> titleMap, String sheetName) {
//        File file = new File("C:\\Users\\Lenovo\\Desktop\\" + name + ".xls");
        // 初始化workbook
        initHSSFWorkbook(sheetName);
        // 标题行
        createTitleRow(titleMap, sheetName);
        // 时间行
        createDateHeadRow(titleMap);
        // 表头行
        createHeadRow(titleMap);
        // 文本行
        createContentRow(dataList, titleMap);
        //设置自动伸缩
        //autoSizeColumn(titleMap.size());
        // 写入处理结果
//        try {
//            //生成UUID文件名称
//            //如果web项目，1、设置下载框的弹出（设置response相关参数)；2、通过httpservletresponse.getOutputStream()获取
////            OutputStream out = new FileOutputStream(file);
////            workbook.write(out);
////            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return workbook;
    }

    /***
     *
     * @param sheetName
     *        sheetName
     */
    private static void initHSSFWorkbook(String sheetName) {
        workbook = new HSSFWorkbook();
        createHelper = workbook.getCreationHelper();
        sheet = workbook.createSheet(sheetName);
    }

    /**
     * 生成标题（第零行创建）
     *
     * @param titleMap  对象属性名称->表头显示名称
     * @param sheetName sheet名称
     */
    private static void createTitleRow(Map<String, String> titleMap, String sheetName) {
        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, titleMap.size() - 1);
        sheet.addMergedRegion(titleRange);
        HSSFRow titleRow = sheet.createRow(TITLE_START_POSITION);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(sheetName);
    }

    /**
     * 创建时间行（第一行创建）
     *
     * @param titleMap 对象属性名称->表头显示名称
     */
    private static void createDateHeadRow(Map<String, String> titleMap) {
        CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, titleMap.size() - 1);
        sheet.addMergedRegion(dateRange);
        HSSFRow dateRow = sheet.createRow(DATEHEAD_START_POSITION);
        HSSFCell dateCell = dateRow.createCell(0);
        dateCell.setCellValue(new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
    }

    /**
     * 创建表头行（第二行创建）
     *
     * @param titleMap 对象属性名称->表头显示名称
     */
    private static void createHeadRow(Map<String, String> titleMap) {
        // 第1行创建
        HSSFRow headRow = sheet.createRow(HEAD_START_POSITION);
        int i = 0;
        for (String entry : titleMap.keySet()) {
            HSSFCell headCell = headRow.createCell(i);
            headCell.setCellValue(titleMap.get(entry));
            i++;
        }
    }

    /**
     * @param dataList 对象数据集合
     * @param titleMap 表头信息
     */
    private static void createContentRow(List<?> dataList, Map<String, String> titleMap) {
        try {
            int i = 0;
            for (Object obj : dataList) {
                HSSFRow textRow = sheet.createRow(CONTENT_START_POSITION + i);
                int j = 0;
                for (String entry : titleMap.keySet()) {
                    String method = "get" + entry.substring(0, 1).toUpperCase() + entry.substring(1);
                    Method m = obj.getClass().getMethod(method, null);
                    String value = m.invoke(obj, null).toString();
                    HSSFCell textcell = textRow.createCell(j);
                    textcell.setCellValue(value);
                    j++;
                }
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getExcelPath(String name) {
        //String str = this.getServletContext().getRealPath("/WEB-INF");
        List<Map<String, Object>> searchlist = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String timestr = df.format(new Date()).toString().replace(" ", "-");
        timestr = timestr.replace(":","");
        String filename = name+ timestr+".xls";
//        logger.info("下载:"+filename);
        return filename;
    }

}
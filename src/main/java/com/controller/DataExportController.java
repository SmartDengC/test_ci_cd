package com.controller;

import com.dao.DataExportDao;
import com.pojo.BankCard;
import com.pojo.KDD;
import com.pojo.Notice;
import com.service.DataExportService;
import com.utils.datautils.toExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ClassName DataExportController
 * @Version 1.0
 * @Author 吕志伟
 * @Date 2019/11/29 19:49
 * @Description TODO
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
@Controller
@CrossOrigin
@RequestMapping("/dataExport")
public class DataExportController {
    @Autowired
    DataExportService dataExportService;

    @Autowired
    DataExportDao dataExportDao;

    @RequestMapping("/getScore")
    public String getScore(){
        String result ="";

        return result;
    }

    toExcel toexcel= new toExcel();

    @ResponseBody
    @RequestMapping("/exportNotice")
    public void exportNotice(HttpServletRequest request, HttpServletResponse response){
        String sheetName = "通知书";
        String year = request.getParameter("year");
        StringBuilder jsonstr = new StringBuilder();//转化成json
        jsonstr.append("{");
        String reqstr = request.getQueryString();//获取请求参数
        if(!"".equals(reqstr) &&reqstr !=null){

            String [] stringArr= reqstr.split("&");
            for(int i = 0;i<stringArr.length;i++){
                String [] strArr = stringArr[i].split("=");

                jsonstr.append("\""+strArr[0]+"\":"+"\""+strArr[1]+"\""+",") ;
            }
            jsonstr.deleteCharAt(jsonstr.length()-1);
        }
        jsonstr.append("}");
        String filename = toexcel.getExcelPath(sheetName);
        HSSFWorkbook wb = dataExportService.exportNotice(year);
        List<Notice> list = dataExportDao.requestNotice(year);
        if(list!=null){
            try{ //写入浏览器
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
                OutputStream outputStream=response.getOutputStream();
                wb.write(outputStream);
                outputStream.flush();
                outputStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @ResponseBody
    @RequestMapping("/exportBankCard")
    public void exportBankCard(HttpServletRequest request, HttpServletResponse response){
        String sheetName = "银行卡";
        String year = request.getParameter("year");
        StringBuilder jsonstr = new StringBuilder();//转化成json
        jsonstr.append("{");
        String reqstr = request.getQueryString();//获取请求参数
        if(!"".equals(reqstr) &&reqstr !=null){

            String [] stringArr= reqstr.split("&");
            for(int i = 0;i<stringArr.length;i++){
                String [] strArr = stringArr[i].split("=");

                jsonstr.append("\""+strArr[0]+"\":"+"\""+strArr[1]+"\""+",") ;
            }
            jsonstr.deleteCharAt(jsonstr.length()-1);
        }
        jsonstr.append("}");
        String filename = toexcel.getExcelPath(sheetName);
        HSSFWorkbook wb = dataExportService.exportBankCard(year);
        List<BankCard> list = dataExportDao.requestBankCard(year);
        if(list!=null){
            try{ //写入浏览器
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
                OutputStream outputStream=response.getOutputStream();
                wb.write(outputStream);
                outputStream.flush();
                outputStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @ResponseBody
    @RequestMapping("/exportExpressSingle")
    public void exportExpressSingle(HttpServletRequest request, HttpServletResponse response){
        String sheetName = "快递单";
        String year = request.getParameter("year");
        StringBuilder jsonstr = new StringBuilder();//转化成json
        jsonstr.append("{");
        String reqstr = request.getQueryString();//获取请求参数
        if(!"".equals(reqstr) &&reqstr !=null){

            String [] stringArr= reqstr.split("&");
            for(int i = 0;i<stringArr.length;i++){
                String [] strArr = stringArr[i].split("=");

                jsonstr.append("\""+strArr[0]+"\":"+"\""+strArr[1]+"\""+",") ;
            }
            jsonstr.deleteCharAt(jsonstr.length()-1);
        }
        jsonstr.append("}");
        String filename = toexcel.getExcelPath(sheetName);
        HSSFWorkbook wb = dataExportService.exportExpressSingle(year);
        List<KDD> list = dataExportDao.requestExpressSingle(year);
        if(list!=null){
            try{ //写入浏览器
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
                OutputStream outputStream=response.getOutputStream();
                wb.write(outputStream);
                outputStream.flush();
                outputStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        }

}
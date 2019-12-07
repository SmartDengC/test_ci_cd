package com.utils.provincescore;

import com.pojo.TD_XSFS;
import joinery.DataFrame;

import java.util.*;
import java.util.HashMap;

/**
 * @ClassName guangXiScore
 * @Version 1.0
 * @Author Lenovo
 * @Date 2019/12/3 16:06
 * @Description TODO
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
public class guangXiScore {
    public HashMap guangXiScore(HashMap colmnMap,String CJXDM,String CJXMC){
        switch (CJXMC){
            case "语文":
                colmnMap.put("YWCJ",CJXDM);
                break;
            case "外语":
                colmnMap.put("WYCJ",CJXDM);
                break;
            default:
                break;
        }
        return colmnMap;
    }

    public List score(DataFrame T_TDDDataFrame, List<TD_XSFS> list){
        HashMap<String, Integer> math = new HashMap<String, Integer>();
        HashMap<String, Integer> comprehensive = new HashMap<String, Integer>();
        for (int i =0;i<T_TDDDataFrame.length();i++) {
            Double math1 = (Double) T_TDDDataFrame.get(i, 80);
            int math2 = Integer.parseInt(new java.text.DecimalFormat("0").format(math1));
            Double math3 = (Double)T_TDDDataFrame.get(i, 81);
            int math4 = Integer.parseInt(new java.text.DecimalFormat("0").format(math3));
            Double com1 = (Double)T_TDDDataFrame.get(i, 83);
            int com2 = Integer.parseInt(new java.text.DecimalFormat("0").format(com1));
            Double com3 = (Double)T_TDDDataFrame.get(i, 84);
            int com4 = Integer.parseInt(new java.text.DecimalFormat("0").format(com3));
            String KSH = (String)T_TDDDataFrame.get(i, 1);
            if (math1 != 0) {
                math.put(KSH, math2);
            } else {
                math.put(KSH, math4);
            }
            if (com1 != 0) {
                comprehensive.put(KSH, com2);
            } else {
                comprehensive.put(KSH, com4);
            }
        }

        for(TD_XSFS xsfs: list){
            String ksh = xsfs.getKSH();
            xsfs.setSXCJ(math.get(ksh));
            xsfs.setZHCJ(comprehensive.get(ksh));
        }
        return list;
    }

}
package com.utils.provincescore;

import com.pojo.TD_XSFS;
import joinery.DataFrame;

import java.util.*;
import java.util.HashMap;

/**
 * @ClassName shanXiScore
 * @Version 1.0
 * @Author Lenovo
 * @Date 2019/12/5 16:02
 * @Description TODO
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
public class shanXiScore {
    public HashMap shanXiScore(HashMap colmnMap, String CJXDM, String CJXMC){
        switch (CJXMC){
            case "语文":
                colmnMap.put("YWCJ",CJXDM);
                break;
            case "数学":
                colmnMap.put("SXCJ",CJXDM);
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
        HashMap<String,Float> comprehensive = new HashMap<>();
        float com2 = 0;
        float com4 = 0;
        for (int i =0;i<T_TDDDataFrame.length();i++) {
            Object com1 = (Double)T_TDDDataFrame.get(i, 81);
            if(com1 != null) {
                com2 = Float.parseFloat(com1.toString());
            }
            Object com3 = (Double)T_TDDDataFrame.get(i, 82);
            if (com3 != null) {
                com4 = Float.parseFloat(com3.toString());
            }
            String KSH =(String) T_TDDDataFrame.get(i, 0);
            if (com1 != null) {
                comprehensive.put(KSH, com2);
            } else {
                comprehensive.put(KSH, com4);
            }
        }

        for(TD_XSFS xsfs: list){
            String ksh = xsfs.getKSH();
            xsfs.setZHCJ(comprehensive.get(ksh));
        }
        return list;
    }
}
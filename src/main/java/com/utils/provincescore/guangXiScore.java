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
        HashMap<String, Float> math = new HashMap<String, Float>();
        HashMap<String, Float> comprehensive = new HashMap<String, Float>();
        float math2=0;
        float math4 = 0;
        float com2 = 0;
        float com4 = 0 ;
        for (int i =0;i<T_TDDDataFrame.length();i++) {
            Object math1 = T_TDDDataFrame.get(i, 79);
            if (math1 != null) {
                 math2 = Float.parseFloat(math1.toString());
            }
            Object math3 = T_TDDDataFrame.get(i, 80);
            if (math3 != null) {
                math4 = Float.parseFloat(math3.toString());
            }
            Object com1 = T_TDDDataFrame.get(i, 82);
            if(com1 != null) {
                com2 = Float.parseFloat(com1.toString());
            }
            Object com3 = T_TDDDataFrame.get(i, 83);
            if (com3 != null) {
                com4 = Float.parseFloat(com3.toString());
            }
            String KSH = (String)T_TDDDataFrame.get(i, 0);
            if (math1 !=  null) {
                math.put(KSH, math2);
            } else {
                math.put(KSH, math4);
            }
            if (com1 != null) {
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
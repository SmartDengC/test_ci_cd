package com.utils.provincescore;

import java.util.HashMap;

/**
 * @ClassName haiNanScore
 * @Version 1.0
 * @Author Lenovo
 * @Date 2019/12/3 14:05
 * @Description TODO
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
public class haiNanScore {
    public HashMap haiNanScore(HashMap colmnMap,String CJXDM,String CJXMC){

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
            case "综合分":
                colmnMap.put("ZHCJ",CJXDM);
            default:
                break;
        }
        return colmnMap;
    }
}
package com.pojo;

import java.time.Year;

/**
 * 对应 ZDBFYSDM 字段规范映射表
 */
public class ZDGFYSDM {
    private int ZDGFYSDM; //(字段规范值)主键
    private Year NF;
    private int SFDM; //(省份代码)外键<==>TD_SFDM表的SFDM主键
    private String ZD;
    private String ZDBGFDM;
    private int ZDGFZDM;

    public int getZDGFYSDM() {
        return ZDGFYSDM;
    }

    public void setZDGFYSDM(int ZDGFYSDM) {
        this.ZDGFYSDM = ZDGFYSDM;
    }

    public Year getNF() {
        return NF;
    }

    public void setNF(Year NF) {
        this.NF = NF;
    }

    public int getSFDM() {
        return SFDM;
    }

    public void setSFDM(int SFDM) {
        this.SFDM = SFDM;
    }

    public String getZD() {
        return ZD;
    }

    public void setZD(String ZD) {
        this.ZD = ZD;
    }

    public String getZDBGFDM() {
        return ZDBGFDM;
    }

    public void setZDBGFDM(String ZDBGFDM) {
        this.ZDBGFDM = ZDBGFDM;
    }

    public int getZDGFZDM() {
        return ZDGFZDM;
    }

    public void setZDGFZDM(int ZDGFZDM) {
        this.ZDGFZDM = ZDGFZDM;
    }


}

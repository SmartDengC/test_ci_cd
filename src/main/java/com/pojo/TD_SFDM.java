package com.pojo;

/**
 * 对应 TD_SFDM表
 */
public class TD_SFDM {
    private int SFDM;   //省份代码:主键
    private String SF;

    public int getSFDM() {
        return SFDM;
    }

    public void setSFDM(int SFDM) {
        this.SFDM = SFDM;
    }

    public String getSF() {
        return SF;
    }

    public void setSF(String SF) {
        this.SF = SF;
    }

    @Override
    public String toString() {
        return "TD_SFDM{" +
                "SFDM=" + SFDM +
                ", SF='" + SF + '\'' +
                '}';
    }
}

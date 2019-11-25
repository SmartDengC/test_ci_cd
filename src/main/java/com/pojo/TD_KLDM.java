package com.pojo;

/**
 * 对应 TD_KLDM 表
 */
public class TD_KLDM {
    private int ID;        //编号:主键
    private String NF;
    private int SFDM;      //年份:外键<==>TD_SFDM的主键
    private String KLDM;
    private String KLMC;
    private String JBKL;    //未知
    private String KSCJBJXH;//未知

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNF() {
        return NF;
    }

    public void setNF(String NF) {
        this.NF = NF;
    }

    public int getSFDM() {
        return SFDM;
    }

    public void setSFDM(int SFDM) {
        this.SFDM = SFDM;
    }

    public String getKLDM() {
        return KLDM;
    }

    public void setKLDM(String KLDM) {
        this.KLDM = KLDM;
    }

    public String getJBKL() {
        return JBKL;
    }

    public void setJBKL(String JBKL) {
        this.JBKL = JBKL;
    }

    public String getKSCJBJXH() {
        return KSCJBJXH;
    }

    public void setKSCJBJXH(String KSCJBJXH) {
        this.KSCJBJXH = KSCJBJXH;
    }

    public String getKLMC() {
        return KLMC;
    }

    public void setKLMC(String KLMC) {
        this.KLMC = KLMC;
    }

    @Override
    public String toString() {
        return "TD_KLDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", KLDM=" + KLDM +
                ", KLMC=" + KLMC +
                ", JBKL='" + JBKL + '\'' +
                ", KSCJBJXH='" + KSCJBJXH + '\'' +
                '}';
    }
}
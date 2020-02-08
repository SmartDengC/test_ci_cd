package com.pojo;

/**
 * 对应 TD_PCDM表
 */
public class TD_PCDM {
    private int ID;     //编号:主键
    private String NF;
    private int SFDM;   //年份:外键<==>TD_SFDM的主键
    private String PCDM;
    private String PCMC;
    private String GBPCDM; //未知
    private String GBCCDM; //未知

    public String getNF() {
        return NF;
    }

    public void setNF(String NF) {
        this.NF = NF;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSFDM() {
        return SFDM;
    }

    public void setSFDM(int SFDM) {
        this.SFDM = SFDM;
    }

    public String getPCDM() {
        return PCDM;
    }

    public void setPCDM(String PCDM) {
        this.PCDM = PCDM;
    }

    public String getPCMC() {
        return PCMC;
    }

    public void setPCMC(String PCMC) {
        this.PCMC = PCMC;
    }

    public String getGBPCDM() {
        return GBPCDM;
    }

    public void setGBPCDM(String GBPCDM) {
        this.GBPCDM = GBPCDM;
    }

    public String getGBCCDM() {
        return GBCCDM;
    }

    public void setGBCCDM(String GBCCDM) {
        this.GBCCDM = GBCCDM;
    }

    @Override
    public String toString() {
        return "TD_PCDM{" +
                "ID=" + ID +
                ", SFDM=" + SFDM +
                ", PCDM='" + PCDM + '\'' +
                ", PCMC='" + PCMC + '\'' +
                ", GBPCDM='" + GBPCDM + '\'' +
                ", GBCCDM='" + GBCCDM + '\'' +
                '}';
    }
}

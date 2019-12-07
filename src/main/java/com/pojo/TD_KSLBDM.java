package com.pojo;

public class TD_KSLBDM {
    private int ID;
    private String NF;
    private int SFDM;
    private int KSLBDM;
    private String KSLBMC;

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

    public int getKSLBDM() {
        return KSLBDM;
    }

    public void setKSLBDM(int KSLBDM) {
        this.KSLBDM = KSLBDM;
    }

    public String getKSLBMC() {
        return KSLBMC;
    }

    public void setKSLBMC(String KSLBMC) {
        this.KSLBMC = KSLBMC;
    }

    @Override
    public String toString() {
        return "TD_KSLBDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", KSLBDM=" + KSLBDM +
                ", KSLBMC='" + KSLBMC + '\'' +
                '}';
    }
}
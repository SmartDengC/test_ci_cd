package com.pojo;

public class TD_KSLXDM {
    private int ID;
    private String NF;
    private int SFDM;
    private int KSLXDM;
    private String KSLXMC;

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

    public int getKSLXDM() {
        return KSLXDM;
    }

    public void setKSLXDM(int KSLXDM) {
        this.KSLXDM = KSLXDM;
    }

    public String getKSLXMC() {
        return KSLXMC;
    }

    public void setKSLXMC(String KSLXMC) {
        this.KSLXMC = KSLXMC;
    }

    @Override
    public String toString() {
        return "TD_KSLXDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", KSLXDM=" + KSLXDM +
                ", KSLXMC='" + KSLXMC + '\'' +
                '}';
    }
}
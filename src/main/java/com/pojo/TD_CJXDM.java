package com.pojo;


public class    TD_CJXDM {
    private int ID;
    private String NF;
    private int SFDM;
    private int CJXDM;
    private String CJXMC;
    private String CJXLXDM;

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

    public int getCJXDM() {
        return CJXDM;
    }

    public void setCJXDM(int CJXDM) {
        this.CJXDM = CJXDM;
    }

    public String getCJXMC() {
        return CJXMC;
    }

    public void setCJXMC(String CJXMC) {
        this.CJXMC = CJXMC;
    }

    public String getCJXLXDM() {
        return CJXLXDM;
    }

    public void setCJXLXDM(String CJXLXDM) {
        this.CJXLXDM = CJXLXDM;
    }

    @Override
    public String toString() {
        return "TD_CJXDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", CJXDM=" + CJXDM +
                ", CJXMC='" + CJXMC + '\'' +
                ", CJXLXDM='" + CJXLXDM + '\'' +
                '}';
    }
}

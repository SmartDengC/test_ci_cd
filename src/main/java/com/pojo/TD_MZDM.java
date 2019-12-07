package com.pojo;

public class TD_MZDM {
    private int ID;
    private String NF;
    private int SFDM;
    private int MZDM;
    private String MZMC;

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

    public int getMZDM() {
        return MZDM;
    }

    public void setMZDM(int MZDM) {
        this.MZDM = MZDM;
    }

    public String getMZMC() {
        return MZMC;
    }

    public void setMZMC(String MZMC) {
        this.MZMC = MZMC;
    }

    @Override
    public String toString() {
        return "TD_MZDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", MZDM=" + MZDM +
                ", MZMC='" + MZMC + '\'' +
                '}';
    }
}
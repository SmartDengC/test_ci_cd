package com.pojo;

public class TD_ZZMMDM {
    private int ID;
    private String NF;
    private int SFDM;
    private int ZZMMDM;
    private String ZZMMMC;

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

    public int getZZMMDM() {
        return ZZMMDM;
    }

    public void setZZMMDM(int ZZMMDM) {
        this.ZZMMDM = ZZMMDM;
    }

    public String getZZMMMC() {
        return ZZMMMC;
    }

    public void setZZMMMC(String ZZMMMC) {
        this.ZZMMMC = ZZMMMC;
    }

    @Override
    public String toString() {
        return "TD_ZZMMDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", ZZMMDM=" + ZZMMDM +
                ", ZZMMMC='" + ZZMMMC + '\'' +
                '}';
    }
}

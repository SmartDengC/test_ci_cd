package com.pojo;

public class TD_WYYZDM {
    private int ID;
    private String NF;
    private int SFDM;
    private int WYYZDM;
    private String WYYZMC;

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

    public int getWYYZDM() {
        return WYYZDM;
    }

    public void setWYYZDM(int WYYZDM) {
        this.WYYZDM = WYYZDM;
    }

    public String getWYYZMC() {
        return WYYZMC;
    }

    public void setWYYZMC(String WYYZMC) {
        this.WYYZMC = WYYZMC;
    }

    @Override
    public String toString() {
        return "TD_WYYZDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", WYYZDM=" + WYYZDM +
                ", WYYZMC='" + WYYZMC + '\'' +
                '}';
    }
}
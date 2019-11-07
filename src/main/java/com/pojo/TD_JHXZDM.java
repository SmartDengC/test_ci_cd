package com.pojo;

public class TD_JHXZDM {
    private int ID;
    private String NF;
    private int SFDM;
    private String JHXZDM;
    private String JHXZMC;

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

    public String getJHXZDM() {
        return JHXZDM;
    }

    public void setJHXZDM(String JHXZDM) {
        this.JHXZDM = JHXZDM;
    }

    public String getJHXZMC() {
        return JHXZMC;
    }

    public void setJHXZMC(String JHXZMC) {
        this.JHXZMC = JHXZMC;
    }

    @Override
    public String toString() {
        return "TD_JHXZDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", JHXZDM='" + JHXZDM + '\'' +
                ", JHXZMC='" + JHXZMC + '\'' +
                '}';
    }
}
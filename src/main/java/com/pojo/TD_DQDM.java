package com.pojo;

public class TD_DQDM {
    private int ID;
    private String NF;
    private int SFDM;
    private int DQDM;
    private String DQMC;

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

    public int getDQDM() {
        return DQDM;
    }

    public void setDQDM(int DQDM) {
        this.DQDM = DQDM;
    }

    public String getDQMC() {
        return DQMC;
    }

    public void setDQMC(String DQMC) {
        this.DQMC = DQMC;
    }

    @Override
    public String toString() {
        return "TD_DQDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", DQDM=" + DQDM +
                ", DQMC='" + DQMC + '\'' +
                '}';
    }
}

package com.pojo;

public class TD_LQFSDM {
    private int ID;
    private String NF;
    private int SFDM;
    private String LQFSDM;
    private String LQFSMC;

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

    public String getLQFSDM() {
        return LQFSDM;
    }

    public void setLQFSDM(String LQFSDM) {
        this.LQFSDM = LQFSDM;
    }

    public String getLQFSMC() {
        return LQFSMC;
    }

    public void setLQFSMC(String LQFSMC) {
        this.LQFSMC = LQFSMC;
    }

    @Override
    public String toString() {
        return "TD_LQFSDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", LQFSDM=" + LQFSDM +
                ", LQFSMC='" + LQFSMC + '\'' +
                '}';
    }
}
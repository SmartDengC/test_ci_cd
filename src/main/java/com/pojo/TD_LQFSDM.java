package com.pojo;

public class TD_LQFSDM {
    private int ID;
    private String NF;
    private int SFDM;
    private int LQFSDM;
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

    public int getLQFSDM() {
        return LQFSDM;
    }

    public void setLQFSDM(int LQFSDM) {
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

package com.pojo;

public class TD_PCDM {
    private int ID;
    private String NF;
    private int SFDM;
    private String PCDM;
    private String PCMC;
    private String GBPCDM;
    private String GBCCDM;

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

    public String getPCDM() {
        return PCDM;
    }

    public void setPCDM(String PCDM) {
        this.PCDM = PCDM;
    }

    public String getPCMC() {
        return PCMC;
    }

    public void setPCMC(String PCMC) {
        this.PCMC = PCMC;
    }

    public String getGBPCDM() {
        return GBPCDM;
    }

    public void setGBPCDM(String GBPCDM) {
        this.GBPCDM = GBPCDM;
    }

    public String getGBCCDM() {
        return GBCCDM;
    }

    public void setGBCCDM(String GBCCDM) {
        this.GBCCDM = GBCCDM;
    }

    @Override
    public String toString() {
        return "TD_PCDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", PCDM=" + PCDM +
                ", PCMC='" + PCMC + '\'' +
                ", GBPCDM='" + GBPCDM + '\'' +
                ", GBCCDM='" + GBCCDM + '\'' +
                '}';
    }
}
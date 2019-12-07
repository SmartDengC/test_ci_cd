package com.pojo;

public class TD_ZCDM {
    private int ID;
    private String NF;
    private int SFDM;
    private String ZCDM;
    private String ZCMC;
    private float ZGF;
    private String XDX;

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

    public String getZCDM() {
        return ZCDM;
    }

    public void setZCDM(String ZCDM) {
        this.ZCDM = ZCDM;
    }

    public String getZCMC() {
        return ZCMC;
    }

    public void setZCMC(String ZCMC) {
        this.ZCMC = ZCMC;
    }

    public float getZGF() {
        return ZGF;
    }

    public void setZGF(float ZGF) {
        this.ZGF = ZGF;
    }

    public String getXDX() {
        return XDX;
    }

    public void setXDX(String XDX) {
        this.XDX = XDX;
    }

    @Override
    public String toString() {
        return "TD_ZCDM{" +
                "ID=" + ID +
                ", NF='" + NF + '\'' +
                ", SFDM=" + SFDM +
                ", ZCDM=" + ZCDM +
                ", ZCMC='" + ZCMC + '\'' +
                ", ZGF=" + ZGF +
                ", XDX='" + XDX + '\'' +
                '}';
    }
}

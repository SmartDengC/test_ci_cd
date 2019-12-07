package com.pojo;

import java.time.Year;

public class KDD {
    private String NF;
    private int ID;
    private String EMS;
    private int AVAI;
    private String YEAR;
    private String SRJ;
    private String LXDH;
    private String JTDZ;
    private String XM;
    private String KSH;


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

    public String getEMS() {
        return EMS;
    }

    public void setEMS(String EMS) {
        this.EMS = EMS;
    }

    public int getAVAI() {
        return AVAI;
    }

    public void setAVAI(int AVAI) {
        this.AVAI = AVAI;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getSRJ() {
        return SRJ;
    }

    public void setSRJ(String SRJ) {
        this.SRJ = SRJ;
    }

    public String getLXDH() {
        return LXDH;
    }

    public void setLXDH(String LXDH) {
        this.LXDH = LXDH;
    }

    public String getJTDZ() {
        return JTDZ;
    }

    public void setJTDZ(String JTDZ) {
        this.JTDZ = JTDZ;
    }

    public String getXM() {
        return XM;
    }

    public void setXM(String XM) {
        this.XM = XM;
    }

    public String getKSH() {
        return KSH;
    }

    public void setKSH(String KSH) {
        this.KSH = KSH;
    }

    @Override
    public String toString() {
        return "KDD{" +
                "ID=" + ID +
                ", EMS='" + EMS + '\'' +
                ", AVAI=" + AVAI +
                ", YEAR='" + YEAR + '\'' +
                ", SRJ='" + SRJ + '\'' +
                ", LXDH='" + LXDH + '\'' +
                ", JTDZ='" + JTDZ + '\'' +
                ", XM='" + XM + '\'' +
                ", KSH='" + KSH + '\'' +
                '}';
    }
}

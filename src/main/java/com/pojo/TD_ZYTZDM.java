package com.pojo;

/**
 * @ClassName TD_ZYTZDM
 * @Version 1.0
 * @Author Rose
 * @Date 2019/10/31 16:25
 * @Description
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
public class TD_ZYTZDM {
    private int ID;
    private String ZYTZDM;
    private String ZYTZMC;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getZYTZDM() {
        return ZYTZDM;
    }

    public void setZYTZDM(String ZYTZDM) {
        this.ZYTZDM = ZYTZDM;
    }

    public String getZYTZMC() {
        return ZYTZMC;
    }

    public void setZYTZMC(String ZYTZMC) {
        this.ZYTZMC = ZYTZMC;
    }

    @Override
    public String toString() {
        return "TD_ZYTZDM{" +
                "ID=" + ID +
                ", ZYTZDM='" + ZYTZDM + '\'' +
                ", ZYTZMC='" + ZYTZMC + '\'' +
                '}';
    }
}
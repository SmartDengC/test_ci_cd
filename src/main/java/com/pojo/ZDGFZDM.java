package com.pojo;

/**
 * 对应 ZDGFZDM 字段规范值表
 */
public class ZDGFZDM {
    private int ZDGFZDM;   //(字段规范值):主键
    private String ZD;
    private String GFZ;

    public int getZDGFZDM() {
        return ZDGFZDM;
    }

    public void setZDGFZDM(int ZDGFZDM) {
        this.ZDGFZDM = ZDGFZDM;
    }

    public String getZD() {
        return ZD;
    }

    public void setZD(String ZD) {
        this.ZD = ZD;
    }

    public String getGFZ() {
        return GFZ;
    }

    public void setGFZ(String GFZ) {
        this.GFZ = GFZ;
    }
}

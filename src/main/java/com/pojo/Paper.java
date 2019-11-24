package com.pojo;/**
 * @title: Paper
 * @projectName ssm
 * @description: TODO
 * @author msi
 * @date 2019/9/512:21
 */

/**
 * @program: ssm
 *
 * @description: test
 *
 * @author: Mr.Wang
 *
 * @create: 2019-09-05 12:21
 **/
public class Paper {
    private long paperId;
    private String paperName;
    private int paperNum;
    private String paperDetail;

    public long getPaperId() {
        return paperId;
    }

    public void setPaperId(long paperId) {
        this.paperId = paperId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public int getPaperNum() {
        return paperNum;
    }

    public void setPaperNum(int paperNum) {
        this.paperNum = paperNum;
    }

    public String getPaperDetail() {
        return paperDetail;
    }

    public void setPaperDetail(String paperDetail) {
        this.paperDetail = paperDetail;
    }
}

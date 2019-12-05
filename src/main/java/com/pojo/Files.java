package com.pojo;

/**
 * @ClassName Files
 * @Version 1.0
 * @Author Lenovo
 * @Date 2019/11/21 20:02
 * @Description TODO
 * Modification User： 程序修改时由修改人员编写
 * Modification Date： 程序修改时间
 */
public class Files {
    private String province;
    private String fileData;
    private String fileName;
    private String fileSize;
    private String year;
    private String path;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
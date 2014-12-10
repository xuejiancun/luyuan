package com.luyuan.mobile.model;

// 系统版本
public class VersionData {

    private String success = "";
    private int code = 0;
    private String version = "";
    private String url = "";
    private int need = 0;
    private float size = 0;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNeed() {
        return need;
    }

    public void setNeed(int need) {
        this.need = need;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}

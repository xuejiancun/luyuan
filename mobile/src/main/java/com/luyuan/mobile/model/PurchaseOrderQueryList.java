package com.luyuan.mobile.model;

public class PurchaseOrderQueryList {

    private String whpCode = "";
    private String whName = "";
    private String UnitLongName = "";
    private String PreparedTime = "";
    private String Status = "";

    public String getwhpCode() {
        return whpCode;
    }

    public void setwhpCode(String whpCode) {
        this.whpCode = whpCode;
    }

    public String getwhName() {
        return whName;
    }

    public void setwhName(String whName) {
        this.whName = whName;
    }
    
    public String getUnitLongName() {
        return UnitLongName;
    }

    public void setUnitLongName(String UnitLongName) {
        this.UnitLongName = UnitLongName;
    }
    
    public String getPreparedTime() {
        return PreparedTime;
    }

    public void setPreparedTime(String PreparedTime) {
        this.PreparedTime = PreparedTime;
    }
    
    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}

package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class PurwhunitData {

    private String success = "";
    private List<UnitInfo>unitInfo = new ArrayList<UnitInfo>();
    private List<WhInfo>whInfo = new ArrayList<WhInfo>();
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<UnitInfo> getunitInfo() {
        return unitInfo;
    }

    public void setunitInfo(List<UnitInfo> unitInfo) {
        this.unitInfo = unitInfo;
    }
    
    public List<WhInfo> getwhInfo() {
        return whInfo;
    }

    public void setwhInfo(List<WhInfo> whInfo) {
        this.whInfo = whInfo;
    }
}

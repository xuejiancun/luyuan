package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class whBinExchangeData {

    private String success = "";
    private String info = "";
    private String singleInfo = "";
    private String inbin = "";
    private String outbin = "";
    private String recentproduct = "";


    private String UnitID = "";

    public String getUnitID() {
        return UnitID;
    }

    public void setUnitID(String unitID) {
        UnitID = unitID;
    }

    public String getRecentproduct() {
        return recentproduct;
    }

    public void setRecentproduct(String recentproduct) {
        this.recentproduct = recentproduct;
    }

    private List<whBinExchangeDetail> data = new ArrayList<whBinExchangeDetail>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public List<whBinExchangeDetail> getData() {
        return data;
    }

    public void setData(List<whBinExchangeDetail> data) {
        this.data = data;
    }

    public String getInbin() {
        return inbin;
    }

    public void setInbin(String inbin) {
        this.inbin = inbin;
    }

    public String getOutbin() {
        return outbin;
    }

    public void setOutbin(String outbin) {
        this.outbin = outbin;
    }

    public String getSingleInfo() {
        return singleInfo;
    }

    public void setSingleInfo(String singleInfo) {
        this.singleInfo = singleInfo;
    }
}

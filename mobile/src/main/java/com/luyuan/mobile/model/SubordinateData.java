package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class SubordinateData {

    private String success = "";
    private List<SubordinateInfo> subordinateInfos = new ArrayList<SubordinateInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<SubordinateInfo> getSubordinateInfos() {
        return subordinateInfos;
    }

    public void setSubordinateInfos(List<SubordinateInfo> subordinateInfos) {
        this.subordinateInfos = subordinateInfos;
    }

}

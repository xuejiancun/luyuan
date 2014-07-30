package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class DedicatedData {

    private String success = "";
    private List<DedicatedInfo> dedicatedInfos = new ArrayList<DedicatedInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<DedicatedInfo> getDedicatedInfos() {
        return dedicatedInfos;
    }

    public void setDedicatedInfos(List<DedicatedInfo> dedicatedInfos) {
        this.dedicatedInfos = dedicatedInfos;
    }
}

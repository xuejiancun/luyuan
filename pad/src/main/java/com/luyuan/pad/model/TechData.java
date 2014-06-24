package com.luyuan.pad.model;

import java.util.ArrayList;
import java.util.List;

public class TechData {

    private String success;
    private List<TechInfo> techInfos = new ArrayList<TechInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<TechInfo> getTechInfos() {
        return techInfos;
    }

    public void setTechInfos(List<TechInfo> techInfos) {
        this.techInfos = techInfos;
    }

}

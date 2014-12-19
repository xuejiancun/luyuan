package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class ProvinceData {

    private String success = "";
    private List<Province> provinces = new ArrayList<Province>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }
}

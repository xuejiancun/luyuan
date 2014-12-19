package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class CityData {

    private String success = "";
    private List<City> citys = new ArrayList<City>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<City> getCitys() {
        return citys;
    }

    public void setCitys(List<City> citys) {
        this.citys = citys;
    }
}

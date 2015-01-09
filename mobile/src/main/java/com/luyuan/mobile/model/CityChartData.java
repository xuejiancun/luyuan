package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class CityChartData {

    private String success = "";
    private List<CityChart> cityCharts = new ArrayList<CityChart>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<CityChart> getCityCharts() {
        return cityCharts;
    }

    public void setCityCharts(List<CityChart> cityCharts) {
        this.cityCharts = cityCharts;
    }
}

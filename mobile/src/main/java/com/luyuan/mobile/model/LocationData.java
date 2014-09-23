package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

// 地理位置
public class LocationData {

    private String success = "";
    private List<LocationInfo> locationInfos = new ArrayList<LocationInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<LocationInfo> getLocationInfos() {
        return locationInfos;
    }

    public void setLocationInfos(List<LocationInfo> locationInfos) {
        this.locationInfos = locationInfos;
    }

}

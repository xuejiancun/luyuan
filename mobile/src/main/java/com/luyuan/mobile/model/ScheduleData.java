package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

// 日程
public class ScheduleData {

    private String success = "";
    private String userId = "";
    private List<ScheduleInfo> scheduleInfos = new ArrayList<ScheduleInfo>();
    private List<LocationInfo> locationInfos = new ArrayList<LocationInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ScheduleInfo> getScheduleInfos() {
        return scheduleInfos;
    }

    public void setScheduleInfos(List<ScheduleInfo> scheduleInfos) {
        this.scheduleInfos = scheduleInfos;
    }

    public List<LocationInfo> getLocationInfos() {
        return locationInfos;
    }

    public void setLocationInfos(List<LocationInfo> locationInfos) {
        this.locationInfos = locationInfos;
    }
}

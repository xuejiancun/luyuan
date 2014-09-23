package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

// 设过日程的天
public class DayData {

    private String success = "";
    private List<DayInfo> dayInfos = new ArrayList<DayInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<DayInfo> getDayInfos() {
        return dayInfos;
    }

    public void setDayInfos(List<DayInfo> dayInfos) {
        this.dayInfos = dayInfos;
    }
}

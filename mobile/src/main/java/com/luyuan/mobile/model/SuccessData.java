package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

// 成功
public class SuccessData {

    private String success = "";
    private String info = "";
    private List<RecieveInfo> data= new ArrayList<RecieveInfo>();

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<RecieveInfo> getData() {
        return data;
    }

    public void setData(List<RecieveInfo> data) {
        this.data = data;
    }

}

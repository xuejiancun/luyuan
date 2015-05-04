package com.luyuan.mobile.model;

import java.util.List;

/**
 * Created by CK on 2015/4/3.
 */
public class modifyData {
    private String total;
    private String success;
    private String error;
    private String singleInfo;
    private List<Data> data;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSingleInfo() {
        return singleInfo;
    }

    public void setSingleInfo(String singleInfo) {
        this.singleInfo = singleInfo;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}

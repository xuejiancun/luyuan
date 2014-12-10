package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class ReturnJson {
    private Integer total = 0;
    private Boolean success = Boolean.TRUE;
    private String error = "";
    private String singleInfo = "";
    private List<ReturnJson_Data> data = new ArrayList<ReturnJson_Data>();

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
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

    public List<ReturnJson_Data> getData() {
        return data;
    }

    public void setData(List<ReturnJson_Data> data) {
        this.data = data;
    }

}

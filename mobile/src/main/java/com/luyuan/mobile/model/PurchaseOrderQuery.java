package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderQuery {

    private String success = "";
    private String info = "";
    private List<PurchaseOrderQueryList> data = new ArrayList<PurchaseOrderQueryList>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<PurchaseOrderQueryList> getData() {
        return data;
    }

    public void setData(List<PurchaseOrderQueryList> data) {
        this.data = data;
    }
    
}

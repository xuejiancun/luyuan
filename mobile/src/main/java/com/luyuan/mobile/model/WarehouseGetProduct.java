package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class WarehouseGetProduct {

    private String success = "";
    private List<WarehouseGetProductInfo> data = new ArrayList<WarehouseGetProductInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<WarehouseGetProductInfo> getWarehouseGetProductInfo() {
        return data;
    }

    public void setWarehouseGetProductInfo(List<WarehouseGetProductInfo> data) {
        this.data = data;
    }

}

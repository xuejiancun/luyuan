package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class WarehouseGetBin {

    private String success = "";
    private List<WarehouseGetBinInfo> data = new ArrayList<WarehouseGetBinInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<WarehouseGetBinInfo> getWarehouseGetBinInfo() {
        return data;
    }

    public void setWarehouseGetBinInfo(List<WarehouseGetBinInfo> data) {
        this.data = data;
    }

}

package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class WarehouseInventoryData {

    private String success = "";
    private List<WarehouseInventoryDataInfo> data = new ArrayList<WarehouseInventoryDataInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<WarehouseInventoryDataInfo> getWarehouseInventoryDataInfos() {
        return data;
    }

    public void setWarehouseInventoryDataInfos(List<WarehouseInventoryDataInfo> data) {
        this.data = data;
    }

}

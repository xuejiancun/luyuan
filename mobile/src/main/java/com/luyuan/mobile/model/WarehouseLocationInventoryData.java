package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class WarehouseLocationInventoryData {

    private String success = "";
    private List<WarehouseLocationInventoryDetail> data = new ArrayList<WarehouseLocationInventoryDetail>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<WarehouseLocationInventoryDetail> getWarehouseLocationInventoryDetail() {
        return data;
    }

    public void setWarehouseLocationInventoryDetail(List<WarehouseLocationInventoryDetail> data) {
        this.data = data;
    }

}

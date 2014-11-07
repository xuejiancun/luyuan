package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class WarehouseInventoryBook {

    private String success = "";
    private List<WarehouseInventoryBookDetail> data = new ArrayList<WarehouseInventoryBookDetail>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<WarehouseInventoryBookDetail> getWarehouseInventoryBookDetail() {
        return data;
    }

    public void setWarehouseInventoryBookDetail(List<WarehouseInventoryBookDetail> data) {
        this.data = data;
    }

}

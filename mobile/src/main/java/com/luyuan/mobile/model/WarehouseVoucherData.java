package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class WarehouseVoucherData {

    private String success = "";
    private List<WarehouseVoucherInfo> warehouseVoucherInfos = new ArrayList<WarehouseVoucherInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<WarehouseVoucherInfo> getWarehouseVoucherInfos() {
        return warehouseVoucherInfos;
    }

    public void setWarehouseVoucherInfos(List<WarehouseVoucherInfo> data) {
        this.warehouseVoucherInfos = data;
    }

}

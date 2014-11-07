package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class WarehouseVoucherData {

    private String success = "";
    private List<WarehouseVoucherInfo> data = new ArrayList<WarehouseVoucherInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<WarehouseVoucherInfo> getWarehouseVoucherInfos() {
        return data;
    }

    public void setWarehouseVoucherInfos(List<WarehouseVoucherInfo> data) {
        this.data = data;
    }

}

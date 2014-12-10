package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class WarehouseVoucheritemList {

    private String success = "";
    private List<WarehouseVoucheritemDetailList> data = new ArrayList<WarehouseVoucheritemDetailList>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<WarehouseVoucheritemDetailList> getWarehouseVoucheritemDetailList() {
        return data;
    }

    public void setWarehouseVoucheritemDetailList(List<WarehouseVoucheritemDetailList> data) {
        this.data = data;
    }

}

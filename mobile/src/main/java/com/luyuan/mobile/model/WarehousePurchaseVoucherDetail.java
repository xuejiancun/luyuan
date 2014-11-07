package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class WarehousePurchaseVoucherDetail {

    private String success = "";
    private List<WarehousePurchaseVoucherDetailInfo> data = new ArrayList<WarehousePurchaseVoucherDetailInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<WarehousePurchaseVoucherDetailInfo> getWarehousePurchaseVoucherDetailInfo() {
        return data;
    }

    public void setWarehousePurchaseVoucherDetailInfo(List<WarehousePurchaseVoucherDetailInfo> data) {
        this.data = data;
    }

}

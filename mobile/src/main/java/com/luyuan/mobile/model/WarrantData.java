package com.luyuan.mobile.model;

public class WarrantData {

    private String WarrantTotalAmount = "0";
    private String WarrantNowAmount = "0";
    private String subAmount = "0";

    public String getWarrantTotalAmount() {
        return WarrantTotalAmount;
    }

    public void setWarrantTotalAmount(String warrantTotalAmount) {
        WarrantTotalAmount = warrantTotalAmount;
    }

    public String getWarrantNowAmount() {
        return WarrantNowAmount;
    }

    public void setWarrantNowAmount(String warrantNowAmount) {
        WarrantNowAmount = warrantNowAmount;
    }

    public String getSubAmount() {
        return subAmount;
    }

    public void setSubAmount(String subAmount) {
        this.subAmount = subAmount;
    }
}

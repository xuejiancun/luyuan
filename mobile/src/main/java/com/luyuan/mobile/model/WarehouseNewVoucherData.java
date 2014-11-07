package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class WarehouseNewVoucherData {

    private String success = "";
    private String info="";
    private List<WarehouseNewVoucherDataInfo> data = new ArrayList<WarehouseNewVoucherDataInfo>();

    public String getSuccess() {
        return success;
    }
    public void setSuccess(String success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }


    public List<WarehouseNewVoucherDataInfo> getData() {
        return data;
    }

    public void setData(List<WarehouseNewVoucherDataInfo> data) {
        this.data = data;
    }
    
}

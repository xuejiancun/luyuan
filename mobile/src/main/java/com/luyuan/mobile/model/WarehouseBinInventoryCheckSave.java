package com.luyuan.mobile.model;

import java.util.HashMap;
import java.util.Map;

public class WarehouseBinInventoryCheckSave {

    private String wbIDDetail;
    private String wbName;
    private String ProductBarCodes;
    private String UnitID;
    private String HeID;
    private String tbl_LocationInventoryDetails;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getHeID() {
        return HeID;
    }

    public void setHeID(String HeID) {
        this.HeID = HeID;
    }

    public String getUnitID() {
        return UnitID;
    }

    public void setUnitID(String UnitID) {
        this.UnitID = UnitID;
    }

    public String getWbName() {
        return wbName;
    }

    public void setWbName(String wbName) {
        this.wbName = wbName;
    }

    public String getProductBarCodes() {
        return ProductBarCodes;
    }

    public void setProductBarCodes(String ProductBarCodes) {
        this.ProductBarCodes = ProductBarCodes;
    }

}

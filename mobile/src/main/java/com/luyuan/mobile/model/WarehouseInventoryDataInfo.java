package com.luyuan.mobile.model;


public class WarehouseInventoryDataInfo {

    private String itemID = "";
    private String itemCode = "";
    private String itemName = "";
    private String whName = "";
    private String whID = "";
    private String Qty = "";
    private String InventoryUOM = "";

    public String getitemID() {
        return itemID;
    }

    public void setitemID(String itemID) {
        this.itemID = itemID;
    }

    public String getitemCode() {
        return itemCode;
    }

    public void setitemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getitemName() {
        return itemName;
    }

    public void setitemName(String itemName) {
        this.itemName = itemName;
    }

    public String getwhID() {
        return whID;
    }

    public void setwhID(String whID) {
        this.whID = whID;
    }

    public String getwhName() {
        return whName;
    }

    public void setwhName(String whName) {
        this.whName = whName;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String Qty) {
        this.Qty = Qty;
    }


    public String getInventoryUOM() {
        return InventoryUOM;
    }

    public void setInventoryUOM(String InventoryUOM) {
        this.InventoryUOM = InventoryUOM;
    }

}

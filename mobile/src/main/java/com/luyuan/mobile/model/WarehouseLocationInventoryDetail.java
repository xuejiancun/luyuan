package com.luyuan.mobile.model;

public class WarehouseLocationInventoryDetail {

    private String wbCode = "";
    private String wbName = "";
    private String UnitID = "";
    private String ProductCode = "";
    private String wbID = "";
    private String wbIDDetail = "";
    private String PrefixName = "";
    private String SpecType = "";
    private String Qty = "";
    private String FreezeQty = "";

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    private String itemSpec = "";
    private String ActualQty = "";

    public String getwbCode() {
        return wbCode;
    }

    public void setwbCode(String wbCode) {
        this.wbCode = wbCode;
    }


    public String getwbName() {
        return wbName;
    }

    public void setwbName(String wbName) {
        this.wbName = wbName;
    }

    public String getActualQty() {
        return ActualQty;
    }

    public void setActualQty(String actualQty) {
        ActualQty = actualQty;
    }


    public String getUnitID() {
        return UnitID;
    }

    public void setUnitID(String UnitID) {
        this.UnitID = UnitID;
    }

    public String getwbID() {
        return wbID;
    }

    public void setwbID(String wbID) {
        this.wbID = wbID;
    }

    public String getwbIDDetail() {
        return wbIDDetail;
    }

    public void setwbIDDetail(String wbIDDetail) {
        this.wbIDDetail = wbIDDetail;
    }

    public String getPrefixName() {
        return PrefixName;
    }

    public void setPrefixName(String PrefixName) {
        this.PrefixName = PrefixName;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String ProductCode) {
        this.ProductCode = ProductCode;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String Qtye) {
        this.Qty = Qty;
    }


    public String getFreezeQty() {
        return FreezeQty;
    }

    public void setFreezeQty(String FreezeQty) {
        this.FreezeQty = FreezeQty;
    }

    public String getSpecType() {
        return SpecType;
    }

    public void setSpecType(String SpecType) {
        this.SpecType = SpecType;
    }
}

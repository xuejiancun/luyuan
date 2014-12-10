package com.luyuan.mobile.model;

public class whBinExchangeDetail {

    private String ProductCode = "";
    private String PrefixName = "";
    private String ColorName = "";
    private String Qty = "";
    private String ProductBarcode = "";

    public String getProductBarCode() {
        return ProductBarcode;
    }

    public void setProductBarCode(String productBarcode) {
        ProductBarcode = productBarcode;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getColorName() {
        return ColorName;
    }

    public void setColorName(String colorName) {
        ColorName = colorName;
    }

    public String getPrefixName() {
        return PrefixName;
    }

    public void setPrefixName(String prefixName) {
        PrefixName = prefixName;
    }
}

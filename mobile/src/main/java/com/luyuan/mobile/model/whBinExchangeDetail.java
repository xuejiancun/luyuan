package com.luyuan.mobile.model;

public class whBinExchangeDetail {

    public String ProductCode = "";
    public String PrefixName = "";
    public String ColorName = "";
    public String Qty = "";
    public String ProductBarcode = "";
    public String result = "";

    public String getresult() {
        return result;
    }

    public void setresult(String result) {
        this.result = result;
    }

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

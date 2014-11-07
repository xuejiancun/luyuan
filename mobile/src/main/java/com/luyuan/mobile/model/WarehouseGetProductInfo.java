package com.luyuan.mobile.model;

public class WarehouseGetProductInfo {

    private String ProductCode="";
    private String PrefixName="";
    private String SpecType="";
	private String Unit="";
    public String getProductCode() {
        return ProductCode;
    }
    public void setProductCode(String ProductCode) {
        this.ProductCode = ProductCode;
    }


    public String getPrefixName() {
        return PrefixName;
    }
    public void setPrefixName(String PrefixName) {
        this.PrefixName = PrefixName;
    }

    public String getSpecType() {
        return SpecType;
    }
    public void setSpecType(String SpecType) {
        this.SpecType = SpecType;
    }

	public String getUnit() {
		return Unit;
	}
	public void setUnit(String Unit) {
		this.Unit = Unit;
	}

}

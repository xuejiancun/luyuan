package com.luyuan.mobile.model;

public class tbl_whPurchaseOrderDetail {

    private String itemName = "";
    private String itemCode="";
    private String itemSpec="";
    private String InventoryUOM="";
    private String ExamineQTY="";
    private String QTY="";
    private String itemID="";
    private String itemCount="";
	private String ActualQTY="";
	private String BADQTY="";

	private String getActualQTY() {
		return ActualQTY;
	}

	public void setActualQTY(String ActualQTY) {
		this.ActualQTY = ActualQTY;
	}

	private String getBADQTY() {
		return BADQTY;
	}

	public void setBADQTY(String BADQTY) {
		this.BADQTY = BADQTY;
	}

	private String getitemName() {
        return itemName;
    }

    public void setitemName(String itemName) {
        this.itemName = itemName;
    }

    public String getitemCode() {
        return itemCode;
    }

    public void setitemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    
    public String getitemSpec() {
        return itemSpec;
    }

    public void setitemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getInventoryUOM() {
        return InventoryUOM;
    }

    public void setInventoryUOM(String InventoryUOM) {
        this.InventoryUOM = InventoryUOM;
    }

    public String getExamineQTY() {
        return ExamineQTY;
    }

    public void setExamineQTY(String ExamineQTY) {
        this.ExamineQTY = ExamineQTY;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }

    public String getitemID() {
        return itemID;
    }

    public void setitemID(String itemID) {
        this.itemID = itemID;
    }

    public String getitemCount() {
        return itemCount;
    }

    public void setitemCount(String itemCount) {
        this.itemCount = itemCount;
    }
}

package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class tbl_whPurchaseOrder {

    private String whpCode="";
    private String InwhID="";
    private String OutUnitID="";
    private String OtherOrderCode="";
    private String Batch="";
    private String Preparedby="";
    private String PreparedTime="";
	private String ExamineBy="";
	private String success="";
	private String info = "";
    private List<tbl_whPurchaseOrderDetail> data = new ArrayList<tbl_whPurchaseOrderDetail>();

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
    public String getwhpCode() {
        return whpCode;
    }

    public void setwhpCode(String whpCode) {
        this.whpCode = whpCode;
    }

    public String getUnitID() {
        return OutUnitID;
    }

    public void setUnitID(String OutUnitID) {
        this.OutUnitID = OutUnitID;
    }

    public String getwhID() {
        return InwhID;
    }

    public void setwhID(String InwhID) {
        this.InwhID = InwhID;
    }

    public String getOtherOrderCode() {
        return OtherOrderCode;
    }

    public void setOtherOrderCode(String OtherOrderCode) {
        this.OtherOrderCode = OtherOrderCode;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String Batch) {
        this.Batch = Batch;
    }


    public String getPreparedby() {
        return Preparedby;
    }

    public void setPreparedby(String Preparedby) {
        this.Preparedby = Preparedby;
    }


    public String getPreparedTime() {
        return PreparedTime;
    }

    public void setPreparedTime(String PreparedTime) {
        this.PreparedTime = PreparedTime;
    }

	public String getExamineBy() {
		return ExamineBy;
	}

	public void setExamineBy(String ExamineBy) {
		this.ExamineBy = ExamineBy;
	}
    public List<tbl_whPurchaseOrderDetail> gettbl_whPurchaseOrderDetail() {
        return data;
    }

    public void settbl_whPurchaseOrderDetail(List<tbl_whPurchaseOrderDetail> data) {
        this.data = data;
    }
    
}

package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;


public class WhBinInventoryChecks {

	private Integer total;
	private Boolean success;
	private String error;
	private String wbName;
	private List<WhBinInventoryChecks_Data> data = new ArrayList<WhBinInventoryChecks_Data>();


	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getWbName() {
		return wbName;
	}

	public void setWbName(String wbName) {
		this.wbName = wbName;
	}

	public List<WhBinInventoryChecks_Data> getData() {
		return data;
	}

	public void setData(List<WhBinInventoryChecks_Data> data) {
		this.data = data;
	}



}


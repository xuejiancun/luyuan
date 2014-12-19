package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class DealerData {

    private String success = "";
    private List<Dealer> dealers = new ArrayList<Dealer>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Dealer> getDealers() {
        return dealers;
    }

    public void setDealers(List<Dealer> dealers) {
        this.dealers = dealers;
    }
}

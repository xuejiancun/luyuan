package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class DealerNewData {

    private String success = "";
    private List<DealerNew> dealers = new ArrayList<DealerNew>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<DealerNew> getDealers() {
        return dealers;
    }

    public void setDealers(List<DealerNew> dealers) {
        this.dealers = dealers;
    }
}

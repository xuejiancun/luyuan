package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class DealerAccountData {

    private String success = "";
    private List<DealerAccount> dealeraccounts = new ArrayList<DealerAccount>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<DealerAccount> getDealeraccounts() {
        return dealeraccounts;
    }

    public void setDealeraccounts(List<DealerAccount> dealeraccounts) {
        this.dealeraccounts = dealeraccounts;
    }
}

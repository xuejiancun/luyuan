package com.luyuan.mobile.model;

public class DealerAccount {

    private String id;
    private String DealerCode;
    private String Balance;
    private String CreditLimit;
    private String Amount;
    private String MoreTimes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDealerCode() {
        return DealerCode;
    }

    public void setDealerCode(String dealerCode) {
        DealerCode = dealerCode;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getCreditLimit() {
        return CreditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        CreditLimit = creditLimit;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getMoreTimes() {
        return MoreTimes;
    }

    public void setMoreTimes(String moreTimes) {
        MoreTimes = moreTimes;
    }
}

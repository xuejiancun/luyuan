package com.luyuan.pad.model;

import java.util.ArrayList;
import java.util.List;

public class ProductThumbData {

    private String success;
    private List<ProductThumbInfo> productThumbInfos = new ArrayList<ProductThumbInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ProductThumbInfo> getProductThumbInfos() {
        return productThumbInfos;
    }

    public void setProductThumbInfos(List<ProductThumbInfo> productThumbInfos) {
        this.productThumbInfos = productThumbInfos;
    }
}

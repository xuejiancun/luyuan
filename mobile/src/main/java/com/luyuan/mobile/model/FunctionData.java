package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

// 功能
public class FunctionData {

    private String success = "";
    private List<FunctionInfo> functionInfos = new ArrayList<FunctionInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FunctionInfo> getFunctionInfos() {
        return functionInfos;
    }

    public void setFunctionInfos(List<FunctionInfo> functionInfos) {
        this.functionInfos = functionInfos;
    }

}

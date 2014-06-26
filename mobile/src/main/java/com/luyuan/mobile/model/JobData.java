package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class JobData {

    private String success = "";
    private List<JobInfo> jobInfos = new ArrayList<JobInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<JobInfo> getJobInfos() {
        return jobInfos;
    }

    public void setJobInfos(List<JobInfo> jobInfos) {
        this.jobInfos = jobInfos;
    }

}

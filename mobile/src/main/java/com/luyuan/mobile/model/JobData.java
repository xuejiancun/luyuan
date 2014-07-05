package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class JobData {

    private String success = "";
    private String userId = "";
    private String sessionId = "";
    private List<JobInfo> jobInfos = new ArrayList<JobInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<JobInfo> getJobInfos() {
        return jobInfos;
    }

    public void setJobInfos(List<JobInfo> jobInfos) {
        this.jobInfos = jobInfos;
    }
    
}

package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

// 资料上传通道
public class ChannelData {

    private String success = "";
    private List<ChannelInfo> channelInfos = new ArrayList<ChannelInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ChannelInfo> getChannelInfos() {
        return channelInfos;
    }

    public void setChannelInfos(List<ChannelInfo> channelInfos) {
        this.channelInfos = channelInfos;
    }

}

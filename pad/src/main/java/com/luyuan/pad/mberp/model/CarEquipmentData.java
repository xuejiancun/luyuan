package com.luyuan.pad.mberp.model;

import java.util.ArrayList;
import java.util.List;

public class CarEquipmentData {

    private String success;
    private List<CarEquipmentInfo> carEquipmentInfos = new ArrayList<CarEquipmentInfo>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<CarEquipmentInfo> getCarEquipmentInfos() {
        return carEquipmentInfos;
    }

    public void setCarEquipmentInfos(List<CarEquipmentInfo> carEquipmentInfos) {
        this.carEquipmentInfos = carEquipmentInfos;
    }

}

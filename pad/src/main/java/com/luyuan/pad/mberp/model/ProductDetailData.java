package com.luyuan.pad.mberp.model;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailData {

    private String success;
    private String model;
    private String arrivaldate;
    private String size;
    private String footsteplength;
    private String liftofflength;
    private String endurance;
    private String tyreform;
    private String tyrespec;
    private String brake;
    private String suspension;
    private String enginespec;
    private String batteryspec;
    private String charger;
    private List<CarAppearanceSlide> carAppearanceSlides = new ArrayList<CarAppearanceSlide>();
    private List<CarDetailSlide> carDetailSlides = new ArrayList<CarDetailSlide>();
    private List<CarColorSlide> carColorSlides = new ArrayList<CarColorSlide>();
    private List<CarEquipmentSlide> carEquipmentSlides = new ArrayList<CarEquipmentSlide>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getArrivaldate() {
        return arrivaldate;
    }

    public void setArrivaldate(String arrivaldate) {
        this.arrivaldate = arrivaldate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFootsteplength() {
        return footsteplength;
    }

    public void setFootsteplength(String footsteplength) {
        this.footsteplength = footsteplength;
    }

    public String getLiftofflength() {
        return liftofflength;
    }

    public void setLiftofflength(String liftofflength) {
        this.liftofflength = liftofflength;
    }

    public String getEndurance() {
        return endurance;
    }

    public void setEndurance(String endurance) {
        this.endurance = endurance;
    }

    public String getTyreform() {
        return tyreform;
    }

    public void setTyreform(String tyreform) {
        this.tyreform = tyreform;
    }

    public String getTyrespec() {
        return tyrespec;
    }

    public void setTyrespec(String tyrespec) {
        this.tyrespec = tyrespec;
    }

    public String getBrake() {
        return brake;
    }

    public void setBrake(String brake) {
        this.brake = brake;
    }

    public String getSuspension() {
        return suspension;
    }

    public void setSuspension(String suspension) {
        this.suspension = suspension;
    }

    public String getEnginespec() {
        return enginespec;
    }

    public void setEnginespec(String enginespec) {
        this.enginespec = enginespec;
    }

    public String getBatteryspec() {
        return batteryspec;
    }

    public void setBatteryspec(String batteryspec) {
        this.batteryspec = batteryspec;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public List<CarAppearanceSlide> getCarAppearanceSlides() {
        return carAppearanceSlides;
    }

    public void setCarAppearanceSlides(List<CarAppearanceSlide> carAppearanceSlides) {
        this.carAppearanceSlides = carAppearanceSlides;
    }

    public List<CarDetailSlide> getCarDetailSlides() {
        return carDetailSlides;
    }

    public void setCarDetailSlides(List<CarDetailSlide> carDetailSlides) {
        this.carDetailSlides = carDetailSlides;
    }

    public List<CarColorSlide> getCarColorSlides() {
        return carColorSlides;
    }

    public void setCarColorSlides(List<CarColorSlide> carColorSlides) {
        this.carColorSlides = carColorSlides;
    }

    public List<CarEquipmentSlide> getCarEquipmentSlides() {
        return carEquipmentSlides;
    }

    public void setCarEquipmentSlides(List<CarEquipmentSlide> carEquipmentSlides) {
        this.carEquipmentSlides = carEquipmentSlides;
    }
    
}

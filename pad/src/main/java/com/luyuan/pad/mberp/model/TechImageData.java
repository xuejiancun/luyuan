package com.luyuan.pad.mberp.model;

import java.util.ArrayList;
import java.util.List;

public class TechImageData {

    private String success;
    private List<TechImageSlide> techImageSlides = new ArrayList<TechImageSlide>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<TechImageSlide> getTechImageSlides() {
        return techImageSlides;
    }

    public void setTechImageSlides(List<TechImageSlide> techImageSlides) {
        this.techImageSlides = techImageSlides;
    }

}

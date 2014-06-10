package com.luyuan.pad.mberp.model;

import java.util.ArrayList;
import java.util.List;

public class PopularCarData {

    private String success;
    private List<PopularCarSlide> popularCarSlides = new ArrayList<PopularCarSlide>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<PopularCarSlide> getPopularCarSlides() {
        return popularCarSlides;
    }

    public void setPopularCarSlides(List<PopularCarSlide> popularCarSlides) {
        this.popularCarSlides = popularCarSlides;
    }

}

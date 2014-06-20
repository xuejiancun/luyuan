package com.luyuan.pad.model;

import java.util.ArrayList;
import java.util.List;

public class ImagePager {

    private String success;
    private List<ImageSlide> imageSlides = new ArrayList<ImageSlide>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ImageSlide> getImageSlides() {
        return imageSlides;
    }

    public void setImageSlides(List<ImageSlide> imageSlides) {
        this.imageSlides = imageSlides;
    }

}

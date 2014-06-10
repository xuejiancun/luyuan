package com.luyuan.pad.mberp.model;

import java.util.ArrayList;
import java.util.List;

public class BrandHonorData {

    private String success;
    private List<BrandHonorSlide> brandHonorSlides = new ArrayList<BrandHonorSlide>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<BrandHonorSlide> getBrandHonorSlides() {
        return brandHonorSlides;
    }

    public void setBrandHonorSlides(List<BrandHonorSlide> brandHonorSlides) {
        this.brandHonorSlides = brandHonorSlides;
    }

}

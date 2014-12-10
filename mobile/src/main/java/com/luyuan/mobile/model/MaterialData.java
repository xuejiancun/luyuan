package com.luyuan.mobile.model;

import java.util.ArrayList;
import java.util.List;

// 资料上传
public class MaterialData {

    private String success = "";
    private List<Material> materials = new ArrayList<Material>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

}

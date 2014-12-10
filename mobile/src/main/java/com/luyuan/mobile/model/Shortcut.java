package com.luyuan.mobile.model;

// 常用功能
public class Shortcut {

    private Integer id;
    private String code;
    private String name;

    public Shortcut(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

}

package com.luyuan.mobile.model;

public class Shortcut {

    private Integer id;
    private String name;
    private Integer order;

    public Shortcut(Integer id, String name) {
        this.name = name;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}

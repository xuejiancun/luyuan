package com.luyuan.mobile.model;

public class User {

    private String id = "";
    private String sob = "";
    private String username = "";
    private String password = "";
    private String sessionId = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSob() {
        return sob;
    }

    public void setSob(String sob) {
        this.sob = sob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

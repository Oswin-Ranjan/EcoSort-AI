package com.ecosort.backend.dto;

public class HFMessage {

    private String role;
    private String content;

    public HFMessage() {}

    public HFMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }
}
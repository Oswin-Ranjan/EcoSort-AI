package com.ecosort.app.models;

public class QueryResponse {

    private String answer;
    private String source;
    private int confidence;

    public String getAnswer() {
        return answer;
    }

    public String getSource() {
        return source;
    }

    public int getConfidence() {
        return confidence;
    }
}
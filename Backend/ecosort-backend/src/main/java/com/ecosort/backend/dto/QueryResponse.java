package com.ecosort.backend.dto;

public class QueryResponse {

    private String answer;
    private String source;
    private int confidence;

    public QueryResponse() {
    }

    public QueryResponse(
            String answer,
            String source,
            int confidence) {

        this.answer = answer;
        this.source = source;
        this.confidence = confidence;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }
}
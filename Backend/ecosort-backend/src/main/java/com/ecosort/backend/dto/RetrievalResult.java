package com.ecosort.backend.dto;

public class RetrievalResult {

    private String document;
    private int confidence;

    public RetrievalResult(String document, int confidence) {
        this.document = document;
        this.confidence = confidence;
    }

    public String getDocument() {
        return document;
    }

    public int getConfidence() {
        return confidence;
    }
}
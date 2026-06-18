package com.ecosort.backend.dto;

import java.util.List;

public class HFRequest {

    private String model;
    private List<HFMessage> messages;
    private double temperature;
    private int max_tokens;

    public HFRequest(
            String model,
            List<HFMessage> messages,
            double temperature,
            int max_tokens) {

        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
    }

    public String getModel() {
        return model;
    }

    public List<HFMessage> getMessages() {
        return messages;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getMax_tokens() {
        return max_tokens;
    }
}
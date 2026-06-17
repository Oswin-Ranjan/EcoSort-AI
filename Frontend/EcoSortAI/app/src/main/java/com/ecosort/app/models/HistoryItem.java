package com.ecosort.app.models;

public class HistoryItem {

    private String question;
    private String answer;
    private String source;

    public HistoryItem(String question,
                       String answer,
                       String source) {

        this.question = question;
        this.answer = answer;
        this.source = source;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getSource() {
        return source;
    }
}
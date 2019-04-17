package com.eng.chula.se.hygeia.models;

public class QuestionRequest {
    private String questionId;
    private String textMessage;
    private String askerAccountId;
    private String askerName;

    public String getQuestionId() {
        return questionId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public String getAskerAccountId() {
        return askerAccountId;
    }

    public String getAskerName() {
        return askerName;
    }
}

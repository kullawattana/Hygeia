package com.eng.chula.se.hygeia.models;

public class AnswerRequest {
    private String answerId;
    private String textMessage;
    private String questionId;
    private String answererAccountId;
    private String answererName;

    public String getAnswerId() {
        return answerId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getAnswererAccountId() {
        return answererAccountId;
    }

    public String getAnswererName() {
        return answererName;
    }
}

package com.eng.chula.se.hygeia.models;

public class DrugRequest {
    private String requestId;
    private String topic;
    private String attachment;


    public DrugRequest(String requestId, String topic, String attachment) {
        this.requestId = requestId;
        this.topic = topic;
        this.attachment = attachment;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getTopic() {
        return topic;
    }

    public String getAttachment() {
        return attachment;
    }
}

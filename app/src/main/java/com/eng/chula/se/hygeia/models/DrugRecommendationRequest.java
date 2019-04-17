package com.eng.chula.se.hygeia.models;

public class DrugRecommendationRequest {

    private String requestId;
    private String requesterId;
    private String pharamacistId;
    private String requestDate;

    public DrugRecommendationRequest(String requestId, String requesterId, String pharamacistId, String requestDate) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.pharamacistId = pharamacistId;
        this.requestDate = requestDate;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public String getPharamacistId() {
        return pharamacistId;
    }

    public String getRequestDate() {
        return requestDate;
    }
}

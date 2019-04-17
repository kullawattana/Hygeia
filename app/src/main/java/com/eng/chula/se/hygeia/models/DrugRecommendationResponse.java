package com.eng.chula.se.hygeia.models;

public class DrugRecommendationResponse {

    private String recommendationId;
    private String creatorId;
    private String creatorName;
    private String receiverId;
    private String receiverName;
    private String createDate;

    public DrugRecommendationResponse(String recommendationId, String creatorId, String creatorName,
                                      String receiverId, String receiverName, String createDate) {
        this.recommendationId = recommendationId;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.createDate = createDate;
    }

    public String getRecommendationId() {
        return recommendationId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void addMedicine(){

    }
}

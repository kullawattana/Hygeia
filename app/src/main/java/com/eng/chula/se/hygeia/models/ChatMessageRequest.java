package com.eng.chula.se.hygeia.models;

import android.graphics.Bitmap;

public class ChatMessageRequest {
    private String messageId;
    private String sendeId;
    private String receiverId;
    private Bitmap image;

    public String getMessageId() {
        return messageId;
    }

    public String getSendeId() {
        return sendeId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public Bitmap getImage() {
        return image;
    }
}

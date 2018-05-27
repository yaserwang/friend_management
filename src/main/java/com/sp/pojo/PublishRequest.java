package com.sp.pojo;

public class PublishRequest {

    private String sender;
    private String text;

    private PublishRequest() {
    }

    public PublishRequest(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }
}

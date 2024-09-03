package com.gamedevs5.gamedevs5.models;

public class Message {
    private String id;
    private String sender;
    private String content;

    public Message() {
    }

    public Message(String id, String sender, String content) {
        this.id = id;
        this.sender = sender;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

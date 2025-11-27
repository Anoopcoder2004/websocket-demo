package com.example.websocket_demo.model;

import java.util.List;

public class ChatMessage {
    private String sender;
    private List<String> receivers; // multiple receivers
    private String content;
    private String timestamp;

    // Getters & Setters
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }
    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

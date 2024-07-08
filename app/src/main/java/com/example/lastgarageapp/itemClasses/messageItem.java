package com.example.lastgarageapp.itemClasses;

public class messageItem {
    private String messageText, hour, sender_id;

    public messageItem(String messageText, String hour, String sender_id) {
        this.messageText = messageText;
        this.hour = hour;
        this.sender_id = sender_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}

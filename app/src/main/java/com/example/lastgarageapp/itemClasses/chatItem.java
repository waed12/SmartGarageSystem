package com.example.lastgarageapp.itemClasses;

public class chatItem {
    private String textName, textMessage, textHour, chatId;

    public chatItem(String textName, String textMessage, String textHour, String chatId) {
        this.textName = textName;
        this.textMessage = textMessage;
        this.textHour = textHour;
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getTextHour() {
        return textHour;
    }

    public void setTextHour(String textHour) {
        this.textHour = textHour;
    }
}

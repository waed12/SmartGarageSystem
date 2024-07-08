package com.example.lastgarageapp.itemClasses;

public class notificationItem {
    private String textName,textHour,newsId;

    public notificationItem(String textName,String textHour, String newsId) {
        this.textName = textName;
        this.textHour=textHour;
        this.newsId=newsId;

    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public String getTextHour() {
        return textHour;
    }

    public void setTextHour(String textHour) {
        this.textHour = textHour;
    }
}

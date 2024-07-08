package com.example.lastgarageapp.itemClasses;


public class garageItem {
    private String cityName, adminName, fromHoure, toHoure, location,id;

    public garageItem(String cityName, String adminName, String fromHoure, String toHoure, String location,String id) {
        this.cityName = cityName;
        this.adminName = adminName;
        this.fromHoure = fromHoure;
        this.toHoure = toHoure;
        this.location = location;
        this.id=id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    public void setid(String id) {
        this.id= id;
    }
    public void setFromHoure(String fromHoure) {
        this.fromHoure = fromHoure;
    }

    public void setToHoure(String toHoure) {
        this.toHoure = toHoure;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCityName() {
        return cityName;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getFromHoure() {
        return fromHoure;
    }

    public String getToHoure() {
        return toHoure;
    }

    public String getLocation() {
        return location;
    }

    public String getadminid() {
        return id;
    }
}

package com.example.lastgarageapp.itemClasses;

public class carItem {
    private String carNumber, driverName, availability,noOfPassenger,capacity, arrivalTime,u_id;

    public carItem(String carNumber,String u_id, String driverName, String availability, String noOfPassenger, String arrivalTime, String capacity) {
        this.carNumber=carNumber;
        this.driverName = driverName;
        this.availability = availability;
        this.noOfPassenger = noOfPassenger;
        this.arrivalTime = arrivalTime;
        this.capacity = capacity;
        this.u_id = u_id;
    }

    public String getuser() {
        return u_id;
    }

    public void setuser(String capacity) {
        this.u_id = u_id;
    }
    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getNoOfPassenger() {
        return noOfPassenger;
    }

    public void setNoOfPassenger(String noOfPassenger) {
        this.noOfPassenger = noOfPassenger;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}

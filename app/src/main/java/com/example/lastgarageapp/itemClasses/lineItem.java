package com.example.lastgarageapp.itemClasses;

public class lineItem {
    private String garage1, garage2, lineFare, noOfCar;

    public lineItem(String garage1, String garage2, String lineFare, String noOfCar) {
        this.garage1 = garage1;
        this.garage2 = garage2;
        this.lineFare = lineFare;
        this.noOfCar = noOfCar;
    }

    public void setGarage1(String garage1) {
        this.garage1 = garage1;
    }

    public void setGarage2(String garage2) {
        this.garage2 = garage2;
    }

    public void setLineFare(String lineFare) {
        this.lineFare = lineFare;
    }

    public void setNoOfCar(String noOfCar) {
        this.noOfCar = noOfCar;
    }

    public String getGarage1() {
        return garage1;
    }

    public String getGarage2() {
        return garage2;
    }

    public String getLineFare() {
        return lineFare;
    }

    public String getNoOfCar() {
        return noOfCar;
    }
}

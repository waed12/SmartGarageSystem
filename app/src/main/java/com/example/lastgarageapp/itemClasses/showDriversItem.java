package com.example.lastgarageapp.itemClasses;

public class showDriversItem {
    private String nameText, sour,dest,id;

    public showDriversItem(String nameText, String sour,String dest,String id) {

        this.id=id;
        this.nameText = nameText;
        this.sour= sour;
        this.dest= dest;

    }
    public void setIdText(String IdText) {
        this.id= IdText;
    }



    public void setnameText(String nameText) {
        this.nameText = nameText;
    }

    public void setsourText(String sour) {
        this.sour = sour;
    }


    public void setdestText(String dest) {
        this.dest = dest;
    }



    public String getnameText() {
        return nameText;
    }

    public String getIdText() {
        return id;
    }

    public String getsourText() {

        return sour;
    }
    public String getdestText() {

        return dest;
    }

}

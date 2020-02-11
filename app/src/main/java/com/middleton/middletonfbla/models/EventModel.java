package com.middleton.middletonfbla.models;

public class EventModel {

    String date,name,address, picture;

    public EventModel(String date, String name, String address, String picture) {
        this.date = date;
        this.name = name;
        this.address = address;
        this.picture = picture;
    }

    public EventModel() {

    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

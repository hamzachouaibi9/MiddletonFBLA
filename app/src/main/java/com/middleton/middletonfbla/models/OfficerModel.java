package com.middleton.middletonfbla.models;

public class OfficerModel {
    String picture;
    String name, position;

    public OfficerModel(String picture, String name, String position) {
        this.picture = picture;
        this.name = name;
        this.position = position;
    }

    public OfficerModel() {

    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

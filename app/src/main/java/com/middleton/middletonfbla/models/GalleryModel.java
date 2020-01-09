package com.middleton.middletonfbla.models;

public class GalleryModel {

    String link, picture;
    String name;

    public GalleryModel(String link, String picture, String name) {
        this.link = link;
        this.picture = picture;
        this.name = name;
    }

    public GalleryModel() {

    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
}

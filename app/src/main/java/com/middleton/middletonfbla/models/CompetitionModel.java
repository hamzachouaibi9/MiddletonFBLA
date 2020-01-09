package com.middleton.middletonfbla.models;

public class CompetitionModel {

    String name, type;
    Boolean ableTo;

    public CompetitionModel(String name, String type, Boolean ableTo) {
        this.name = name;
        this.type = type;
        this.ableTo = ableTo;
    }

    public CompetitionModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAbleTo() {
        return ableTo;
    }

    public void setAbleTo(Boolean ableTo) {
        this.ableTo = ableTo;
    }
}

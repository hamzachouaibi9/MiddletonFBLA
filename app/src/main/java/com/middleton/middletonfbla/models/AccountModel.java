package com.middleton.middletonfbla.models;

public class AccountModel {
    String name;
    long phone;
    int studentID;
    int grade;
    String imageLink;
    double gpa;

    public AccountModel(String name, long phone, int studentID, int grade, String imageLink, double gpa) {
        this.name = name;
        this.phone = phone;
        this.studentID = studentID;
        this.grade = grade;
        this.imageLink = imageLink;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}

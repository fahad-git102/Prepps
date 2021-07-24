package com.fahaddev.prepps.models;

public class StudentFilterModel {
    String name, state, career_interest, gpa, zip_code;

    public StudentFilterModel(String name, String state, String career_interest, String gpa, String zip_code) {
        this.name = name;
        this.state = state;
        this.career_interest = career_interest;
        this.gpa = gpa;
        this.zip_code = zip_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCareer_interest() {
        return career_interest;
    }

    public void setCareer_interest(String career_interest) {
        this.career_interest = career_interest;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}

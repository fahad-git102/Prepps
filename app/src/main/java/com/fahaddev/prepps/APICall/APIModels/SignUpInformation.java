package com.fahaddev.prepps.APICall.APIModels;

public class SignUpInformation {

    String name, email, password, type, city, state, high_school, height,
            weight, position, gpa, hobbies, activity, career_interested;

    public SignUpInformation(String name, String email, String password, String type, String city, String state, String high_school,
                             String height, String weight, String position, String gpa, String hobbies, String activity, String career_interested) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.city = city;
        this.state = state;
        this.high_school = high_school;
        this.height = height;
        this.weight = weight;
        this.position = position;
        this.gpa = gpa;
        this.hobbies = hobbies;
        this.activity = activity;
        this.career_interested = career_interested;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHigh_school() {
        return high_school;
    }

    public void setHigh_school(String high_school) {
        this.high_school = high_school;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getCareer_interested() {
        return career_interested;
    }

    public void setCareer_interested(String career_interested) {
        this.career_interested = career_interested;
    }
}

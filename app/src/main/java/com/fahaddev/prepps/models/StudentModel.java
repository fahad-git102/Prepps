package com.fahaddev.prepps.models;

import com.google.gson.annotations.SerializedName;

public class StudentModel {
    @SerializedName("id")
    private Integer id;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("zip_code")
    private String zipCode;
    @SerializedName("year")
    private String year;
    @SerializedName("subject")
    private String subject;
    @SerializedName("image")
    private String image;
    @SerializedName("ranking")
    private String ranking;
    @SerializedName("student_population")
    private String studentPopulation;
    @SerializedName("award")
    private String award;
    @SerializedName("accreditation")
    private String accreditation;
    @SerializedName("sat_score")
    private String satScore;
    @SerializedName("sport_played")
    private String sportPlayed;
    @SerializedName("name")
    private String name;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;
    @SerializedName("high_school")
    private String highSchool;
    @SerializedName("height")
    private String height;
    @SerializedName("weight")
    private String weight;
    @SerializedName("position")
    private String position;
    @SerializedName("gpa")
    private String gpa;
    @SerializedName("hobbies")
    private String hobbies;
    @SerializedName("activity")
    private String activity;
    @SerializedName("career_interested")
    private String careerInterested;
    @SerializedName("college_address")
    private String collegeAddress;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("user")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getStudentPopulation() {
        return studentPopulation;
    }

    public void setStudentPopulation(String studentPopulation) {
        this.studentPopulation = studentPopulation;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getAccreditation() {
        return accreditation;
    }

    public void setAccreditation(String accreditation) {
        this.accreditation = accreditation;
    }

    public String getSatScore() {
        return satScore;
    }

    public void setSatScore(String satScore) {
        this.satScore = satScore;
    }

    public String getSportPlayed() {
        return sportPlayed;
    }

    public void setSportPlayed(String sportPlayed) {
        this.sportPlayed = sportPlayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getHighSchool() {
        return highSchool;
    }

    public void setHighSchool(String highSchool) {
        this.highSchool = highSchool;
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

    public String getCareerInterested() {
        return careerInterested;
    }

    public void setCareerInterested(String careerInterested) {
        this.careerInterested = careerInterested;
    }

    public String getCollegeAddress() {
        return collegeAddress;
    }

    public void setCollegeAddress(String collegeAddress) {
        this.collegeAddress = collegeAddress;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

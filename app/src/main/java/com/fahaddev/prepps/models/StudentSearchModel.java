package com.fahaddev.prepps.models;

import com.fahaddev.prepps.models.ApiModels.StudentUsers;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentSearchModel {
    @SerializedName("users")
    private StudentUsers users;
    @SerializedName("career_interest")
    private List<String> careerInterest = null;
    @SerializedName("gpas")
    private List<String> gpas = null;
    @SerializedName("zipCodes")
    private List<String> zipCodes = null;

    public StudentUsers getUsers() {
        return users;
    }

    public void setUsers(StudentUsers users) {
        this.users = users;
    }

    public List<String> getCareerInterest() {
        return careerInterest;
    }

    public void setCareerInterest(List<String> careerInterest) {
        this.careerInterest = careerInterest;
    }

    public List<String> getGpas() {
        return gpas;
    }

    public void setGpas(List<String> gpas) {
        this.gpas = gpas;
    }

    public List<String> getZipCodes() {
        return zipCodes;
    }

    public void setZipCodes(List<String> zipCodes) {
        this.zipCodes = zipCodes;
    }
}

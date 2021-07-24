package com.fahaddev.prepps.models;

import com.google.gson.annotations.SerializedName;

public class User {
    private float id;
    private String email;
    private String type;
    private String about_us;
    private float is_verified;
    private String cover;
    private String email_verified_at = null;
    private String created_at;
    private String updated_at;
    @SerializedName("user_detail")
    User_detail User_detailObject;
    private String token;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAbout_us() {
        return about_us;
    }

    public void setAbout_us(String about_us) {
        this.about_us = about_us;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public float getIs_verified() {
        return is_verified;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public User_detail getUser_detail() {
        return User_detailObject;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIs_verified(float is_verified) {
        this.is_verified = is_verified;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setUser_detail(User_detail user_detailObject) {
        this.User_detailObject = user_detailObject;
    }
}

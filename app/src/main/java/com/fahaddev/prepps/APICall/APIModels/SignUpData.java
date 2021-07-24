package com.fahaddev.prepps.APICall.APIModels;

import com.fahaddev.prepps.models.User_detail;

public class SignUpData {
    private float id;
    private String email;
    private String type;
    private float is_verified;
    private String email_verified_at = null;
    private String created_at;
    private String updated_at;
    User_detail User_detailObject;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(float is_verified) {
        this.is_verified = is_verified;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public User_detail getUser_detailObject() {
        return User_detailObject;
    }

    public void setUser_detailObject(User_detail user_detailObject) {
        User_detailObject = user_detailObject;
    }
}

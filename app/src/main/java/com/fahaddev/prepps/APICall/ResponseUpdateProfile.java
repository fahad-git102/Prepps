package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.User_detail;
import com.google.gson.annotations.SerializedName;

public class ResponseUpdateProfile {
    @SerializedName("type")
    private String type;
    @SerializedName("message")
    private String message;
    @SerializedName("user_detail")
    private User_detail user_detail;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User_detail getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(User_detail user_detail) {
        this.user_detail = user_detail;
    }
}

package com.fahaddev.prepps.models.ApiModels;

import com.fahaddev.prepps.models.User;
import com.google.gson.annotations.SerializedName;

public class UserDataResponse {
    @SerializedName("token")
    private String token;
    @SerializedName("user")
    User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

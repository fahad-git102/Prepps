package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.ApiModels.UserDataResponse;
import com.fahaddev.prepps.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFavouriteStudents {
    private float status;
    private String message;
    @SerializedName("data")
    List<User> data;

    public float getStatus() {
        return status;
    }

    public void setStatus(float status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}

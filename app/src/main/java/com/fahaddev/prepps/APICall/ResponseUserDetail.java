package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.User;
import com.google.gson.annotations.SerializedName;

public class ResponseUserDetail {
    public int status;
    public String message;
    @SerializedName("data")
    public User data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}

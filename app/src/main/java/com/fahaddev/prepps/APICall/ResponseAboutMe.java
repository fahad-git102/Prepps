package com.fahaddev.prepps.APICall;

import com.google.gson.annotations.SerializedName;

public class ResponseAboutMe {
    @SerializedName("type")
    private String type;
    @SerializedName("message")
    private String message;

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
}

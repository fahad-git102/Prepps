package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.ApiModels.SentMessage;
import com.google.gson.annotations.SerializedName;

public class ResponseSentMessageCall {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private SentMessage data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SentMessage getData() {
        return data;
    }

    public void setData(SentMessage data) {
        this.data = data;
    }
}

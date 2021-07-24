package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.InboxModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseInboxCall {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<InboxModel> data ;


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

    public List<InboxModel> getData() {
        return data;
    }

    public void setData(List<InboxModel> data) {
        this.data = data;
    }
}

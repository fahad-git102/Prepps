package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.InboxModel;
import com.fahaddev.prepps.models.MessageModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseChatCall {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<MessageModel> data ;

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

    public List<MessageModel> getData() {
        return data;
    }

    public void setData(List<MessageModel> data) {
        this.data = data;
    }
}

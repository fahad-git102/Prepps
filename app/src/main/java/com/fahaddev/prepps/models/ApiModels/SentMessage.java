package com.fahaddev.prepps.models.ApiModels;

import com.fahaddev.prepps.models.MessageModel;
import com.fahaddev.prepps.models.User;
import com.google.gson.annotations.SerializedName;

public class SentMessage {
    @SerializedName("message")
    private MessageModel message;
    @SerializedName("user")
    private User user;

    public MessageModel getMessage() {
        return message;
    }

    public void setMessage(MessageModel message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

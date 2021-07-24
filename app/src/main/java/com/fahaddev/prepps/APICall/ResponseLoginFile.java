package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.ApiModels.UserDataResponse;
import com.google.gson.annotations.SerializedName;

public class ResponseLoginFile {
    private float status;
    private String message;
    @SerializedName("data")
    UserDataResponse DataObject;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float getStatus() {
        return status;
    }

    public void setStatus(float status) {
        this.status = status;
    }

    public UserDataResponse getDataObject() {
        return DataObject;
    }

    public void setDataObject(UserDataResponse dataObject) {
        DataObject = dataObject;
    }
}

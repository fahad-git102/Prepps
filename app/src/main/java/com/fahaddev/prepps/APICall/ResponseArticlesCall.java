package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.ApiModels.ArticlesDataResponse;
import com.google.gson.annotations.SerializedName;

public class ResponseArticlesCall {
    private float status;
    private String message;
    @SerializedName("data")
    ArticlesDataResponse DataObject;

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

    public ArticlesDataResponse getDataObject() {
        return DataObject;
    }

    public void setDataObject(ArticlesDataResponse dataObject) {
        DataObject = dataObject;
    }
}

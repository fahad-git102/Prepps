package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.ApiModels.CollegeNavigatorResponse;
import com.fahaddev.prepps.models.CollegeNavigatorModel;
import com.google.gson.annotations.SerializedName;

public class ResponseCollegeNavigator {
    private float status;
    private String message;
    @SerializedName("data")
    CollegeNavigatorResponse data;

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

    public CollegeNavigatorResponse getData() {
        return data;
    }

    public void setData(CollegeNavigatorResponse data) {
        this.data = data;
    }
}

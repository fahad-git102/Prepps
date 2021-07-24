package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.ApiModels.CollegeNavigatorResponse;
import com.fahaddev.prepps.models.CollegeNavigatorModel;
import com.google.gson.annotations.SerializedName;

public class ResponseCollegeDetail {
    private float status;
    private String message;
    @SerializedName("data")
    CollegeNavigatorModel data;

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

    public CollegeNavigatorModel getData() {
        return data;
    }

    public void setData(CollegeNavigatorModel data) {
        this.data = data;
    }
}

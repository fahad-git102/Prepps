package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.ApiModels.TimelineResponseData;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ResponseTimelineFile {
    public int status;
    public String message;
    @SerializedName("data")
    public TimelineResponseData data;

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

    public TimelineResponseData getData() {
        return data;
    }

    public void setData(TimelineResponseData data) {
        this.data = data;
    }
}

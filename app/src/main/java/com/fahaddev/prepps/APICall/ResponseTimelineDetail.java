package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.ArticlesModel;

public class ResponseTimelineDetail {
    public int status;
    public String message;
    public TimelineModel data;

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

    public TimelineModel getData() {
        return data;
    }

    public void setData(TimelineModel data) {
        this.data = data;
    }
}

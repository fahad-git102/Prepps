package com.fahaddev.prepps.APICall.APIModels;

import com.fahaddev.prepps.models.StudentSearchModel;
import com.google.gson.annotations.SerializedName;

public class ResponseStudentSearch {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private StudentSearchModel data;

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

    public StudentSearchModel getData() {
        return data;
    }

    public void setData(StudentSearchModel data) {
        this.data = data;
    }
}

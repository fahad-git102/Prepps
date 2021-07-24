package com.fahaddev.prepps.APICall.APIModels;

public class ResponseCollegeSignUpInfo {
    private float status;
    private String message;
    SignUpData DataObject;

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

    public SignUpData getDataObject() {
        return DataObject;
    }

    public void setDataObject(SignUpData dataObject) {
        DataObject = dataObject;
    }
}

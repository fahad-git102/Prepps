package com.fahaddev.prepps.APICall;

import com.google.gson.annotations.SerializedName;

public class ResponseUploadCover {
    @SerializedName("type")
    private String type;
    @SerializedName("filename")
    private String filename;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

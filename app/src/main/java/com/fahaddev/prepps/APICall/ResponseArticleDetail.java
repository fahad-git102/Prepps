package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.models.ArticlesModel;

public class ResponseArticleDetail {
    public int status;
    public String message;
    public ArticlesModel data;

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

    public ArticlesModel getData() {
        return data;
    }

    public void setData(ArticlesModel data) {
        this.data = data;
    }
}

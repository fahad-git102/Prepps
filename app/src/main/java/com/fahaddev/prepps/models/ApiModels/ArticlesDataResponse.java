package com.fahaddev.prepps.models.ApiModels;

import com.fahaddev.prepps.models.ArticlesModel;

import java.util.List;

public class ArticlesDataResponse {
    private float current_page;
    List<ArticlesModel> data ;
    private String first_page_url;
    private float from;
    private float last_page;
    private String last_page_url;
    private String next_page_url = null;
    private String path;
    private float per_page;
    private String prev_page_url = null;
    private float to;
    private float total;

    public float getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(float current_page) {
        this.current_page = current_page;
    }

    public List<ArticlesModel> getData() {
        return data;
    }

    public void setData(List<ArticlesModel> data) {
        this.data = data;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public float getFrom() {
        return from;
    }

    public void setFrom(float from) {
        this.from = from;
    }

    public float getLast_page() {
        return last_page;
    }

    public void setLast_page(float last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public float getPer_page() {
        return per_page;
    }

    public void setPer_page(float per_page) {
        this.per_page = per_page;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public float getTo() {
        return to;
    }

    public void setTo(float to) {
        this.to = to;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}

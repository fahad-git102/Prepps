package com.fahaddev.prepps.models.ApiModels;

import com.fahaddev.prepps.models.CollegeNavigatorModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollegeNavigatorResponse {
    @SerializedName("current_page")
    private Integer currentPage;
    @SerializedName("data")
    private List<CollegeNavigatorModel> data = null;
    @SerializedName("first_page_url")
    private String firstPageUrl;
    @SerializedName("from")
    private Integer from;
    @SerializedName("last_page")
    private Integer lastPage;
    @SerializedName("last_page_url")
    private String lastPageUrl;
    @SerializedName("next_page_url")
    private String nextPageUrl;
    @SerializedName("path")
    private String path;
    @SerializedName("per_page")
    private Integer perPage;
    @SerializedName("prev_page_url")
    private String prevPageUrl;
    @SerializedName("to")
    private Integer to;
    @SerializedName("total")
    private Integer total;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<CollegeNavigatorModel> getData() {
        return data;
    }

    public void setData(List<CollegeNavigatorModel> data) {
        this.data = data;
    }

    public String getFirstPageUrl() {
        return firstPageUrl;
    }

    public void setFirstPageUrl(String firstPageUrl) {
        this.firstPageUrl = firstPageUrl;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public String getLastPageUrl() {
        return lastPageUrl;
    }

    public void setLastPageUrl(String lastPageUrl) {
        this.lastPageUrl = lastPageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public void setPrevPageUrl(String prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

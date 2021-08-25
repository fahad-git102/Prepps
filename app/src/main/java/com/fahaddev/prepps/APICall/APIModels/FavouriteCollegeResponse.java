package com.fahaddev.prepps.APICall.APIModels;

import com.fahaddev.prepps.models.CollegeNavigatorModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavouriteCollegeResponse {
    @SerializedName("type")
    String type;
    @SerializedName("data")
    List<CollegeNavigatorModel> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CollegeNavigatorModel> getData() {
        return data;
    }

    public void setData(List<CollegeNavigatorModel> data) {
        this.data = data;
    }
}

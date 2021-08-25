package com.fahaddev.prepps.helpers;

import android.app.Application;

import com.fahaddev.prepps.APICall.APIModels.FavouriteCollegeResponse;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.models.CollegeNavigatorModel;
import com.fahaddev.prepps.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Prepps extends Application {

    public static List<User> allUsersList;
    public static List<String> statesList;
    public static List<CollegeNavigatorModel> favouriteColleges;

    @Override
    public void onCreate() {
        super.onCreate();
        allUsersList = new ArrayList<>();
        statesList = new ArrayList<>();
        statesList.add("Alabama");
        statesList.add("Alaska");
        statesList.add("Arizona");
        statesList.add("Arkansas");
        statesList.add("California");
        statesList.add("Colorado");
        statesList.add("Connecticut");
        statesList.add("Delaware");
        statesList.add("Florida");
        statesList.add("Georgia");
        statesList.add("Hawaii");
        statesList.add("Idaho");
        statesList.add("Illinois");
        statesList.add("Indiana");
        statesList.add("Iowa");
        statesList.add("Kansas");
        statesList.add("Kentucky");
        statesList.add("Louisiana");
        statesList.add("Maine");
        statesList.add("Maryland");
        statesList.add("Massachusetts");
        statesList.add("Michigan");
        statesList.add("Minnesota");
        statesList.add("Mississippi");
        statesList.add("Missouri");
        statesList.add("Montana");
        statesList.add("Nebraska");
        statesList.add("Nevada");
        statesList.add("New Hampshire");
        statesList.add("New Jersey");
        statesList.add("New Mexico");
        statesList.add("New York");
        statesList.add("North Carolina");
        statesList.add("North Dakota");
        statesList.add("Ohio");
        statesList.add("Oklahoma");
        statesList.add("Oregon");
        statesList.add("Pennsylvania");
        statesList.add("Rhode Island");
        statesList.add("South Carolina");
        statesList.add("South Dakota");
        statesList.add("Tennessee");
        statesList.add("Texas");
        statesList.add("Utah");
        statesList.add("Vermont");
        statesList.add("Virginia");
        statesList.add("Washington");
        statesList.add("West Virginia");
        statesList.add("Wisconsin");
        statesList.add("Wyoming");
        getFavoriteColleges();
    }

    private void getFavoriteColleges(){
        favouriteColleges = new ArrayList<>();
        if (StaticClass.currentUser!=null) {
            Call<FavouriteCollegeResponse> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).getFavouriteColleges(StaticClass.currentUser.getToken());
            call.enqueue(new Callback<FavouriteCollegeResponse>() {
                @Override
                public void onResponse(Call<FavouriteCollegeResponse> call, Response<FavouriteCollegeResponse> response) {
                    if (response.isSuccessful()){
                        favouriteColleges = response.body().getData();
                    }
                }

                @Override
                public void onFailure(Call<FavouriteCollegeResponse> call, Throwable t) {

                }
            });
        }
    }

}

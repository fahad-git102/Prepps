package com.fahaddev.prepps.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chootdev.recycleclick.RecycleClick;
import com.fahaddev.prepps.APICall.APIModels.FavouriteCollegeResponse;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.college.FavouriteStudentsActivity;
import com.fahaddev.prepps.adapters.CollegeAdapter;
import com.fahaddev.prepps.helpers.Prepps;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.CollegeNavigatorModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteCollegesActivity extends AppCompatActivity {

    RecyclerView recyclerFavourites;
    List<CollegeNavigatorModel> collegeNavigatorModelList;
    TextView tvNoData;
    ImageButton goBack;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_colleges);
        recyclerFavourites = findViewById(R.id.recycler_favourites);
        recyclerFavourites.setLayoutManager(new LinearLayoutManager(this));
        tvNoData = findViewById(R.id.noData);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        progressBar = findViewById(R.id.progress);
        collegeNavigatorModelList = new ArrayList<>();
        getFavourites();
    }
    private void getFavourites(){
        progressBar.setVisibility(View.VISIBLE);
        if (StaticClass.currentUser!=null){
            Call<FavouriteCollegeResponse> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).getFavouriteColleges(StaticClass.currentUser.getToken());
            call.enqueue(new Callback<FavouriteCollegeResponse>() {
                @Override
                public void onResponse(Call<FavouriteCollegeResponse> call, Response<FavouriteCollegeResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()){
                        collegeNavigatorModelList = response.body().getData();
                        Prepps.favouriteColleges.clear();
                        Prepps.favouriteColleges = response.body().getData();
                        if (collegeNavigatorModelList.size()>0){
                            tvNoData.setVisibility(View.GONE);
                            CollegeAdapter adapter = new CollegeAdapter(collegeNavigatorModelList, FavouriteCollegesActivity.this);
                            recyclerFavourites.setAdapter(adapter);
                            RecycleClick.addTo(recyclerFavourites).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                                @Override
                                public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(FavouriteCollegesActivity.this);
                                    builder.setTitle("Remove from favorites ?");
                                    builder.setMessage("Are you sure you want to remove "+collegeNavigatorModelList.get(i).getName()+ " from your favorite List ?");
                                    builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            });
                        }else {
                            tvNoData.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<FavouriteCollegeResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(FavouriteCollegesActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
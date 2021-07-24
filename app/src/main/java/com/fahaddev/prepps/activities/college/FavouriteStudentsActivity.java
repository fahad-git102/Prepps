package com.fahaddev.prepps.activities.college;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fahaddev.prepps.APICall.ResponseFavouriteStudents;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.adapters.FavouriteStudentsAdapter;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteStudentsActivity extends AppCompatActivity implements View.OnClickListener{
    List<User> userList;
    RecyclerView recyclerFavStudents;
    TextView noData;
    ImageButton goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_students);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(this);
        userList = new ArrayList<>();
        noData = findViewById(R.id.noData);
        recyclerFavStudents = findViewById(R.id.recycler_students);
        recyclerFavStudents.setLayoutManager(new LinearLayoutManager(this));
        getFavStudents();
    }
    private void getFavStudents(){
        Call<ResponseFavouriteStudents> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).getFavouriteStudents("Bearer "+StaticClass.currentUser.getToken());
        call.enqueue(new Callback<ResponseFavouriteStudents>() {
            @Override
            public void onResponse(Call<ResponseFavouriteStudents> call, Response<ResponseFavouriteStudents> response) {
                if (response.isSuccessful()){
                    userList = response.body().getData();
                    if (userList!=null){
                        if (userList.size()==0){
                            recyclerFavStudents.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                        }else {
                            recyclerFavStudents.setVisibility(View.VISIBLE);
                            noData.setVisibility(View.GONE);
                            FavouriteStudentsAdapter adapter = new FavouriteStudentsAdapter(userList, FavouriteStudentsActivity.this);
                            recyclerFavStudents.setAdapter(adapter);
                        }
                    }else {
                        recyclerFavStudents.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseFavouriteStudents> call, Throwable t) {
                Toast.makeText(FavouriteStudentsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goBack:
                finish();
                break;
        }
    }
}
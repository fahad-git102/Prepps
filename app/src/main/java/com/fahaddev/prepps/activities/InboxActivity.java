package com.fahaddev.prepps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.chootdev.recycleclick.RecycleClick;
import com.fahaddev.prepps.APICall.ResponseInboxCall;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.adapters.AdapterInbox;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.InboxModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton goBack;
    RecyclerView recyclerInbox;
    AdapterInbox adapter;
    List<InboxModel> inboxModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(this);
        recyclerInbox = findViewById(R.id.recyceler_inbox);
        inboxModelList = new ArrayList<>();
        recyclerInbox.setLayoutManager(new LinearLayoutManager(this));
        recyclerInbox.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        getInboxList();
    }

    private void getInboxList(){
        if (StaticClass.currentUser!=null){
            Call<ResponseInboxCall> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL)
                    .getInboxList("Bearer "+StaticClass.currentUser.getToken());
            call.enqueue(new Callback<ResponseInboxCall>() {
                @Override
                public void onResponse(Call<ResponseInboxCall> call, Response<ResponseInboxCall> response) {
                    if (response.isSuccessful()){
                        inboxModelList = response.body().getData();
                        adapter = new AdapterInbox(inboxModelList, InboxActivity.this);
                        recyclerInbox.setAdapter(adapter);
                        RecycleClick.addTo(recyclerInbox).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                                Intent intent = new Intent(InboxActivity.this, ChatActivity.class);
                                intent.putExtra("inbox", inboxModelList.get(i));
                                intent.putExtra("message", inboxModelList.get(i).getMessage());
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<ResponseInboxCall> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.goBack){
            finish();
        }
    }
}
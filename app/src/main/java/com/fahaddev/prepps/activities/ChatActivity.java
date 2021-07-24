package com.fahaddev.prepps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fahaddev.prepps.APICall.APIModels.SendMessageInformation;
import com.fahaddev.prepps.APICall.ResponseChatCall;
import com.fahaddev.prepps.APICall.ResponseSentMessageCall;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.adapters.ChatAdapter;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.InboxModel;
import com.fahaddev.prepps.models.MessageModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    List<MessageModel> messageModelList;
    RecyclerView recyclerChat;
    ChatAdapter adapter;
    ImageButton goBack, btnSend;
    InboxModel inbox;
    EditText etTypeHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerChat = findViewById(R.id.recycler_chat);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(this);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        messageModelList = new ArrayList<>();
        etTypeHere = findViewById(R.id.etTypeHere);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerChat.setLayoutManager(linearLayoutManager);
        Intent intent = getIntent();
        inbox = intent.getParcelableExtra("inbox");
        MessageModel messageModel = intent.getParcelableExtra("message");
        if (inbox!=null){
            inbox.setMessage(messageModel);
            getChat(inbox.getId());
            final Handler ha=new Handler();
            ha.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //call function
                    getChat(inbox.getId());
                    ha.postDelayed(this, 10000);
                }
            }, 10000);
        }
    }

    private void getChat(float id){
        messageModelList.clear();
        Call<ResponseChatCall> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL)
                .getChat("Bearer "+StaticClass.currentUser.getToken(), id);
        call.enqueue(new Callback<ResponseChatCall>() {
            @Override
            public void onResponse(Call<ResponseChatCall> call, Response<ResponseChatCall> response) {
                if (response.isSuccessful()){
                    messageModelList = response.body().getData();
                        adapter = new ChatAdapter(messageModelList, ChatActivity.this);
                        recyclerChat.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseChatCall> call, Throwable t) {
                Toast.makeText(ChatActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.goBack){
            finish();
        }else if (view.getId()==R.id.btnSend){
            String text = etTypeHere.getText().toString();
            if (text.isEmpty()){
                return;
            }
            if (inbox.getMessage()!=null){
                sendMessage(inbox.getMessage().getSession_id(), inbox.getMessage().getReceive_id(), inbox.getId(), text);
            } else {
                inbox.setMessage(messageModelList.get(0));
                sendMessage(inbox.getMessage().getSession_id(), inbox.getMessage().getReceive_id(), inbox.getId(), text);
            }
        }
    }

    private void sendMessage(int sessionId, int receiveId, float id, String message){
        SendMessageInformation sendMessageInformation = new SendMessageInformation(sessionId, receiveId, message);
        Call<ResponseSentMessageCall> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL)
                .sendMessage("Bearer "+StaticClass.currentUser.getToken(), sendMessageInformation);
        call.enqueue(new Callback<ResponseSentMessageCall>() {
            @Override
            public void onResponse(Call<ResponseSentMessageCall> call, Response<ResponseSentMessageCall> response) {
                if (response.isSuccessful()){
                    etTypeHere.setText("");
                    getChat(id);
                }
            }

            @Override
            public void onFailure(Call<ResponseSentMessageCall> call, Throwable t) {
                Toast.makeText(ChatActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
package com.fahaddev.prepps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.MessageModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    List<MessageModel> messageModelList;
    Context context;

    public ChatAdapter(List<MessageModel> messageModelList, Context context) {
        this.messageModelList = messageModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        if (messageModelList.get(position).getUser_id()== StaticClass.currentUser.getId()){
            holder.linearCurrent.setVisibility(View.VISIBLE);
            holder.linearOther.setVisibility(View.GONE);
            holder.currentUserMsg.setText(messageModelList.get(position).getMessage());
            String date = StaticClass.getFormattedDate(messageModelList.get(position).getCreated_at(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    "dd MMM hh:mm a");
            holder.currentUserDate.setText(date);
        }else {
            holder.linearOther.setVisibility(View.VISIBLE);
            holder.linearCurrent.setVisibility(View.GONE);
            holder.otherUserMsg.setText(messageModelList.get(position).getMessage());
            String date = StaticClass.getFormattedDate(messageModelList.get(position).getCreated_at(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    "dd MMM hh:mm a");
            holder.otherUserDate.setText(date);
        }
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder{
        LinearLayout linearCurrent, linearOther;
        TextView otherUserMsg, otherUserDate, currentUserMsg, currentUserDate;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            linearCurrent = itemView.findViewById(R.id.linearCurrent);
            linearOther = itemView.findViewById(R.id.linearOther);
            otherUserMsg = itemView.findViewById(R.id.otherUserMsg);
            otherUserDate = itemView.findViewById(R.id.otherUserDate);
            currentUserMsg = itemView.findViewById(R.id.currentUserMsg);
            currentUserDate = itemView.findViewById(R.id.currentUserDate);
        }
    }
}

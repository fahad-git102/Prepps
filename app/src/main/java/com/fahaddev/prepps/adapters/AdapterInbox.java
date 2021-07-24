package com.fahaddev.prepps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.InboxModel;
import com.fahaddev.prepps.models.MessageModel;

import java.util.List;

public class AdapterInbox extends RecyclerView.Adapter<AdapterInbox.InboxHolder> {

    List<InboxModel> inboxModelList;
    Context context;

    public AdapterInbox(List<InboxModel> inboxModelList, Context context) {
        this.inboxModelList = inboxModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public InboxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_item, parent, false);
        return new InboxHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxHolder holder, int position) {
        MessageModel messageModel = inboxModelList.get(position).getMessage();
        if (messageModel!=null){
            holder.tvLastMessage.setText(messageModel.getMessage());
            String date = StaticClass.getFormattedDate(messageModel.getCreated_at(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    "dd MMM hh:mm a");
            holder.tvTime.setText(date);
        }else {
            holder.tvLastMessage.setText("New Message");
            holder.tvTime.setText("Not Found");
        }

        holder.tvUserName.setText(inboxModelList.get(position).getUserDetail().getName());
    }

    @Override
    public int getItemCount() {
        return inboxModelList.size();
    }

    public class InboxHolder extends RecyclerView.ViewHolder{

        TextView tvUserName, tvLastMessage, tvTime;

        public InboxHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.user_name);
            tvLastMessage = itemView.findViewById(R.id.last_message);
            tvTime = itemView.findViewById(R.id.last_msg_time);
        }
    }
}

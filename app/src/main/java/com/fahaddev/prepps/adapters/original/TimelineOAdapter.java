package com.fahaddev.prepps.adapters.original;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fahaddev.prepps.APICall.TimelineModel;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.helpers.StaticClass;
import com.squareup.picasso.Picasso;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineOAdapter extends RecyclerView.Adapter<TimelineOAdapter.TimelineHolder> {

    List<TimelineModel> timelineModelList;
    Context context;

    public TimelineOAdapter(List<TimelineModel> timelineModelList, Context context) {
        this.timelineModelList = timelineModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public TimelineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.timeline_items, parent, false);
        return new TimelineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineHolder holder, int position) {
        if (timelineModelList.get(position).getImage()!=null){
            Picasso.with(context).load(timelineModelList.get(position).getImage())
                    .placeholder(R.drawable.default_thumbnail).fit().into(holder.image);
        }else {
            Glide.with(context).load(timelineModelList.get(position).getVideo_link())
                    .placeholder(R.drawable.default_thumbnail).into(holder.image);
        }

        holder.tvUserName.setText(timelineModelList.get(position).getUser_detail().getName());
        holder.tvDesc.setText(timelineModelList.get(position).getTitle());
        String date = StaticClass.getFormattedDate(timelineModelList.get(position).getCreated_at(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "dd MMM, yyyy hh:mm a");
        if (date!=null){
            holder.tvDate.setText(date);
        }
    }

    @Override
    public int getItemCount() {
        return timelineModelList.size();
    }

    public class TimelineHolder extends RecyclerView.ViewHolder{

        CircleImageView profileImage;
        TextView tvUserName, tvDate, tvDesc;
        ImageView image;

        public TimelineHolder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvDate = itemView.findViewById(R.id.tvDate);
            profileImage = itemView.findViewById(R.id.profilePic);
            image = itemView.findViewById(R.id.post_image);
        }
    }
}

package com.fahaddev.prepps.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.ChatActivity;
import com.fahaddev.prepps.activities.ProfileActivity;
import com.fahaddev.prepps.models.StudentModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentsSearchAdapter extends RecyclerView.Adapter<StudentsSearchAdapter.StudentHolder> {

    List<StudentModel> studentModels;
    Context context;

    public StudentsSearchAdapter(List<StudentModel> studentModels, Context context) {
        this.studentModels = studentModels;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_items, parent, false);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        if (studentModels.get(position).getImage()!=null){
            Picasso.with(context).load(studentModels.get(position).getImage()).placeholder(R.drawable.default_user_image).into(holder.profilePic);
        }else {
            holder.profilePic.setImageDrawable(context.getResources().getDrawable(R.drawable.default_user_image));
        }
        holder.studentName.setText(studentModels.get(position).getName());
        holder.highSchool.setText(studentModels.get(position).getHighSchool());
        holder.address.setText(studentModels.get(position).getCity()+", "+ studentModels.get(position).getState());
        holder.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });
        holder.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);
                int userId = studentModels.get(position).getId();
                intent.putExtra("userId", String.valueOf(userId));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentModels.size();
    }

    public class StudentHolder extends RecyclerView.ViewHolder{
        CircleImageView profilePic;
        TextView studentName, highSchool, address;
        ImageButton btnChat;
        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            studentName = itemView.findViewById(R.id.studentName);
            highSchool = itemView.findViewById(R.id.highSchool);
            address = itemView.findViewById(R.id.address);
            btnChat = itemView.findViewById(R.id.btnChat);
        }
    }
}

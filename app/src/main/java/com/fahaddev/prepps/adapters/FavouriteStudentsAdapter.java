package com.fahaddev.prepps.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahaddev.prepps.APICall.APIModels.ResponseCheckSuccess;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteStudentsAdapter extends RecyclerView.Adapter<FavouriteStudentsAdapter.FavHolder> {

    List<User> userList;
    Context context;

    public FavouriteStudentsAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_items, parent, false);
        return new FavHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavHolder holder, int position) {
//        if (userList.get(position).getImage()!=null){
//            Picasso.with(context).load(studentModels.get(position).getImage()).placeholder(R.drawable.default_user_image).into(holder.profilePic);
//        }else {
//            holder.profilePic.setImageDrawable(context.getResources().getDrawable(R.drawable.default_user_image));
//        }
        holder.btnRemove.setVisibility(View.VISIBLE);
        holder.studentName.setText(userList.get(position).getUser_detail().getName());
        holder.highSchool.setText(userList.get(position).getUser_detail().getHigh_school());
        holder.address.setText(userList.get(position).getUser_detail().getCity()+", "+ userList.get(position).getUser_detail().getState());
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Remove "+userList.get(position).getUser_detail().getName());
                builder.setMessage("Are you sure you want to remove "+userList.get(position).getUser_detail().getName()
                        + " from your favorite list ?");
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        removeUser(position);
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
    }

    private void removeUser(int position){
        Call<ResponseCheckSuccess> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).removeFavourite("Bearer "+ StaticClass.currentUser.getToken(), userList.get(position).getId());
        call.enqueue(new Callback<ResponseCheckSuccess>() {
            @Override
            public void onResponse(Call<ResponseCheckSuccess> call, Response<ResponseCheckSuccess> response) {
                if (response.isSuccessful()){
                    if (response.body().getMessage().equals("success")){
                        userList.remove(position);
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckSuccess> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class FavHolder extends RecyclerView.ViewHolder{
        CircleImageView profilePic;
        TextView studentName, highSchool, address;
        ImageButton btnRemove;
        public FavHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            studentName = itemView.findViewById(R.id.studentName);
            highSchool = itemView.findViewById(R.id.highSchool);
            address = itemView.findViewById(R.id.address);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}

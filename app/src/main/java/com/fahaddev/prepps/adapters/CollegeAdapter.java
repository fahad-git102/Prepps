package com.fahaddev.prepps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.helpers.Prepps;
import com.fahaddev.prepps.models.CollegeNavigatorModel;

import java.util.List;

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.CollegeVHolder> {

    List<CollegeNavigatorModel> collegeNavigatorModelList;
    Context context;

    public CollegeAdapter(List<CollegeNavigatorModel> collegeNavigatorModelList, Context context) {
        this.collegeNavigatorModelList = collegeNavigatorModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CollegeVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.college_navigator_item, parent, false);
        return new CollegeVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollegeVHolder holder, int position) {
        if (collegeNavigatorModelList.size()>0){
            holder.tvCollegeName.setText(collegeNavigatorModelList.get(position).getName());
            holder.tvLocation.setText(collegeNavigatorModelList.get(position).getState());
            holder.tvGradRatio.setText(collegeNavigatorModelList.get(position).getGrad_ratio());
            boolean collegeMatched = false;
            if (Prepps.favouriteColleges.size()>0){
                for (int i = 0; i< Prepps.favouriteColleges.size(); i++){
                    if (Prepps.favouriteColleges.get(i).getId()==collegeNavigatorModelList.get(position).getId()){
                        collegeMatched = true;
                    }
                }
                if (collegeMatched){
                    holder.btnStar.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favourite__1_));
                }else {
                    holder.btnStar.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favourite));
                }
            }

            if (collegeNavigatorModelList.get(position).getSize()!=null){
                holder.linearSize.setVisibility(View.VISIBLE);
                holder.tvSize.setText(collegeNavigatorModelList.get(position).getSize());
            }else {
                holder.linearSize.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return collegeNavigatorModelList.size();
    }

    public class CollegeVHolder extends RecyclerView.ViewHolder{

        TextView tvCollegeName, tvLocation, tvGradRatio, tvSize;
        LinearLayout linearSize;
                ImageButton btnStar;

        public CollegeVHolder(@NonNull View itemView) {
            super(itemView);
            tvCollegeName = itemView.findViewById(R.id.collegeName);
            tvLocation = itemView.findViewById(R.id.location);
            tvGradRatio = itemView.findViewById(R.id.grad_ratio);
            tvSize = itemView.findViewById(R.id.size);
            btnStar = itemView.findViewById(R.id.btnStar);
            linearSize = itemView.findViewById(R.id.linear_size);
        }
    }
}

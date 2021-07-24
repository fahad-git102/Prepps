package com.fahaddev.prepps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahaddev.prepps.R;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobsVHolder> {

    @NonNull
    @Override
    public JobsVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_items, parent, false);
        return new JobsVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobsVHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class JobsVHolder extends RecyclerView.ViewHolder{

        public JobsVHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

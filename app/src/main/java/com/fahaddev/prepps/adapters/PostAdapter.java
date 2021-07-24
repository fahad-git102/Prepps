package com.fahaddev.prepps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahaddev.prepps.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostVHolder> {
    @NonNull
    @Override
    public PostVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_items, parent, false);
        return new PostVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostVHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class PostVHolder extends RecyclerView.ViewHolder{

        public PostVHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

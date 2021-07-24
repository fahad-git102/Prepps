package com.fahaddev.prepps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahaddev.prepps.R;

import java.util.List;

public class SingleStringSelectAdapter extends RecyclerView.Adapter<SingleStringSelectAdapter.SSHolder> {

    List<String> stringList;
    Context context;

    public SingleStringSelectAdapter(List<String> stringList, Context context) {
        this.stringList = stringList;
        this.context = context;
    }

    @NonNull
    @Override
    public SSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SSHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_string_selector_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SSHolder holder, int position) {
        holder.tvProgram.setText(stringList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class SSHolder extends RecyclerView.ViewHolder{
        TextView tvProgram;
        public SSHolder(@NonNull View itemView) {
            super(itemView);
            tvProgram = itemView.findViewById(R.id.tvProgram);
        }
    }
}

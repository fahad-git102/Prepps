package com.fahaddev.prepps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.models.ProgramsModel;

import java.util.List;

public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.PHolder> {

    List<ProgramsModel> programsModelList;
    Context context;

    public ProgramsAdapter(List<ProgramsModel> programsModelList, Context context) {
        this.programsModelList = programsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.programs_item, parent, false);
        return new PHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PHolder holder, int position) {
        int aa = position+1;
        holder.tvProgram.setText(aa+ "-  " + programsModelList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return programsModelList.size();
    }

    public class PHolder extends RecyclerView.ViewHolder{
        TextView tvProgram;
        public PHolder(@NonNull View itemView) {
            super(itemView);
            tvProgram = itemView.findViewById(R.id.tvProgram);
        }
    }
}

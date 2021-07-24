package com.fahaddev.prepps.adapters;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.student.StudentSignUpActivity;
import com.fahaddev.prepps.activities.student.StudentsHomeActivity;

import java.util.ArrayList;
import java.util.List;

public class SportsSelectAdapter extends RecyclerView.Adapter<SportsSelectAdapter.SportsSelectVHolder> {

    List<String> stringList;
    Context context;
    Activity activity;

    public SportsSelectAdapter(List<String> stringList, Context context, Activity activity) {
        this.stringList = stringList;
        this.context = context;
        this.activity = activity;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public SportsSelectVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_select_item, parent, false);
        return new SportsSelectVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsSelectVHolder holder, int position) {
        holder.checkBox.setText(stringList.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    StudentSignUpActivity.selectedSports.add(compoundButton.getText().toString());
                }else {
                    holder.checkBox.setChecked(false);
                    StudentSignUpActivity.selectedSports.remove(compoundButton.getText().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class SportsSelectVHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;

        public SportsSelectVHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}

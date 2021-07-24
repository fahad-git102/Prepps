package com.fahaddev.prepps.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.tv.TvContract;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.fahaddev.prepps.R;
import com.fahaddev.prepps.adapters.ProgramsAdapter;
import com.fahaddev.prepps.models.ProgramsModel;

import java.util.List;

public class ProgramsCollegeActivity extends AppCompatActivity {

    List<ProgramsModel> programsModelList;
    RecyclerView recycler_programs;
    ImageButton goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs_college);
        recycler_programs = findViewById(R.id.recycler_programs);
        programsModelList = getIntent().getParcelableArrayListExtra("programs");
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recycler_programs.setLayoutManager(new LinearLayoutManager(this));
        recycler_programs.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ProgramsAdapter adapter = new ProgramsAdapter(programsModelList, this);
        recycler_programs.setAdapter(adapter);
    }
}